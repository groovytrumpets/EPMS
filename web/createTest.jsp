<form action="createTest" method="post">
    <label>
        <input type="checkbox" name="isForGroup" value="yes" onclick="toggleGroup(this)">
        T?o test cho nhóm Employee?
    </label>
    <br><br>

    <div id="groupSection" style="display: none;">
        Role ID (ví d? Employee = 3):<br>
        <input type="number" name="roleId" value="3"><br><br>
    </div>

    <div id="userSection">
        User ID:<br>
        <input type="number" name="userId"><br><br>
    </div>

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
    function toggleGroup(checkbox) {
        document.getElementById("groupSection").style.display = checkbox.checked ? "block" : "none";
        document.getElementById("userSection").style.display = checkbox.checked ? "none" : "block";
    }

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
