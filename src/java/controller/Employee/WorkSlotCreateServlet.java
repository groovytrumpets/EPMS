/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Employee;

import DAO.WorkScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import model.WorkSchedule;
import model.WorkShift;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
@WebServlet(name = "WorkSlotCreateServlet", urlPatterns = {"/createSlot"})
public class WorkSlotCreateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet WorkSlotCreateServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WorkSlotCreateServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("slotCreate.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String slottime = request.getParameter("slotTime");
        String employeeId_raw = request.getParameter("employeeId");
        System.out.println(slottime);

        int employeeId;
        int start = 0;
        int end = 0;
        switch (slottime) {
            case "1" -> {
                start = 8;
                end = 17;
            }
            case "2" -> {
                start = 8;
                end = 12;
            }
            case "3" -> {
                start = 13;
                end = 17;
            }
            default -> {
                start = 0;
                end = 0;
                throw new IllegalArgumentException("Out of slot range!");
            }
        }
       //genarate date
        LocalDate today = LocalDate.now();
        LocalTime timeStart = LocalTime.of(start, 0);
        LocalTime timeEnd = LocalTime.of(end, 0);
        
        LocalDateTime WorkScheduleStart = LocalDateTime.of(today, timeStart);
        LocalDateTime WorkScheduleEnd = LocalDateTime.of(today, timeEnd);
        
        
        WorkScheduleDAO wsd = new WorkScheduleDAO();
//        employeeId = Integer.parseInt(employeeId_raw);
        WorkSchedule schedule = new WorkSchedule();
        schedule.setStatus("pending");
        schedule.setStartDate(WorkScheduleStart);
        schedule.setEndDate(WorkScheduleEnd);
        schedule.setRemainLeave(12);      
        schedule.setWorkDay(0);          
        schedule.setUserId(8);
        


        if (wsd.addSlot(schedule)) {
            response.sendRedirect("slotdraft?id=" + employeeId_raw + "&mess=Your slot has been created successfully!");
        } else {
            response.sendRedirect("slotdraft?id=" + employeeId_raw + "&mess=Unable to create your slot in the Slot draft. Please try again.");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
