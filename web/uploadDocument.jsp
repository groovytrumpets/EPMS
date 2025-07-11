<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Upload Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <h2 class="mb-4">Upload Document</h2>

    <div class="card p-4 shadow-sm">
        <form action="uploadDocument" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label class="form-label">Title</label>
                <input type="text" name="title" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Type</label>
                <input type="text" name="type" class="form-control" required>
            </div>

            <div class="mb-4">
                <label class="form-label">File</label>
                <input type="file" name="file" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-success">
                Upload
            </button>
        </form>

        <% 
            String msg = (String) request.getAttribute("message");
            if (msg != null) { 
        %>
            <div class="alert alert-danger mt-3" role="alert">
                <%= msg %>
            </div>
        <% } %>
    </div>
</div>

</body>
</html>
