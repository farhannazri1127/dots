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
        <link href="${contextPath}/resources/vendor/select2/css/select2.min.css" rel="stylesheet">
<!--<link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->
        <link href="${contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/aos/aos.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />

        <link href="${contextPath}/resources/css/main.css" rel="stylesheet">

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
                    <h1>GRATS<span>.</span> | Add new Request</h1>
                </a>
            </div>
        </header>
        <div>
            <form id="bs_form" class="sample-form needs-validation" role="form" action="${contextPath}/newRequest/save" method="post" enctype="multipart/form-data" novalidate>
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
                    <textarea class="form-control" id="description" name="description" rows="4" data-mdb-showcounter="true" maxlength="500" required></textarea>
                    <label class="form-label" for="description">Description</label>
                    <div class="form-helper"></div>
                </div>

                <div class="form-outline mb-4">
                    <div class="col">
                        <div class="form-floating col-lg-12" >
                            <select class="mySelect" id="emailCc" name ="emailCc"  multiple="multiple" style="width: 100%" aria-label="Floating label select example">
                                <!--<option value="" selected=""></option>-->
                                <c:forEach items="${listCc}" var="group">
                                    <option value="${group.email}">${group.name}&nbsp;&nbsp;(${group.email})</option>
                                </c:forEach>
                            </select>
                            <!--<label for="emailCc" >Email Cc</label>-->
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
        <script src="${contextPath}/resources/vendor/select2/js/select2.min.js"></script>
        <script src="${contextPath}/resources/vendor/aos/aos.js"></script>
        <script src="${contextPath}/resources/vendor/glightbox/js/glightbox.min.js"></script>
        <script src="${contextPath}/resources/vendor/purecounter/purecounter_vanilla.js"></script>
        <script src="${contextPath}/resources/vendor/swiper/swiper-bundle.min.js"></script>
        <script src="${contextPath}/resources/vendor/isotope-layout/isotope.pkgd.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-filestyle.min.js"></script>

        <!-- Template Main JS File -->
        <script src="${contextPath}/resources/js/main.js"></script>

        <script>
            $(document).ready(function () {
                
                var placeholder = "Email cc";
                $(".mySelect").select2({
                    
                    allowClear: true,
                    placeholder: placeholder,
                    minimumInputLength: 3,
                    page: 10
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