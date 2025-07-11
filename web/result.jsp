<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Kết quả thi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <h2>Kết quả bài thi</h2>

    <div class="alert alert-info mt-4">
        Bạn trả lời đúng: <strong><%= request.getAttribute("correct") %></strong> /
        <%= request.getAttribute("total") %> <br>
        Điểm của bạn: <strong><%= request.getAttribute("mark") %></strong> / 100
    </div>

    <%
        String msg = (String) request.getAttribute("message");
        if (msg != null) {
    %>
        <div class="alert <%= msg.contains("kích hoạt") ? "alert-success" : "alert-danger" %>">
            <%= msg %>
        </div>
    <% } %>

    <a href="myTests" class="btn btn-primary mt-3">Quay lại danh sách bài test</a>
</div>

</body>
</html>
