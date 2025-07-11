<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Upload Document</title>
</head>
<body>
<h2>Upload Document</h2>

<form action="uploadDocument" method="post" enctype="multipart/form-data">
    Title: <input type="text" name="title" required/><br/>
    Type: <input type="text" name="type" required/><br/>
    File: <input type="file" name="file" required/><br/>
    <input type="submit" value="Upload"/>
</form>

<% String msg = (String) request.getAttribute("message");
   if (msg != null) { %>
    <p style="color: red;"><%= msg %></p>
<% } %>

</body>
</html>
