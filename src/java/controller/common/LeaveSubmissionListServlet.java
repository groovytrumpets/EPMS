package controller.common;

import DAO.HRDAO;
import model.FormSubmission;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LeaveSubmissionListServlet", urlPatterns = {"/leave-submissions"})
public class LeaveSubmissionListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HRDAO dao = new HRDAO();
        List<FormSubmission> submissions = dao.getFormSubmissionsByType("LEAVE");
        request.setAttribute("submissions", submissions);
        request.getRequestDispatcher("leaveSubmissionList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        String action = request.getParameter("action"); // approve/reject
        String note = request.getParameter("note");
        HRDAO dao = new HRDAO();
        String status = "PENDING";
        if ("approve".equals(action)) status = "APPROVED";
        else if ("reject".equals(action)) status = "REJECTED";
        dao.updateFormSubmissionStatus(submissionId, status, note);
        response.sendRedirect("leave-submissions");
    }
} 