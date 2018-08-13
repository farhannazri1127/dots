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
            <h1>Previous Interval Availability</h1>
            <div class="row">
                <div class="col-lg-8">
                    <div class="main-box">
                        <h2>Query Requirements Search</h2>
                        <form id="update_hardwareinventory_form" class="form-horizontal" role="form" action="${contextPath}/do/request/intervalCheck" method="post" style="width: 100%">
                            <div class="form-group col-lg-12" style="font-style: italic; color: red;" >
                                *Please insert the requirement(s) accordingly.</font
                                <br />
                            </div>

                            <div class="form-group col-lg-12" id = "alert_placeholder"></div>
                            <div class="form-group col-lg-11" >
                                <label for="rms_event" class="col-lg-2 control-label">RMS_event</label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="rms_event" name="rms_event" placeholder="Scan Barcode" autofocus="">
                                </div>
                            </div>
                            <div class="form-group col-lg-11" >
                                <label for="interval" class="col-lg-2 control-label">Interval</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="interval" name="interval">
                                </div>
                            </div> 
                            <div class="col-lg-12">
                                <br/>
                            </div>
                            <div class="col-lg-12">
                                <a href="${contextPath}/do/request/add" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                                        <th><span>Quantity</span></th> 
                                        <th><span>Device</span></th> 
                                        <th><span>Package</span></th> 
                                        <th><span>Status</span></th>
                                        <th><span>Disposition</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${retrieveQueryList}" var="whRequest" varStatus="whRetrieveLoop">
                                        <tr>
                                            <td class = "col-lg-1"><c:out value="${whRetrieveLoop.index+1}"/></td>
                                            <td><c:out value="${whRequest.rmsEvent}"/></td>
                                            <td class = "col-lg-1"><c:out value="${whRequest.interval}"/></td>
                                            <td class = "col-lg-1"><c:out value="${whRequest.quantity}"/></td>
                                            <td><c:out value="${whRequest.device}"/></td>
                                            <td><c:out value="${whRequest.packages}"/></td>
                                            <td><c:out value="${whRequest.disposition}"/></td>
                                            <td class = "col-lg-1" align="left">
                                                <c:choose>
                                                    <c:when test="${groupId == '1' || groupId== '2' || groupId== '29'}">
                                                        <a href="${contextPath}/disposition/edit/${whRequest.id}" id="disposition" class="table-link" title="Disposition">
                                                            <span class="fa-stack">
                                                                <i class="fa fa-square fa-stack-2x"></i>
                                                                <i class="fa fa-arrow-circle-right fa-stack-1x fa-inverse"></i>
                                                            </span>
                                                        </a> 
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a modaldeleteid="${whRequest.id}" title="Disposition" class="table-link" style="color: silver" onclick="return false;">
                                                            <span class="fa-stack">
                                                                <i class="fa fa-square fa-stack-2x"></i>
                                                                <i class="fa fa-arrow-circle-right fa-stack-1x fa-inverse"></i>
                                                            </span>
                                                        </a> 
                                                    </c:otherwise>
                                                </c:choose>
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

                                                                var validator = $("#update_hardwareinventory_form").validate({
                                                                    rules: {
                                                                        rms_event: {
                                                                            required: true
                                                                        },
                                                                        interval: {
                                                                            required: true,
                                                                            number: true
                                                                        }
                                                                    }
                                                                });

                                                                $(".js-example-basic-single").select2({
                                                                    placeholder: "Choose one",
                                                                    allowClear: true
                                                                });

                                                                $(".cancel").click(function () {
                                                                    validator.resetForm();
                                                                });

                                                                $(".submit").click(function () {
                                                                    $("#data").show();
                                                                });

                                                                oTable = $('#dt_spml').DataTable({
                                                                    dom: 'Brtip',
                                                                    buttons: [
                                                                        {
                                                                            extend: 'copy',
                                                                            exportOptions: {
                                                                                columns: [0, 1, 2, 3, 4]
                                                                            }
                                                                        },
                                                                        {
                                                                            extend: 'excel',
                                                                            exportOptions: {
                                                                                columns: [0, 1, 2, 3, 4]
                                                                            }
                                                                        },
                                                                        {
                                                                            extend: 'pdf',
                                                                            exportOptions: {
                                                                                columns: [0, 1, 2, 3, 4]
                                                                            }
                                                                        },
                                                                        {
                                                                            extend: 'print',
                                                                            exportOptions: {
                                                                                columns: [0, 1, 2, 3, 4]
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