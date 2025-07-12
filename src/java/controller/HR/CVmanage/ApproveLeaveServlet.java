package controller.HR.CVmanage;

import DAO.AdminDAO;
import DAO.FormSubmissionDAO;
import DAO.HRDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;
import utilities.EmailSender;

@WebServlet(name = "ApproveLeaveServlet", urlPatterns = {"/ApproveLeaveServlet"})
public class ApproveLeaveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String submissionId_raw = request.getParameter("submissionId");
        String userId_raw = request.getParameter("userId");

        try {
            int submissionId = Integer.parseInt(submissionId_raw);
            int userId = Integer.parseInt(userId_raw);

            // 1. Cập nhật trạng thái leave request thành "Approved"
            HRDAO hrd = new HRDAO();
            hrd.updateLeaveRequestStatus(submissionId, "approved");

            // 2. Lấy thông tin người dùng
            User user = hrd.getUserbyId(userId);

            // 3. Gửi email thông báo
            String subject = "Your Leave Request Has Been Approved – PFTU-SWD391";

            String content = """
                Dear %s,
                
                We are pleased to inform you that your leave request has been APPROVED.
                
                You may proceed with your planned leave as requested.
                
                If you have any questions or require adjustments, feel free to contact HR.

                Best regards,
                HR Department – PFTU, SWD391
                """.formatted(user.getFullName());

            EmailSender.sendNotificationEmail(user.getEmail(), subject, content);

            // 4. Ghi log hành động
            HttpSession session = request.getSession();
            User admin = (User) session.getAttribute("acc");  // Lấy admin hiện tại từ session
            AdminDAO admindao = new AdminDAO();
            String action = "HR approved leave request ID: " + submissionId_raw;
            admindao.logAction(admin.getUserId(), action);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. Redirect về trang danh sách yêu cầu nghỉ
        response.sendRedirect("leavelist");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        FormSubmissionDAO formDAO = new FormSubmissionDAO();
        formDAO.approveSubmission(submissionId);
        response.sendRedirect("EmployeeLeaveRequestServlet");
    }
}
