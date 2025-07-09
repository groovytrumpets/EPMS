package controller.common;



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

public class ResetChangePassword extends HttpServlet {

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
        request.getRequestDispatcher("reset-changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        String newPass1 = request.getParameter("newPass1");
        String newPass2 = request.getParameter("newPass2");
        String email = (String) session.getAttribute("email");

        // Validate password strength
        if (!isStrongPassword(newPass1)) {
            request.setAttribute("error", "Password must contain at least 8 characters including uppercase, lowercase, number, and special character.");
            request.getRequestDispatcher("reset-changePassword.jsp").forward(request, response);
            return;
        }

        // Validate password confirmation
        if (!newPass1.equals(newPass2)) {
            request.setAttribute("error", "Passwords do not match!");
            request.getRequestDispatcher("reset-changePassword.jsp").forward(request, response);
            return;
        }

        // Encrypt password and update in DB
        String encryptedPassword = encrypt(newPass1);
        boolean success = userDAO.updatePasswordByEmail(email, encryptedPassword);

        if (success) {
            request.setAttribute("message", "Password changed successfully. Please log in again.");
        } else {
            request.setAttribute("error", "An error occurred while updating the password.");
        }

        request.getRequestDispatcher("reset-changePassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles password reset and update functionality after OTP verification.";
    }
}
