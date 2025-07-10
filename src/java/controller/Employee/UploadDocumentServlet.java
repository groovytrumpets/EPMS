/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Employee;

import DAO.DocumentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import model.Document;
import model.User;
import utilities.CloudinaryUploader;

/**
 *
 * @author Asus
 */
@MultipartConfig
public class UploadDocumentServlet extends HttpServlet {
   

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
        int userId = user.getUserId();

        String title = request.getParameter("title");
        String type = request.getParameter("type");
        Part filePart = request.getPart("file");

        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("message", "Vui lòng chọn file!");
            request.getRequestDispatcher("uploadDocument.jsp").forward(request, response);
            return;
        }

        // Đọc file thành temp file để upload lên Cloudinary
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        File tempFile = File.createTempFile("upload-", fileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        String cloudUrl = "";
        try {
            cloudUrl = CloudinaryUploader.uploadFile(tempFile);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi upload file lên Cloudinary!");
            request.getRequestDispatcher("uploadDocument.jsp").forward(request, response);
            return;
        }

        Document doc = new Document(
            0,
            cloudUrl,
            type,
            "pending",
            title,
            LocalDateTime.now(),
            userId
        );

        try {
            DocumentDAO dao = new DocumentDAO();
            dao.insertDocument(doc);
            request.setAttribute("message", "Upload thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Lỗi lưu DB!");
        }

        request.getRequestDispatcher("uploadDocument.jsp").forward(request, response);
    }
}
