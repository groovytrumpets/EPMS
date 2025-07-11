/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.HR.CVmanage;

import DAO.AdminDAO;
import DAO.HRDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import utilities.EmailSender;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
@WebServlet(name="RejectCVServlet", urlPatterns={"/rejectCV"})
public class RejectCVServlet extends HttpServlet {
   
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
            out.println("<title>Servlet RejectCVServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RejectCVServlet at " + request.getContextPath () + "</h1>");
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
        String cvId_raw = request.getParameter("cvid");
        String userId_raw = request.getParameter("uid");
        try {
            int cvId=Integer.parseInt(cvId_raw);
            int userId=Integer.parseInt(userId_raw);
            
            HRDAO hrd = new HRDAO();
            hrd.changeCVStatusReject(cvId);
            User rejectUser = hrd.getUserbyId(userId);
            String title = """
                           Subject: Application Status at PFTU-SWD391
                           """;
            String content ="""
                           Dear %s,
                    
                           Thank you for your interest in the position at PFTU-SWD391.                           
                           We have carefully reviewed your CV and, unfortunately, it does not meet our current requirements for this role. 
                           We truly appreciate your time and effort, and we hope to consider you for other opportunities in the future.
                           We wish you all the best in your career journey.
                           Best regards,  
                           HR Department – PFTU,SWD391
                           """.formatted(rejectUser.getFullName());
            
            
            EmailSender.sendNotificationEmail(rejectUser.getEmail(), title, content);
            HttpSession session = request.getSession();
            User admin = (User) session.getAttribute("acc");
            AdminDAO admindao = new AdminDAO();
            String action = "HR reject CV ID: "+cvId_raw;
            admindao.logAction(admin.getUserId(), action);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        response.sendRedirect("candiAccManage");
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
