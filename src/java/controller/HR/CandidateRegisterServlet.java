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
        HRDAO dao = new HRDAO();
        boolean success = dao.addCandidate(fullname, email, phone, password, savedFileName);
        if (success) {
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