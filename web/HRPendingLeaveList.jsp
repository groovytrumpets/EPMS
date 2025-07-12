<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Pending Leave Requests</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="assets/css/assets.css">
        <link rel="stylesheet" type="text/css" href="assets/css/typography.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="assets/css/dashboard.css">
        <link rel="stylesheet" type="text/css" href="assets/css/color/color-1.css">
    </head>
    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- header -->
        <jsp:include page="AdminPage/headerAdmin.jsp" />

        <!-- Main container -->
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Leave Management</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                        <li>Leave</li>
                        <li>Pending Requests</li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4>Pending Leave Requests</h4>
                            </div>
                            <div class="widget-inner">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>Full Name</th>
                                            <th>Email</th>
                                            <th>Phone</th>
                                            <th>Reason</th>
                                            <th>Attachment</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="row" items="${leaveList}">
                                        <c:if test="${row[6] eq 'pending'}">
                                            <tr>
                                                <td>${row[1]}</td>
                                                <td>${row[2]}</td>
                                                <td>${row[3]}</td>
                                                <td>${row[4]}</td>
                                                <td>
                                                    <a href="${row[5]}" target="_blank">View File</a>
                                                </td>
                                                <td>
                                                    
                                                        
                                                        <a class="btn green btn-sm" href="ApproveLeaveServlet?submissionId=${row[0]}&userId=${row[7]}" target="target"><i class="ti-check"></i></a>
                                                        <a class="btn red btn-sm" href="RejectLeaveServlet?submissionId=${row[0]}&userId=${row[7]}" target="target"><i class="ti-close"></i></a>
                                                       
                                                    
                                                    
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <div class="ttr-overlay"></div>

        <!-- Scripts -->
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/functions.js"></script>
        <script src="assets/js/admin.js"></script>

    </body>
</html>
