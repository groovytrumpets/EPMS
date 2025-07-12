<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
    if (errors == null) errors = new java.util.HashMap<>();
    String success = (String) request.getAttribute("success");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Candidate Registration</title>
        <meta charset="UTF-8">
        <!-- Bootstrap 5 CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .form-container {
                max-width: 600px;
                margin: 50px auto;
                padding: 30px;
                background-color: #ffffff;
                border-radius: 12px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            }
            .error-msg {
                color: #dc3545;
                font-size: 0.9rem;
            }
            .success-msg {
                color: #198754;
                font-size: 1rem;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body style="background-color: #f8f9fa;">
        <div class="container">
            <div class="form-container">
                <h3 class="text-center mb-4">🎓 Candidate Registration & CV Upload</h3>

                <% if (success != null) { %>
                <div class="alert alert-success"><%= success %></div>
                <% } %>

                <form action="CandidateRegisterServlet" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="fullName" name="fullName"
                               value="<%= request.getParameter("fullName") != null ? request.getParameter("fullName") : "" %>" required>
                        <% if (errors.get("fullName") != null) { %>
                        <div class="error-msg"><%= errors.get("fullName") %></div>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email"
                               value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required>
                        <% if (errors.get("email") != null) { %>
                        <div class="error-msg"><%= errors.get("email") %></div>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="phone" name="phone"
                               value="<%= request.getParameter("phone") != null ? request.getParameter("phone") : "" %>"
                               pattern="[0-9]{10,15}" title="Please enter a valid phone number" required>
                        <% if (errors.get("phone") != null) { %>
                        <div class="error-msg"><%= errors.get("phone") %></div>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="dob" class="form-label">Date of Birth</label>
                        <input type="date" class="form-control" id="dob" name="dob"
                               value="<%= request.getParameter("dob") != null ? request.getParameter("dob") : "" %>" required>
                        <% if (errors.get("dob") != null) { %>
                        <div class="error-msg"><%= errors.get("dob") %></div>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="gender" class="form-label">Gender</label>
                        <select class="form-control" id="gender" name="gender" required>
                            <option value="">Select Gender</option>
                            <option value="Male" <%= request.getParameter("gender") != null && request.getParameter("gender").equals("Male") ? "selected" : "" %>>Male</option>
                            <option value="Female" <%= request.getParameter("gender") != null && request.getParameter("gender").equals("Female") ? "selected" : "" %>>Female</option>
                            <option value="Other" <%= request.getParameter("gender") != null && request.getParameter("gender").equals("Other") ? "selected" : "" %>>Other</option>
                        </select>
                        <% if (errors.get("gender") != null) { %>
                        <div class="error-msg"><%= errors.get("gender") %></div>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="username"
                               value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>" required>
                        <% if (errors.get("username") != null) { %>
                        <div class="error-msg"><%= errors.get("username") %></div>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="cvFile" class="form-label">Upload CV (PDF, DOC, DOCX - max 2MB)</label>
                        <input class="form-control" type="file" id="cvFile" name="cvFile" accept=".pdf,.doc,.docx" required>
                        <% if (errors.get("cvFile") != null) { %>
                        <div class="error-msg"><%= errors.get("cvFile") %></div>
                        <% } %>
                    </div>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Submit Registration</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
