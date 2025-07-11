<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%
    // Retrieve the errors map from the request, create a new one if it doesn't exist.
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
    if (errors == null) {
        errors = new java.util.HashMap<>();
    }
    // Retrieve the success message from the request.
    String success = (String) request.getAttribute("success");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Candidate Registration & CV Submission</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="assets/css/style.css">
    <style>
        .error-msg { color: #f55; font-size: 0.95em; margin: 2px 0 8px 0; }
        .success-msg { color: green; font-size: 1em; margin: 8px 0; }
    </style>
</head>
<body>
    <h2>Candidate Registration & CV Submission</h2>
    
    <%-- Display success message if it exists --%>
    <% if (success != null) { %>
        <div class="success-msg"><%= success %></div>
    <% } %>
    
    <form action="CandidateRegisterServlet" method="post" enctype="multipart/form-data">
        
        <label for="fullName">Full Name:</label><br>
        <input type="text" id="fullName" name="fullName" value="<%= request.getParameter("fullName") != null ? request.getParameter("fullName") : "" %>" required><br>
        <%-- Display full name validation error --%>
        <% if (errors.get("fullName") != null) { %>
            <div class="error-msg"><%= errors.get("fullName") %></div>
        <% } %>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required><br>
        <%-- Display email validation error --%>
        <% if (errors.get("email") != null) { %>
            <div class="error-msg"><%= errors.get("email") %></div>
        <% } %>

        <label for="phone">Phone Number:</label><br>
        <input type="text" id="phone" name="phone" value="<%= request.getParameter("phone") != null ? request.getParameter("phone") : "" %>" required pattern="[0-9]{10,15}" title="Please enter a valid phone number"><br>
        <%-- Display phone number validation error --%>
        <% if (errors.get("phone") != null) { %>
            <div class="error-msg"><%= errors.get("phone") %></div>
        <% } %>

        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username" value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>" required><br>
        <%-- Display username validation error --%>
        <% if (errors.get("username") != null) { %>
            <div class="error-msg"><%= errors.get("username") %></div>
        <% } %>

        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" required minlength="6"><br>
        <%-- Display password validation error --%>
        <% if (errors.get("password") != null) { %>
            <div class="error-msg"><%= errors.get("password") %></div>
        <% } %>

        <label for="confirmPassword">Confirm Password:</label><br>
        <input type="password" id="confirmPassword" name="confirmPassword" required minlength="6"><br>
        <%-- Display password confirmation validation error --%>
        <% if (errors.get("confirmPassword") != null) { %>
            <div class="error-msg"><%= errors.get("confirmPassword") %></div>
        <% } %>

        <label for="cvFile">Submit CV (PDF, DOC, DOCX, max 2MB):</label><br>
        <input type="file" id="cvFile" name="cvFile" accept=".pdf,.doc,.docx" required><br>
        <%-- Display CV file validation error --%>
        <% if (errors.get("cvFile") != null) { %>
            <div class="error-msg"><%= errors.get("cvFile") %></div>
        <% } %>

        <button type="submit">Register & Submit CV</button>
    </form>
</body>
</html>