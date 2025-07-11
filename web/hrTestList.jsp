<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>HR - Quản Lý Test Schedule</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <h2 class="mb-4">Danh sách Test Schedule</h2>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Schedule ID</th>
                    <th>Employee</th>
                    <th>Title</th>
                    <th>Deadline</th>
                    <th>Status</th>
                    <th>Mark</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Object[]> schedules = (List<Object[]>) request.getAttribute("schedules");
                    for (Object[] row : schedules) {
                        int scheduleId = (Integer) row[0];
                        String fullName = (String) row[1];
                        String title = (String) row[2];
                        java.sql.Timestamp deadline = (java.sql.Timestamp) row[3];
                        String status = (String) row[4];
                        int mark = (Integer) row[5];
                        int userId = (Integer) row[6];
                        int testId = (Integer) row[7];

                        String badgeClass = "secondary";
                        if ("done".equalsIgnoreCase(status)) {
                            badgeClass = "success";
                        } else if ("not_started".equalsIgnoreCase(status)) {
                            badgeClass = "warning";
                        } else if ("pending".equalsIgnoreCase(status)) {
                            badgeClass = "info";
                        }
                %>
                <tr>
                    <td><%= scheduleId %></td>
                    <td><%= fullName %></td>
                    <td><%= title %></td>
                    <td><%= deadline %></td>
                    <td>
                        <span class="badge bg-<%= badgeClass %>">
                            <%= status %>
                        </span>
                    </td>
                    <td>
                        <% if ("done".equalsIgnoreCase(status)) { %>
                            <span class="text-success fw-bold"><%= mark %> / 100</span>
                        <% } else { %>
                            <span class="text-muted">-</span>
                        <% } %>
                    </td>
                    <td>
                        <a href="viewTestDetail?testId=<%= testId %>" class="btn btn-primary btn-sm">
                            Xem chi tiết
                        </a>
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
