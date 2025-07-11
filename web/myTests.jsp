<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.TestSession" %>

<html>
<head>
    <title>My Tests</title>
</head>
<body>
<h2>Danh sách bài test của bạn</h2>

<table border="1" cellpadding="5">
    <tr>
        <th>Title</th>
        <th>Deadline</th>
        <th>Thời gian làm bài (phút)</th>
        <th>Trạng thái</th>
        <th>Hành động</th>
    </tr>
<%
    List<TestSession> tests = (List<TestSession>) request.getAttribute("tests");
    if (tests != null) {
        for (TestSession t : tests) {
%>
    <tr>
        <td><%= t.getTitle() %></td>
        <td><%= t.getDeadline() %></td>
        <td><%= t.getTimer() %></td>
        <td><%= t.getStatus() %></td>
        <td>
            <a href="startTest?testId=<%= t.getTestId() %>">Làm bài</a>
        </td>
    </tr>
<%
        }
    }
%>
</table>

</body>
</html>
