<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>USL Timelapse Limit</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Ionic USL Limit Information</h2>
                        <form id="edit_limit_form" class="form-horizontal" role="form" action="${contextPath}/admin/uslTimelapse/update" method="post">
                            <input type="hidden" name="id" value="${uslTimelapse.id}" />
                            <div class="form-group">
                                <label for="process" class="col-lg-3 control-label">Process *</label>
                                <div class="col-lg-4">
                                    <input class="form-control" id="process" name="process" value="${uslTimelapse.process}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="hour" class="col-lg-3 control-label">Hour Limit *</label>
                                <div class="col-lg-3">
                                    <input class="form-control" id="hour" name="hour" value="${uslTimelapse.hour}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="remarks" class="col-lg-3 control-label">Remarks </label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks">${uslTimelapse.remarks}</textarea>
                                </div>
                            </div>
                            <a href="${contextPath}/admin/uslTimelapse" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                var validator = $("#edit_limit_form").validate({
                    rules: {
                        process: {
                            required: true
                        },
                        hour: {
                            required: true,
                            number: true
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