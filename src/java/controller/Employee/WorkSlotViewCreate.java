/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Employee;

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
import java.util.ArrayList;
import java.util.List;
import model.User;
import model.WorkSchedule;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
@WebServlet(name="WorkSlotViewCreate", urlPatterns={"/slotview"})
public class WorkSlotViewCreate extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet WorkSlotViewCreate</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WorkSlotViewCreate at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("acc");
        String error = request.getParameter("error");
        String mess = request.getParameter("mess");
        int userId;
         try {
           
            userId = user.getUserId();
            WorkScheduleDAO wsd = new WorkScheduleDAO();
             List<WorkSchedule> pendingSchedulesList = wsd.getListofRuningSchedule(userId);
             request.setAttribute("pendingSchedulesList", pendingSchedulesList);
             
//            request.setAttribute("skillMentor", mentorSkillList);
            request.setAttribute("error", error);
            request.setAttribute("mess", mess);
//            request.setAttribute("uFound", mentor);
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
         }catch(Exception e){
             System.out.println(e);
         }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
