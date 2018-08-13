<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            .select2-container-active .select2-choice,
            .select2-container-active .select2-choices {
                border: 1px solid $input-border-focus !important;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                /* -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;
                                   box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;*/
            }

            .select2-dropdown-open .select2-choice {
                border-bottom: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .select2-dropdown-open.select2-drop-above .select2-choice,
            .select2-dropdown-open.select2-drop-above .select2-choices {
                border: 1px solid $input-border-focus !important;
                border-top: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .no-border {
                border: 0;
                box-shadow: none; /* You may want to include this as bootstrap applies these styles too */
            }

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>RMS#_Event/Interval Details</h1>
            <div class="row">
                <div class="col-lg-9">
                    <div class="main-box">
                        <h2>RMS Information</h2>
                        <div class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            <b>*Please ensure all information are tally with RMS. If there are any issue, please contact Rel coordinator / Planner.</b>

                        </div> 
                        <form id="edit_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/lotPenang/breakIntervalUpdate" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="requestId" id="requestId" value="${request.id}" />
                            <input type="hidden" name="oldLotPenangId" id="oldLotPenangId" value="${oldLotPenangId}" />
                            <div class="form-group">
                                <label for="rms" class="col-lg-2 control-label">RMS</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="rms" name="rms" value="${request.rms}" readonly>
                                </div>
                                <label for="lotId" class="col-lg-1 control-label">Lot ID</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="lotId" name="lotId" value="${request.lotId}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="event" class="col-lg-2 control-label">Event</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="event" name="event" value="${request.event}" readonly>
                                </div>
                                <label for="device" class="col-lg-1 control-label">Device</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="device" name="device" value="${request.device}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="packages" class="col-lg-2 control-label">Package</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="packages" name="packages" value="${request.packages}" readonly>
                                </div>
                                <label for="interval" class="col-lg-1 control-label">Interval</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="interval" name="interval" value="${request.interval}" readonly> 
                                </div>
                                <c:if test="${request.event == 'TC'}">
                                    <label for="interval" class="col-lg-1 pull-left">cycle</label>
                                </c:if>
                                <c:if test="${request.event == 'HTSL'}">
                                    <label for="interval" class="col-lg-1 pull-left">hours</label>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="quantity" class="col-lg-2 control-label">Sample Size</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${request.quantity}">
                                </div>
                                <label for="expectedCondition" class="col-lg-2 control-label">Test Condition</label>                                 
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="expectedCondition" name="expectedCondition" value="${request.expectedTestCondition}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="loadingDate" class="col-lg-2 control-label">Loading Date *</label>
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="loadingDate" class="form-control" id="loadingDate" value="${request.loadingDateView}" disabled>
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="loadingDate" style="display: none;"></label>
                                </div>
                                <label for="unloadingDate" class="col-lg-1 control-label">Unloading Date *</label>
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="unloadingDate" class="form-control" id="unloadingDate" value="${request.unloadingDateView}" disabled>
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="unloadingDate" style="display: none;"></label>
                                </div>
                            </div>
                            <div class="form-group" id="remarksTtdiv">
                                <label for="remarks" class="col-lg-2 control-label">Remarks </label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-2 control-label" for="fileUpload">Upload new Trip Ticket</label>
                                <div class="col-lg-7">
                                    <input type="file" class="filestyle" data-buttonBefore="true" data-buttonBefore="true" data-iconName="glyphicon glyphicon-inbox" data-buttonName="btn-info" id="fileUpload" name="fileUpload" />
                                </div>
                            </div>
                            <a href="${contextPath}/lotPenang/breakInterval/${oldLotPenangId}" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" id="submit" class="btn btn-primary">Save</button>
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
        <script src="${contextPath}/resources/private/js/bootstrap-filestyle.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                $(":file").filestyle({buttonBefore: true});
                $(":file").filestyle({iconName: "glyphicon-inbox"});
                $(":file").filestyle({buttonName: "btn-info"});

                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });

                jQuery.extend(jQuery.validator.messages, {
                    required: "This field is required.",
                    equalTo: "Value is not match! Please re-check.",
                    email: "Please enter a valid email.",
                });


                var validator = $("#edit_hardwarequest_form").validate({
                    rules: {
                        quantity: {
                            required: true,
                            number: true
                        },
//                        ,
                        fileUpload: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>