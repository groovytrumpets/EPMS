package controller.common;

import DAO.UserDAO;
import DAO.FormSubmissionDAO;
import model.User;
import model.FormSubmission;
import utilities.PassCheck;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "CandidateRegisterServlet", urlPatterns = {"/CandidateRegisterServlet"})
@MultipartConfig(maxFileSize = 2 * 1024 * 1024) // 2MB
public class CandidateRegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        Part cvFilePart = request.getPart("cvFile");

        java.util.Map<String, String> errors = new java.util.HashMap<>();
        String fileName = null;
        // Validate bắt buộc nhập
        if (fullName == null || fullName.trim().isEmpty()) {
            errors.put("fullName", "Họ tên là bắt buộc!");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email là bắt buộc!");
        }
        if (phone == null || phone.trim().isEmpty()) {
            errors.put("phone", "Số điện thoại là bắt buộc!");
        }
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "Tên đăng nhập là bắt buộc!");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "Mật khẩu là bắt buộc!");
        }
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            errors.put("confirmPassword", "Nhập lại mật khẩu là bắt buộc!");
        }
        // Validate email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email != null && !email.trim().isEmpty() && !email.matches(emailRegex)) {
            errors.put("email", "Email không đúng định dạng!");
        }
        // Validate số điện thoại: chỉ số, không ký tự đặc biệt, độ dài 10-15
        String phoneRegex = "^[0-9]{10,15}$";
        if (phone != null && !phone.trim().isEmpty() && !phone.matches(phoneRegex)) {
            errors.put("phone", "Số điện thoại phải là số, không có ký tự đặc biệt, độ dài 10-15 số!");
        }
        // Validate fullName không ký tự đặc biệt
        String nameRegex = "^[a-zA-ZÀ-ỹ\s]+$";
        if (fullName != null && !fullName.trim().isEmpty() && !fullName.matches(nameRegex)) {
            errors.put("fullName", "Họ tên không được chứa ký tự đặc biệt hoặc số!");
        }
        // Kiểm tra mật khẩu nhập lại
        if (password != null && confirmPassword != null && !password.equals(confirmPassword)) {
            errors.put("confirmPassword", "Mật khẩu nhập lại không khớp!");
        }
        // Kiểm tra trùng username/email/sđt
        UserDAO userDAO = new UserDAO();
        if (username != null && userDAO.isUsernameExists(username)) {
            errors.put("username", "Tên đăng nhập đã tồn tại!");
        }
        if (email != null && userDAO.isEmailExists(email)) {
            errors.put("email", "Email đã tồn tại!");
        }
        if (phone != null && userDAO.isPhoneExists(phone)) {
            errors.put("phone", "Số điện thoại đã tồn tại!");
        }
        // Validate file
        if (cvFilePart == null || cvFilePart.getSize() == 0) {
            errors.put("cvFile", "Bạn phải chọn file CV!");
        } else {
            fileName = cvFilePart.getSubmittedFileName();
            String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            if (!(fileExt.equals("pdf") || fileExt.equals("doc") || fileExt.equals("docx"))) {
                errors.put("cvFile", "Chỉ chấp nhận file PDF, DOC, DOCX!");
            }
            if (cvFilePart.getSize() > 2 * 1024 * 1024) {
                errors.put("cvFile", "File vượt quá dung lượng 2MB!");
            }
        }
        // Nếu có lỗi, forward lại với errors
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }

        // Không hash password nếu chưa có PassCheck
        String hashedPassword = password;

        // Tạo user mới với Status = 'Chờ kích hoạt'
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUserName(username);
        user.setPassword(hashedPassword);
        user.setStatus("Chờ kích hoạt");
        user.setRoleId(3); // Giả sử 3 là role candidate
        int userId = userDAO.insertUser(user); // Trả về id vừa tạo

        // Lưu file CV vào thư mục uploads (tạo nếu chưa có)
        String uploadPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        String cvFilePath = uploadPath + File.separator + userId + "_" + fileName;
        cvFilePart.write(cvFilePath);

        // Lưu hồ sơ vào FormSubmission với Status = 'Chờ duyệt'
        FormSubmission form = new FormSubmission();
        form.setUserId(userId);
        form.setStatus("Chờ duyệt");
        form.setFileLink("uploads/" + userId + "_" + fileName);
        form.setCreateDate(java.time.LocalDateTime.now());
        DAO.FormSubmissionDAO formDAO = new DAO.FormSubmissionDAO();
        formDAO.insertFormSubmission(form);

        // Chuyển hướng hoặc thông báo thành công
        request.setAttribute("success", "Đăng ký thành công! Vui lòng chờ kích hoạt tài khoản.");
        request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
    }
} 