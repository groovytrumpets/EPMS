<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>
<%@ page import="model.TestSession" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Làm Bài Test</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .timer-box {
            font-size: 24px;
            font-weight: bold;
            color: #dc3545;
            background-color: #f8d7da;
            border-radius: 5px;
            padding: 10px 20px;
            display: inline-block;
        }
    </style>

    <script>
        let totalSeconds;

        function startCountdown(minutes) {
            totalSeconds = minutes * 60;
            updateDisplay();
            setInterval(function () {
                totalSeconds--;
                updateDisplay();

                if (totalSeconds <= 0) {
                    alert("Hết giờ! Bài sẽ được tự động nộp.");
                    document.getElementById("testForm").submit();
                }
            }, 1000);
        }

        function updateDisplay() {
            let mins = Math.floor(totalSeconds / 60);
            let secs = totalSeconds % 60;
            document.getElementById("timer").innerText = 
                (mins < 10 ? "0" : "") + mins + ":" + (secs < 10 ? "0" : "") + secs;
        }
    </script>
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Làm Bài Test</h2>
        <div class="timer-box">
            Thời gian còn lại: <span id="timer"></span>
        </div>
    </div>

    <form id="testForm" action="submitTest" method="post" class="bg-white p-4 rounded shadow-sm">
        <input type="hidden" name="testId" value="<%= request.getAttribute("testId") %>">

        <%
            List<Question> qs = (List<Question>) request.getAttribute("questions");
            int i = 1;
            for (Question q : qs) {
        %>
            <div class="mb-4 border rounded p-3 bg-light">
                <h5 class="mb-2">Câu <%= i %></h5>
                <p><%= q.getQuestion() %></p>
                <label>Trả lời (nhập số thứ tự đúng):</label>
                <input type="number" class="form-control w-25" name="answer_<%= q.getQuestionId() %>" required>
            </div>
        <%
                i++;
            }
        %>

        <button type="submit" class="btn btn-success">Nộp bài</button>
    </form>
</div>

<script>
    let timerMinutes = <%= request.getAttribute("timer") %>;
    startCountdown(timerMinutes);
</script>

</body>
</html>
