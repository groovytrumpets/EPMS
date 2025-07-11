/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.HR;

import DAO.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Question;

/**
 *
 * @author Asus
 */
public class HRViewTestDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int testId = Integer.parseInt(request.getParameter("testId"));

        try {
            QuestionDAO dao = new QuestionDAO();
            List<Question> questions = dao.getQuestionsByTestId(testId);

            request.setAttribute("questions", questions);
            request.getRequestDispatcher("/hrTestDetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi load chi tiết bài test: " + e.getMessage());
        }
    }
}
