/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import DAO.AdminDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
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
public class ChangePassword extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Encrypt password using MD5
    private String encrypt(String password) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    // Check password strength (at least 8 chars, upper, lower, digit, special)
    public static boolean isStrongPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$";
        return password.matches(regex);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        UserDAO userDAO = new UserDAO();
        String currentPass = request.getParameter("currentPassword");
        String newPass1 = request.getParameter("newPass1");
        String newPass2 = request.getParameter("newPass2");

        User currentUser = (User) session.getAttribute("acc");  // Đã đăng nhập
        String email = currentUser.getEmail();  // Lấy email từ user hiện tại

        // Check current password
        String encryptedCurrent = encrypt(currentPass);
        if (!encryptedCurrent.equals(currentUser.getPassword())) {
            session.setAttribute("error", "Current password is incorrect.");
            response.sendRedirect("changePassword");
            return;
        }

        if (!isStrongPassword(newPass1)) {
            session.setAttribute("error", "Password must be at least 8 characters long and contain uppercase, lowercase, number, and special character.");
            response.sendRedirect("changePassword");
            return;
        }

        if (!newPass1.equals(newPass2)) {
            session.setAttribute("error", "Passwords do not match.");
            response.sendRedirect("changePassword");
            return;
        }

        String encryptedNew = encrypt(newPass1);
        boolean success = userDAO.updatePasswordByEmail(email, encryptedNew);

        if (success) {
            User admin = (User) session.getAttribute("acc");

            AdminDAO admindao = new AdminDAO();

            String action = "Changed password of user " + currentUser.getFullName() ;
            admindao.logAction(admin.getUserId(), action);
            session.setAttribute("message", "Password changed successfully.");
            response.sendRedirect("changePassword");

        } else {
            session.setAttribute("error", "An error occurred while updating the password.");
        }

    }

    @Override
    public String getServletInfo() {
        return "Change Password.";
    }

}
