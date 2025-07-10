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
import java.util.List;
import model.WorkSchedule;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
@WebServlet(name="WorkSlotDeleteServlet", urlPatterns={"/deleteslot"})
public class WorkSlotDeleteServlet extends HttpServlet {
   
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
            out.println("<title>Servlet WorkSlotDeleteServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WorkSlotDeleteServlet at " + request.getContextPath () + "</h1>");
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
        String slotId_raw = request.getParameter("requestid");
        int slotId;
        try {
            WorkScheduleDAO wsd = new WorkScheduleDAO();
            slotId = Integer.parseInt(slotId_raw);
            WorkSchedule slot = wsd.getWorkScheduleById(slotId);
//            if (slot.getStatus().equalsIgnoreCase("available")) {
//                List<Slot> slotActive = wsd.getListofActiveSlotsByMentorId(slot.getMentorID());
//                for (Slot slot1 : slotActive) {
//                    //re-add another slot except slot wantto delet
//                    if (slot1.getSlotID() != slot.getSlotID()) {
//                        slot1.setStatus("inactive");
//                        wsd.addSlot(slot1);
//                    }
//                }
//            }
//            if (slot.getStatus().equalsIgnoreCase("inactive")) {
                wsd.deleteWorkSchedule(slotId);
//            }
            //System.out.println(slotId);
            response.sendRedirect("slotdraft?id=" + slot.getUserId()+"&mess=Your slot has been deleted all successfully!");
        } catch (NumberFormatException e) {
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
