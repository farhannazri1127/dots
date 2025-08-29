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
        <link href="${contextPath}/resources/vendor/mdb/css/bootstrap-multiselect.min.css" rel="stylesheet">
        <!--<link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->
        <link href="${contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/aos/aos.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
        <!--<link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />-->
        <link rel="stylesheet" href="${contextPath}/resources/vendor/DataTables/datatables.min.css" type="text/css" />

        <link href="${contextPath}/resources/css/main.css" rel="stylesheet">
        <link href="${contextPath}/resources/css/nav.css" rel="stylesheet">

        <style>
            footer {
                position: fixed;
                height: 100px;
                bottom: 0;
                width: 100%;
            }

            /*            .modal {
                            width: 120px;
                            margin: auto;
                        }*/
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
                    <h1>GRATS<span>.</span> | Add new Incident</h1>
                </a>
            </div>
        </header>
        <div>
            <form id="bs_form" class="sample-form needs-validation" role="form" action="${contextPath}/incident/save" method="post" enctype="multipart/form-data" novalidate>
                <div class="row mb-4">
                    <div class="col">
                        <div class="form-floating">
                            <select class="form-select" id="category" name ="category" aria-label="Floating label select example" required>
                                <option value="" selected=""></option>
                                <c:forEach items="${category}" var="group">
                                    <option value="${group.name}" ${group.selected}>${group.name}</option>
                                </c:forEach>
                            </select>
                            <label for="category" >Category</label>
                        </div>
                        <div class="invalid-feedback">
                            Pls select a category.
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-floating">
                            <select class="form-select" id="subCategory" name ="subCategory" aria-label="Floating label select example" required>
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
                    <input type="text" id="title" name ="title" class="form-control" required>
                    <label class="form-label" for="title">Title</label>
                </div>

                <!-- Message input -->
                <div class="form-outline mb-4">
                    <textarea class="form-control" id="description" name="description" rows="4" data-mdb-showcounter="true" maxlength="300" required></textarea>
                    <label class="form-label" for="description">Description</label>
                    <div class="form-helper"></div>
                </div>
                <div class="form-outline mb-4">
                    <div class="col">
                        <div class="form-outline col-lg-10" >
                            <select class="select" id="emailCc" name ="emailCc" multiple data-mdb-filter="true">
                                <option value="" selected=""></option>
                                <c:forEach items="${listCc}" var="group">
                                    <option value="${group.email}">${group.name}&nbsp;&nbsp;(${group.email})</option>
                                </c:forEach>
                            </select>
                            <label for="urgency" >Email Cc</label>
                        </div>
                    </div>
                </div>
                <!-- Text input -->
                <div class="form-outline mb-4">
                    <div class="col">
                        <div class="form-floating col-lg-4" >
                            <select class="form-select" id="urgency" name ="urgency" aria-label="Floating label select example" required>
                                <option value="" selected=""></option>
                                <c:forEach items="${urgency}" var="group">
                                    <option value="${group.name}" ${group.selected}>${group.name}</option>
                                </c:forEach>
                            </select>
                            <label for="urgency" >Urgency</label>
                        </div>
                    </div>
                </div>
                <div class="form-outline mb-4">
                    <div class="mb-3">
                        <label for="formFile" class="form-label">Upload File</label>
                        <input class="form-control" type="file" id="formFile" name="formFile">
                    </div>
                </div>



                <!-- Modal -->
<!--                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-xl">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel"></h5>
                                <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                table
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
                                                <th><span>First Name</span></th>
                                                <th><span>Last Name</span></th>
                                                <th><span>ID</span></th> 
                                                <th><span>Email</span></th> 
                                                <th><span>Location</span></th> 
                                                <th><span>Add</span></th> 
                                            </tr>
                                        </thead>
                                                                                <tbody>
                                        <c:forEach items="${ldapUserList}" var="whRequest" varStatus="whRequestLoop">
                                            <tr>
                                                <td class="col-lg-1"><c:out value="${whRequestLoop.index+1}"/></td>
                                                <td class="col-lg-1"><c:out value="${whRequest.firstname}"/></td>
                                                <td class="col-lg-1"><c:out value="${whRequest.lastname}"/></td>
                                                <td class="col-lg-1"><c:out value="${whRequest.loginId}"/></td>
                                                <td class="col-lg-1"><c:out value="${whRequest.email}"/></td>
                                                <td class="col-lg-1"><c:out value="${whRequest.location}"/></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    </table>
                                </div>
                                end table
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-mdb-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                            </div>
                        </div>
                    </div>
                </div>-->


                <div>
                    <!-- Submit button -->
                    <button type="submit" class="btn btn-primary btn-block mb-4">Submit</button>
                </div>
            </form>
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
        <script src="${contextPath}/resources/private/js/bootstrap-filestyle.min.js"></script>
        <script src="${contextPath}/resources/vendor/DataTables/datatables.min.js"></script>

        <!-- Template Main JS File -->
        <script src="${contextPath}/resources/js/main.js"></script>

        <script>
            $(document).ready(function () {


                // Get the <datalist> and <input> elements.
                var dataList = document.getElementById('cc_list');
                var input = document.getElementById('cc');

// Create a new XMLHttpRequest.
                var request = new XMLHttpRequest();

// Handle state changes for the request.
                request.onreadystatechange = function (response) {
                    if (request.readyState === 4) {
                        if (request.status === 200) {
                            // Parse the JSON
                            var jsonOptions = JSON.parse(request.responseText);

                            // Loop over the JSON array.
                            jsonOptions.forEach(function (item) {
                                // Create a new <option> element.
                                var option = document.createElement('option');
                                // Set the value using the item in the JSON array.
                                option.value = item;
                                // Add the <option> element to the <datalist>.
                                dataList.appendChild(option);
                            });

                            // Update the placeholder text.
                            input.placeholder = "e.g. datalist";
                        } else {
                            // An error occured :(
                            input.placeholder = "Couldn't load datalist options :(";
                        }
                    }
                };

// Update the placeholder text.
                input.placeholder = "Loading options...";

// Set up and make the request.
                request.open('GET', 'loadUser2.email', true);
//            request.open
                request.send();

                oTable = $('#dt_spml').DataTable({
                    "fixedHeader": false,
                    "paging": true,
                    "info": true,
                    "autoWidth": false,
                    "order": [],
                    ajax: {
                        url: 'loadUser',
                        dataSrc: ''
                    },
                    columns: [
                        {"data": "firstname"},
                        {"data": "lastname"},
                        {"data": "loginId"},
                        {"data": "email"},
                        {"data": "location"},
                        {"data": null,
//                            defaultContent: '<button>Add</button>',
                            defaultContent: '<a href="${contextPath}/docs/downloadAttach/" id="download" name="download" class="table-link" title="download"><i class="bi bi-plus-square-fill btn-xl"></i>',
                            targets: -1}
                    ],
                    dom: 'Brtip'
                });

                $('#dt_spml_search').keyup(function () {
                    oTable.search($(this).val()).draw();
                });

                $("#dt_spml_rows").change(function () {
                    oTable.page.len($(this).val()).draw();
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