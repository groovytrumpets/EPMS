<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.FormTemplate" %>

<html>
<head>
    <title>Form List</title>
</head>
<body>
<h2>Available Forms</h2>

<table border="1">
    <tr>
        <th>Title</th>
        <th>Status</th>
        <th>Download</th>
    </tr>
<%
    List<FormTemplate> forms = (List<FormTemplate>) request.getAttribute("forms");
    if (forms != null) {
        for (FormTemplate form : forms) {
%>
    <tr>
        <td><%= form.getTitle() %></td>
        <td><%= form.getStatus() %></td>
        <td>
            <a href="downloadForm?id=<%= form.getTemplateId() %>">Download</a>
        </td>
    </tr>
<%
        }
    }
%>
</table>

</body>
</html>
