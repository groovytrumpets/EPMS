<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>

<html>
<head>
    <title>Làm Bài Test</title>
</head>
<body>

<h2>Làm Bài Test</h2>

<form action="submitTest" method="post">
    <input type="hidden" name="testId" value="<%= request.getAttribute("testId") %>">

<%
    List<Question> qs = (List<Question>) request.getAttribute("questions");
    int i = 1;
    for (Question q : qs) {
%>
    <h3>Câu <%= i %></h3>
    <p><%= q.getQuestion() %></p>
    Trả lời (nhập số thứ tự đúng): <input type="number" name="answer_<%= q.getQuestionId() %>" required><br><br>
<%
        i++;
    }
%>

    <input type="submit" value="Nộp bài">
</form>

</body>
</html>
