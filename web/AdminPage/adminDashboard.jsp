<%-- 
    Document   : adminDashboard
    Created on : Jul 9, 2025, 4:37:45 PM
    Author     : asus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="Happy Programming">
        <meta name="format-detection" content="telephone=no">
        <title>Happy Programming</title>
        <link rel="icon" href="assets/images/faviconV2.png" type="image/x-icon" />
        <link rel="stylesheet" href="assets/css/assets.css">
        <link rel="stylesheet" href="assets/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" href="assets/css/typography.css">
        <link rel="stylesheet" href="assets/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/dashboard.css">
        <link rel="stylesheet" href="assets/css/color/color-1.css">
        <style>
            .new-user-list {
                max-height: 200px;
                overflow-y: auto;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <jsp:include page="headerAdmin.jsp" />
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Dashboard</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                        <li>Dashboard</li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4>User List</h4>
                            </div>
                            <div class="widget-inner">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Username</th>
                                                <th>Full Name</th>
                                                <th>Email</th>
                                                <th>Phone</th>
                                                <th>Gender</th>
                                                <th>Status</th>
                                                <th>Role ID</th>
                                                <th>Created At</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="u" items="${listuser}">
                                                <tr>
                                                    <td>${u.userId}</td>
                                                    <td>${u.userName}</td>
                                                    <td>${u.fullName}</td>
                                                    <td>${u.email}</td>
                                                    <td>${u.phone}</td>
                                                    <td>${u.gender}</td>
                                                    <td>${u.status}</td>
                                                    <td>${u.role.roleName}</td>
                                                    <td>${u.formattedCreateDate}</td>
                                                    <td>
                                                        <a href="togglestatus?userid=${u.userId}&status=${u.status eq 'active' ? 'inactive' : 'active'}" class="btn btn-sm btn-primary">
                                                            ${u.status eq 'active' ? 'Deactivate' : 'Activate'}
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </main>
        <div class="ttr-overlay"></div>

        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/vendors/bootstrap/js/popper.min.js"></script>
        <script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="assets/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="assets/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="assets/vendors/counter/waypoints-min.js"></script>
        <script src="assets/vendors/counter/counterup.min.js"></script>
        <script src="assets/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="assets/vendors/masonry/masonry.js"></script>
        <script src="assets/vendors/masonry/filter.js"></script>
        <script src="assets/vendors/owl-carousel/owl.carousel.js"></script>
        <script src='assets/vendors/scroll/scrollbar.min.js'></script>
        <script src="assets/js/functions.js"></script>
        <script src="assets/vendors/chart/chart.min.js"></script>
        <script src="assets/js/admin.js"></script>
        <script src='assets/vendors/calendar/moment.min.js'></script>
        <script src='assets/vendors/calendar/fullcalendar.js'></script>
    </body>
</html>
