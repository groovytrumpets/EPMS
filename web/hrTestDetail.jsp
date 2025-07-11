<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài test</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <h2 class="mb-4">Chi tiết bài test</h2>

    <%
        List<Question> qs = (List<Question>) request.getAttribute("questions");
        int i = 1;
        for (Question q : qs) {
    %>
        <div class="mb-4 border rounded p-4 bg-white shadow-sm">
            <h5 class="mb-3 text-primary">Câu <%= i %></h5>
            <p class="mb-2"><%= q.getQuestion() %></p>
            <p class="mb-0">
                <strong>Đáp án đúng:</strong> 
                <span class="text-success"><%= q.getAnswer() %></span>
            </p>
        </div>
    <%
            i++;
        }
    %>

    <a href="viewTests" class="btn btn-secondary mt-3">
        ← Quay lại danh sách
    </a>
</div>

</body>
</html>
