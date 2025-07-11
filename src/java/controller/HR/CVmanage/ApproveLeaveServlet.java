package controller.HR.CVmanage;

import DAO.FormSubmissionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApproveLeaveServlet", urlPatterns = {"/ApproveLeaveServlet"})
public class ApproveLeaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        FormSubmissionDAO formDAO = new FormSubmissionDAO();
        formDAO.approveSubmission(submissionId);
        response.sendRedirect("/HRPendingLeaveList.jsp");
    }
} 