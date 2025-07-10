/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Employee;

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
public class StartTestServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String testIdRaw = request.getParameter("testId");
        int testId = Integer.parseInt(testIdRaw);

        try {
            QuestionDAO qDao = new QuestionDAO();
            List<Question> questions = qDao.getQuestionsByTestId(testId);

            request.setAttribute("questions", questions);
            request.setAttribute("testId", testId);
            request.getRequestDispatcher("doTest.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi load bài test: " + e.getMessage());
        }
    }
}
