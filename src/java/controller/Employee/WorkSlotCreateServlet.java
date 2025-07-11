/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Employee;

import DAO.AdminDAO;
import DAO.WorkScheduleDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.User;
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
//        String userId_raw = request.getParameter("id");
        String error = request.getParameter("error");
        String mess = request.getParameter("mess");
        int userId;
        try {

            userId = user.getUserId();
            WorkScheduleDAO wsd = new WorkScheduleDAO();
            List<WorkSchedule> pendingSchedulesList = wsd.getListofPendingSchedule(userId);
            request.setAttribute("pendingSchedulesList", pendingSchedulesList);

//            request.setAttribute("skillMentor", mentorSkillList);
            request.setAttribute("error", error);
            request.setAttribute("mess", mess);
            request.setAttribute("uFound", userId);
            request.setAttribute("slotList", pendingSchedulesList);
            //s;ot view
            List<String> dateConverted = new ArrayList<>();
            List<String> enddateConverted = new ArrayList<>();
            List<String> statusSlot = new ArrayList<>();

            for (int i = 0; i < pendingSchedulesList.size(); i++) {
                String startDate = pendingSchedulesList.get(i).getStartDate().toString();
                String endDate = pendingSchedulesList.get(i).getEndDate().toString();
                statusSlot.add(pendingSchedulesList.get(i).getStatus());
                //System.out.println(startDate+", "+mentorSlot.get(i).getEndTime());
                dateConverted.add(startDate);
                enddateConverted.add(endDate);
            }

            request.setAttribute("status", new Gson().toJson(statusSlot));
            request.setAttribute("values", new Gson().toJson(dateConverted));
            request.setAttribute("endValues", new Gson().toJson(enddateConverted));

            request.getRequestDispatcher("slotCreate.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
        String slottime = request.getParameter("slotTime");

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
        try {
            employeeId = user.getUserId();
            schedule.setUserId(employeeId);

        } catch (Exception e) {
            System.out.println(e);
        }

        if (wsd.addSlot(schedule)) {
            User admin = (User) session.getAttribute("acc");
            AdminDAO admindao = new AdminDAO();
            String action = "Create new work schedule";
            admindao.logAction(admin.getUserId(), action);
            response.sendRedirect("slotdraft?mess=Your slot has been created successfully!");
        } else {
            response.sendRedirect("slotdraft?mess=Unable to create your slot in the Slot draft. Please try again.");
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
