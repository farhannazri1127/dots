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
        <!--<link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->
        <link href="${contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/aos/aos.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/datatables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/css/buttons.dataTables.min.css" />

        <link href="${contextPath}/resources/css/main.css" rel="stylesheet">
        <link href="${contextPath}/resources/css/nav.css" rel="stylesheet">

        <style>
            footer {
                position: fixed;
                height: 100px;
                bottom: 0;
                width: 100%;
            }
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
                <a href="#" class="logo d-flex align-items-center">
                    <h1>GRATS<span>.</span> | Query</h1>
                </a>
            </div>
        </header>
        <div>
            <form id="bs_form" class="sample-form needs-validation" role="form" action="${contextPath}/query" method="post" novalidate>
                <div class="row mb-4">
                    <div class="col-lg-3">
                        <div class="form-floating">
                            <select class="form-select" id="type" name ="type" aria-label="Floating label select example" required>
                                <option value="" selected=""></option>
                                <c:forEach items="${typeList}" var="group">
                                    <option value="${group.name}" ${group.selected}>${group.name}</option>
                                </c:forEach>
                            </select>
                            <label for="type" >Type</label>
                        </div>
                        <div class="invalid-feedback">
                            Pls select a category.
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="form-floating">
                            <select class="form-select" id="category" name ="category" aria-label="Floating label select example">
                                <option value="" selected=""></option>
                                <c:forEach items="${categoryList}" var="group">
                                    <option value="${group.name}" ${group.selected}>${group.name}</option>
                                </c:forEach>
                            </select>
                            <label for="category" >Category</label>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-floating">
                            <select class="form-select" id="subCategory" name ="subCategory" aria-label="Floating label select example">
                                <option value="" selected=""></option>
                                <c:forEach items="${subCategoryList}" var="group">
                                    <option value="${group.name}" ${group.selected}>${group.name}</option>
                                </c:forEach>
                            </select>
                            <label for="subCategory">Sub-Category</label>
                        </div>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col-lg-3">
                        <div class="form-outline mb-3">
                            <input type="text" id="ticketNo" name ="ticketNo" class="form-control">
                            <label class="form-label" for="ticketNo">Ticket No</label>
                        </div>
                    </div>   
                    <div class="col">
                        <div class="form-floating">
                            <select class="form-select" id="urgency" name ="urgency" aria-label="Floating label select example">
                                <option value="" selected=""></option>
                                <c:forEach items="${urgencyList}" var="group">
                                    <option value="${group.name}" ${group.selected}>${group.name}</option>
                                </c:forEach>
                            </select>
                            <label for="urgency" >Urgency</label>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-floating">
                            <select class="form-select" id="user" name ="user" aria-label="Floating label select example">
                                <option value="" selected=""></option>
                                <c:forEach items="${userList}" var="group">
                                    <option value="${group.user}">${group.user}</option>
                                </c:forEach>
                            </select>
                            <label for="user" >Requestor</label>
                        </div>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col-lg-2">
                        <div class="form-floating">
                            <select class="form-select" id="site" name ="site" aria-label="Floating label select example">
                                <option value="" selected=""></option>
                                <c:forEach items="${siteList}" var="group">
                                    <option value="${group.site}">${group.site}</option>
                                </c:forEach>
                            </select>
                            <label for="site" >Site</label>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-floating">
                            <select class="form-select" id="pic" name ="pic" aria-label="Floating label select example">
                                <option value="" selected=""></option>
                                <c:forEach items="${picList}" var="group">
                                    <option value="${group.name}">${group.name}</option>
                                </c:forEach>
                            </select>
                            <label for="pic" >Person in Charge</label>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-floating">
                            <select class="form-select" id="status" name ="status" aria-label="Floating label select example">
                                <option value="" selected=""></option>
                                <option value="New">New</option>
                                <option value="PIC Assigned">PIC Assigned</option>
                                <option value="In Process">In Process</option>
                                <option value="Completed">Completed</option>
                                <option value="Closed">Closed</option>
                                <option value="Cancelled">Cancelled</option>
                            </select>
                            <label for="status" >Status</label>
                        </div>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col-lg-3">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="bi bi-calendar2-month"></i></span>
                            <input type="text" name="requestDate" class="form-control" id="requestDate" value="">
                        </div>
                        <label id="datepickerDate-error" class="error" for="eta" style="display: none;"></label>
                        <label for="requestDate" class="form-label">Request Date</label>
                    </div>
                    <div class="col-lg-3">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="bi bi-calendar2-month"></i></span>
                            <input type="text" name="eta" class="form-control" id="eta" value="">
                        </div>
                        <label id="datepickerDate-error" class="error" for="eta" style="display: none;"></label>
                        <label for="eta" class="form-label">Estimate Completion Date</label>
                    </div>
                </div>

                <div>
                    <!-- Submit button -->
                    <button type="submit" class="btn btn-primary btn-block mb-4">Submit</button>
                </div>
            </form>
        </div>

        <div class="sample-form">

            <!-- Tabs content -->
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
                            <th><span>Type</span></th>
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
                    <c:forEach items="${queryList}" var="whRequest" varStatus="whRequestLoop">
                        <tr>
                            <td class="col-lg-1"><c:out value="${whRequestLoop.index+1}"/></td>
                        <c:choose>
                            <c:when test="${whRequest.ticket == 'I'}">
                                <td><c:out value="Incident"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><c:out value="Request"/></td>
                            </c:otherwise>
                        </c:choose>
                        <td><c:out value="${whRequest.ticketNo}"/></td>
                        <td><c:out value="${whRequest.title}"/></td>
                        <td><c:out value="${whRequest.category}"/></td>
                        <td><c:out value="${whRequest.subCategory}"/></td>
                        <td><c:out value="${whRequest.status}"/></td>
                        <td><c:out value="${whRequest.createdDate}"/></td>
                        <td class="col-lg-1">
                        <c:choose>
                            <c:when test="${whRequest.ticket == 'I'}">
                                <a href="${contextPath}/incident/edit/${whRequest.id}" id="view" name="edit/view" class="table-link" title="edit/view">
                                    <i class="bi bi-pencil-square"></i>
                                    </span>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="${contextPath}/newRequest/edit/${whRequest.id}" id="view" name="edit/view" class="table-link" title="edit/view">
                                    <i class="bi bi-pencil-square"></i>
                                    </span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                        </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </div>

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
    <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
    <script src="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/js/buttons.dataTables.min.js"></script>
    <script src="${contextPath}/resources/vendor/DataTables/datatables.min.js"></script>

    <!-- Template Main JS File -->
    <script src="${contextPath}/resources/js/main.js"></script>

    <script>
        $(document).ready(function () {


            var oTable = $('#dt_spml').DataTable({
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

            $('#eta').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true
            });
            $('#requestDate').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true
            });


//               // Example starter JavaScript for disabling form submissions if there are invalid fields
//                (() => {
            'use strict';

            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            const forms = document.querySelectorAll('.needs-validation');

            // Loop over them and prevent submission
            Array.prototype.slice.call(forms).forEach((form) => {
                form.addEventListener('submit', (event) => {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        });
    </script>

</body>

</html>