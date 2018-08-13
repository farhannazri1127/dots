<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <!--<title>HMS - Login</title>-->
        <title>DOTS - Login</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Delivery Order Tracking System">
        <meta name="author" content="DOTS">

        <!-- Le styles -->
        <link href="${contextPath}/resources/public/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/public/css/bootstrap-responsive.min.css" rel="stylesheet">

        <link rel="stylesheet" href="${contextPath}/resources/public/css/typica-login.css">

        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <!-- Le favicon -->
        <link rel="shortcut icon" href="${contextPath}/resources/img/favicondots.ico">

        <style>
            body {
                /*background: url(../img/bg1.png) no-repeat center center fixed;*/
                background: url(/DOTS/resources/public/img/blue.jpg) no-repeat center center fixed;
                -webkit-background-size: cover;
                -moz-background-size: cover;
                -o-background-size: cover;
                background-size: cover;
            }

            #login-wraper {
                -webkit-border-radius: 15px;
                -moz-border-radius: 15px;
                -ms-border-radius: 15px;
                -o-border-radius: 15px;
                border-radius: 15px;
                -webkit-box-shadow: 0 0 8px rgba(0, 0, 0, 0.4);
                -moz-box-shadow: 0 0 8px rgba(0, 0, 0, 0.4);
                box-shadow: 0 0 8px rgba(0, 0, 0, 0.4);
                position: absolute;
                top: 41%;
                left: 50%;
                display: block;
                margin-top: -185px;
                margin-left: -235px;
                padding: 25px;
                width: 420px;
                height: 500px;
                background: #326483;
                text-align: center;
            }

            .login-form label {
                font-family: 'Lato', Helvetica, sans-serif;
                font-size: 20px;
                color: #ffffff;
                font-weight: 300;
            }

            .login-form legend {
                font-family: 'Lato', Helvetica, sans-serif;
                font-size: 25px;
                font-weight: 300;
                color: #ffffff;
                -webkit-font-smoothing: subpixel-antialiased;
            }
        </style>

    </head>

    <body>

        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <!--<center style="margin-top: 30px;"><a href="${contextPath}/" style="font-family: 'Lato'; font-size: 22px;">Hardware Management System (HMS) - Rel Lab</a></center>-->
                    <center style="margin-top: 30px;"><a href="${contextPath}/" style="font-family: 'Lato'; font-size: 22px;">Delivery Order Tracking System (DOTS)</a></center>

                </div>
            </div>
        </div>

        <div class="container">

            <div id="login-wraper">
                <form action="${contextPath}/" method="post" class="form login-form">
                    <a href="${contextPath}/">
                        <img src="${contextPath}/resources/img/dots2.png" alt="DOTS" width="35%" />
                    </a>
                    <!--<legend>Sign in to <span class="blue">HMS</span></legend>-->
                    <legend>Sign in to <span class="white">DOTS</span></legend>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            <a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
                            <strong>${error}</strong>
                        </div>
                    </c:if>

                    <div class="body">
                        <!--<label>Username</label-->
                        <label>Ondex ID</label>
                        <input type="text" name="username" autofocus="autofocus">

                        <label>Password</label>
                        <input type="password" name="password">
                    </div>

                    <div class="footer">
                        <!--<label class="checkbox inline">
                            <input type="checkbox" id="inlineCheckbox1" value="option1"> Remember me
                        </label>-->

                        <button type="submit" class="btn btn-success">Login</button>
                    </div>

                </form>
            </div>

        </div>

        <footer class="white navbar-fixed-bottom">
            <!--Apply for Strategic Partner?&nbsp;&nbsp;<a hrcef="${contextPath}/register" class="btn btn-black">Register</a>-->
        </footer>


        <!-- Le javascript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${contextPath}/resources/public/js/jquery.js"></script>
        <script src="${contextPath}/resources/public/js/bootstrap.js"></script>
        <script src="${contextPath}/resources/public/js/backstretch.min.js"></script>
        <script src="${contextPath}/resources/public/js/typica-typica-login-2.js"></script>

    </body>
</html>
