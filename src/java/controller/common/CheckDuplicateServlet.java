package controller.common;

import DAO.HRDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckDuplicateServlet", urlPatterns = {"/check-duplicate"})
public class CheckDuplicateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        HRDAO dao = new HRDAO();
        boolean exists = false;
        if (email != null && !email.isEmpty()) {
            exists = dao.isEmailExists(email);
        } else if (phone != null && !phone.isEmpty()) {
            exists = dao.isPhoneExists(phone);
        }
        response.setContentType("text/plain");
        response.getWriter().write(Boolean.toString(exists));
    }
} 