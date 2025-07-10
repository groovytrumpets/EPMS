package controller.common;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utilities.EmailSender;

@WebServlet(name = "VerifyEmailServlet", urlPatterns = {"/verify"})
public class VerifyEmailServlet extends HttpServlet {

    // ====== Inner Class: Generate 6-digit OTP ======
    public static class OTPGenerator {

        public static String generateOTP() {
            Random random = new Random();
            int otp = 100000 + random.nextInt(900000); // 6-digit number
            return String.valueOf(otp);
        }
    }

    // ====== Handle OTP Request (GET): Send email and save session ======
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String otp = OTPGenerator.generateOTP();

        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);
        session.setAttribute("otp_time", System.currentTimeMillis()); // ⏱ Save OTP generation time

        try {
            EmailSender.sendNotificationEmail(email, "Email Verification",
                    "Your OTP is: " + otp + ". This code is valid for 5 minutes.");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(VerifyEmailServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Failed to send email. Please try again later.");
            request.getRequestDispatcher("forget-password.jsp").forward(request, response);
        }
    }

    // ====== Handle OTP Verification (POST) ======
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userOtp = request.getParameter("otp");
        HttpSession session = request.getSession();

        String correctOtp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("email");
        Long otpTime = (Long) session.getAttribute("otp_time");

        // ✅ Check for OTP timeout (5 minutes)
        long currentTime = System.currentTimeMillis();
        if (otpTime == null || (currentTime - otpTime) > 5 * 60 * 1000) {
            session.removeAttribute("otp");
            session.removeAttribute("otp_time");
            request.setAttribute("error", "OTP has expired. Please request a new one.");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
            return;
        }

        // ✅ Compare entered OTP with session
        if (correctOtp != null && correctOtp.equals(userOtp)) {
            response.sendRedirect("reset-changePassword?email=" + email);
        } else {
            request.setAttribute("error", "Incorrect OTP.");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Email OTP verification handler for user account recovery.";
    }
}
