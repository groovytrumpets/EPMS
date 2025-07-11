<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Leave Request</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <h2>Submit a Leave Request</h2>
    <form action="EmployeeLeaveRequestServlet" method="post" enctype="multipart/form-data">
        <label for="reason">Reason for leave:</label><br>
        <textarea id="reason" name="reason" required rows="3" cols="40"></textarea><br><br>

        <label for="leaveFile">Attach file (PDF, DOC, DOCX, max 2MB):</label><br>
        <input type="file" id="leaveFile" name="leaveFile" accept=".pdf,.doc,.docx" required><br><br>

        <button type="submit">Submit</button>
    </form>
</body>
</html>