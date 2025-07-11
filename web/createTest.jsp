<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Test</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <h2 class="mb-4">Create New Test</h2>

    <form action="createTest" method="post" id="createTestForm" class="border p-4 bg-white rounded shadow-sm">
        
        <div class="form-check form-switch mb-3">
            <input class="form-check-input" type="checkbox" id="isForGroup" name="isForGroup" value="yes" onclick="toggleGroup(this)">
            <label class="form-check-label" for="isForGroup">Make a test for all employees?</label>
        </div>

        <div id="groupSection" class="mb-3" style="display: none;">
            <label class="form-label">Role ID (e.g. Employee = 3):</label>
            <input type="number" class="form-control" name="roleId" value="3">
        </div>

        <div id="userSection" class="mb-3">
            <label class="form-label">User ID:</label>
            <input type="number" class="form-control" name="userId">
        </div>

        <div class="mb-3">
            <label class="form-label">Title:</label>
            <input type="text" class="form-control" name="title" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Timer (minutes):</label>
            <input type="number" class="form-control" name="timer" required>
        </div>

        <div class="mb-4">
            <label class="form-label">Deadline:</label>
            <input type="datetime-local" class="form-control" name="deadline" required>
        </div>

        <h4 class="mb-3">Questions:</h4>
        <div id="questions-container">
            <div class="question-block mb-4 border rounded p-3 bg-light">
                <label class="form-label">Question:</label>
                <textarea class="form-control mb-2" name="questionText" rows="3" required></textarea>
                <label class="form-label">Correct Answer (number):</label>
                <input type="number" class="form-control" name="answerIndex" required>
            </div>
        </div>

        <button type="button" class="btn btn-outline-primary mb-3" onclick="addQuestion()">
            + Add another question
        </button>

        <br>
        <button type="submit" class="btn btn-success">Save Test</button>
    </form>
</div>

<script>
    function toggleGroup(checkbox) {
        document.getElementById("groupSection").style.display = checkbox.checked ? "block" : "none";
        document.getElementById("userSection").style.display = checkbox.checked ? "none" : "block";
    }

    function addQuestion() {
        var container = document.getElementById("questions-container");
        var block = document.createElement("div");
        block.className = "question-block mb-4 border rounded p-3 bg-light";
        block.innerHTML = `
            <label class="form-label">Question:</label>
            <textarea class="form-control mb-2" name="questionText" rows="3" required></textarea>
            <label class="form-label">Correct Answer (number):</label>
            <input type="number" class="form-control" name="answerIndex" required>
        `;
        container.appendChild(block);
    }
</script>

</body>
</html>
