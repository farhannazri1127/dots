<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
<!--        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/dataTables.tableTools.css" type="text/css" />-->
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/jquery.datetimepicker.css" type="text/css" />
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
            <h1>Delivery Order</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Add GTS# and Shipment Date</h2>
                        <form id="add_gts_form" class="form-horizontal" role="form" action="${contextPath}/do/finalRequest/updateGtsAndShipDate" method="post">
                            <div class="form-group">
                                <input type="hidden" name="groupId" id="groupId" value="${groupId}" />
                                <label for="gts" class="col-lg-2 control-label">GTS/INV#</label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="gts" name="gts" value="${gts}" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="shippingDate" class="col-lg-2 control-label">Shipment Date *</label>
                                <div class="col-lg-4">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="shippingDate" class="form-control" id="shippingDate" value="${nowDate}">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="shippingDate" style="display: none;"></label>
                                </div>
                            </div>
                            <a href="${contextPath}/do/request" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>

                                <button type="submit" id ="saveBtn" class="btn btn-primary">Save</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
        <!--//table grid-->
        <div class="col-lg-12">
            <!--<h1>Delivery Order - Pending List</h1>-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Delivery Order Pending List</h2>
                            <div class="filter-block pull-right">
                                <a href="#delete_modal" data-toggle="modal" class="btn btn-danger danger group_delete pull-right" onclick="modalDeleteAll(this);">
                                    <i class="fa fa-trash-o fa-lg"></i> Clear All
                                </a>
                            </div>

                            <div class="filter-block pull-right">
                                <a href="#print_modal" data-toggle="modal" class="btn btn-primary" id="printBtn" onclick="modalPrint(this);">
                                    <i class="fa fa-print fa-lg"></i> View & Print DO
                                </a>
                            </div>

                        </div>
                        <div class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            <b>*Please delete all data after print the DO list.</b>

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
                                        <th><span>Interval</span></th>
                                        <th><span>Qty</span></th>
                                        <th><span>DO No.</span></th>
                                        <th><span>GTS#</span></th>
                                        <th><span>Shipment Date</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${finalRequestList}" var="request" varStatus="requestLoop">
                                        <tr>
                                            <td><c:out value="${requestLoop.index+1}"/></td>
                                            <td id="modal_delete_info_${request.id}"><c:out value="${request.rmsEvent}"/></td>
                                            <td class="col-lg-2"><c:out value="${request.device}"/></td>
                                            <td class="col-lg-2"><c:out value="${request.packages}"/></td>
                                            <td class="col-lg-1"><c:out value="${request.interval}"/></td>
                                            <td class="col-lg-1"><c:out value="${request.quantity}"/></td>
                                            <td><c:out value="${request.doNumber}"/></td>
                                            <td class="col-lg-1"><c:out value="${request.gts}"/></td>
                                            <td ><c:out value="${request.shippingDateView}"/></td>
                                            <td align="left">
                                                <c:if test="${request.flag == '0'}">
                                                    <a addid="${request.id}" href="#add" class="table-link cancel_request" title="Cancel"> Cancel
                                                    </a>
                                                </c:if>
                                                <c:if test="${request.flag == '1'}">
                                                    <a modaldeleteid="${request.id}" title="Delete" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                            <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                                        </span>
                                                    </a>
                                                </c:if>
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
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
        <script src="${contextPath}/resources/private/js/jquery.datetimepicker.full.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
                                                        $(document).ready(function () {

                                                            var group = $('#groupId'); //for finish button
                                                            if (group.val() === "1" || group.val() === '2' || group.val() === '29' || group.val() === '30' || group.val() === '31'
                                                                    || group.val() === '35' || group.val() === '23') {
                                                                $("#saveBtn").attr("disabled", false);
                                                                $("#printBtn").attr("disabled", false);
                                                            } else {
                                                                $("#saveBtn").attr("disabled", true);
                                                                $("#printBtn").attr("disabled", true);
                                                            }

                                                            $('.printAndEmail').click(function () {
                                                                window.location.reload();
                                                            });

                                                            var validator = $("#add_gts_form").validate({
                                                                rules: {
                                                                    gts: {
                                                                        required: true,
                                                                    },
                                                                    shippingDate: {
                                                                        required: true,
                                                                    }
                                                                }
                                                            });

                                                            $(".cancel").click(function () {
                                                                validator.resetForm();
                                                            });

//                                                            $('#shippingDate').datepicker({
//                                                                format: 'yyyy-mm-dd',
//                                                                autoclose: true
//                                                            });

                                                            $(function () {
                                                                var dateToDisable = new Date();
                                                                dateToDisable.setDate(dateToDisable.getDate());
                                                                $('#shippingDate').datetimepicker({
                                                                    formatTime: 'H:i',
//                                                                    formatDate: 'd-m-Y',
                                                                    formatDate: 'yyyy-mm-dd',
                                                                    defaultTime: '08:00',
                                                                    beforeShowDay: function (date) {
                                                                        var now = new Date();
                                                                        if (date.getFullYear() === now.getFullYear() && date.getMonth() === now.getMonth() && date.getDate() >= now.getDate())
                                                                            return [true];
                                                                        if (date.getFullYear() >= now.getFullYear() && date.getMonth() > now.getMonth())
                                                                            return [true];
                                                                        if (date.getFullYear() > now.getFullYear())
                                                                            return [true];
                                                                        return [false];
                                                                    }
                                                                });
                                                            });

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

                                                            $(".cancel_request").click(function () {
                                                                showBlockUI();
                                                                var loginId = $(this).attr("addid");
                                                                addUser(loginId);

                                                                function addUser(loginId) {

                                                                    var url = '${contextPath}/do/finalRequest/cancel';
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
                                                                                        window.location = "${contextPath}/do/finalRequest";
                                                                                    });
                                                                        } else {
//                                                                        alert("00");
                                                                            swal('Error!', res.statusMessage, 'error');
                                                                        }
                                                                    }

                                                                    $.post(url, data, success, 'json').fail(function (res) {
//                                                                    alert("ll");
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
                                                                    }
//                                                                },
//                                                                {
//                                                                    extend: 'print',
//                                                                    customize: function (win) {
//                                                                        $(win.document.body)
//                                                                                .css('font-size', '10pt');
//                                                                        $(win.document.body).find('table')
//                                                                                .addClass('compact')
//                                                                                .css('font-size', 'inherit');
//                                                                    }
//                                                                }
                                                                ]
                                                            });

                                                            $('#dt_spml_search').keyup(function () {
                                                                oTable.search($(this).val()).draw();
                                                            });

                                                            $("#dt_spml_rows").change(function () {
                                                                oTable.page.len($(this).val()).draw();
                                                            });
                                                        });

                                                        $('#printDo').on('click', function () {
                                                            location.reload();
                                                            window.open('${contextPath}/do/finalRequest/print', 'Delivery Order', 'width=1600,height=1100').print();

                                                        });

                                                        function printlor() {
                                                            location.reload();
                                                            window.open('${contextPath}/do/finalRequest/print', 'Delivery Order', 'width=1600,height=1100').print();
                                                        }

                                                        function modalPrint(e) {
//                                                            printlor();
                                                            var printUrl = "${contextPath}/do/finalRequest/print";
                                                            var deleteMsg = "Please check all details before you view/print. Once you view/print, rms#_event can't be cancel anymore.";
                                                            $("#print_modal .modal-body").html(deleteMsg);
                                                            $("#modal_print_button").attr("href", printUrl);
                                                        }

                                                        function modalDeleteAll(e) {
                                                            var deleteUrl = "${contextPath}/do/finalRequest/deleteAll";
                                                            var deleteMsg = "Are you sure want to delete all? All related data will be deleted.";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        }

                                                        function modalDelete(e) {
                                                            var deleteId = $(e).attr("modaldeleteid");
                                                            var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                            var deleteUrl = "${contextPath}/do/finalRequest/delete/" + deleteId;
                                                            var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        }
            </script>
    </s:layout-component>
</s:layout-render>