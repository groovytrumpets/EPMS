<%@ page import="DAO.FormSubmissionDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%
    FormSubmissionDAO dao = new FormSubmissionDAO();
    List<Object[]> list = dao.getPendingSubmissionsWithUser();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Pending Leave Requests</title>
    <meta charset="UTF-8">
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #eee; }
    </style>
</head>
<body>
    <h2>Pending Leave Requests</h2>
    <table>
        <tr>
            <th>Full Name</th>
            <th>Email</th>
            <th>Phone Number</th>
            <th>Reason for Leave</th>
            <th>Attachment</th>
            <th>Actions</th>
        </tr>
        <% for (Object[] row : list) {
            // The original string was likely "??n xin ngh?".
            // Only show leave requests.
            if (!"Leave Request".equals(row[6])) continue;
        %>
        <tr>
            <td><%= Objects.toString(row[1], "") %></td>
            <td><%= Objects.toString(row[2], "") %></td>
            <td><%= Objects.toString(row[3], "") %></td>
            <td><%= Objects.toString(row[4], "") %></td>
            <td><a href="<%= request.getContextPath() + "/" + row[5] %>" target="_blank">View File</a></td>
            <td>
                <form action="ApproveLeaveServlet" method="post" style="display:inline;">
                    <input type="hidden" name="submissionId" value="<%= row[0] %>" />
                    <input type="hidden" name="userId" value="<%= row[7] %>" />
                    <button type="submit">Approve</button>
                </form>
                <form action="RejectLeaveServlet" method="post" style="display:inline; margin-left: 8px;">
                    <input type="hidden" name="submissionId" value="<%= row[0] %>" />
                    <button type="submit" style="background:#f55;color:#fff;">Reject</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>