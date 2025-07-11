/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Employee;

import DAO.QuestionDAO;
import DAO.TestSessionDAO;
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
public class SubmitTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int testId = Integer.parseInt(request.getParameter("testId"));

        try {
            QuestionDAO qDao = new QuestionDAO();
            List<Question> questions = qDao.getQuestionsByTestId(testId);

            int correct = 0;
            for (Question q : questions) {
                String paramName = "answer_" + q.getQuestionId();
                String ansStr = request.getParameter(paramName);

                if (ansStr == null || ansStr.isEmpty()) {
                    continue;
                }

                int ans = Integer.parseInt(ansStr);
                if (ans == q.getAnswer()) {
                    correct++;
                }
            }

            int total = questions.size();
            double rate = (double) correct / total;
            int mark = (int) Math.round(rate * 100);

            // Update mark and status
            TestSessionDAO sessionDao = new TestSessionDAO();
            sessionDao.updateMarkAndStatus(testId, mark, "done");

            response.getWriter().println(
                    "Your test has been submitted successfully!____"
                    + "You answered correctly: " + correct + "/" + total+"____"  
                    + "Your score: " + mark + "/100"
            );

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi nộp bài: " + e.getMessage());
        }
    }
}
