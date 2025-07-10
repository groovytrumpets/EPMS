package controller.Employee;

import DAO.DocumentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Document;
import model.User;

public class MyDocumentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
        session.setAttribute("acc", user);
        int userId = user.getUserId();
        try {
            DocumentDAO dao = new DocumentDAO();
            List<Document> docs = dao.getDocumentsByUser(userId);
            
            request.setAttribute("docs", docs);
            request.getRequestDispatcher("myDocuments.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().println("Lỗi: " + ex.getMessage());
        }
    }
}
