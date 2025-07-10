<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Candidate Registration - Submit CV</title>
    <link rel="stylesheet" href="assets/assets/bootstrap.min.css">
    <script>
        function validateForm() {
            let hasError = false;
            // Xóa trạng thái lỗi cũ
            document.querySelectorAll('.form-control').forEach(e => e.classList.remove('is-invalid'));
            document.querySelectorAll('.invalid-feedback').forEach(e => e.textContent = '');

            var fullname = document.getElementById('fullname').value.trim();
            var email = document.getElementById('email').value.trim();
            var phone = document.getElementById('phone').value.trim();
            var password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirmPassword').value;
            var fileInput = document.getElementById('cvFile');
            var filePath = fileInput.value;
            var allowedExtensions = /\.(pdf|doc|docx|jpg)$/i;
            var maxSize = 5 * 1024 * 1024; // 5MB

            // Validate tên
            var nameRegex = /^[A-Za-zÀ-ỹà-ỹ\s]+$/;
            if (!nameRegex.test(fullname)) {
                document.getElementById('fullname').classList.add('is-invalid');
                document.getElementById('fullname-error').textContent = 'Tên không được chứa ký tự đặc biệt!';
                hasError = true;
            } else if (fullname.includes('  ')) {
                document.getElementById('fullname').classList.add('is-invalid');
                document.getElementById('fullname-error').textContent = 'Tên chỉ được phép có 1 khoảng trắng giữa các từ!';
                hasError = true;
            } else if (fullname.split(' ').length < 2) {
                document.getElementById('fullname').classList.add('is-invalid');
                document.getElementById('fullname-error').textContent = 'Tên phải có ít nhất 2 từ!';
                hasError = true;
            }
            // Validate email
            var emailRegex = /^[A-Za-z0-9._%+-]+@gmail\.com$/;
            if (!emailRegex.test(email)) {
                document.getElementById('email').classList.add('is-invalid');
                document.getElementById('email-error').textContent = 'Email phải đúng định dạng @gmail.com!';
                hasError = true;
            }
            // Validate số điện thoại
            var phoneRegex = /^0[0-9]{9}$/;
            if (!phoneRegex.test(phone)) {
                document.getElementById('phone').classList.add('is-invalid');
                document.getElementById('phone-error').textContent = 'Số điện thoại phải bắt đầu từ 0, gồm 10 chữ số và không có ký tự đặc biệt!';
                hasError = true;
            }
            // Validate mật khẩu xác nhận
            if (password !== confirmPassword) {
                document.getElementById('confirmPassword').classList.add('is-invalid');
                document.getElementById('confirmPassword-error').textContent = 'Mật khẩu xác nhận không khớp!';
                hasError = true;
            }
            // Validate file CV
            if (!allowedExtensions.exec(filePath)) {
                document.getElementById('cvFile').classList.add('is-invalid');
                document.getElementById('cvFile-error').textContent = 'Chỉ cho phép file PDF, DOC, DOCX, JPG!';
                hasError = true;
            } else if (fileInput.files[0] && fileInput.files[0].size > maxSize) {
                document.getElementById('cvFile').classList.add('is-invalid');
                document.getElementById('cvFile-error').textContent = 'Dung lượng file tối đa là 5MB!';
                hasError = true;
            }
            if (document.getElementById('email').dataset.duplicate === 'true') {
                document.getElementById('email').classList.add('is-invalid');
                document.getElementById('email-error').textContent = 'Email đã tồn tại!';
                hasError = true;
            }
            if (document.getElementById('phone').dataset.duplicate === 'true') {
                document.getElementById('phone').classList.add('is-invalid');
                document.getElementById('phone-error').textContent = 'Số điện thoại đã tồn tại!';
                hasError = true;
            }
            return !hasError;
        }

        // AJAX check for duplicate email
        function checkDuplicateEmail() {
            var email = document.getElementById('email').value.trim();
            if (email.length === 0) return;
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'check-duplicate?email=' + encodeURIComponent(email), true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var isDup = xhr.responseText === 'true';
                    var emailInput = document.getElementById('email');
                    emailInput.dataset.duplicate = isDup;
                    var msg = document.getElementById('email-dup-msg');
                    if (isDup) {
                        msg.textContent = 'Email already exists!';
                        msg.style.color = 'red';
                    } else {
                        msg.textContent = '';
                    }
                }
            };
            xhr.send();
        }

        // AJAX check for duplicate phone number
        function checkDuplicatePhone() {
            var phone = document.getElementById('phone').value.trim();
            if (phone.length === 0) return;
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'check-duplicate?phone=' + encodeURIComponent(phone), true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var isDup = xhr.responseText === 'true';
                    var phoneInput = document.getElementById('phone');
                    phoneInput.dataset.duplicate = isDup;
                    var msg = document.getElementById('phone-dup-msg');
                    if (isDup) {
                        msg.textContent = 'Phone number already exists!';
                        msg.style.color = 'red';
                    } else {
                        msg.textContent = '';
                    }
                }
            };
            xhr.send();
        }
    </script>
</head>
<body>
<div class="container mt-5">
    <h2>Candidate Registration & CV Submission</h2>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getAttribute("message") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("message") %></div>
    <% } %>
    <form action="CandidateRegisterServlet" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
        <div class="mb-3">
            <label for="fullname" class="form-label">Họ và tên</label>
            <input type="text" class="form-control" id="fullname" name="fullname" required value="<%= request.getParameter("fullname") != null ? request.getParameter("fullname") : "" %>">
            <div class="invalid-feedback" id="fullname-error"></div>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" required onblur="checkDuplicateEmail()" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
            <span id="email-dup-msg"></span>
            <div class="invalid-feedback" id="email-error"></div>
        </div>
        <div class="mb-3">
            <label for="phone" class="form-label">Số điện thoại</label>
            <input type="text" class="form-control" id="phone" name="phone" required pattern="[0-9]{10,11}" onblur="checkDuplicatePhone()" value="<%= request.getParameter("phone") != null ? request.getParameter("phone") : "" %>">
            <span id="phone-dup-msg"></span>
            <div class="invalid-feedback" id="phone-error"></div>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Mật khẩu</label>
            <input type="password" class="form-control" id="password" name="password" required minlength="6" value="<%= request.getParameter("password") != null ? request.getParameter("password") : "" %>">
        </div>
        <div class="mb-3">
            <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required minlength="6" value="<%= request.getParameter("confirmPassword") != null ? request.getParameter("confirmPassword") : "" %>">
            <div class="invalid-feedback" id="confirmPassword-error"></div>
        </div>
        <div class="mb-3">
            <label for="cvFile" class="form-label">Nộp CV (PDF, DOC, DOCX, JPG, tối đa 5MB)</label>
            <input type="file" class="form-control" id="cvFile" name="cvFile" accept=".pdf,.doc,.docx,.jpg" required>
            <div class="invalid-feedback" id="cvFile-error"></div>
        </div>
        <button type="submit" class="btn btn-primary">Đăng ký & Nộp CV</button>
    </form>
</div>
<script>
    // Check confirm password
    document.querySelector('form').addEventListener('submit', function(e) {
        var pw = document.getElementById('password').value;
        var cpw = document.getElementById('confirmPassword').value;
        if (pw !== cpw) {
            alert('Confirm password does not match!');
            e.preventDefault();
        }
    });
</script>
</body>
</html>