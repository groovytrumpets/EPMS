<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.TestSession" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>My Tests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <h2 class="mb-4">Danh sách bài test của bạn</h2>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Tiêu đề</th>
                    <th>Deadline</th>
                    <th>Thời gian (phút)</th>
                    <th>Trạng thái</th>
                    <th>Điểm</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<TestSession> tests = (List<TestSession>) request.getAttribute("tests");
                if (tests != null) {
                    for (TestSession t : tests) {
                        String status = t.getStatus();
                        String badgeClass = "secondary";
                        if ("done".equalsIgnoreCase(status)) {
                            badgeClass = "success";
                        } else if ("not_started".equalsIgnoreCase(status)) {
                            badgeClass = "warning";
                        }
            %>
                <tr>
                    <td><%= t.getTitle() %></td>
                    <td><%= t.getDeadline() %></td>
                    <td><%= t.getTimer() %></td>
                    <td>
                        <span class="badge bg-<%= badgeClass %>">
                            <%= t.getStatus() %>
                        </span>
                    </td>
                    <td>
                        <% if ("done".equalsIgnoreCase(t.getStatus())) { %>
                            <span class="text-success fw-bold"><%= t.getMark() %> / 100</span>
                        <% } else { %>
                            <span class="text-muted">-</span>
                        <% } %>
                    </td>
                    <td>
                        <% if (!"done".equalsIgnoreCase(t.getStatus())) { %>
                            <a href="startTest?testId=<%= t.getTestId() %>" class="btn btn-primary btn-sm">
                                Làm bài
                            </a>
                        <% } else { %>
                            <span class="text-success">Đã hoàn thành</span>
                        <% } %>
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
