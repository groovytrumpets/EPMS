<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Approve Candidate CV</title>
    <link rel="stylesheet" href="assets/assets/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Candidate CV List</h2>
    <table class="table table-bordered mt-4">
        <thead>
            <tr>
                <th>ID</th>
                <th>Applicant (userId)</th>
                <th>CV File</th>
                <th>Submission Date</th>
                <th>Status</th>
                <th>Note</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <% java.util.List<model.FormSubmission> submissions = (java.util.List<model.FormSubmission>) request.getAttribute("submissions");
           if (submissions != null) for (model.FormSubmission s : submissions) { %>
            <tr>
                <td><%= s.getSubmissionId() %></td>
                <td><%= s.getUserId() %></td>
                <td><a href="uploads/cv/<%= s.getFileLink() %>" target="_blank">View File</a></td>
                <td><%= s.getCreateDate() %></td>
                <td><%= s.getStatus() %></td>
                <td><%= s.getNote() == null ? "" : s.getNote() %></td>
                <td>
                <% if ("PENDING".equals(s.getStatus())) { %>
                    <form action="cv-submissions" method="post" style="display:inline-block;">
                        <input type="hidden" name="submissionId" value="<%= s.getSubmissionId() %>">
                        <input type="hidden" name="action" value="approve">
                        <input type="text" name="note" placeholder="Note" class="form-control mb-1">
                        <button type="submit" class="btn btn-success btn-sm">Approve</button>
                    </form>
                    <form action="cv-submissions" method="post" style="display:inline-block;">
                        <input type="hidden" name="submissionId" value="<%= s.getSubmissionId() %>">
                        <input type="hidden" name="action" value="reject">
                        <input type="text" name="note" placeholder="Note" class="form-control mb-1">
                        <button type="submit" class="btn btn-danger btn-sm">Reject</button>
                    </form>
                <% } %>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>