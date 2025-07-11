/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.User;

/**
 *
 * @author Acer
 */
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        String email = null;
        String password = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    email = cookie.getValue();
                } else if ("pass".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }

        // Set username and password as request attributes
        request.setAttribute("email", email);
        request.setAttribute("password", password);

        // Forward to the JSP page
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String rememberMe = request.getParameter("rememberMe");
        HttpSession session = request.getSession();
        String enpass = encrypt(pass);
        UserDAO u = new UserDAO();
        User a = u.findUserPass(email, enpass);
        try {

            if (a == null) {
                request.setAttribute("notify", "SAI");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                if (a != null) {
                    
                    

                    if (rememberMe != null) {  // Checkbox was checked
                        Cookie emailCookie = new Cookie("email", email);
                        Cookie passwordCookie = new Cookie("pass", pass);

                        // Set cookie age to one week (7 days)
                        emailCookie.setMaxAge(7 * 24 * 60 * 60);
                        passwordCookie.setMaxAge(7 * 24 * 60 * 60);

                        // Add cookies to the response
                        response.addCookie(emailCookie);
                        response.addCookie(passwordCookie);
                    } else {
                        // Clear cookies if "Remember Me" is not checked
                        Cookie emailCookie = new Cookie("email", null);
                        Cookie passwordCookie = new Cookie("pass", null);

                        // Invalidate the cookies by setting the max age to 0
                        emailCookie.setMaxAge(0);
                        passwordCookie.setMaxAge(0);

                        response.addCookie(emailCookie);
                        response.addCookie(passwordCookie);
                    }

                    session.setAttribute("acc", a);

                    response.sendRedirect("home");
                } else if (a != null && a.getStatus().equalsIgnoreCase("unverified")) {
                    request.setAttribute("notify", "Your account is not active, please active by link in your email");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            request.setAttribute("notify", "Error occured ");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private String encrypt(String pass) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(pass.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;

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
