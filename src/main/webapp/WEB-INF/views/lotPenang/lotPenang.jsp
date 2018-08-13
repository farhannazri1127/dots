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


        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Lot in Penang</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">List of Lot in Penang</h2>

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
                            <table id="dt_spml" class="table">
                                <thead>
                                    <tr>
                                        <th><span>No</span></th>
                                        <th><span>RMS#_event</span></th>
                                        <th><span>Intervals</span></th>
                                        <th><span>DO Number</span></th>
                                        <th><span>GTS Number</span></th>
                                        <th><span>Loading Date</span></th>
                                        <th><span>Unloading Date</span></th>
                                        <th><span>Status</span></th>
                                        <th><span>Manage</span></th>
                                            <c:if test="${groupId == '1' || groupId == '2' || groupId == '24' || groupId == '29' || groupId == '30' || groupId == '31' || groupId == '33'}">
                                            <th><span>Interval Mgt</span></th>
                                            </c:if>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${lotPenangList}" var="lotPenang" varStatus="lotPenangLoop">
                                        <tr>
                                            <td><c:out value="${lotPenangLoop.index+1}"/></td>
                                            <td id="modal_delete_info_${lotPenang.id}"><c:out value="${lotPenang.rmsEvent}"/></td>
                                            <td><c:out value="${lotPenang.interval}"/></td>
                                            <td><c:out value="${lotPenang.doNumber}"/></td>
                                            <td><c:out value="${lotPenang.gts}"/></td>
                                            <td><c:out value="${lotPenang.loadingDateView}"/></td>
                                            <td><c:out value="${lotPenang.unloadingDateView}"/></td>
                                            <c:choose>
                                                <c:when test="${lotPenang.potsNotify == '0'}">
                                                    <td style="color: red" title="Not Verified by POTS Yet"><c:out value="${lotPenang.status}"/>
                                                        <span class="fa-stack">
                                                            <i class="fa fa-exclamation"></i>
                                                        </span></td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${lotPenang.rmaId != NULL}">
                                                            <td style="color: red" title="Preloading VM Failed"><c:out value="${lotPenang.status}"/>
                                                                <span class="fa-stack">
                                                                    <i class="fa fa-exclamation"></i>
                                                                </span></td>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <td><c:out value="${lotPenang.status}"/></td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                            <td align="left">
                                                <c:choose>
                                                    <c:when test="${lotPenang.status == 'Ship to Rel Lab' && lotPenang.postVmId == null}">
                                                        <a href="${contextPath}/lotPenang/postVm/add/${lotPenang.id}" id="editB" class="table-link" title="Post VM">
                                                            <span class="fa-stack">
                                                                <i class="fa fa-square fa-stack-2x"></i>
                                                                <i class="fa fa-eye fa-stack-1x fa-inverse"></i>
                                                            </span>
                                                        </a>
                                                    </c:when>
                                                    <c:when test="${lotPenang.status == 'Pending Close Lot' && lotPenang.postVmId != null}">
                                                        <a href="${contextPath}/lotPenang/postVm/edit/${lotPenang.postVmId}" id="editB" class="table-link" title="Post VM">
                                                            <span class="fa-stack">
                                                                <i class="fa fa-square fa-stack-2x"></i>
                                                                <i class="fa fa-eye fa-stack-1x fa-inverse"></i>
                                                            </span>
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a modaldeleteid="${lotPenang.id}" title="Post VM" class="table-link" style="color: silver" onclick="return false;">
                                                            <span class="fa-stack">
                                                                <i class="fa fa-square fa-stack-2x"></i>
                                                                <i class="fa fa-eye fa-stack-1x fa-inverse"></i>
                                                            </span>
                                                        </a> 
                                                    </c:otherwise>
                                                </c:choose>
                                                <a href="${contextPath}/lotPenang/edit/${lotPenang.id}" id="editB" class="table-link" title="RMS_event Process Details">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-arrow-circle-right fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a> 
                                                <a href="${contextPath}/lotPenang/view/${lotPenang.id}" class="table-link" title="RMS_event Information">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-search-plus fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                            </td>
                                            <c:if test="${groupId == '1' || groupId == '2' || groupId == '24' || groupId == '29' || groupId == '30' || groupId == '31' || groupId == '33'}">
                                                <td align="left">
                                                    <c:choose>
                                                        <c:when test="${lotPenang.status == 'Loading Process' || lotPenang.status == 'Loading Process - Revise Interval'}">
                                                            <a href="${contextPath}/lotPenang/breakInterval/${lotPenang.id}" id="breakInterval" class="table-link" title="Break Interval">
                                                            <!--<a modaldeleteid="${lotPenang.id}" title="Break Interval" class="table-link" style="color: silver" onclick="return false;">-->
                                                                <span class="fa-stack">
                                                                    <i class="fa fa-square fa-stack-2x"></i>
                                                                    <i class="fa fa-chain-broken fa-stack-1x fa-inverse"></i>
                                                                </span>
                                                            </a> 
                                                        </c:when>
                                                        <c:otherwise>
                                                            <!--<a href="${contextPath}/lotPenang/breakInterval/${lotPenang.id}" id="breakInterval" class="table-link"  style="color: silver" title="Break Interval" onclick="return false;">-->
                                                            <a modaldeleteid="${lotPenang.id}" title="Break Interval" class="table-link" style="color: silver" onclick="return false;">
                                                                <span class="fa-stack">
                                                                    <i class="fa fa-square fa-stack-2x"></i>
                                                                    <i class="fa fa-chain-broken fa-stack-1x fa-inverse"></i>
                                                                </span>
                                                            </a> 
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${lotPenang.status == 'Unloading Process'|| lotPenang.status == 'Break Interval'}">
                                                            <a href="${contextPath}/lotPenang/newInterval/${lotPenang.id}" class="table-link" title="New Interval">
                                                            <!--<a modaldeleteid="${lotPenang.id}" title="New Interval" class="table-link" style="color: silver" onclick="return false;">-->
                                                                <span class="fa-stack">
                                                                    <i class="fa fa-square fa-stack-2x"></i>
                                                                    <i class="fa fa-external-link fa-stack-1x fa-inverse"></i>
                                                                </span>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <!--<a href="${contextPath}/lotPenang/newInterval/${lotPenang.id}" class="table-link" style="color: silver" title="New Interval" onclick="return false;">-->
                                                            <a modaldeleteid="${lotPenang.id}" title="New Interval" class="table-link" style="color: silver" onclick="return false;">
                                                                <span class="fa-stack">
                                                                    <i class="fa fa-square fa-stack-2x"></i>
                                                                    <i class="fa fa-external-link fa-stack-1x fa-inverse"></i>
                                                                </span>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${lotPenang.status == 'Ship to Penang' || lotPenang.status == 'Received in Penang' || lotPenang.status == 'PreLoading VM' || lotPenang.status == 'Loading Process' || lotPenang.status == 'Pending Loading' ||
                                                                        lotPenang.status == 'Ship to Penang - Revise Interval' || lotPenang.status == 'Received in Penang - Revise Interval' || lotPenang.status == 'PreLoading VM - Revise Interval' || lotPenang.status == 'Loading Process - Revise Interval' || lotPenang.status == 'Pending Loading - Revise Interval'}">
                                                                <a href="${contextPath}/lotPenang/reviseInterval/${lotPenang.id}" class="table-link" title="Revise Interval">
                                                                <!--<a modaldeleteid="${lotPenang.id}" title="Revise Interval" class="table-link" style="color: silver" onclick="return false;">-->
                                                                    <span class="fa-stack">
                                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                                        <i class="fa fa-retweet fa-stack-1x fa-inverse"></i>
                                                                    </span>
                                                                </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <!--<a href="${contextPath}/lotPenang/view/${lotPenang.id}" class="table-link" style="color: silver" title="Change Interval" onclick="return false;">-->
                                                            <a modaldeleteid="${lotPenang.id}" title="Revise Interval" class="table-link" style="color: silver" onclick="return false;">
                                                                <span class="fa-stack">
                                                                    <i class="fa fa-square fa-stack-2x"></i>
                                                                    <i class="fa fa-retweet fa-stack-1x fa-inverse"></i>
                                                                </span>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${lotPenang.status == 'Ship to Penang' || lotPenang.status == 'Received in Penang' || lotPenang.status == 'Pending Loading' || lotPenang.status == 'PreLoading VM' ||
                                                                        lotPenang.status == 'Ship to Penang - Revise Interval' || lotPenang.status == 'Received in Penang - Revise Interval' || lotPenang.status == 'PreLoading VM - Revise Interval' || lotPenang.status == 'Pending Loading - Revise Interval'}">
                                                                <a modaldeleteid="${lotPenang.id}" title="Cancel Interval" data-toggle="modal" href="#cancel_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                                <!--<a modaldeleteid="${lotPenang.id}" title="Cancel Interval" class="table-link" style="color: silver" onclick="return false;">-->
                                                                    <span class="fa-stack">
                                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                                        <i class="fa fa-ban fa-stack-1x fa-inverse"></i>
                                                                    </span>
                                                                </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                       <!--<a modaldeleteid="${lotPenang.id}" title="Cancel Interval" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">-->
                                                            <a modaldeleteid="${lotPenang.id}" title="Cancel Interval" class="table-link" style="color: silver" onclick="return false;">
                                                                <span class="fa-stack">
                                                                    <i class="fa fa-square fa-stack-2x"></i>
                                                                    <i class="fa fa-ban fa-stack-1x fa-inverse"></i>
                                                                </span>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </c:if>
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
                                                                $(document).ready(function () {

                                                                    oTable = $('#dt_spml').DataTable({
                                                                        dom: 'Brtip',
                                                                        buttons: [
                                                                            {
                                                                                extend: 'copy',
                                                                                exportOptions: {
                                                                                    columns: [0, 1, 2, 3, 4, 5]
                                                                                }
                                                                            },
                                                                            {
                                                                                extend: 'excel',
                                                                                exportOptions: {
                                                                                    columns: [0, 1, 2, 3, 4, 5]
                                                                                }
                                                                            },
                                                                            {
                                                                                extend: 'pdf',
                                                                                exportOptions: {
                                                                                    columns: [0, 1, 2, 3, 4, 5]
                                                                                }
                                                                            },
                                                                            {
                                                                                extend: 'print',
                                                                                exportOptions: {
                                                                                    columns: [0, 1, 2, 3, 4, 5]
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


                                                                    $('#dt_spml_search').keyup(function () {
                                                                        oTable.search($(this).val()).draw();
                                                                    });

                                                                    $("#dt_spml_rows").change(function () {
                                                                        oTable.page.len($(this).val()).draw();
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