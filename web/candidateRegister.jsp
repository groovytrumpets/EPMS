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
    <title>Đăng ký ứng viên & Nộp CV</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="assets/css/style.css">
    <style>
        .error-msg { color: #f55; font-size: 0.95em; margin: 2px 0 8px 0; }
        .success-msg { color: green; font-size: 1em; margin: 8px 0; }
    </style>
</head>
<body>
    <h2>Đăng ký ứng viên & Nộp CV</h2>
    <% if (success != null) { %>
        <div class="success-msg"><%= success %></div>
    <% } %>
    <form action="CandidateRegisterServlet" method="post" enctype="multipart/form-data">
        <label for="fullName">Họ và tên:</label><br>
        <input type="text" id="fullName" name="fullName" value="<%= request.getParameter("fullName") != null ? request.getParameter("fullName") : "" %>" required><br>
        <% if (errors.get("fullName") != null) { %>
            <div class="error-msg"><%= errors.get("fullName") %></div>
        <% } %>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required><br>
        <% if (errors.get("email") != null) { %>
            <div class="error-msg"><%= errors.get("email") %></div>
        <% } %>

        <label for="phone">Số điện thoại:</label><br>
        <input type="text" id="phone" name="phone" value="<%= request.getParameter("phone") != null ? request.getParameter("phone") : "" %>" required pattern="[0-9]{10,15}" title="Số điện thoại hợp lệ"><br>
        <% if (errors.get("phone") != null) { %>
            <div class="error-msg"><%= errors.get("phone") %></div>
        <% } %>

        <label for="username">Tên đăng nhập:</label><br>
        <input type="text" id="username" name="username" value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>" required><br>
        <% if (errors.get("username") != null) { %>
            <div class="error-msg"><%= errors.get("username") %></div>
        <% } %>

        <label for="password">Mật khẩu:</label><br>
        <input type="password" id="password" name="password" required minlength="6"><br>
        <% if (errors.get("password") != null) { %>
            <div class="error-msg"><%= errors.get("password") %></div>
        <% } %>

        <label for="confirmPassword">Nhập lại mật khẩu:</label><br>
        <input type="password" id="confirmPassword" name="confirmPassword" required minlength="6"><br>
        <% if (errors.get("confirmPassword") != null) { %>
            <div class="error-msg"><%= errors.get("confirmPassword") %></div>
        <% } %>

        <label for="cvFile">Nộp CV (PDF, DOC, DOCX, tối đa 2MB):</label><br>
        <input type="file" id="cvFile" name="cvFile" accept=".pdf,.doc,.docx" required><br>
        <% if (errors.get("cvFile") != null) { %>
            <div class="error-msg"><%= errors.get("cvFile") %></div>
        <% } %>

        <button type="submit">Đăng ký & Nộp CV</button>
    </form>
</body>
</html> 