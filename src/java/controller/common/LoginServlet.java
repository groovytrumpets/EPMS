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

public class LoginServlet extends HttpServlet {

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
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

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

        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String rememberMe = request.getParameter("rememberMe");

        HttpSession session = request.getSession();
        UserDAO u = new UserDAO();
        String encryptedPass = encrypt(pass);
        User user = u.findUserPass(email, encryptedPass);

        try {
            if (user == null) {
                request.setAttribute("notify", "Invalid email or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            if ("unverified".equalsIgnoreCase(user.getStatus())) {
                request.setAttribute("notify", "Your account is not active. Please check your email to activate.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Handle Remember Me
            if (rememberMe != null) {
                Cookie emailCookie = new Cookie("email", email);
                Cookie passCookie = new Cookie("pass", pass); // original password

                emailCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                passCookie.setMaxAge(7 * 24 * 60 * 60);

                response.addCookie(emailCookie);
                response.addCookie(passCookie);
            } else {
                // Clear cookies
                Cookie emailCookie = new Cookie("email", null);
                Cookie passCookie = new Cookie("pass", null);
                emailCookie.setMaxAge(0);
                passCookie.setMaxAge(0);
                response.addCookie(emailCookie);
                response.addCookie(passCookie);
            }

            session.setAttribute("acc", user); // Save user in session

            // Redirect based on role
            if (user.getRoleId() == 1) {
                response.sendRedirect("admindashboard");
            } else {
                response.sendRedirect("home");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("notify", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles login, cookies, session and role-based redirect.";
    }
}
