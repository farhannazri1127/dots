<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Reliability Apps Ticketing System</title>
        <meta content="" name="description">
        <meta content="" name="keywords">
        <link rel="shortcut icon" href="${contextPath}/resources/img/ticketIcon.ico">

        <link href="${contextPath}/resources/css/googleFont.css" rel="stylesheet">

        <link href="${contextPath}/resources/vendor/mdb/css/mdb.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/aos/aos.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/datatables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/css/buttons.dataTables.min.css" />

        <link href="${contextPath}/resources/css/main.css" rel="stylesheet">
        <link href="${contextPath}/resources/css/nav.css" rel="stylesheet">

        <style>
        </style>
    </head>
    <body>

        <!-- ======= Header ======= -->
        <section id="topbar" class="topbar d-flex align-items-center">
            <div class="container d-flex justify-content-center justify-content-md-between">
                <div class="contact-info d-flex align-items-center">
                    <i class="bi bi-envelope d-flex align-items-center"><a href="mailto:Global-Rel-IT@onsemi.com">Global Rel IT Team</a></i>
                    <i class="bi bi-phone d-flex align-items-center ms-4"><span>+606-6822731</span></i>
                    <i class="bi bi-phone d-flex align-items-center ms-4"><span>+606-6822732</span></i>
                    <i class="bi bi-phone d-flex align-items-center ms-4"><span>+606-6822923</span></i>
                </div>
                <div class="contact-info d-flex align-items-center">
                    <i class="ms-4"><span>${sessionScope.userSession.fullname}</span></i>
                    <i class="bi bi-gear d-flex align-items-center  ms-4"><a href="${contextPath}/admin">Admin Page</a></i>
                    <i class="bi bi-house d-flex align-items-center  ms-4"><a href="${contextPath}/">Home</a></i>
                    <i class="bi bi-box-arrow-right d-flex align-items-center  ms-4"><a href="${contextPath}/logout">logout</a></i>
                </div>
            </div>
        </section><!-- End Top Bar -->

        <header id="header" class="header d-flex align-items-center">

            <div class="container-fluid container-xl d-flex align-items-center justify-content-between">
                <a href="${contextPath}/" class="logo d-flex align-items-center">
                    <h1>GRATS<span>.</span> | ${title}</h1>
                </a>
            </div>
        </header>
        <div class="sample-form">

            <ul class="nav nav-tabs mb-3" id="ex-with-icons" role="tablist">
                <li class="nav-item" role="incident">
                    <a class="nav-link active" id="ex-with-icons-tab-2" data-mdb-toggle="tab" href="#ex-with-icons-tabs-2" role="tab"
                       aria-controls="ex-with-icons-tabs-2" aria-selected="true"><i class="bi bi-exclamation-triangle"></i><b> Incident</b></a>
                </li>
                <li class="nav-item" role="request">
                    <a class="nav-link" id="ex-with-icons-tab-3" data-mdb-toggle="tab" href="#ex-with-icons-tabs-3" role="tab"
                       aria-controls="ex-with-icons-tabs-3" aria-selected="false"><i class="bi bi-plus-square"></i><b> Request</b></a>
                </li>
            </ul>
            <!-- Tabs navs -->

            <!-- Tabs content -->
            <div class="tab-content" id="ex-with-icons-content">
                <div class="tab-pane fade show active" id="ex-with-icons-tabs-2" role="tabpanel" aria-labelledby="ex-with-icons-tab-2">
                    <div class="sample-form" >
                        <div class="row">
                            <div class="col-lg-9">
                                <div class="col-lg-2">
                                    <select id="dt_spml_rows" class="form-select">
                                        <option value="10">10</option>
                                        <option value="25">25</option>
                                        <option value="50">50</option>
                                        <option value="100">100</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-3 ">
                                <div class="input-group">
                                    <input type="text" id="dt_spml_search" name="dt_spml_search" class="form-control" placeholder="Search">
                                    <span class="input-group-addon"><i class="bi bi-search"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="sample-form">
                        <table id="dt_spml" class="table">
                            <thead>
                                <tr>
                                    <th><span>No</span></th>
                                    <th><span>Ticket No</span></th>
                                    <th><span>Title</span></th> 
                                    <th><span>Category</span></th> 
                                    <th><span>Sub-Category</span></th> 
                                    <th><span>Status</span></th> 
                                    <th ><span>Date</span></th>
                                    <th ><span>Edit/View</span></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${incidentList}" var="whRequest" varStatus="whRequestLoop">
                                    <tr>
                                        <td class="col-lg-1"><c:out value="${whRequestLoop.index+1}"/></td>
                                        <td><c:out value="${whRequest.ticketNo}"/></td>
                                        <td><c:out value="${whRequest.title}"/></td>
                                        <td><c:out value="${whRequest.category}"/></td>
                                        <td><c:out value="${whRequest.subCategory}"/></td>
                                        <td><c:out value="${whRequest.status}"/></td>
                                        <td><c:out value="${whRequest.createdDate}"/></td>
                                        <td class="col-lg-1">
                                            <a href="${contextPath}/incident/edit/${whRequest.id}" id="view" name="edit/view" class="table-link" title="edit/view">
                                                <i class="bi bi-pencil-square"></i>
                                                </span>
                                            </a>
                                            <%--</c:if>--%>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="tab-pane fade" id="ex-with-icons-tabs-3" role="tabpanel" aria-labelledby="ex-with-icons-tab-3">
                    <!--Tab 2 content-->

                    <div class="sample-form" >
                        <div class="row">
                            <div class="col-lg-9">
                                <div class="col-lg-2">
                                    <select id="dt_spml_rows2" class="form-select">
                                        <option value="10">10</option>
                                        <option value="25">25</option>
                                        <option value="50">50</option>
                                        <option value="100">100</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-3 ">
                                <div class="input-group">
                                    <input type="text" id="dt_spml_search2" name="dt_spml_search2" class="form-control" placeholder="Search">
                                    <span class="input-group-addon"><i class="bi bi-search"></i></span>
                                </div>
                            </div>
                        </div>
                    </div> 

                    <div class="sample-form">
                        <table id="dt_spml2" class="table">
                            <thead>
                                <tr>
                                    <th><span>No</span></th>
                                    <th><span>Ticket No</span></th>
                                    <th><span>Title</span></th> 
                                    <th><span>Category</span></th> 
                                    <th><span>Sub-Category</span></th> 
                                    <th><span>Status</span></th> 
                                    <th ><span>Date</span></th>
                                    <th ><span>Edit/View</span></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestList}" var="whRequest" varStatus="whRequestLoop">
                                    <tr>
                                        <td class="col-lg-1"><c:out value="${whRequestLoop.index+1}"/></td>
                                        <td><c:out value="${whRequest.ticketNo}"/></td>
                                        <td><c:out value="${whRequest.title}"/></td>
                                        <td><c:out value="${whRequest.category}"/></td>
                                        <td><c:out value="${whRequest.subCategory}"/></td>
                                        <td><c:out value="${whRequest.status}"/></td>
                                        <td><c:out value="${whRequest.createdDate}"/></td>
                                        <td class="col-lg-1">
                                            <a href="${contextPath}/newRequest/edit/${whRequest.id}" id="view" name="edit/view" class="table-link" title="edit/view">
                                                <i class="bi bi-pencil-square"></i>
                                                </span>
                                            </a>
                                            <%--</c:if>--%>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- Tabs content -->

        <a href="#" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

        <div id="preloader"></div>

        <!-- Vendor JS Files -->
        <script src="${contextPath}/resources/private/js/jquery.js"></script>
        <script src="${contextPath}/resources/vendor/mdb/js/mdb.min.js"></script>
        <script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${contextPath}/resources/vendor/aos/aos.js"></script>
        <script src="${contextPath}/resources/vendor/glightbox/js/glightbox.min.js"></script>
        <script src="${contextPath}/resources/vendor/purecounter/purecounter_vanilla.js"></script>
        <script src="${contextPath}/resources/vendor/swiper/swiper-bundle.min.js"></script>
        <script src="${contextPath}/resources/vendor/isotope-layout/isotope.pkgd.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/js/buttons.dataTables.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/datatables.min.js"></script>

        <!-- Template Main JS File -->
        <script src="${contextPath}/resources/js/main.js"></script>

        <script>
            $(document).ready(function () {

                oTable = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    buttons: [
                        'copy', 'csv', 'pdf'
                    ]
                });

                $('#dt_spml_search').keyup(function () {
                    oTable.search($(this).val()).draw();
                });

                $("#dt_spml_rows").change(function () {
                    oTable.page.len($(this).val()).draw();
                });

                oTable2 = $('#dt_spml2').DataTable({
                    dom: 'Brtip',
                    buttons: [
                        'copy', 'csv', 'pdf'
                    ]
                });

                $('#dt_spml_search2').keyup(function () {
                    oTable2.search($(this).val()).draw();
                });

                $("#dt_spml_rows2").change(function () {
                    oTable2.page.len($(this).val()).draw();
                });
            });
        </script>

    </body>

</html>