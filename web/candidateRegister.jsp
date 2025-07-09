<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký Ứng viên - Nộp CV</title>
    <link rel="stylesheet" href="assets/assets/bootstrap.min.css">
    <script>
        function validateForm() {
            var fileInput = document.getElementById('cvFile');
            var filePath = fileInput.value;
            var allowedExtensions = /\.(pdf|doc|docx)$/i;
            var maxSize = 2 * 1024 * 1024; // 2MB
            if (!allowedExtensions.exec(filePath)) {
                alert('Chỉ cho phép file PDF, DOC, DOCX!');
                fileInput.value = '';
                return false;
            }
            if (fileInput.files[0].size > maxSize) {
                alert('Dung lượng file tối đa là 2MB!');
                fileInput.value = '';
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="container mt-5">
    <h2>Đăng ký Ứng viên & Nộp CV</h2>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getAttribute("message") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("message") %></div>
    <% } %>
    <form action="CandidateRegisterServlet" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
        <div class="mb-3">
            <label for="fullname" class="form-label">Họ và tên</label>
            <input type="text" class="form-control" id="fullname" name="fullname" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="mb-3">
            <label for="phone" class="form-label">Số điện thoại</label>
            <input type="text" class="form-control" id="phone" name="phone" required pattern="[0-9]{10,11}">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Mật khẩu</label>
            <input type="password" class="form-control" id="password" name="password" required minlength="6">
        </div>
        <div class="mb-3">
            <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required minlength="6">
        </div>
        <div class="mb-3">
            <label for="cvFile" class="form-label">Nộp CV (PDF, DOC, DOCX, tối đa 2MB)</label>
            <input type="file" class="form-control" id="cvFile" name="cvFile" accept=".pdf,.doc,.docx" required>
        </div>
        <button type="submit" class="btn btn-primary">Đăng ký & Nộp CV</button>
    </form>
</div>
<script>
    // Kiểm tra xác nhận mật khẩu
    document.querySelector('form').addEventListener('submit', function(e) {
        var pw = document.getElementById('password').value;
        var cpw = document.getElementById('confirmPassword').value;
        if (pw !== cpw) {
            alert('Mật khẩu xác nhận không khớp!');
            e.preventDefault();
        }
    });
</script>
</body>
</html> 