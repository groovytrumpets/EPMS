package controller.HR;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import DAO.HRDAO;
import model.FormSubmission;

@WebServlet(name = "CandidateRegisterServlet", urlPatterns = {"/CandidateRegisterServlet"})
@MultipartConfig(maxFileSize = 2 * 1024 * 1024) // 2MB
public class CandidateRegisterServlet extends HttpServlet {
    private static final String[] ALLOWED_EXTENSIONS = {"pdf", "doc", "docx"};
    private static final String UPLOAD_DIR = "uploads/cv";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        Part filePart = request.getPart("cvFile");

        HRDAO dao = new HRDAO();
        // Validate trùng email
        if (dao.isEmailExists(email)) {
            request.setAttribute("error", "Email đã tồn tại!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }
        // Validate trùng số điện thoại
        if (dao.isPhoneExists(phone)) {
            request.setAttribute("error", "Số điện thoại đã tồn tại!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }
        // Validate email đúng định dạng @gmail.com
        if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            request.setAttribute("error", "Email phải đúng định dạng @gmail.com!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }
        // Validate số điện thoại: bắt đầu từ 0, 10 chữ số, không ký tự đặc biệt
        if (!phone.matches("^0[0-9]{9}$")) {
            request.setAttribute("error", "Số điện thoại phải bắt đầu từ 0, gồm 10 chữ số và không có ký tự đặc biệt!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }
        // Validate tên không ký tự đặc biệt và chỉ 1 khoảng trắng giữa các từ
        if (!fullname.matches("^[A-Za-zÀ-ỹà-ỹ\s]+$")) {
            request.setAttribute("error", "Tên không được chứa ký tự đặc biệt!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }
        if (fullname.trim().replaceAll(" +", " ").split(" ").length < 2 && fullname.contains(" ")) {
            request.setAttribute("error", "Tên phải có ít nhất 2 từ!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }
        if (fullname.contains("  ")) {
            request.setAttribute("error", "Tên chỉ được phép có 1 khoảng trắng giữa các từ!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu xác nhận
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }

        // Kiểm tra file
        String fileName = getFileName(filePart);
        String fileExt = getFileExtension(fileName);
        boolean validExt = false;
        for (String ext : ALLOWED_EXTENSIONS) {
            if (ext.equalsIgnoreCase(fileExt)) {
                validExt = true;
                break;
            }
        }
        if (!validExt) {
            request.setAttribute("error", "Chỉ cho phép file PDF, DOC, DOCX!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }
        if (filePart.getSize() > 2 * 1024 * 1024) {
            request.setAttribute("error", "Dung lượng file tối đa là 2MB!");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }

        // Lưu file
        String appPath = request.getServletContext().getRealPath("");
        String uploadPath = appPath + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        String savedFileName = System.currentTimeMillis() + "_" + fileName;
        File file = new File(uploadDir, savedFileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        // Lưu thông tin ứng viên vào DB
        boolean success = dao.addCandidate(fullname, email, phone, password, savedFileName);
        if (success) {
            // Lấy userId vừa đăng ký (giả sử username là email, lấy userId theo email)
            int userId = dao.getUserIdByEmail(email);
            FormSubmission submission = new FormSubmission();
            submission.setType("CV");
            submission.setPurpose("Nộp CV ứng tuyển");
            submission.setStatus("PENDING");
            submission.setNote("");
            submission.setFileLink(savedFileName);
            submission.setUserId(userId);
            dao.addFormSubmission(submission);
            request.setAttribute("message", "Đăng ký thành công! Vui lòng chờ xác nhận.");
        } else {
            request.setAttribute("error", "Đăng ký thất bại. Vui lòng thử lại!");
        }
        request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int dot = fileName.lastIndexOf('.');
        return (dot >= 0) ? fileName.substring(dot + 1) : "";
    }
} 