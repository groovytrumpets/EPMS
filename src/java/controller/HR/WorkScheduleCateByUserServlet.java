/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.HR;

import DAO.HRDAO;
import DAO.WorkScheduleDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Document;
import model.User;
import model.WorkSchedule;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
@WebServlet(name = "WorkScheduleCateByUserServlet", urlPatterns = {"/slotmanagercate"})
public class WorkScheduleCateByUserServlet extends HttpServlet {

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
            out.println("<title>Servlet WorkScheduleCateByUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WorkScheduleCateByUserServlet at " + request.getContextPath() + "</h1>");
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
        String id_raw = request.getParameter("id");
        String error = request.getParameter("error");
        int id;
        try {
            id = Integer.parseInt(id_raw);
            System.out.println(id);
            HRDAO hrdao = new HRDAO();
            WorkScheduleDAO wsd = new WorkScheduleDAO();
            List<User> listEmployee = hrdao.getListOfEmployeeAndCandidateUsers();
            List<Document> listCV = hrdao.getAllListOfCV();

//        System.out.println(listMentor.get(0).getUsername());
            request.setAttribute("mentorList", listEmployee);
            request.setAttribute("listCV", listCV);
            request.setAttribute("uid", id);
            
//        System.out.println(listCV.get(3).getCvId());
            request.setAttribute("error", error);
            
            List<WorkSchedule> listDraftSchedule = wsd.getListofWorkScheduleByUid(id);

            
            request.setAttribute("listActiveSlot", listDraftSchedule);
            List<WorkSchedule> pendingSchedulesList = wsd.getListofPendingSchedule(id);
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
            request.setAttribute("startTime", new Gson().toJson(dateConverted));
           // System.out.println(dateConverted);
            request.setAttribute("endTime", new Gson().toJson(enddateConverted));
            
            pendingSchedulesList = wsd.getListofRuningSchedule(id);
            //s;ot view
            dateConverted = new ArrayList<>();
            enddateConverted = new ArrayList<>();
            statusSlot = new ArrayList<>();
            
            for (int i = 0; i < pendingSchedulesList.size(); i++) {
                String startDate = pendingSchedulesList.get(i).getStartDate().toString();
                String endDate = pendingSchedulesList.get(i).getEndDate().toString();
                statusSlot.add(pendingSchedulesList.get(i).getStatus());
                //System.out.println(startDate+", "+mentorSlot.get(i).getEndTime());
                dateConverted.add(startDate);
                enddateConverted.add(endDate);
            }
            request.setAttribute("status2", new Gson().toJson(statusSlot));
            request.setAttribute("startTime2", new Gson().toJson(dateConverted));
            //System.out.println(dateConverted);
            request.setAttribute("endTime2", new Gson().toJson(enddateConverted));
            request.getRequestDispatcher("managerEmployee.jsp").forward(request, response);
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
        processRequest(request, response);
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
