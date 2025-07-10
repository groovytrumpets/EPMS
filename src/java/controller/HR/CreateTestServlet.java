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

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Asus
 */
public class CreateTestServlet extends HttpServlet {
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String title = request.getParameter("title");
            int timer = Integer.parseInt(request.getParameter("timer"));
            String deadlineStr = request.getParameter("deadline");
            LocalDateTime deadline = LocalDateTime.parse(deadlineStr.replace("T", " ") + ":00",
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // 1. Create TestSchedule
            TestScheduleDAO scheduleDao = new TestScheduleDAO();
            int scheduleId = scheduleDao.createTestSchedule(userId, "published");

            // 2. Create TestSession
            TestSession session = new TestSession();
            session.setTestScheduleId(scheduleId);
            session.setTitle(title);
            session.setTimer(timer);
            session.setDeadline(Timestamp.valueOf(deadline));
            session.setMark(0);
            session.setStatus("not_started");

            TestSessionDAO sessionDao = new TestSessionDAO();
            int testId = sessionDao.insertTestSession(session);

            // 3. Insert Questions
            String[] questions = request.getParameterValues("questionText");
            String[] answers = request.getParameterValues("answerIndex");

            QuestionDAO qDao = new QuestionDAO();
            for (int i = 0; i < questions.length; i++) {
                Question q = new Question();
                q.setQuestion(questions[i]);
                q.setAnswer(Integer.parseInt(answers[i]));
                q.setStatus("draft");
                q.setTestId(testId);
                qDao.insertQuestion(q);
            }

            response.getWriter().println("Tạo bài test thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
