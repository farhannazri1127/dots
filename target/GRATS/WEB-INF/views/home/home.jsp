<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Global Rel Apps Ticketing System</title>
        <meta content="" name="description">
        <meta content="" name="keywords">

        <!-- Favicons -->
        <link rel="shortcut icon" href="${contextPath}/resources/img/ticketIcon.ico">


        <!-- Google Fonts -->
        <!--<link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>  -->
        <!--<link href="${contextPath}/resources/css/googleFont.css" rel="stylesheet">-->
        <link href='${contextPath}/resources/css/googleFont.css' rel='stylesheet' type='text/css'>
        <!--<link href="https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,600;1,700&family=Montserrat:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&family=Raleway:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap" rel="stylesheet">-->  

        <!-- Vendor CSS Files -->
        <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/aos/aos.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

        <!-- Template Main CSS File -->
        <link href="${contextPath}/resources/css/main.css" rel="stylesheet">

        <style>
            .test {
                height: 100%;
                /*position: fixed;*/
            }
            /*            footer {
                            position: fixed;
                            height: 100px;
                            bottom: 0;
                            width: 100%;
                        }*/
        </style>

        <!-- =======================================================
        * Template Name: Impact
        * Updated: Sep 18 2023 with Bootstrap v5.3.2
        * Template URL: https://bootstrapmade.com/impact-bootstrap-business-website-template/
        * Author: BootstrapMade.com
        * License: https://bootstrapmade.com/license/
        ======================================================== -->
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
                    <c:if test="${groupId == '1'}"><i class="bi bi-search d-flex align-items-center  ms-4"><a href="${contextPath}/query">Query</a></i></c:if>
                    <i class="bi bi-box-arrow-right d-flex align-items-center  ms-4"><a href="${contextPath}/logout">logout</a></i>
                </div>
            </div>
        </section><!-- End Top Bar -->

        <header id="header" class="header d-flex align-items-center">

            <div class="container-fluid container-xl d-flex align-items-center justify-content-between">
                <a href="${contextPath}/" class="logo d-flex align-items-center">
                    <!-- Uncomment the line below if you also wish to use an image logo -->
                    <!-- <img src="assets/img/logo.png" alt=""> -->
                    <h1>GRATS<span>.</span></h1>
                </a>

            </div>
        </header><!-- End Header -->
        <!-- End Header -->

        <!-- ======= Hero Section ======= -->
        <section id="hero" class="hero test">
            <!--<section id="hero" class="hero">-->
            <c:if test="${sessionScope.userSession.id == '0'}">

                <div class="container position-relative">
                    <div class="row gy-5" data-aos="fade-in">
                        <div class="col-lg-6 order-2 order-lg-1 d-flex flex-column justify-content-center text-center text-lg-start">
                            <h2>Unregistered User</span></h2>
                            <p>Sorry, your ID is not registered with RATS application. Please click <a href="${contextPath}/register"
                                                                                                       class="link-info">Here</a> to register your ID.</p>
                        </div>
                        <div class="col-lg-6 order-1 order-lg-2">
                            <img src="${contextPath}/resources/img/clipart633480.png" class="img-fluid" alt="" data-aos="zoom-out" data-aos-delay="100">
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${sessionScope.userSession.id != '0'}">
                <div class="container position-relative">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            <a class="close" data-dismiss="alert" href="${contextPath}/" aria-hidden="true">&times;</a>
                            <strong>${error}</strong>
                        </div>
                    </c:if>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">
                            <a class="close" data-dismiss="alert" href="${contextPath}/" aria-hidden="true">&times;</a>
                            <strong>${success}</strong>
                        </div>
                    </c:if>

                    <div class="row gy-5" data-aos="fade-in">
                        <div class="col-lg-6 order-2 order-lg-1 d-flex flex-column justify-content-center text-center text-lg-start">
                            <h2>Welcome to <span>Global Rel Apps Ticketing System (GRATS)</span></h2>
                            <p>GRATS is a platform for Rel Lab application's user to submit an incident or a new request.</p>
                            <c:if test="${groupId == '1'}">
                                <div class="d-flex justify-content-center justify-content-lg-start">
                                    <a href="${contextPath}/admin" class="btn-get-started">Admin Page</a>
                                </div>
                            </c:if>
                        </div>
                        <div class="col-lg-6 order-1 order-lg-2">
                            <img src="${contextPath}/resources/img/clipart633480.png" class="img-fluid" alt="" data-aos="zoom-out" data-aos-delay="100">
                        </div>
                    </div>
                </div>

                <div class="icon-boxes position-relative">
                    <div class="container position-relative">
                        <div class="row gy-4 mt-5">

                            <div class="col-xl-3 col-md-6" data-aos="fade-up" data-aos-delay="100">
                                <div class="icon-box">
                                    <div class="icon"><i class="bi bi-exclamation-triangle"></i></div>
                                    <h4 class="title"><a href="${contextPath}/incident/add" class="stretched-link">Submit an Incident</a></h4>
                                </div>
                            </div><!--End Icon Box -->

                            <div class="col-xl-3 col-md-6" data-aos="fade-up" data-aos-delay="200">
                                <div class="icon-box">
                                    <div class="icon"><i class="bi bi-plus-square"></i></div>
                                    <h4 class="title"><a href="${contextPath}/newRequest/add" class="stretched-link">New Request</a></h4>
                                </div>
                            </div><!--End Icon Box -->

                            <div class="col-xl-3 col-md-6" data-aos="fade-up" data-aos-delay="300">
                                <div class="icon-box">
                                    <div class="icon"><i class="bi bi-ticket-perforated-fill"></i></div>
                                    <h4 class="title"><a href="${contextPath}/ticket" class="stretched-link">My Ticket</a></h4>
                                </div>
                            </div><!--End Icon Box -->

                            <div class="col-xl-3 col-md-6" data-aos="fade-up" data-aos-delay="500">
                                <div class="icon-box">
                                    <div class="icon"><i class="bi bi-cloud-download"></i></div>
                                    <h4 class="title"><a href="${contextPath}/docs" class="stretched-link">Download MQC / User Manual / Specs</a></h4>
                                </div>
                            </div><!--End Icon Box -->

                        </div>
                    </div>
                </div>
            </c:if>
        </section>
        <!-- End Hero Section -->



        <!-- ======= Footer ======= -->
        <!--        <footer id="footer" class="footer">
        
                    <div class="container">
                        <div class="row gy-4">
                            <div class="col-lg-1 col-md-12 footer-info">
                                <a href="${contextPath}/" class="logo d-flex align-items-center">
                                    <span>RATS</span>
                                </a>
        
                            </div>
                        </div>
                    </div>
        
                    <div class="container mt-1">
                        <div class="copyright">
                            &copy; Copyright <strong><span>onsemi</span></strong>. All Rights Reserved
                        </div>
                    </div>
        
                </footer> End Footer 
                 End Footer -->

        <a href="#" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

        <div id="preloader"></div>

        <!-- Vendor JS Files -->
        <script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${contextPath}/resources/vendor/aos/aos.js"></script>
        <script src="${contextPath}/resources/vendor/glightbox/js/glightbox.min.js"></script>
        <script src="${contextPath}/resources/vendor/purecounter/purecounter_vanilla.js"></script>
        <script src="${contextPath}/resources/vendor/swiper/swiper-bundle.min.js"></script>
        <script src="${contextPath}/resources/vendor/isotope-layout/isotope.pkgd.min.js"></script>
        <script src="${contextPath}/resources/vendor/php-email-form/validate.js"></script>

        <!-- Template Main JS File -->
        <script src="${contextPath}/resources/js/main.js"></script>

    </body>

</html>