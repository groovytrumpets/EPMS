package controller.Employee;

import DAO.FormSubmissionDAO;
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

@WebServlet(name = "EmployeeLeaveRequestServlet", urlPatterns = {"/EmployeeLeaveRequestServlet"})
@MultipartConfig(maxFileSize = 2 * 1024 * 1024) // 2MB
public class EmployeeLeaveRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String reason = request.getParameter("reason");
        Part leaveFilePart = request.getPart("leaveFile");

        // Lấy userId từ session
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Kiểm tra định dạng file
        String fileName = leaveFilePart.getSubmittedFileName();
        String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        if (!(fileExt.equals("pdf") || fileExt.equals("doc") || fileExt.equals("docx"))) {
            request.setAttribute("error", "Chỉ chấp nhận file PDF, DOC, DOCX!");
            request.getRequestDispatcher("employeeLeaveRequest.jsp").forward(request, response);
            return;
        }
        // Kiểm tra dung lượng file
        if (leaveFilePart.getSize() > 2 * 1024 * 1024) {
            request.setAttribute("error", "File vượt quá dung lượng 2MB!");
            request.getRequestDispatcher("employeeLeaveRequest.jsp").forward(request, response);
            return;
        }

        // Lưu file vào thư mục uploads
        String uploadPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        String saveFileName = userId + "_leave_" + System.currentTimeMillis() + "_" + fileName;
        String filePath = uploadPath + File.separator + saveFileName;
        leaveFilePart.write(filePath);

        // Lưu vào FormSubmission
        FormSubmission form = new FormSubmission();
        form.setUserId(userId);
        form.setType("Đơn xin nghỉ");
        form.setStatus("Chờ duyệt");
        form.setNote(reason);
        form.setFileLink("uploads/" + saveFileName);
        form.setCreateDate(LocalDateTime.now());
        FormSubmissionDAO formDAO = new FormSubmissionDAO();
        formDAO.insertFormSubmission(form);

        request.setAttribute("success", "Nộp đơn thành công! Vui lòng chờ HR duyệt.");
        request.getRequestDispatcher("employeeLeaveRequest.jsp").forward(request, response);
    }
} 