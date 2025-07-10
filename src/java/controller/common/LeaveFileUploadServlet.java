package controller.common;

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

@WebServlet(name = "LeaveFileUploadServlet", urlPatterns = {"/LeaveFileUploadServlet"})
@MultipartConfig(maxFileSize = 2 * 1024 * 1024) // 2MB
public class LeaveFileUploadServlet extends HttpServlet {
    private static final String[] ALLOWED_EXTENSIONS = {"pdf", "doc", "docx"};
    private static final String UPLOAD_DIR = "uploads/leave";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Lấy userId từ session (giả sử đã đăng nhập)
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            request.setAttribute("error", "Bạn cần đăng nhập để nộp đơn!");
            request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
            return;
        }

        Part filePart = request.getPart("leaveFile");
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
            request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
            return;
        }
        if (filePart.getSize() > 2 * 1024 * 1024) {
            request.setAttribute("error", "Dung lượng file tối đa là 2MB!");
            request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
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

        // Lưu vào FormSubmission
        HRDAO dao = new HRDAO();
        FormSubmission submission = new FormSubmission();
        submission.setType("LEAVE");
        submission.setPurpose("Nộp đơn xin nghỉ phép");
        submission.setStatus("PENDING");
        submission.setNote("");
        submission.setFileLink(savedFileName);
        submission.setUserId(userId);
        boolean ok = dao.addFormSubmission(submission);
        if (ok) {
            request.setAttribute("message", "Nộp đơn xin nghỉ phép thành công!");
        } else {
            request.setAttribute("error", "Nộp đơn thất bại. Vui lòng thử lại!");
        }
        request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
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