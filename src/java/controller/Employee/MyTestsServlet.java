/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Employee;

import DAO.TestScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.TestSession;
import model.User;

/**
 *
 * @author Asus
 */
public class MyTestsServlet extends HttpServlet {
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
        int userId = user.getUserId();

        try {
            TestScheduleDAO dao = new TestScheduleDAO();
            List<TestSession> tests = dao.getTestsByUserId(userId);

            request.setAttribute("tests", tests);
            request.getRequestDispatcher("myTests.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi load danh sách test: " + e.getMessage());
        }
    }
}
