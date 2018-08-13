<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Add RMS#_Event</h1>
            <div class="row">
                <div class="col-lg-8">
                    <div class="main-box">
                        <div class="clearfix">
                            <h2 class="pull-left">RMS#_Event Information</h2>  
                            <div class="filter-block pull-right">
                                <a href="${contextPath}/do/request/intervalCheck" class="btn btn-primary pull-right">
                                    <i class="fa fa-check-square-o fa-lg"></i>  Check Interval Availability
                                </a>
                            </div>
                        </div>

                        <form id="add_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/do/request/save" method="post">
                            <div class="form-group">
                                <label for="barcode" class="col-lg-2 control-label">RMS#_Event</label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="barcode" name="barcode" placeholder="Scan Barcode" value="${barcode}" autofocus>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="interval" class="col-lg-2 control-label">Interval</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="interval" name="interval" value="${interval}" autofocus>
                                    <input type="hidden" class="form-control" id="previousinterval" name="previousinterval" value="${countPrevInterval}" >
                                    <input type="hidden" class="form-control" id="reqEmail" name="reqEmail" value="${reqEmail}" >
                                </div>
                            </div>
                            <a href="${contextPath}/do/request" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" id="submit" class="btn btn-primary">Submit</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                var element = $('#previousinterval');
                var element2 = $('#barcode');
                var element3 = $('#interval');
                var element4 = $('#reqEmail');

                var element5 = element2.val().split("_")[0] + "_" + element2.val().split("_")[1];
                var element6 = element2.val().split("_")[2];

//                alert(element5);

                var test = "";
                if (element2.val().includes("TC")) {
                    test = "cyc";
                } else if (element2.val().includes("HTSL")) {
                    test = "hrs";
                }
                if (element.val() !== "" && element.val() !== "0") {

                    //original
//                    var deleteUrl = "${contextPath}/do/request/sendEmail/" + element2.val() + "/" + element3.val() + "/" + element.val() + "/" + element4.val();
//                    var deleteMsg = "There are <b>" + element.val() + "</b> interval before <b>" + element2.val() + " - " + element3.val() + " " + test + " </b>not register into DOTS yet. Please click <b>'Send email'</b> for Supervisor \n\
//                clarification or click <b>'Check Interval Availability'</b> for more details.";
//                    $("#email_modal .modal-body").html(deleteMsg);
//                    $("#modal_email_button").attr("href", deleteUrl);
//                    $('#email_modal').modal('show');
//                    
//                    var deleteUrl = "${contextPath}/do/request/sendEmail/" + element5 + "/" + element6 + "/" + element.val() + "/" + element4.val();
                    var deleteUrl = "${contextPath}/do/request/sendEmail/" + element5 + "/" + element3.val() + "/" + element.val() + "/" + element4.val();
                    var deleteMsg = "There are <b>" + element.val() + "</b> interval before <b>" + element5 + " - " + element3.val() + " " + test + " </b>not register into DOTS yet. Please click <b>'Send email'</b> for Supervisor \n\
                clarification or click <b>'Check Interval Availability'</b> for more details.";
                    $("#email_modal .modal-body").html(deleteMsg);
                    $("#modal_email_button").attr("href", deleteUrl);
                    $('#email_modal').modal('show');
                }


                var validator = $("#add_hardwarequest_form").validate({
                    rules: {
                        barcode: {
                            required: true
                        },
                        interval: {
                            required: true,
                            number: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
            });

            function modalDelete(e) {
                var deleteId = $(e).attr("modaldeleteid");
                var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                var deleteUrl = "${contextPath}/lotPenang/cancelInterval/" + deleteId;
                var deleteMsg = "Are you sure want to cancel interval for this rms#_event?";
                $("#cancel_modal .modal-body").html(deleteMsg);
                $("#modal_cancel_button").attr("href", deleteUrl);
            }

        </script>
    </s:layout-component>
</s:layout-render>