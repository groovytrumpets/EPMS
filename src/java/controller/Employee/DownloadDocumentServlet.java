package controller.Employee;

import DAO.DocumentDAO;
import model.Document;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

public class DownloadDocumentServlet extends HttpServlet {

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        String idRaw = request.getParameter("id");
        if (idRaw == null || idRaw.isEmpty()) {
            response.getWriter().println("Thiếu tham số id!");
            return;
        }

        int docId = Integer.parseInt(idRaw);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");

        try {
            DocumentDAO dao = new DocumentDAO();
            Document doc = dao.getDocumentById(docId);

            if (doc != null && doc.getUserId() == user.getUserId()) {
                System.out.println("Cloudinary URL = " + doc.getFileLink());
                if (doc.getFileLink() == null || doc.getFileLink().isEmpty()) {
                    response.getWriter().println("File link trống hoặc không tồn tại!");
                    return;
                }
                response.sendRedirect(doc.getFileLink());
            } else {
                response.getWriter().println("Bạn không có quyền tải file này!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi tải file! Lý do: " + e.getMessage());
        }
    }
}
