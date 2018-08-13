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
            <h1>Pre VM Before Shipment</h1>
            <div class="row">
                <div class="col-lg-10">
                    <div class="main-box">
                        <h2>RMS Information</h2>
                        <form id="edit_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/do/preVm/update" method="post">
                            <input type="hidden" name="requestId" value="${preVm.requestId}" />
                            <input type="hidden" name="id" value="${preVm.id}" />
                            <div class="form-group">
                                <label for="rms" class="col-lg-1 control-label">RMS</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="rms" name="rms" value="${request.rms}" readonly>
                                </div>
                                <label for="lotId" class="col-lg-1 control-label">Lot ID</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="lotId" name="lotId" value="${request.lotId}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="event" class="col-lg-1 control-label">Event</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="event" name="event" value="${request.event}" readonly>
                                </div>
                                <label for="device" class="col-lg-1 control-label">Device</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="device" name="device" value="${request.device}" readonly>
                                    <!--<img src="${contextPath}/images/a9rEw9W_700b.jpg" style="width: 50%"/>-->
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="packages" class="col-lg-1 control-label">Package</label>
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
                                <label for="quantity" class="col-lg-1 control-label">Sample Size</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${request.quantity}" readonly>
                                </div>
                                <label for="expectedCondition" class="col-lg-2 control-label">Test Condition</label>                                 
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="expectedCondition" name="expectedCondition" value="${request.testCondition}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="loadingDate" class="col-lg-1 control-label">Loading Date </label>
                                <div class="col-lg-3">
                                    <input type="text" name="loadingDate" class="form-control" id="loadingDate" value="${request.loadingDateView}" disabled>
                                </div>
                                <label for="unloadingDate" class="col-lg-1 control-label">Unloading Date </label>
                                <div class="col-lg-3">
                                    <input type="text" name="unloadingDate" class="form-control" id="unloadingDate" value="${request.unloadingDateView}" disabled>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="chamber" class="col-lg-1 control-label">Chamber </label>
                                <div class="col-lg-3">
                                    <input type="text" name="chamber" class="form-control" id="chamber" value="${request.chamber}" disabled>
                                </div>
                                <label for="chamberLevel" class="col-lg-1 control-label">Chamber Level </label>
                                <div class="col-lg-1">
                                    <input type="text" name="chamberLevel" class="form-control" id="chamberLevel" value="${request.chamberLocation}" disabled>
                                </div>
                            </div>
                            <hr class="separator">
                            <h2>Pre VM</h2>
                            <div class="form-group">
                                <label for="quantityMatch" class="col-lg-1 control-label">Quantity per Lot Match?</label>
                                <div class="col-lg-3">                                      
                                    <select id="quantityMatch" name="quantityMatch" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${quantityMatch}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="cdpaSample" class="col-lg-2 control-label"><strong>Any CDPA/DPA/SAT/OTHERS samples?</strong></label>
                                <div class="col-lg-2">                                      
                                    <select id="cdpaSample" name="cdpaSample" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${cdpaSample}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="rmsMatch" class="col-lg-1 control-label">RMS/Lot Match?</label>
                                <div class="col-lg-3">                                      
                                    <select id="rmsMatch" name="rmsMatch" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${rmsMatch}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="packageMatch" class="col-lg-1 control-label">Package Match?</label>
                                <div class="col-lg-3">                                      
                                    <select id="packageMatch" name="packageMatch" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${packageMatch}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="eventMatch" class="col-lg-1 control-label">Event Match?</label>
                                <div class="col-lg-3">                                      
                                    <select id="eventMatch" name="eventMatch" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${eventMatch}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="intervalMatch" class="col-lg-1 control-label">Interval Match?</label>
                                <div class="col-lg-3">                                      
                                    <select id="intervalMatch" name="intervalMatch" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${intervalMatch}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="pcbEventMatch" class="col-lg-1 control-label">Event Code on PCB Match?</label>
                                <div class="col-lg-3">                                      
                                    <select id="pcbEventMatch" name="pcbEventMatch" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbEventMatch}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="chamberMatch" class="col-lg-1 control-label">Chamber in Penang?</label>
                                <div class="col-lg-3">                                      
                                    <select id="chamberMatch" name="chamberMatch" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${chamberMatch}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="vmMix" class="col-lg-1 control-label">VM Mix</label>
                                <div class="col-lg-3">                                      
                                    <select id="vmMix" name="vmMix" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${vmMix}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>                                     
                                <label for="vmMixRemarks" class="col-lg-1 control-label">VM Mix Remarks </label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="5" id="vmMixRemarks" name="vmMixRemarks">${preVm.vmMixRemarks}</textarea>
                                    <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="vmDemount" class="col-lg-1 control-label">VM Demount</label>
                                <div class="col-lg-3">                                      
                                    <select id="vmDemount" name="vmDemount" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${vmDemount}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>                                     
                                <label for="vmDemountRemarks" class="col-lg-1 control-label">VM Demount Remarks </label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="5" id="vmDemountRemarks" name="vmDemountRemarks">${preVm.vmDemountRemarks}</textarea>
                                    <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="vmBroken" class="col-lg-1 control-label">VM Broken</label>
                                <div class="col-lg-3">                                      
                                    <select id="vmBroken" name="vmBroken" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${vmBroken}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>                                     
                                <label for="vmBrokenRemarks" class="col-lg-1 control-label">VM Broken Remarks </label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="5" id="vmBrokenRemarks" name="vmBrokenRemarks">${preVm.vmBrokenRemarks}</textarea>
                                    <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                </div>
                            </div>
                            <div class="form-group" id="remarksTtdiv">
                                <label for="remarks" class="col-lg-1 control-label">Remarks </label>
                                <div class="col-lg-11">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks">${preVm.remarks}</textarea>
                                    <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                </div>
                            </div>
                            <a href="${contextPath}/do/request" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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

                jQuery.extend(jQuery.validator.messages, {
                    required: "This field is required.",
                    equalTo: "Value is not match! Please re-check.",
                    email: "Please enter a valid email.",
                });


                var validator = $("#edit_hardwarequest_form").validate({
                    rules: {
                        quantityMatch: {
                            required: true
                        },
                        cdpaSample: {
                            required: true
                        },
                        rmsMatch: {
                            required: true
                        },
                        packageMatch: {
                            required: true
                        },
                        eventMatch: {
                            required: true
                        },
                        intervalMatch: {
                            required: true
                        },
                        pcbEventMatch: {
                            required: true
                        },
                        vmMix: {
                            required: true
                        },
                        vmMixRemarks: {
                            maxlength: 150,
                            required: function () {
                                if ($('#vmMix').val() === "Fail") {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        vmDemount: {
                            required: true
                        },
                        vmDemountRemarks: {
                            maxlength: 150,
                            required: function () {
                                if ($('#vmDemount').val() === "Fail") {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        vmBroken: {
                            required: true,
                            maxlength: 150
                        },
                        vmBrokenRemarks: {
                            maxlength: 150,
                            required: function () {
                                if ($('#vmBroken').val() === "Fail") {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        remarks: {
                            maxlength: 150
                        },
                        chamberMatch: {
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