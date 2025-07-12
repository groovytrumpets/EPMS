package controller.Employee;

import DAO.FormSubmissionDAO;
import DAO.HRDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.FormSubmission;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import model.User;

@WebServlet(name = "EmployeeLeaveRequestServlet", urlPatterns = {"/EmployeeLeaveRequestServlet"})
@MultipartConfig(maxFileSize = 2 * 1024 * 1024) // 2MB
public class EmployeeLeaveRequestServlet extends HttpServlet {

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        String type = request.getParameter("type"); // "LeaveRequest"
        String purpose = request.getParameter("purpose");
        String note = request.getParameter("note");
        Part filePart = request.getPart("fileLink"); // tên input trong JSP

        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("acc") : null;

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = currentUser.getUserId();

        // Validate file
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("error", "Vui lòng chọn tệp đính kèm.");
            request.getRequestDispatcher("employeeLeaveRequest.jsp").forward(request, response);
            return;
        }

        String fileName = filePart.getSubmittedFileName();
        String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        if (!(fileExt.equals("pdf") || fileExt.equals("doc") || fileExt.equals("docx"))) {
            request.setAttribute("error", "Chỉ chấp nhận file PDF, DOC hoặc DOCX!");
            request.getRequestDispatcher("employeeLeaveRequest.jsp").forward(request, response);
            return;
        }

        if (filePart.getSize() > 2 * 1024 * 1024) {
            request.setAttribute("error", "File vượt quá dung lượng 2MB!");
            request.getRequestDispatcher("employeeLeaveRequest.jsp").forward(request, response);
            return;
        }

        // Lưu file
        String uploadPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String sanitizedFileName = fileName.replaceAll("\\s+", "_");
        String saveFileName = userId + "_leave_" + System.currentTimeMillis() + "_" + sanitizedFileName;
        String filePath = uploadPath + File.separator + saveFileName;
        filePart.write(filePath);

        // Tạo form submission
        FormSubmission form = new FormSubmission();
        form.setUserId(userId);
        form.setType(type); // "LeaveRequest"
        form.setPurpose(purpose);
        form.setStatus("pending");
        form.setNote(note);
        form.setFileLink("uploads/" + saveFileName);
        form.setCreateDate(LocalDateTime.now());

        try {
            FormSubmissionDAO formDAO = new FormSubmissionDAO();
            formDAO.insertFormSubmission(form);
            request.setAttribute("success", "Đơn xin nghỉ của bạn đã được gửi thành công. Vui lòng chờ HR duyệt.");
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi lưu vào cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Không thể gửi đơn nghỉ do lỗi hệ thống. Vui lòng thử lại sau.");
        }

        request.getRequestDispatcher("employeeLeaveRequest.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        request.getRequestDispatcher("employeeLeaveRequest.jsp").forward(request, response);
    }

}