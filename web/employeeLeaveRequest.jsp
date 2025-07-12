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
        <title>Request Leave</title>
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
                <h3 class="text-center mb-4">📝 Submit Leave Request</h3>

                <% if (success != null) { %>
                <div class="alert alert-success"><%= success %></div>
                <% } %>

                <form action="EmployeeLeaveRequestServlet" method="post" enctype="multipart/form-data">
                    <!-- Hidden field for type -->
                    <input type="hidden" name="type" value="Leave Request">

                    <div class="mb-3">
                        <label for="purpose" class="form-label">Reason for Leave</label>
                        <textarea class="form-control" id="purpose" name="purpose" rows="3" required><%= request.getParameter("purpose") != null ? request.getParameter("purpose") : "" %></textarea>
                        <% if (errors.get("purpose") != null) { %>
                        <div class="error-msg"><%= errors.get("purpose") %></div>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="note" class="form-label">Note (optional)</label>
                        <textarea class="form-control" id="note" name="note" rows="2"><%= request.getParameter("note") != null ? request.getParameter("note") : "" %></textarea>
                        <% if (errors.get("note") != null) { %>
                        <div class="error-msg"><%= errors.get("note") %></div>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="fileLink" class="form-label">Attach File (PDF, DOC, DOCX - max 2MB)</label>
                        <input class="form-control" type="file" id="fileLink" name="fileLink" accept=".pdf,.doc,.docx" required>
                        <% if (errors.get("fileLink") != null) { %>
                        <div class="error-msg"><%= errors.get("fileLink") %></div>
                        <% } %>
                    </div>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Submit Request</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
