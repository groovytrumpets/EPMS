/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import DAO.AdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import model.FormTemplate;
import utilities.CloudinaryUploader;

/**
 *
 * @author asus
 */
@MultipartConfig
@WebServlet(name = "UploadFormTemplateServlet", urlPatterns = {"/uploadformtemplate"})
public class UploadFormTemplateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadFormTemplateServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadFormTemplateServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        File tempFile = File.createTempFile("upload-", fileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        String uploadedUrl = CloudinaryUploader.uploadFile(tempFile);

        String title = request.getParameter("title");
        int userId = Integer.parseInt(request.getParameter("userId"));
        String status = "Active";

        FormTemplate template = new FormTemplate();
        template.setTitle(title);
        template.setFileLink(uploadedUrl);
        template.setCreateDate(LocalDateTime.now());
        template.setStatus(status);
        template.setDownloadCount(0);
        template.setUserId(userId);

        try {
            AdminDAO dao = new AdminDAO();
            dao.insertFormTemplate(template);

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Upload & Save Successfully: <a href='" + uploadedUrl + "'>" + uploadedUrl + "</a>");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Save Unsuccessfully");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
