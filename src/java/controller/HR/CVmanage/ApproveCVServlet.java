/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.HR.CVmanage;

import DAO.UserDAO;
import DAO.FormSubmissionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApproveCVServlet", urlPatterns = {"/ApproveCVServlet"})
public class ApproveCVServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        // Cập nhật trạng thái user
        UserDAO userDAO = new UserDAO();
        userDAO.activateUser(userId);
        // Cập nhật trạng thái form submission
        FormSubmissionDAO formDAO = new FormSubmissionDAO();
        formDAO.approveSubmission(submissionId);
        // Chuyển hướng về trang danh sách
        response.sendRedirect("/HRPendingCVList.jsp");
    }
}
