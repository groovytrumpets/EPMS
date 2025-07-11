/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.HR;

import DAO.TestScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Asus
 */
public class HRViewTestsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            TestScheduleDAO dao = new TestScheduleDAO();
            List<Object[]> schedules = dao.getAllTestSchedules();

            request.setAttribute("schedules", schedules);
            request.getRequestDispatcher("/hrTestList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi load danh sách test schedule: " + e.getMessage());
        }
    }
}
