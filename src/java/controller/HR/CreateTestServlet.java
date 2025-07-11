/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.HR;

import DAO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import DAO.UserDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Asus
 */
public class CreateTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String title = request.getParameter("title");
            int timer = Integer.parseInt(request.getParameter("timer"));
            String deadlineStr = request.getParameter("deadline");
            String isForGroup = request.getParameter("isForGroup");
            int roleId = 0;

            if ("yes".equals(isForGroup)) {
                roleId = Integer.parseInt(request.getParameter("roleId"));
            }

            LocalDateTime deadline = LocalDateTime.parse(
                    deadlineStr.replace("T", " ") + ":00",
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Prepare question data from form
            String[] questions = request.getParameterValues("questionText");
            String[] answers = request.getParameterValues("answerIndex");

            if ("yes".equals(isForGroup)) {
                // HR tạo test cho nhóm

                UserDAO userDao = new UserDAO();
                List<User> users = userDao.getUsersByRole(roleId);

                for (User user : users) {
                    // 1. Create TestSchedule for this user
                    TestScheduleDAO scheduleDao = new TestScheduleDAO();
                    int scheduleId = scheduleDao.createTestSchedule(user.getUserId(), "published");

                    // 2. Create TestSession
                    TestSession test = new TestSession();
                    test.setTestScheduleId(scheduleId);
                    test.setTitle(title);
                    test.setTimer(timer);
                    test.setDeadline(Timestamp.valueOf(deadline));
                    test.setMark(0);
                    test.setStatus("not_started");

                    TestSessionDAO sessionDao = new TestSessionDAO();
                    int testId = sessionDao.insertTestSession(test);

                    // 3. Clone Questions
                    QuestionDAO qDao = new QuestionDAO();
                    for (int i = 0; i < questions.length; i++) {
                        Question q = new Question();
                        q.setQuestion(questions[i]);
                        q.setAnswer(Integer.parseInt(answers[i]));
                        q.setStatus("draft");
                        q.setTestId(testId);
                        qDao.insertQuestion(q);
                    }
                }

                response.getWriter().println("Successfully created a quiz for the Employee group!");

            } else {
                // HR tạo test cho 1 user duy nhất

                int userId = Integer.parseInt(request.getParameter("userId"));

                // 1. Create TestSchedule
                TestScheduleDAO scheduleDao = new TestScheduleDAO();
                int scheduleId = scheduleDao.createTestSchedule(userId, "published");

                // 2. Create TestSession
                TestSession test = new TestSession();
                test.setTestScheduleId(scheduleId);
                test.setTitle(title);
                test.setTimer(timer);
                test.setDeadline(Timestamp.valueOf(deadline));
                test.setMark(0);
                test.setStatus("not_started");

                TestSessionDAO sessionDao = new TestSessionDAO();
                int testId = sessionDao.insertTestSession(test);

                // 3. Insert Questions
                QuestionDAO qDao = new QuestionDAO();
                for (int i = 0; i < questions.length; i++) {
                    Question q = new Question();
                    q.setQuestion(questions[i]);
                    q.setAnswer(Integer.parseInt(answers[i]));
                    q.setStatus("draft");
                    q.setTestId(testId);
                    qDao.insertQuestion(q);
                }

                response.getWriter().println("Tạo bài test cho user thành công!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
