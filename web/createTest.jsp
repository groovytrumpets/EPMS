<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create Test</title>
</head>
<body>
<h2>Create Test</h2>

<form action="createTest" method="post">
    User ID:<br>
    <input type="number" name="userId" required><br><br>

    Title:<br>
    <input type="text" name="title" required><br><br>

    Timer (minutes):<br>
    <input type="number" name="timer" required><br><br>

    Deadline:<br>
    <input type="datetime-local" name="deadline" required><br><br>

    <h3>Questions:</h3>
    <div id="questions-container">
        <div class="question-block">
            Question:<br>
            <textarea name="questionText" rows="5" cols="60" required></textarea><br>
            Correct Answer (number):<br>
            <input type="number" name="answerIndex" required><br><br>
        </div>
    </div>

    <button type="button" onclick="addQuestion()">Add another question</button><br><br>
    <input type="submit" value="Save Test">
</form>

<script>
    function addQuestion() {
        var container = document.getElementById("questions-container");
        var block = document.createElement("div");
        block.className = "question-block";
        block.innerHTML = `
            <hr>
            Question:<br>
            <textarea name="questionText" rows="5" cols="60" required></textarea><br>
            Correct Answer (number):<br>
            <input type="number" name="answerIndex" required><br><br>
        `;
        container.appendChild(block);
    }
</script>

</body>
</html>
