package controller.Candidate;

import DAO.UserDAO;
import DAO.FormSubmissionDAO;
import DAO.DocumentDAO;
import model.User;
import model.FormSubmission;
import model.Document;
import utilities.PassCheck;
import java.io.InputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CandidateRegisterServlet", urlPatterns = {"/CandidateRegisterServlet"})
@MultipartConfig(maxFileSize = 2 * 1024 * 1024) // 2MB
public class CandidateRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> errors = new HashMap<>();
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String gender = request.getParameter("gender");
        String dobString = request.getParameter("dob");
        Part cvFilePart = request.getPart("cvFile");


        java.sql.Date dob = null;
        if (dobString == null || dobString.trim().isEmpty()) {
            errors.put("dob", "Date of Birth is required.");
        } else {
            try {
                dob = java.sql.Date.valueOf(dobString);
            } catch (Exception e) {
                errors.put("dob", "Invalid date format. Use yyyy-MM-dd.");
            }
        }

        String fileName = null;

        // === Validation ===
        if (fullName == null || fullName.trim().isEmpty()) {
            errors.put("fullName", "Full name is required.");
        }

        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email is required.");
        }

        if (phone == null || phone.trim().isEmpty()) {
            errors.put("phone", "Phone number is required.");
        }

        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "Username is required.");
        }

        if (gender == null || gender.trim().isEmpty()) {
            errors.put("gender", "Gender is required.");
        }

        // Email format check
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email != null && !email.matches(emailRegex)) {
            errors.put("email", "Invalid email format.");
        }

        // Phone format check
        String phoneRegex = "^[0-9]{10,15}$";
        if (phone != null && !phone.matches(phoneRegex)) {
            errors.put("phone", "Phone must be 10-15 digits.");
        }

        // Name format check
        String nameRegex = "^[a-zA-ZÀ-ỹ\\s]+$";
        if (fullName != null && !fullName.matches(nameRegex)) {
            errors.put("fullName", "Name must not contain numbers or special characters.");
        }

        // Username/email/phone uniqueness check
        UserDAO userDAO = new UserDAO();
        if (userDAO.isUsernameExists(username)) {
            errors.put("username", "Username already exists.");
        }
        if (userDAO.isEmailExists(email)) {
            errors.put("email", "Email already exists.");
        }
        if (userDAO.isPhoneExists(phone)) {
            errors.put("phone", "Phone number already exists.");
        }

        // File validation
        if (cvFilePart == null || cvFilePart.getSize() == 0) {
            errors.put("cvFile", "Please select a CV file.");
        } else {
            fileName = cvFilePart.getSubmittedFileName();
            String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            if (!(fileExt.equals("pdf") || fileExt.equals("doc") || fileExt.equals("docx"))) {
                errors.put("cvFile", "Only PDF, DOC, or DOCX files are allowed.");
            }
            if (cvFilePart.getSize() > 2 * 1024 * 1024) {
                errors.put("cvFile", "File exceeds 2MB limit.");
            }
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }

        // === Upload CV to Cloudinary ===
        String cloudUrl = "";
        try {
            // Bước 1: Lấy file part và tên file
            Part filePart = request.getPart("cvFile");
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Bước 2: Tạo file tạm trên hệ thống
            File tempFile = File.createTempFile("upload-", fileName);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // Bước 3: Upload lên Cloudinary
            cloudUrl = utilities.CloudinaryUploader.uploadFile(tempFile);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Error uploading CV to Cloudinary.");
            request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
            return;
        }

        // === Register User ===
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setDob(dob);
        user.setUserName(username);
        user.setGender(gender);
        user.setPassword("123"); // Default password
        user.setStatus("unverified");
        user.setRoleId(4); // Candidate role
        int userId = userDAO.insertUser(user);

        // === Save Document (CV) ===
        Document cvDoc = new Document();
        cvDoc.setTitle("CV");
        cvDoc.setFileLink(cloudUrl);
        cvDoc.setType("CV");
        cvDoc.setStatus("submitted");
        cvDoc.setUserId(userId);
        cvDoc.setUploadDate(LocalDateTime.now());
        DocumentDAO docDAO = new DocumentDAO();
        try {
            docDAO.insertDocument(cvDoc);
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, you can set an error message for the user here
        }

        // === Save FormSubmission ===
        FormSubmission form = new FormSubmission();
        form.setUserId(userId);
        form.setStatus("pending");
        form.setFileLink(cloudUrl);
        form.setType("CV"); // auto-set type to CV
        form.setCreateDate(LocalDateTime.now());

        FormSubmissionDAO formDAO = new FormSubmissionDAO();
        formDAO.insertFormSubmission(form);

        // === Success Response ===
        request.setAttribute("success", "Registration successful! Your CV has been submitted.");
        request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("candidateRegister.jsp").forward(request, response);
    }
}
