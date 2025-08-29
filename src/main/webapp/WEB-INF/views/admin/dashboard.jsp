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
        <link rel="shortcut icon" href="${contextPath}/resources/img/ticketIcon.ico">

        <link href="${contextPath}/resources/css/googleFont.css" rel="stylesheet">

        <link href="${contextPath}/resources/vendor/mdb/css/mdb.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="${contextPath}/resources/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
        <!--<link href="${contextPath}/resources/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">-->

        <link href="${contextPath}/resources/css/main.css" rel="stylesheet">
        <link href="${contextPath}/resources/css/nav.css" rel="stylesheet">

        <!--from kiaalap-->
        <!-- Bootstrap CSS
            ============================================ -->
        <link rel="stylesheet" href="${contextPath}/resources/vendor/kiaalap-master/css/bootstrap.min.css">
        <!-- Bootstrap CSS
            ============================================ -->
        <link rel="stylesheet" href="${contextPath}/resources/vendor/kiaalap-master/css/font-awesome.min.css">

        <!-- animate CSS
                    ============================================ -->
        <link rel="stylesheet" href="${contextPath}/resources/vendor/kiaalap-master/css/animate.css">
        <!-- normalize CSS
                    ============================================ -->
        <link rel="stylesheet" href="${contextPath}/resources/vendor/kiaalap-master/css/normalize.css">

        <!-- morrisjs CSS
                    ============================================ -->
        <link rel="stylesheet" href="${contextPath}/resources/vendor/kiaalap-master/css/morrisjs/morris.css">

        <!-- style CSS
                    ============================================ -->
        <link rel="stylesheet" href="${contextPath}/resources/vendor/kiaalap-master/style.css">
        <!-- responsive CSS
                    ============================================ -->
        <link rel="stylesheet" href="${contextPath}/resources/vendor/kiaalap-master/css/responsive.css">

        <style>
            .all-content-wrapper {
                margin-left: 0px;
                margin-right: 0px;
            }

            footer {
                /*position: fixed;*/
                height: 100px;
                bottom: 0;
                width: 100%;
            }
            .graphHeight {
                height: 100%;
            }

            .mt-0 {
                margin-top: 3 !important;
            }
            .col-lg-2p {
                width: 20%;
            }
            .progress-bar-success2{
                background:#7A92A3;
            }
            .text-danger2{
                color:#933EC5;
            }

            .text-info2{
                color:#65b12d;
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
                    <i class="bi bi-search d-flex align-items-center  ms-4"><a href="${contextPath}/query">Query</a></i>
                    <i class="bi bi-house d-flex align-items-center  ms-4"><a href="${contextPath}/">Home</a></i>
                    <i class="bi bi-box-arrow-right d-flex align-items-center  ms-4"><a href="${contextPath}/logout">logout</a></i>
                </div>
            </div>
        </section><!-- End Top Bar -->

        <header id="header" class="header d-flex align-items-center">

            <div class="container-fluid container-xl d-flex align-items-center justify-content-between">
                <a href="${contextPath}/" class="logo d-flex align-items-center">
                    <h1>GRATS<span>.</span> | Dashboard</h1>
                </a>
            </div>
        </header>
        <!--<div class="sample-form">-->

        <div class="all-content-wrapper">
            <div class="analytics-sparkle-area">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-2p col-md-6 col-sm-6 col-xs-12">
                            <a href="${contextPath}/admin/new"><div class="analytics-sparkle-line reso-mg-b-30">
                                    <div class="analytics-content">
                                        <h5>New Incident/Request</h5>
                                        <!--<h2>$<span class="counter">5000</span> <span class="tuition-fees">Tuition Fees</span></h2>-->
                                        <h2><span class="counter">${newCompiled}</span></h2>
                                        <span class="text-inverse">${percentNew}%</span>
                                        <div class="progress m-b-0">
                                            <div class="progress-bar progress-bar-inverse" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:${percentNew}%;"> <span class="sr-only">20% Complete</span> </div>
                                        </div>
                                    </div>
                                </div></a>
                        </div>
                        <div class="col-lg-2p col-md-6 col-sm-6 col-xs-12">
                            <a href="${contextPath}/admin/pic"><div class="analytics-sparkle-line reso-mg-b-30">
                                    <div class="analytics-content">
                                        <h5>PIC Assigned</h5>
                                        <!--<h2>$<span class="counter">5000</span> <span class="tuition-fees">Tuition Fees</span></h2>-->
                                        <h2><span class="counter">${picAssignedCompiled}</span></h2>
                                        <span class="text-success2">${percentPic}%</span>
                                        <div class="progress m-b-0">
                                            <div class="progress-bar progress-bar-success2" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:${percentPic}%;"> <span class="sr-only">20% Complete</span> </div>
                                        </div>
                                    </div>
                                </div></a>
                        </div>
                        <div class="col-lg-2p col-md-6 col-sm-6 col-xs-12">
                            <a href="${contextPath}/admin/inProcess"><div class="analytics-sparkle-line reso-mg-b-30">
                                    <div class="analytics-content">
                                        <h5>In Process</h5>
                                        <h2><span class="counter">${inProcessCompiled}</span></h2>
                                        <span class="text-danger2">${percentInProcess}%</span>
                                        <div class="progress m-b-0">
                                            <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:${percentInProcess}%;"> <span class="sr-only">230% Complete</span> </div>
                                        </div>
                                    </div>
                                </div></a>
                        </div>
                        <div class="col-lg-2p col-md-6 col-sm-6 col-xs-12">
                            <a href="${contextPath}/admin/completed"><div class="analytics-sparkle-line reso-mg-b-30 table-mg-t-pro dk-res-t-pro-30">
                                    <div class="analytics-content">
                                        <h5>Completed</h5>
                                        <h2><span class="counter">${completeCompiled}</span></h2>
                                        <span class="text-info2">${percentComplete}%</span>
                                        <div class="progress m-b-0">
                                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:${percentComplete}%;"> <span class="sr-only">20% Complete</span> </div>
                                        </div>
                                    </div>
                                </div></a>
                        </div>
                        <div class="col-lg-2p col-md-6 col-sm-6 col-xs-12">
                            <a href="${contextPath}/admin/closeCancel"><div class="analytics-sparkle-line table-mg-t-pro dk-res-t-pro-30">
                                    <div class="analytics-content">
                                        <h5>Closed/Cancelled</h5>
                                        <h2><span class="counter">${closeCancelCompiled}</span></h2>
                                        <span class="text-success">${percentClosed}%</span>
                                        <div class="progress m-b-0">
                                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:${percentClosed}%;"> <span class="sr-only">230% Complete</span> </div>
                                        </div>
                                    </div>
                                </div></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="product-sales-area mg-tb-30">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-9 col-md-12 col-sm-12 col-xs-12 ">
                            <div class="product-sales-chart graphHeight">
                                <div class="portlet-title">
                                    <div class="row">
                                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                            <div class="caption pro-sl-hd">
                                                <span class="caption-subject"><b>Request VS Closed Ticket [${year}]</b></span>
                                            </div>
                                        </div>
                                        <!--                                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                                                                    <div class="actions graph-rp graph-rp-dl">
                                                                                        <p>All Earnings are in million $</p>
                                                                                    </div>
                                                                                </div>-->
                                    </div>
                                </div>
                                <ul class="list-inline cus-product-sl-rp">
                                    <li>
                                        <h5><i class="fa fa-circle" style="color: #006DF0;"></i>Request</h5>
                                    </li>
                                    <li>
                                        <h5><i class="fa fa-circle" style="color: #7A92A3;"></i>Closed/Cancelled</h5>
                                    </li>
                                </ul>
                                <div id="morris-bar-chart" style="height: 356px;"></div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                            <div class="analysis-progrebar res-mg-t-30 mg-ub-10 table-mg-t-pro-n res-mg-b-30 tb-sm-res-d-n dk-res-t-d-n">
                                <a href="${contextPath}/admin/atiqah"><div class="analysis-progrebar-content">
                                        <h5>Nor Atiqah Mohd Zahar</h5>
                                        <h2 class="storage-right"><span class="counter">${percentAtiqah}</span>%</h2>
                                        <div class="progress progress-mini ug-1">
                                            <div style="width: ${percentAtiqah}%;" class="progress-bar"></div>
                                        </div>
                                        <div class="m-t-sm small">
                                            <p>Closed/Cancelled:<b>${compTotalAtiqah}</b></p>
                                            <p>Total Ticket:<b>${totalAtiqah}</b></p> 
                                        </div>
                                    </div></a>
                            </div>
                            <div class="analysis-progrebar reso-mg-b-30 mg-ub-10 res-mg-b-30 tb-sm-res-d-n dk-res-t-d-n">
                                <a href="${contextPath}/admin/arif"><div class="analysis-progrebar-content">
                                        <h5>Mohd Arif Dzainal Abidin</h5>
                                        <h2 class="storage-right"><span class="counter">${percentArif}</span>%</h2>
                                        <div class="progress progress-mini ug-2">
                                            <div style="width: ${percentArif}%;" class="progress-bar"></div>
                                        </div>
                                        <div class="m-t-sm small">
                                            <p>Closed/Cancelled:<b>${compTotalArif}</b></p>
                                            <p>Total Ticket:<b>${totalArif}</b></p>
                                        </div>
                                    </div></a>
                            </div>
                            <div class="analysis-progrebar reso-mg-b-30 res-mg-t-30 mg-ub-10 res-mg-b-30 tb-sm-res-d-n dk-res-t-d-n">
                                <a href="${contextPath}/admin/farhan"><div class="analysis-progrebar-content">
                                        <h5>Mohd Farhan Nazri</h5>
                                        <h2 class="storage-right"><span class="counter">${percentFarhan}</span>%</h2>
                                        <div class="progress progress-mini ug-3">
                                            <div style="width: ${percentFarhan}%;" class="progress-bar progress-bar-danger"></div>
                                        </div>
                                        <div class="m-t-sm small">
                                            <p>Closed/Cancelled:<b>${compTotalFarhan}</b></p>
                                            <p>Total Ticket:<b>${totalFarhan}</b></p>
                                        </div>
                                    </div></a>
                            </div>
                            <div class="analysis-progrebar res-mg-t-30 table-dis-n-pro tb-sm-res-d-n dk-res-t-d-n">
                                <a href="${contextPath}/admin/total"><div class="analysis-progrebar-content">
                                        <h5>Total</h5>
                                        <h2 class="storage-right"><span class="counter">${percentTotal}</span>%</h2>
                                        <div class="progress progress-mini ug-4">
                                            <div style="width: ${percentTotal}%;" class="progress-bar progress-bar-danger"></div>
                                        </div>
                                        <div class="m-t-sm small">
                                            <p>Closed/Cancelled:<b>${compTotalCompiled}</b></p>
                                            <p>Total Ticket:<b>${totalCompiled}</b></p>
                                        </div>
                                    </div></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--</div>-->
        <!-- Tabs content -->

        <!-- ======= Footer ======= -->
        <footer id="footer" class="footer">

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

        </footer> 

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

        <!--from kiaalap-->
        <!-- jquery
               ============================================ -->
        <script src="${contextPath}/resources/vendor/kiaalap-master/js/vendor/jquery-1.12.4.min.js"></script>
        <!-- wow JS
                    ============================================ -->
        <script src="${contextPath}/resources/vendor/kiaalap-master/js/wow.min.js"></script>
        <!-- counterup JS
                    ============================================ -->
        <script src="${contextPath}/resources/vendor/kiaalap-master/js/counterup/jquery.counterup.min.js"></script>
        <script src="${contextPath}/resources/vendor/kiaalap-master/js/counterup/waypoints.min.js"></script>
        <script src="${contextPath}/resources/vendor/kiaalap-master/js/counterup/counterup-active.js"></script>
        <!-- morrisjs JS
                    ============================================ -->
        <script src="${contextPath}/resources/vendor/kiaalap-master/js/morrisjs/raphael-min.js"></script>
        <script src="${contextPath}/resources/vendor/kiaalap-master/js/morrisjs/morris.js"></script>
        <!-- plugins JS
                    ============================================ -->
        <script src="${contextPath}/resources/vendor/kiaalap-master/js/plugins.js"></script>

        <!-- Template Main JS File -->
        <script src="${contextPath}/resources/js/main.js"></script>

        <script>
            $(document).ready(function () {


                Morris.Bar({
                    element: 'morris-bar-chart',
                    data: [{
                            period: 'Jan',
                            Request: ${totalJan},
                            Closed: ${compJan}

                        }, {
                            period: 'Feb',
                            Request: ${totalFeb},
                            Closed: ${compFeb}

                        }, {
                            period: 'Mar',
                            Request: ${totalMar},
                            Closed: ${compMar}

                        }, {
                            period: 'Apr',
                            Request: ${totalApr},
                            Closed: ${compApr}

                        }, {
                            period: 'May',
                            Request: ${totalMay},
                            Closed: ${compMay}

                        }, {
                            period: 'Jun',
                            Request: ${totalJun},
                            Closed: ${compJun}

                        },
                        {
                            period: 'Jul',
                            Request: ${totalJul},
                            Closed: ${compJul}

                        },
                        {
                            period: 'Aug',
                            Request: ${totalAug},
                            Closed: ${compAug}

                        },
                        {
                            period: 'Sep',
                            Request: ${totalSep},
                            Closed: ${compSep}

                        },
                        {
                            period: 'Oct',
                            Request: ${totalOct},
                            Closed: ${compOct}

                        },
                        {
                            period: 'Nov',
                            Request: ${totalNov},
                            Closed: ${compNov}

                        },
                        {
                            period: 'Dec',
                            Request: ${totalDec},
                            Closed: ${compDec}

                        }, ],
                    xkey: 'period',
                    ykeys: ['Request', 'Closed'],
                    labels: ['Request', 'Closed'],
                    pointSize: 3,
                    fillOpacity: 0,
                    pointStrokeColors: ['#006DF0', '#933EC5'],
                    behaveLikeLine: true,
                    gridLineColor: '#e0e0e0',
                    lineWidth: 1,
                    hideHover: 'auto',
                    lineColors: ['#006DF0', '#933EC5'],
                    resize: true

                });
            });
        </script>

    </body>

</html>