<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Backup Result</title>
    </head>
    <body>
        <h2>Database Backup Result</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <c:if test="${not empty backupPath}">
        <p>✅ Backup saved to local: <strong>${backupPath}</strong></p>
    </c:if>

    <c:if test="${not empty cloudinaryUrl}">
        <p>☁️ Download from Cloudinary: 
            <a href="${cloudinaryUrl}" target="_blank">${cloudinaryUrl}</a>
        </p>
    </c:if>

    <br>
    <a href="home">Back to Home</a>
</body>
</html>
