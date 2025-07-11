<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.FormTemplate" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <h2 class="mb-4">Danh sách Form có sẵn</h2>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Tiêu đề</th>
                    <th>Trạng thái</th>
                    <th>Tải xuống</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<FormTemplate> forms = (List<FormTemplate>) request.getAttribute("forms");
                    if (forms != null) {
                        for (FormTemplate form : forms) {
                            String badgeClass = "secondary";
                            if ("created".equalsIgnoreCase(form.getStatus())) {
                                badgeClass = "primary";
                            } else if ("pending".equalsIgnoreCase(form.getStatus())) {
                                badgeClass = "warning";
                            } else if ("approved".equalsIgnoreCase(form.getStatus())) {
                                badgeClass = "success";
                            }
                %>
                <tr>
                    <td><%= form.getTitle() %></td>
                    <td>
                        <span class="badge bg-<%= badgeClass %>">
                            <%= form.getStatus() %>
                        </span>
                    </td>
                    <td>
                        <a href="downloadForm?id=<%= form.getTemplateId() %>" class="btn btn-sm btn-success">
                            Download
                        </a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
