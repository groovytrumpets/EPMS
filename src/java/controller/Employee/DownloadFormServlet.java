package controller.Employee;

import DAO.AdminDAO;
import DAO.FormTemplateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import model.FormTemplate;
import model.User;

public class DownloadFormServlet extends HttpServlet {
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
        if (idRaw == null || idRaw.isEmpty()) {
            // **KHÔNG có id → HIỂN THỊ DANH SÁCH**
            FormTemplateDAO dao = new FormTemplateDAO();
            try {
                List<FormTemplate> forms = dao.getAllForms();
                request.setAttribute("forms", forms);
                request.getRequestDispatcher("listForms.jsp").forward(request, response);
            } catch (SQLException ex) {
                ex.printStackTrace();
                response.getWriter().println("Lỗi: " + ex.getMessage());
            }

        } else {
            // **CÓ id → DOWNLOAD TỪ CLOUDINARY**
            try {
                int templateId = Integer.parseInt(idRaw);

                FormTemplateDAO dao = new FormTemplateDAO();
                FormTemplate form = dao.getFormById(templateId);

                if (form != null) {
                    dao.updateDownloadCount(templateId);

                    String cloudUrl = form.getFileLink();
            User admin = (User) session.getAttribute("acc");
            AdminDAO admindao = new AdminDAO();
            String action = "User ID " + user.getUserId() +" download form" ;
            admindao.logAction(admin.getUserId(), action);
                    if (cloudUrl == null || cloudUrl.isEmpty()) {
                        response.getWriter().println("File không tồn tại trên Cloudinary!");
                        return;
                    }

                    // Redirect đến Cloudinary URL
                    response.sendRedirect(cloudUrl);
                } else {
                    response.getWriter().println("Form không tồn tại!");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("ID không hợp lệ!");
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("Lỗi tải form!");
            }
        }
    }
}
