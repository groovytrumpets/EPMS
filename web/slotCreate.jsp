<%-- 
    Document   : slotCreate
    Created on : 25 thg 10, 2024, 01:50:40
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <!-- Mirrored from educhamp.themetrades.com/demo/admin/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:08:15 GMT -->
    <head>

        <!-- META ============================================= -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />

        <!-- DESCRIPTION -->
        <meta name="description" content="Happy Programing" />

        <!-- OG -->
        <meta property="og:title" content="Happy Programing" />
        <meta property="og:description" content="Happy Programing" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="assets/images/faviconV2.png" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="assets/images/faviconV2.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>EPMS </title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--[if lt IE 9]>
        <script src="assets/js/html5shiv.min.js"></script>
        <script src="assets/js/respond.min.js"></script>
        <![endif]-->

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/assets.css">
        <link rel="stylesheet" type="text/css" href="assets/vendors/calendar/fullcalendar.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="assets/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="assets/css/color/color-1.css">

    </head>
    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- header start -->
        <jsp:include page="headerEmployee.jsp" />
        <!-- header end -->

        <!-- Left sidebar menu end -->

        <!--Main container start -->
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Dashboard</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                        <li>Dashboard</li>
                    </ul>
                </div>	
                <!-- Card -->
                <div style="display: flex;justify-content: center">
                    <c:if test="${not empty requestScope.error}">
                        <div class="alert alert-danger text-center d-inline-block" role="alert" >
                            ${requestScope.error}
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.mess}">
                        <a href="#" class="alert alert-success text-center d-inline-block" role="alert" >
                            ${requestScope.mess}
                        </a>
                    </c:if>
                    <c:if test="${empty requestScope.slotList}">
                        <a href="#" class="alert alert-primary text-center d-inline-block" role="alert" >
                            It looks like you don't have any slots. Please create a new slot to get started.
                        </a>
                    </c:if>
                </div>
                <!-- Card END -->
                <div class="row">
                    <!-- Your Profile Views Chart -->

                    <div class="col-lg-6 m-b30">
                        <div class="widget-box">
                            <div class="wc-title d-flex align-items-center">
                                <h4 class="d-inline-block">Create Slot:&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">+</a></h4>
                                <!--                                <div class="d-inline-block ml-auto">
                                                                    <a href="deleteslot?all=all&userId=${uFound}" class="btn button-layout red">Delete All</a>
                                                                </div>-->
                            </div>
                            <div class="widget-inner">
                                <div class="new-user-list" style="max-height: 600px; overflow-y: auto;" >
                                    <table class="table table-hover">
                                        <thead class="thead-light">
                                            <tr>
                                                <th scope="col">STT</th>
                                                <th scope="col">Start Time</th>
                                                <th scope="col">End Time</th>
                                                <th scope="col">Status</th>
                                                <th scope="col">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach items="${requestScope.slotList}" var="c"  varStatus="status">
                                                <tr>
                                                    <th class="align-middle" scope="row">${status.index + 1}</th>
                                                    <td class="align-middle">${fn:substring(c.startDate, 11, 16)}</td>
                                                    <td class="align-middle">${fn:substring(c.endDate, 11, 16)}</td>
                                                    <td class="align-middle">${c.status}</td>
                                                    <td class="align-middle" style="max-width: 200px;word-wrap: break-word;">
                                                        <span class="orders-btn">
                                                            <a href="#" class="btn button-sm orange" data-toggle="modal" data-target="#updateModal${c.workScheduleId}"><i class="ti-pencil"></i></a>
                                                        </span>
                                                        <span class="orders-btn">
                                                            <a href="#" onclick="delete1('${c.workScheduleId}', '${c.startDate}', '${c.endDate}')" class="btn button-sm red"><i class="ti-close"></i></a>
                                                        </span>



                                                        <div class="modal fade" id="updateModal${c.workScheduleId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                            <div class="modal-dialog modal-dialog-centered">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title" id="exampleModalLabel">Update Slot</h5>
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                    </div>

                                                                    <form action="updateslot" method="get">
                                                                        <input class="form-control" type="text" value="${uFound}" name="userId" hidden="">
                                                                        <input class="form-control" type="text" value="${c.workScheduleId}" name="slotId" hidden="">

                                                                        <div class="modal-body">
                                                                            <div class="row edit-profile m-b30">

                                                                                <div class="form-group col-8">
                                                                                    <label class="col-form-label">Slot</label>
                                                                                    <select name="slotTime" class="">
                                                                                        <option value="1" <c:if test="${fn:substring(c.startDate, 11, 16) == '08:00'}">selected</c:if>>Full-time: 08:00h – 17:00h</option>
                                                                                        <option value="2" <c:if test="${fn:substring(c.endDate, 11, 16) == '12:00'}">selected</c:if>>Partime: 08:00h – 12:00h</option>
                                                                                        <option value="3" <c:if test="${fn:substring(c.startDate, 11, 16) == '13:00'}">selected</c:if>>Partime: 13:00h – 17:00h</option>
                                                                                        </select>
                                                                                    </div>


                                                                                </div>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                                                <button type="submit" class="btn btn-primary">Save changes</button>
                                                                            </div>
                                                                        </form>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <!-- Button trigger modal -->
                                                        </td>     




                                                    </tr>
                                            </c:forEach>



                                        </tbody>
                                    </table>

                                </div>
                            </div>
                        </div>


                        <!-- Modal -->
                        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Create New Slot</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>

                                    <form action="createSlot" method="post">
                                        <input class="form-control" type="text" value="${uFound}" name="userId" hidden="">
                                        <div class="modal-body">
                                            <div class="row edit-profile m-b30">

                                                <div class="form-group col-12"style="text-align: center">
                                                    <label class="col-form-label">Slot</label>
                                                    <select name="slotTime" class="">
                                                        <option style="text-align: center" value="1">Full-time: 08:00h – 17:00h </option>
                                                        <option style="text-align: center" value="2">Partime: 08:00h – 12:00h </option>
                                                        <option style="text-align: center" value="3">Partime: 13:00h – 17:00h</option>
                                                    </select>
                                                </div>


                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                            <button type="submit" class="btn btn-primary">Save changes</button>
                                        </div>
                                    </form>

                                </div>
                            </div>
                        </div>

                    </div>
                    <!-- Your Profile Views Chart END-->
                    <div class="col-lg-6 col-md-12 m-b30">
                        <div class="widget-box">
                            <div class="widget-box">
                                <div class="wc-title">
                                    <h4>Schedule</h4>
                                </div>
                                <div class="widget-inner">
                                    <div id="calendar"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-12 m-b30">

                    </div>
                </div>
            </div>
        </main>
        <div class="ttr-overlay"></div>

        <!-- External JavaScripts -->
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

        <script>
                                                                function delete1(id, start, end) {
                                                                    if (confirm('Do you sure to delete slot start at ' + start + ' to ' + end + '?')) {
                                                                        window.location = '/EPMS/deleteslot?requestid=' + id;
                                                                    }
                                                                }
        </script>
        <script>
            $(document).ready(function () {
                var status = JSON.parse('${requestScope.status}');
                var start = JSON.parse('${requestScope.values}');
                var end = JSON.parse('${requestScope.endValues}');
                var eventsArray = [];
                for (var i = 0; i < start.length; i++) {
                    var eventColor = '';
                    if (status[i].toLowerCase() === 'pending') {
                        eventColor = '#c22d2d';
                    } else {
                        eventColor = '#2dc22d';
                    }
                    console.log('debug:');
                    console.log(status[i]);
                    console.log(status[i].toLowerCase() === 'pending');
                    eventsArray.push({
                        title: status[i],
                        start: start[i],
                        end: end[i],
                        color: eventColor
                    });
                }
                $('#calendar').fullCalendar({
                    header: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'agendaWeek,listWeek,agendaDay'
                    },

                    defaultView: 'agendaWeek',
                    navLinks: true, // can click day/week names to navigate views

                    weekNumbers: true,
                    weekNumbersWithinDays: true,
                    weekNumberCalculation: 'ISO',

                    editable: true,
                    eventLimit: true, // allow "more" link when too many events
                    events: eventsArray,
                    height: 500
                });

            });

        </script>
    </body>

    <!-- Mirrored from educhamp.themetrades.com/demo/admin/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:09:05 GMT -->
</html>
