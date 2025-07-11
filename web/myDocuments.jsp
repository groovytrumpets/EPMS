<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Document" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>My Documents</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">My Uploaded Documents</h2>
        <a href="uploadDocument.jsp" class="btn btn-success">
            + Upload New Document
        </a>
    </div>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Title</th>
                    <th>Type</th>
                    <th>Upload Date</th>
                    <th>Download</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Document> docs = (List<Document>) request.getAttribute("docs");
                if (docs != null && !docs.isEmpty()) {
                    for (Document doc : docs) {
            %>
                <tr>
                    <td><%= doc.getTitle() %></td>
                    <td><%= doc.getType() %></td>
                    <td><%= doc.getUploadDate() %></td>
                    <td>
                        <a href="downloadDocument?id=<%= doc.getDocumentId() %>" class="btn btn-primary btn-sm">
                            Download
                        </a>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="4" class="text-center text-muted">
                        Chưa có tài liệu nào được upload.
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
