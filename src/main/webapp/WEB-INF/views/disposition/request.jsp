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
                            <table id="dt_spml" class="table">
                                <thead>
                                    <tr>
                                        <th><span>No</span></th>
                                        <th><span>RMS#_Event</span></th>
                                        <th><span>Device</span></th>
                                        <th><span>Package</span></th>
                                        <th><span>Chamber</span></th>
                                        <th><span>Interval</span></th>
                                        <th><span>Quantity</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestList}" var="request" varStatus="requestLoop">
                                        <tr>
                                            <td><c:out value="${requestLoop.index+1}"/></td>
                                            <td id="modal_delete_info_${request.id}"><c:out value="${request.rmsEvent}"/></td>
                                            <td class="col-lg-2"><c:out value="${request.device}"/></td>
                                            <td><c:out value="${request.packages}"/></td>
                                            <td><c:out value="${request.chamber}"/></td>
                                            <td><c:out value="${request.interval}"/></td>
                                            <td><c:out value="${request.quantity}"/></td>
                                            <td align="left" >
                                                <a addid="${request.id}" href="#add" class="table-link sync_user" title="Add to DO list">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-plus fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <c:if test="${groupId == '1' || groupId == '2' || groupId == '24' || groupId == '29' || groupId == '30' || groupId == '31'}">
                                                    <a href="${contextPath}/do/request/edit/${request.id}" class="table-link" title="Edit">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                            <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                        </span>
                                                    </a>
                                                </c:if>
                                                <a modaldeleteid="${request.id}" title="Delete" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
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

                                                        $.fn.center = function () {
                                                            this.css("position", "absolute");
                                                            this.css("top", ($(window).height() - this.height()) / 2 + $(window).scrollTop() + "px");
                                                            this.css("left", ($(window).width() - this.width()) / 2 + $(window).scrollLeft() + "px");
                                                            return this;
                                                        };

                                                        function showBlockUI() {
                                                            $.blockUI({
                                                                css: {
                                                                    width: 'auto',
                                                                    padding: '5px',
                                                                    backgroundColor: '#fff',
                                                                    '-webkit-border-radius': '10px',
                                                                    '-moz-border-radius': '10px'
                                                                },
                                                                message: '<img src="${contextPath}/resources/private/img/loading_gedik.gif" width="100" />'
                                                            });
                                                            $('.blockUI.blockMsg').center();
                                                        }

                                                        function hideBlockUI() {
                                                            $.unblockUI();
                                                        }

                                                        $(".sync_user").click(function () {
                                                            showBlockUI();
                                                            var loginId = $(this).attr("addid");
                                                            addUser(loginId);

                                                            function addUser(loginId) {
//                                                                var id = $("#firstname_" + loginId).html();
//                                                                var lastname = $("#lastname_" + loginId).html();
//                                                                var title = $("#title_" + loginId).html();
//                                                                var email = $("#email_" + loginId).html();
//                                                                var oncid = $("#oncid_" + loginId).html();

                                                                var url = '${contextPath}/do/request/updateToDO';
                                                                var data = {
                                                                    id: loginId
                                                                };

                                                                function success(res) {
                                                                    hideBlockUI();
                                                                    if (res.status) {
                                                                        swal({
                                                                            title: "Success",
                                                                            text: res.statusMessage,
                                                                            html: true,
                                                                            type: "success",
                                                                            showCancelButton: false,
                                                                            closeOnConfirm: false
                                                                        },
                                                                                function () {
                                                                                    window.location = "${contextPath}/do/request";
                                                                                });
                                                                    } else {
//                                                                    alert("00");
                                                                        swal('Error!', res.statusMessage, 'error');
                                                                    }
                                                                }

                                                                $.post(url, data, success, 'json').fail(function (res) {
//                                                                alert("ll");
                                                                    hideBlockUI();
                                                                    swal('Error!', JSON.stringify(res, null, 4), 'error');
                                                                });
                                                            }
                                                        });

                                                        oTable = $('#dt_spml').DataTable({
                                                            dom: 'Brtip',
                                                            buttons: [
                                                                {
                                                                    extend: 'copy',
                                                                    exportOptions: {
                                                                        columns: [0, 1, 2, 3, 4, 5, 6]
                                                                    }
                                                                },
                                                                {
                                                                    extend: 'excel',
                                                                    exportOptions: {
                                                                        columns: [0, 1, 2, 3, 4, 5, 6]
                                                                    }
                                                                },
                                                                {
                                                                    extend: 'pdf',
                                                                    exportOptions: {
                                                                        columns: [0, 1, 2, 3, 4, 5, 6]
                                                                    }
                                                                },
                                                                {
                                                                    extend: 'print',
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
                                                        var deleteUrl = "${contextPath}/do/request/delete/" + deleteId;
                                                        var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                        $("#delete_modal .modal-body").html(deleteMsg);
                                                        $("#modal_delete_button").attr("href", deleteUrl);
                                                    }
            </script>
    </s:layout-component>
</s:layout-render>