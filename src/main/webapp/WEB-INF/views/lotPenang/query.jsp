<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            @media print {
                /*                table  {
                                    border-top: #000 solid 1px;
                                    border-bottom: #000 solid 1px;
                                    border-left: #000 solid 1px;
                                    border-right: #000 solid 1px;
                                }*/
                table thead {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
                table tbody {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
            }
            .dataTables_wrapper .dt-buttons {
                float:none;  
                text-align:right;
            }

            .select2-container-active .select2-choice,
            .select2-container-active .select2-choices {
                border: 1px solid $input-border-focus !important;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
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
            <h1>Query</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box">
                        <h2>Query Requirements Search</h2>
                        <form id="update_hardwareinventory_form" class="form-horizontal" role="form" action="${contextPath}/query" method="post" style="width: 100%">
                            <div class="form-group col-lg-12" style="font-style: italic; color: red;" >
                                *Please insert the requirement(s) accordingly.</font
                                <br />
                            </div>

                            <div class="form-group col-lg-12" id = "alert_placeholder"></div>
                            <div class="form-group col-lg-11" >
                                <label for="rms_event" class="col-lg-2 control-label">RMS_event</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="rms_event" name="rms_event">
                                </div>
                                <label for="rms" class="col-lg-2 control-label">RMS</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="rms" name="rms">
                                </div>
                            </div>
                            <div class="form-group col-lg-11" >
                                <label for="event" class="col-lg-2 control-label">Event</label>
                                <div class="col-lg-3">                                      
                                    <select id="event" name="event" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${eventList}" var="group">
                                            <option value="${group.name}" >${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="interval" class="col-lg-2 control-label">Interval</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="interval" name="interval">
                                </div>
                            </div>
                            <div class="form-group col-lg-11" >
                                <label for="device" class="col-lg-2 control-label">Device</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="device" name="device">
                                </div>
                                <label for="packages" class="col-lg-2 control-label">Package</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="packages" name="packages">
                                </div>
                            </div>
                            <div class="form-group col-lg-11" >
                                <label for="chamber" class="col-lg-2 control-label">Chamber</label>
                                <div class="col-lg-3">                                      
                                    <select id="chamber" name="chamber" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${chamberList}" var="group">
                                            <option value="${group.name}" >${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="gts" class="col-lg-2 control-label">GTS No.</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="gts" name="gts">
                                </div>
                            </div>
                            <div class="form-group col-lg-11" >
                                <label for="doNumber" class="col-lg-2 control-label">DO No.</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="doNumber" name="doNumber">
                                </div>
                                <label for="loadingDate" class="col-lg-2 control-label">Loading Date </label>
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="loadingDate" class="form-control" id="loadingDate" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="loadingDate" style="display: none;"></label>
                                </div>
                            </div>
                            <div class="form-group col-lg-11" >
                                <label for="unloadingDate" class="col-lg-2 control-label">Unloading Date </label>
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="unloadingDate" class="form-control" id="unloadingDate" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="unloadingDate" style="display: none;"></label>
                                </div>
                                <label for="shipToPenang" class="col-lg-2 control-label">Shipment Date to Penang </label>
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="shipToPenang" class="form-control" id="shipToPenang" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="shipToPenang" style="display: none;"></label>
                                </div>
                            </div>  
                            <div class="form-group col-lg-11" >
                                <label for="penangReceived" class="col-lg-2 control-label">Received Date in Penang </label>
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="penangReceived" class="form-control" id="penangReceived" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="penangReceived" style="display: none;"></label>
                                </div>
                                <label for="penangShipment" class="col-lg-2 control-label">Shipment Date to Rel Lab </label>
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="penangShipment" class="form-control" id="penangShipment" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="penangShipment" style="display: none;"></label>
                                </div>
                            </div>  
                            <div class="form-group col-lg-11" >
                                <label for="relReceived" class="col-lg-2 control-label">Received Date in Rel Lab </label>
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="relReceived" class="form-control" id="relReceived" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="relReceived" style="display: none;"></label>
                                </div>
                                <label for="status" class="col-lg-2 control-label">Status</label>
                                <div class="col-lg-3">                                      
                                    <select id="status" name="status" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${statusList}" var="group">
                                            <option value="${group.name}" >${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>  
                            <div class="col-lg-12">
                                <br/>
                            </div>
                            <div class="col-lg-12">
                                <!--<a href="${contextPath}/wh/whRetrieve" class="btn btn-info pull-left" id="cancel"><i class="fa fa-reply"></i>Back</a>-->
                                <button type="submit" class="btn btn-primary pull-right" name="submit" id="submit" >View Data</button>
                            </div>
                            <div class="clearfix"><br/></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <!--                        <div class="clearfix">
                                                    <h2 class="pull-left">Query Search List</h2>
                                                </div>-->
                        <hr/>
                        <div class="clearfix">
                            <div class="form-group pull-left">
                                <select id="dt_spml_rows" class="form-control">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select>
                            </div>
                            <div class="filter-block pull-right">
                                <div id="dt_spml_tt" class="form-group pull-left" style="margin-right: 5px;"></div>
                                <div class="form-group pull-left" style="margin-right: 0px;">
                                    <input id="dt_spml_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>">
                                    <i class="fa fa-search search-icon"></i>
                                </div>
                            </div>
                        </div>
                        <!--<div><br/></div>-->
                        <div class="table-responsive">            
                            <!--<table id="dt_spml" class="table" style="font-size: 10px;">-->
                            <table id="dt_spml" class="table">
                                <thead>
                                    <tr>
                                        <th><span>No</span></th>
                                        <th><span>RMS_event</span></th>
                                        <th><span>Interval</span></th>
                                        <th><span>DO Number</span></th> 
                                        <th><span>GTS Number</span></th>
                                        <th><span>Shipment to Penang</span></th>
                                        <th><span>Chamber</span></th>
                                        <th><span>Loading Date</span></th>
                                        <th><span>Unloading Date</span></th>
                                        <th><span>Status</span></th>
                                        <th><span>Log</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${retrieveQueryList}" var="whRetrieve" varStatus="whRetrieveLoop">
                                        <tr>
                                            <td><c:out value="${whRetrieveLoop.index+1}"/></td>
                                            <td><c:out value="${whRetrieve.rmsEvent}"/></td>
                                            <td><c:out value="${whRetrieve.interval}"/></td>
                                            <td><c:out value="${whRetrieve.doNumber}"/></td>
                                            <td><c:out value="${whRetrieve.gts}"/></td>
                                            <td><c:out value="${whRetrieve.shipmentFromRelView}"/></td>
                                            <td><c:out value="${whRetrieve.chamberId}"/></td>
                                            <td><c:out value="${whRetrieve.loadingDateView}"/></td>
                                            <td><c:out value="${whRetrieve.unloadingDateView}"/></td>
                                            <td><c:out value="${whRetrieve.status}"/></td>
                                            <td align="left">
                                                <a href="${contextPath}/query/view/${whRetrieve.requestId}" class="table-link" title="Log">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-search-plus fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
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

        <!--print-->
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
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

                $('#shipToPenang').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                $('#penangReceived').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                $('#penangShipment').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                $('#relReceived').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

//                var validator = $("#update_hardwareinventory_form").validate({
//                    rules: {
//                        requestedDate1: {
//                            required: function (element) {
//                                if ($('#requestedDate1').val() === "" && $('#requestedDate2').val() !== "") {
//                                    return true;
//                                } else {
//                                    return false;
//                                }
//                            }
//                        },
//                        requestedDate2: {
//                            required: function (element) {
//                                if ($('#requestedDate2').val() === "" && $('#requestedDate1').val() !== "") {
//                                    return true;
//                                } else {
//                                    return false;
//                                }
//                            }
//                        },
//                        materialPassExpiry1: {
//                            required: function (element) {
//                                if ($('#materialPassExpiry1').val() === "" && $('#materialPassExpiry2').val() !== "") {
//                                    return true;
//                                } else {
//                                    return false;
//                                }
//                            }
//                        },
//                        materialPassExpiry2: {
//                            required: function (element) {
//                                if ($('#materialPassExpiry2').val() === "" && $('#materialPassExpiry1').val() !== "") {
//                                    return true;
//                                } else {
//                                    return false;
//                                }
//                            }
//                        }
//                    }
//                });

                $(".cancel").click(function () {
                    validator.resetForm();
                });

                $(".submit").click(function () {
                    $("#data").show();
                });

//                oTable = $('#dt_spml').DataTable({
//                    dom: 'Bfrtip',
//                    buttons: [
//                        'print'
//                    ]
//                });
                oTable = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
                            },
                            customize: function (win) {
                                $(win.document.body)
                                        .css('font-size', '10pt');
                                $(win.document.body).find('table')
                                        .addClass('compact')
                                        .css('font-size', 'inherit');
                            }
                        }
                    ]
                });

//                oTable.buttons().container().appendTo($("#dt_spml_tt", oTable.table().container() ) );

                $('#dt_spml_search').keyup(function () {
                    oTable.search($(this).val()).draw();
                });

                $("#dt_spml_rows").change(function () {
                    oTable.page.len($(this).val()).draw();
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>