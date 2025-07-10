<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Apply for Leave</title>
    <link rel="stylesheet" href="assets/assets/bootstrap.min.css">
    <script>
        function validateLeaveFile() {
            var fileInput = document.getElementById('leaveFile');
            var filePath = fileInput.value;
            var allowedExtensions = /\.(pdf|doc|docx)$/i;
            var maxSize = 2 * 1024 * 1024; // 2MB
            if (!allowedExtensions.exec(filePath)) {
                alert('Only PDF, DOC, DOCX files are allowed!');
                fileInput.value = '';
                return false;
            }
            if (fileInput.files[0].size > maxSize) {
                alert('Maximum file size is 2MB!');
                fileInput.value = '';
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="container mt-5" style="max-width: 500px;">
    <h2 class="mb-4">Apply for Leave</h2>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getAttribute("message") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("message") %></div>
    <% } %>
    <form action="LeaveFileUploadServlet" method="post" enctype="multipart/form-data" onsubmit="return validateLeaveFile()">
        <div class="mb-3">
            <label for="leaveFile" class="form-label">Select leave application file (PDF, DOC, DOCX, max 2MB)</label>
            <input type="file" class="form-control" id="leaveFile" name="leaveFile" accept=".pdf,.doc,.docx" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit Application</button>
    </form>
</div>
</body>
</html>