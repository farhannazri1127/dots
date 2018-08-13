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
            <h1>Disposition</h1>
            <div class="row">
                <div class="col-lg-9">
                    <div class="main-box">
                        <h2>RMS Information</h2>
                        <form id="edit_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/disposition/update" method="post">
                            <input type="hidden" name="requestId" id="requestId" value="${request.id}" />
                            <input type="hidden" name="nextReqId" id="nextReqId" value="${nextReqId}" />
                            <input type="hidden" name="nextRmsEvent" id="nextRmsEvent" value="${nextRmsEvent}" />
                            <input type="hidden" name="nextInterval" id="nextInterval" value="${nextInterval}" />
                            <input type="hidden" name="dispoId" id="dispoId" value="${request.dispositionId}" />
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
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${request.quantity}" readonly>
                                </div>
                                <label for="expectedCondition" class="col-lg-2 control-label">Test Condition</label>                                 
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="expectedCondition" name="expectedCondition" value="${request.expectedTestCondition}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="loadingDate" class="col-lg-2 control-label">Loading Date </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="loadingDate" name="loadingDate" value="${request.loadingDateView}" readonly>
                                </div>
                                <label for="unloadingDate" class="col-lg-1 control-label">Unloading Date </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="unloadingDate" name="unloadingDate" value="${request.unloadingDateView}" readonly>
                                </div>
                            </div>
                            <hr class="separator">
                            <h2>Disposition</h2>
                            <div class="form-group">
                                <label for="disposition" class="col-lg-2 control-label">Disposition*</label>
                                <div class="col-lg-4">                                      
                                    <select id="disposition" name="disposition" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${disposition}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="remarksTtdiv">
                                <label for="remarks" class="col-lg-2 control-label">Remarks* </label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks">${request.dispoRemarks}</textarea>
                                    <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                </div>
                            </div>
                            <a href="${contextPath}/do/request/intervalCheck?rms_event=${nextRmsEvent}&interval=${nextInterval}" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });

                $('#loadingDate').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                $('#unloadingDate').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                jQuery.extend(jQuery.validator.messages, {
                    required: "This field is required.",
                    equalTo: "Value is not match! Please re-check.",
                    email: "Please enter a valid email.",
                });


                var validator = $("#edit_hardwarequest_form").validate({
                    rules: {
                        disposition: {
                            required: true
                        },
                        remarks: {
                            required: true,
                            maxlength: 150
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