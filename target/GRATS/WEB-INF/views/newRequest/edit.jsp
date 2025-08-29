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
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/datatables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/css/buttons.dataTables.min.css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/jquery.datetimepicker.css" type="text/css" />

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
                    <i class="bi bi-house d-flex align-items-center  ms-4"><a href="${contextPath}/">Home</a></i>
                    <i class="bi bi-box-arrow-right d-flex align-items-center  ms-4"><a href="${contextPath}/logout">logout</a></i>
                </div>
            </div>
        </section><!-- End Top Bar -->

        <header id="header" class="header d-flex align-items-center">

            <div class="container-fluid container-xl d-flex align-items-center justify-content-between">
                <a href="#" class="logo d-flex align-items-center">
                    <h1>GRATS<span>.</span> | New Request - ${request.ticketNo}</h1>
                </a>
            </div>
        </header>
        <div class="sample-form">

            <ul class="nav nav-tabs mb-3" id="ex-with-icons" role="tablist">
                <li class="nav-item" role="detail">
                    <a class="nav-link active" id="ex-with-icons-tab-1" data-mdb-toggle="tab" href="#ex-with-icons-tabs-1" role="tab"
                       aria-controls="ex-with-icons-tabs-1" aria-selected="true"><i class="bi bi-info-square"></i><b> Detail</b></a>
                </li>
                <li class="nav-item" role="note">
                    <a class="nav-link" id="ex-with-icons-tab-2" data-mdb-toggle="tab" href="#ex-with-icons-tabs-2" role="tab"
                       aria-controls="ex-with-icons-tabs-2" aria-selected="true"><i class="bi bi-card-checklist"></i><b> Note / Remark</b></a>
                </li>
                <li class="nav-item" role=s"attachment">
                    <a class="nav-link" id="ex-with-icons-tab-3" data-mdb-toggle="tab" href="#ex-with-icons-tabs-3" role="tab"
                       aria-controls="ex-with-icons-tabs-3" aria-selected="false"><i class="bi bi-archive"></i><b> Attachment</b></a>
                </li>
                <li>
                    <a class="nav-link navbar-right" href="${contextPath}/ticket" role="tab"
                       aria-controls="ex-with-icons-tabs-4" aria-selected="false"><i class="bi bi-skip-backward-fill"></i><b> Back</b></a>
                </li>
            </ul>
            <!-- Tabs navs -->

            <!-- Tabs content -->
            <div class="tab-content" id="ex-with-icons-content">
                <div class="tab-pane fade show active" id="ex-with-icons-tabs-1" role="tabpanel" aria-labelledby="ex-with-icons-tab-1">

                    <form id="bs_form" class="sample-form needs-validation" role="form" action="${contextPath}/newRequest/update" method="post" enctype="multipart/form-data" novalidate>

                        <div class="col-lg-2">
                            <div class="form-outline mb-3 col-lg-1">
                                <input type="text" id="id" name ="id" class="form-control" value="${request.id}" hidden>
                                <input type="text" id="groupId" name ="groupId" class="form-control" value="${GroupID}" hidden>
                                <input type="text" id="requestNo" name ="requestNo" class="form-control" value="${request.ticketNo}" readonly>
                                <label class="form-label" for="requestNo">Ticket No</label>
                            </div>
                        </div>
                        <div class="row mb-4">
                            <div class="col">
                                <div class="form-outline">
                                    <input type="text" id="user" name ="user" class="form-control" value="${request.user}" readonly>
                                    <label class="form-label" for="user">Requestor</label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-outline">
                                    <input type="text" id="site" name ="site" class="form-control" value="${request.site}" readonly>
                                    <label class="form-label" for="site">Site</label>
                                </div>
                            </div>
                        </div>

                        <div class="row mb-4">
                            <div class="col">
                                <div class="form-floating">
                                    <select class="form-select" id="category" name ="category" aria-label="Floating label select example" disabled>
                                        <option value="" selected=""></option>
                                        <c:forEach items="${category}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="category" >Category</label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-floating">
                                    <select class="form-select" id="subCategory" name ="subCategory" aria-label="Floating label select example" disabled>
                                        <option value="" selected=""></option>
                                        <c:forEach items="${subCategory}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="subCategory" >Sub-Category</label>
                                </div>
                            </div>
                        </div>

                        <!-- Text input -->
                        <div class="form-outline mb-4">
                            <input type="text" id="title" name ="title" class="form-control" value="${request.title}" disabled>
                            <label class="form-label" for="title">Title</label>
                        </div>

                        <!-- Message input -->
                        <div class="form-outline mb-4">
                            <textarea class="form-control" id="description" name="description" rows="4" disabled>${request.description}</textarea>
                            <label class="form-label" for="description">Description</label>
                        </div>

                        <!-- Text input -->
                        <div class="form-outline mb-4">
                            <input type="text" id="cc" name ="cc" class="form-control" value="${request.cc}" disabled>
                            <label class="form-label" for="cc">Email cc</label>
                        </div>

                        <div class="row mb-4">
                            <div class="col-lg-5">
                                <div class="form-floating">
                                    <select class="form-select" id="urgency" name ="urgency" aria-label="Floating label select example" disabled>
                                        <option value="" selected=""></option>
                                        <c:forEach items="${urgency}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="pic" >Urgency</label>
                                </div>
                            </div>
                        </div>
                        <c:if test="${GroupID == '1' && request.flag == '0'}">
                            <div class="row form-group mb-4">

                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="bi bi-calendar2-month"></i></span>
                                        <input type="text" name="eta" class="form-control" id="eta" value="${request.eta}">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="eta" style="display: none;"></label>
                                    <label for="eta" class="form-label">Estimate Completion Date</label>
                                </div>
                                <div class="col-lg-5">
                                    <div class="form-floating">
                                        <select class="form-select" id="pic" name ="pic" aria-label="Floating label select example" required>
                                            <option value="" selected=""></option>
                                            <c:forEach items="${pic}" var="group">
                                                <option value="${group.name}" ${group.selected}>${group.name}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="pic" >PIC</label>
                                    </div>
                                </div>
                                <div class="col-lg-4">
                                    <div class="form-floating">
                                        <select class="form-select" id="status" name ="status" aria-label="Floating label select example" required>
                                            <option value="" selected=""></option>
                                            <c:forEach items="${status}" var="group">
                                                <option value="${group.name}" ${group.selected}>${group.name}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="status" >Status</label>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${GroupID != '1' || request.flag == '1'}">
                            <div class="row form-group mb-4">
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="bi bi-calendar2-month"></i></span>
                                        <input type="text" name="eta" class="form-control" id="eta" value="${request.eta}" disabled>
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="eta" style="display: none;"></label>
                                    <label for="eta" class="form-label">Estimate Completion Date</label>
                                </div>

                                <div class="col-lg-5">
                                    <div class="form-floating">
                                        <select class="form-select" id="pic" name ="pic" aria-label="Floating label select example" disabled>
                                            <option value="" selected=""></option>
                                            <c:forEach items="${pic}" var="group">
                                                <option value="${group.name}" ${group.selected}>${group.name}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="pic" >PIC</label>
                                    </div>
                                </div>
                                <div class="col-lg-4">
                                    <div class="form-floating">
                                        <select class="form-select" id="status" name ="status" aria-label="Floating label select example" disabled>
                                            <option value="" selected=""></option>
                                            <c:forEach items="${status}" var="group">
                                                <option value="${group.name}" ${group.selected}>${group.name}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="status" >Status</label>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${request.flag == '0'}">
                            <div class="form-outline mb-4">
                                <textarea class="form-control" id="note" name="note" rows="4" data-mdb-showcounter="true" maxlength="500" required></textarea>
                                <label class="form-label" for="note">Note</label>
                                <div class="form-helper"></div>
                            </div>
                            <div class="form-outline mb-4">
                                <div class="mb-3">
                                    <label for="formFile" class="form-label">Upload File</label>
                                    <input class="form-control" type="file" id="formFile" name="formFile">
                                </div>
                            </div>

                            <div>
                                <!-- Submit button -->
                                <button type="submit" class="btn btn-primary btn-block mb-4">Submit</button>
                            </div>
                        </c:if>
                        <c:if test="${request.flag == '1'}">
                            <div class="form-outline mb-4">
                                <textarea class="form-control" id="note" name="note" rows="4" data-mdb-showcounter="true" maxlength="500" disabled></textarea>
                                <label class="form-label" for="note">Note</label>
                                <div class="form-helper"></div>
                            </div>
                            <div class="form-outline mb-4">
                                <div class="mb-3">
                                    <label for="formFile" class="form-label">Upload File</label>
                                    <input class="form-control" type="file" id="formFile" name="formFile" disabled>
                                </div>
                            </div>

                            <div>
                                <!-- Submit button -->
                                <button type="submit" class="btn btn-primary btn-block mb-4" disabled>Submit</button>
                            </div>
                        </c:if>

                    </form>
                    <!--</div>-->
                </div>
                <div class="tab-pane fade" id="ex-with-icons-tabs-2" role="tabpanel" aria-labelledby="ex-with-icons-tab-2">
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
                                    <th><span>Note</span></th>
                                    <th><span>Detail</span></th> 
                                    <th><span>By</span></th> 
                                    <th ><span>Date</span></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${log}" var="whRequest" varStatus="whRequestLoop">
                                    <tr>
                                        <td class="col-lg-1"><c:out value="${whRequestLoop.index+1}"/></td>
                                        <td><c:out value="${whRequest.status}"/></td>
                                        <td><c:out value="${whRequest.description}"/></td>
                                        <td><c:out value="${whRequest.createdBy}"/></td>
                                        <td><c:out value="${whRequest.statusDate}"/></td>
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
                                    <th><span>Filename</span></th>
                                    <th><span>Upload By</span></th> 
                                    <th><span>Date</span></th>
                                    <th><span>Download</span></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${attachment}" var="whRequest" varStatus="whRequestLoop">
                                    <tr>
                                        <td class="col-lg-1"><c:out value="${whRequestLoop.index+1}"/></td>
                                        <td><c:out value="${whRequest.filename}"/></td>
                                        <td><c:out value="${whRequest.createdBy}"/></td>
                                        <td><c:out value="${whRequest.createdDate}"/></td>
                                        <!--<td><c:out value="${whRequest.createdDate}"/></td>-->
                                        <td class="col-lg-1">
                                            <a href="${contextPath}/newRequest/downloadAttach/${whRequest.id}" id="donwloadAtt" name="donwloadAtt" class="table-link" title="Download">
                                                <i class="bi bi-file-earmark-arrow-down-fill"></i>
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
        <script src="${contextPath}/resources/private/js/bootstrap-filestyle.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/Buttons-2.4.2/js/buttons.dataTables.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/datatables.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
        <script src="${contextPath}/resources/private/js/jquery.datetimepicker.full.js"></script>

        <!-- Template Main JS File -->
        <script src="${contextPath}/resources/js/main.js"></script>

        <script>
            $(document).ready(function () {

                $(function () {
                    var dateToDisable = new Date();
                    dateToDisable.setDate(dateToDisable.getDate());
                    $('#eta').datetimepicker({
                        formatTime: 'H:i',
                        //                                                                    formatDate: 'd-m-Y',
                        formatDate: 'yyyy-mm-dd',
                        defaultTime: '08:00',
                        beforeShowDay: function (date) {
                            var now = new Date();
                            if (date.getFullYear() === now.getFullYear() && date.getMonth() === now.getMonth() && date.getDate() >= now.getDate())
                                return [true];
                            if (date.getFullYear() >= now.getFullYear() && date.getMonth() > now.getMonth())
                                return [true];
                            if (date.getFullYear() > now.getFullYear())
                                return [true];
                            return [false];
                        }
                    });
                });

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

                $(":file").filestyle({buttonBefore: true});
                $(":file").filestyle({iconName: "glyphicon-inbox"});
                $(":file").filestyle({buttonName: "btn-info"});

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