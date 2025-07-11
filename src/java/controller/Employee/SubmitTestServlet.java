/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Employee;

import DAO.QuestionDAO;
import DAO.TestScheduleDAO;
import DAO.TestSessionDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Question;
import model.TestSession;
import model.User;

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
                if (ansStr == null || ansStr.isEmpty()) continue;

                int ans = Integer.parseInt(ansStr);
                if (ans == q.getAnswer()) {
                    correct++;
                }
            }

            int total = questions.size();
            int mark = (int) Math.round(((double) correct / total) * 100);

            // Update mark & status
            TestSessionDAO sessionDao = new TestSessionDAO();
            sessionDao.updateMarkAndStatus(testId, mark, "done");

            // Lấy userId từ TestSchedule
            TestSession test = sessionDao.getTestById(testId);
            int scheduleId = test.getTestScheduleId();

            TestScheduleDAO schedDao = new TestScheduleDAO();
            int userId = schedDao.getUserIdByScheduleId(scheduleId);

            UserDAO userDao = new UserDAO();
            User user = userDao.getUserById(userId);

            String message = null;

            if (user != null && user.getRoleId() == 4) {
                if (mark >= 75) {
                    userDao.updateStatusAndRole(userId, "active", 3);
                    message = "Bạn đã đạt yêu cầu. Tài khoản của bạn đã được kích hoạt thành Employee!";
                } else {
                    message = "Bạn chưa đạt đủ điểm để trở thành nhân viên.";
                }
            }

            request.setAttribute("message", message);
            request.setAttribute("mark", mark);
            request.setAttribute("correct", correct);
            request.setAttribute("total", total);

            request.getRequestDispatcher("result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi nộp bài: " + e.getMessage());
        }
    }
}
