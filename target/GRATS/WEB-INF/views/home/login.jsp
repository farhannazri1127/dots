<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <!--<title>HMS - Login</title>-->
        <title>GRATS - Login</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Reliability Apps Ticketing System">
        <meta name="author" content="GRATS">

        <!-- Le styles -->

        <!-- Vendor CSS Files -->
        <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/aos/aos.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">


        <!-- Le favicon -->
        <link rel="shortcut icon" href="${contextPath}/resources/img/ticketIcon.ico">
        <link href='${contextPath}/resources/css/googleFont.css' rel='stylesheet' type='text/css'>

        <!-- Template Main CSS File -->
        <link href="${contextPath}/resources/css/main.css" rel="stylesheet">

        <style>
            .divider:after,
            .divider:before {
                content: "";
                flex: 1;
                height: 1px;
                background: #eee;
            }
            .h-custom {
                height: calc(100% - 73px);
            }
            @media (max-width: 450px) {
                .h-custom {
                    height: 100%;
                }
            }

            footer {
                position: fixed;
                height: 5%;
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
            </div>
        </section><!-- End Top Bar -->

        <header id="header" class="header d-flex align-items-center">

            <div class="container-fluid container-xl d-flex align-items-center justify-content-between">
                <a href="${contextPath}/" class="logo d-flex align-items-center">
                    <!-- Uncomment the line below if you also wish to use an image logo -->
                    <!-- <img src="assets/img/logo.png" alt=""> -->
                    <h1>Global Rel Apps Ticketing System (GRATS)<span>.</span></h1>
                </a>
                <!--<i class="mobile-nav-toggle mobile-nav-show bi bi-list"></i>-->
                <!--<i class="mobile-nav-toggle mobile-nav-hide d-none bi bi-x"></i>-->
            </div>
        </header><!-- End Header -->
        <!-- End Header -->

        <section class="vh-99">
            <div class="container-fluid h-custom">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-md-9 col-lg-6 col-xl-5">
                        <img src="${contextPath}/resources/img/clipart633480.png"
                             class="img-fluid" alt="GRATS">
                    </div>
                    <div class="col-md-7 col-lg-5 col-xl-4 offset-xl-1">
                        <div class="divider d-flex align-items-center my-4">
                            <p class="text-center fw-bold mx-3 mb-0">Global Rel Apps Ticketing System</p>
                        </div>
                        <form action="${contextPath}/" method="post">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">
                                    <a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
                                    <strong>${error}</strong>
                                </div>
                            </c:if>
                            <!-- Email input -->
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" id="username" name="username" placeholder="name@example.com">
                                <label for="username">Ondex ID</label>
                            </div>
                            <div class="form-floating">
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                                <label for="password">Password</label>
                            </div>

                            <div class="form-outline text-center text-lg-start mt-4 pt-2">
                                <button type="submit" class="btn btn-success">Login</button>
<!--                                <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account? <a href="${contextPath}/register"
                                                                                                  class="link-danger">Register</a></p>-->
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <!-- ======= Footer ======= -->
        <footer id="footer" class="footer">

            <div class="container">
                <div class="row gy-4">
                    <div class="col-lg-1 col-md-12 footer-info">
                        <a href="${contextPath}/" class="logo d-flex align-items-center">
                            <span>GRATS</span>
                        </a>

                    </div>
                </div>
            </div>

            <div class="container mt-1">
                <div class="copyright">
                    &copy; Copyright <strong><span>onsemi</span></strong>. All Rights Reserved
                </div>
            </div>

        </footer><!-- End Footer -->
        <!-- End Footer -->


        <!-- Le javascript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
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
