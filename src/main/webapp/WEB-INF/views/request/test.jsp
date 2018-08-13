<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
<!--        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/dataTables.tableTools.css" type="text/css" />-->
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            @media print {
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
            td.details-control {
                background: url(/DOTS/resources/img/details_open.png) no-repeat center center;
                cursor: pointer;
            }
            tr.shown td.details-control {
                background: url(/DOTS/resources/img/details_close.png) no-repeat center center;
            }

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Delivery Order Tracking</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">RMS / Lot Pending List</h2>
                            <div class="filter-block pull-right">
                                <a href="${contextPath}/do/finalRequest" class="btn btn-primary pull-right">
                                    <i class="fa fa-list fa-lg"></i>  View DO List
                                </a>
                            </div>
                            <div class="filter-block pull-right">
                                <a href="${contextPath}/do/request/add" class="btn btn-primary pull-right">
                                    <i class="fa fa-plus-circle fa-lg"></i> Add New RMS#_Event
                                </a>
                            </div>


                        </div>
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
                                <div id="dt_spml_tt" class="form-group pull-left" style="margin-right: 5px;">
                                </div>
                                <div class="form-group pull-left" style="margin-right: 0px;">
                                    <input id="dt_spml_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>">
                                    <i class="fa fa-search search-icon"></i>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table id="example" class="display nowrap" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Item 1</th>
                                        <th>Item 2</th>
                                        <th>Item 3</th>
                                        <th>Item 4</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr data-child-value="S_12345_TC, S_12345_HTSL">
                                        <td class="details-control"></td>
                                        <td>data 1a</td>
                                        <td>data 1b</td>
                                        <td>data 1c</td>
                                        <td>data 1d</td>
                                    </tr>
                                    <tr data-child-value="hidden 2">
                                        <td class="details-control"></td>
                                        <td>data 2a</td>
                                        <td>data 2b</td>
                                        <td>data 2c</td>
                                        <td>data 2d</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
<!--        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.tableTools.js"></script>-->

        <!--print-->
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>

            function format(value) {
                return '<div><b>RMS :</b> ' + value + '</div>';
            }
            $(document).ready(function () {
                var table = $('#example').DataTable({});

                $('#dt_spml_search').keyup(function () {
                    table.search($(this).val()).draw();
                });

                $("#dt_spml_rows").change(function () {
                    table.page.len($(this).val()).draw();
                });

                // Add event listener for opening and closing details
                $('#example').on('click', 'td.details-control', function () {
                    var tr = $(this).closest('tr');
                    var row = table.row(tr);

                    if (row.child.isShown()) {
                        // This row is already open - close it
                        row.child.hide();
                        tr.removeClass('shown');
                    } else {
                        // Open this row
                        row.child(format(tr.data('child-value'))).show();
                        tr.addClass('shown');
                    }
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>