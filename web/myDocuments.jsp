<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Document" %>

<html>
<head>
    <title>My Documents</title>
</head>
<body>
<h2>My Uploaded Documents</h2>

<a href="uploadDocument.jsp">Upload New Document</a>

<table border="1">
    <tr>
        <th>Title</th>
        <th>Type</th>
        <th>Upload Date</th>
        <th>Download</th>
    </tr>
<%
    List<Document> docs = (List<Document>) request.getAttribute("docs");
    if (docs != null) {
        for (Document doc : docs) {
%>
    <tr>
        <td><%= doc.getTitle() %></td>
        <td><%= doc.getType() %></td>
        <td><%= doc.getUploadDate() %></td>
        <td>
            <a href="downloadDocument?id=<%= doc.getDocumentId() %>">Download</a>
        </td>
    </tr>
<%
        }
    }
%>
</table>

</body>
</html>
