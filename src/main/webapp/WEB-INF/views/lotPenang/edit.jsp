<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <style>
            .highlight {
                border-color: red;
                box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(126, 239, 104, 0.6);
                outline: 0 none;
            }
        </style>
        <div class="col-lg-12">
            <h1>RMS_event Process Details</h1>
            <div class="row">
                <div class="col-lg-10">
                    <div class="main-box">
                        <h2>Details</h2>
                        <form id="detail_form" class="form-horizontal" role="form" action="${contextPath}/lotPenang/changeTripTicket" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <input type="hidden" name="statusF" id="statusF" value="${lotPenang.status}" />
                                <input type="hidden" name="lotPId" id="lotPId" value="${lotPenang.id}" />
                                <input type="hidden" name="reqId" id="reqId" value="${lotPenang.requestId}" />
                                <label for="rmsEvent" class="col-lg-1 control-label">RMS_event </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="rmsEvent" name="rmsEvent" value="${lotPenang.rmsEvent}" readonly>
                                </div>
                                <label for="interval" class="col-lg-3 control-label">Interval </label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="interval" name="interval" value="${lotPenang.interval}" readonly>
                                </div>
                                <c:if test="${lotPenang.oldLotPenangId != NULL}">
                                    <label for="interval" class="col-lg-1 control-label pull-left">Previous Interval </label>
                                    <div class="col-lg-1">
                                        <input type="text" class="form-control" id="interval" name="interval" value="${oldInterval}" readonly>
                                    </div>
                                </c:if>
<!--                                <label for="multiplier" class="col-lg-1 control-label">Multiplier </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="multiplier" name="multiplier" value="${lotPenang.multiplier}" readonly>
                                </div>-->
                            </div>
                            <div class="form-group">
                                <label for="device" class="col-lg-1 control-label">Device </label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="device" name="device" value="${lotPenang.device}" readonly>
                                </div>
                                <label for="packages" class="col-lg-1 control-label">Package </label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="packages" name="packages" value="${lotPenang.packages}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="expectedChamber" class="col-lg-1 control-label">Chamber </label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="expectedChamber" name="expectedChamber" value="${lotPenang.expectedChamber}" readonly>
                                </div>
                                <label for="expectedChamberLevel" class="col-lg-4 control-label">Chamber Level</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="expectedChamberLevel" name="expectedChamberLevel" value="${lotPenang.expectedChamberLevel}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="expectedCondition" class="col-lg-1 control-label">Test Condition </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="expectedCondition" name="expectedCondition" value="${testCondition}" readonly>
                                </div>
                                <label for="expectedQuatity" class="col-lg-2 control-label">Quantity </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="expectedQuatity" name="expectedQuatity" value="${lotPenang.expectedQuantity}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="expectedLoadingDate" class="col-lg-1 control-label">Estimate Loading Date </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="expectedLoadingDate" name="expectedLoadingDate" value="${lotPenang.expectedLoadingDate}" readonly>
                                </div>
                                <label for="expectedUnloadingDate" class="col-lg-2 control-label">Estimate Unloading Date </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="expectedUnloadingDate" name="expectedUnloadingDate" value="${lotPenang.expectedUnloadingDate}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="doNumber" class="col-lg-1 control-label">DO Number </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="doNumber" name="doNumber" value="${lotPenang.doNumber}" readonly>
                                </div>
                                <label for="gts" class="col-lg-2 control-label">GTS Number </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="gts" name="gts" value="${lotPenang.gts}" readonly>
                                </div>
                            </div>
                            <c:if test="${lotPenang.tripTicketPath != null}">
                                <div class="form-group">
                                    <label class="col-lg-1 control-label" for="fileUpload">Change Trip Ticket</label>
                                    <div class="col-lg-5">
                                        <input type="file" class="filestyle" data-buttonBefore="true" data-iconName="glyphicon glyphicon-inbox" data-buttonName="btn-info" id="fileUpload" name="fileUpload">
                                    </div>
                                    <button type="submit" id="submit" class="btn btn-info pull-left">Upload</button>
                                    <div class="col-lg-1">
                                        <!--<label for="doNumber" class="col-lg-1 control-label"></label>-->
                                        <a href="${contextPath}/lotPenang/download/${lotPenang.id}" class="btn btn-primary pull-left" id="backBtn"><i class="fa fa-download"></i> Download Trip Ticket</a>
                                    </div>
                                </div>
                            </c:if>
                            <div class="clearfix">           
                                <a href="${contextPath}/lotPenang" class="btn btn-info pull-left" id="backBtn"><i class="fa fa-reply"></i> Back</a>
                                <a href="${contextPath}/lotPenang" class="btn btn-primary pull-right finishBtn" id="finishBtn">Finish <i class="fa fa-arrow-right"></i></a>
                                <div class="clearfix"></div>
                            </div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
        <hr class="separator">
        <div class="col-lg-12">
            <br>
            <div class="row">
                <ul class="nav nav-tabs">
                    <li class="${reActive}"><a data-toggle="tab" href="#re">Received Details </a></li>
                    <li class="${ldActive}"><a data-toggle="tab" href="#ld">Loading Details </a></li>
                    <li class="${udActive}"><a data-toggle="tab" href="#ud">Unloading Details</a></li>
                    <li class="${clActive}" id="disTab"><a data-toggle="tab" href="#cl">Close Lot</a></li>
                </ul>
                <div class="tab-content">

                    <!--tab for received-->

                    <div id="re" class="tab-pane fade ${reActiveTab}">
                        <!--<br>-->
                        <h6></h6>
                        <div class="col-lg-9">
                            <div class="main-box">
                                <h2>Received Details</h2>
                                <form id="bs_form" class="form-horizontal" role="form" action="${contextPath}/lotPenang/updateRma" method="post">
                                    <input type="hidden" name="id" id="id" value="${lotPenang.id}" />
                                    <input type="hidden" name="status" id="statusBs" value="${lotPenang.status}" />
                                    <input type="hidden" name="requestId" id="requestId" value="${lotPenang.requestId}" />
                                    <input type="hidden" name="tab" value="${reActive}" />
                                    <div class="form-group">
                                        <label for="ReceivedDate" class="col-lg-1 control-label">Received Date </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="ReceivedDate" name="ReceivedDate" value="${lotPenang.receivedDateView}" readonly>
                                        </div>
                                        <label for="receivedQuantity" class="col-lg-1 control-label">Received Quantity</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="receivedQuantity" name="receivedQuantity" value="${lotPenang.receivedQuantity}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="verificationStatus" class="col-lg-1 control-label">Verification Status </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="verificationStatus" name="verificationStatus" value="${lotPenang.receivedVerificationStatus}" readonly>
                                        </div>
                                        <label for="preVmDate" class="col-lg-1 control-label">Pre Loading Date</label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="preVmDate" name="preVmDate" value="${lotPenang.preVmDateView}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="mix" class="col-lg-1 control-label">VM(Mix) </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="mix" name="mix" value="${lotPenang.receivedMixStatus}" readonly>
                                        </div>
                                        <label for="mixRemarks" class="col-lg-1 control-label">VM(Mix) Remarks </label>
                                        <div class="col-lg-5">
                                            <textarea class="form-control" rows="5" id="mixRemarks" name="mixRemarks" readonly>${lotPenang.receivedMixRemarks}</textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="demount" class="col-lg-1 control-label">VM(Demount) </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="demount" name="demount" value="${lotPenang.receivedDemountStatus}" readonly>
                                        </div>
                                        <label for="demountRemarks" class="col-lg-1 control-label">VM(Demount) Remarks </label>
                                        <div class="col-lg-5">
                                            <textarea class="form-control" rows="5" id="demountRemarks" name="demountRemarks" readonly>${lotPenang.receivedDemountRemarks}</textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="broken" class="col-lg-1 control-label">VM(Broken) </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="broken" name="broken" value="${lotPenang.receivedBrokenStatus}" readonly>
                                        </div>
                                        <label for="brokenRemarks" class="col-lg-1 control-label">VM(Broken) Remarks </label>
                                        <div class="col-lg-5">
                                            <textarea class="form-control" rows="5" id="brokenRemarks" name="brokenRemarks" readonly>${lotPenang.receivedBrokenRemarks}</textarea>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <c:if test="${lotPenang.rmaFlag == '1'}">
                                        <hr class="mt-2 mb-2">
                                        <h2>RMA</h2>
                                        <input type="hidden" class="form-control" id="rmaId" name="rmaId" value="${lotPenang.rmaId}" readonly>
                                        <input type="hidden" class="form-control" id="rmaFlag" name="rmaFlag" value="${lotPenang.rmaFlag}" readonly>
                                        <input type="hidden" name="rmaCount" id="rmaCount" value="${lotPenang.rmaCount}" />
                                        <div class="form-group">
                                            <label for="report1Date" class="col-lg-1 control-label">Report Date </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="report1Date" name="report1Date" value="${lotPenang.rmaDate1View}" readonly>
                                            </div>
                                            <label for="report1Remarks" class="col-lg-1 control-label">Report Remarks </label>
                                            <div class="col-lg-5">
                                                <textarea class="form-control" rows="5" id="report1Remarks" name="report1Remarks" readonly>${lotPenang.rmaRemarks1}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="dis1" class="col-lg-1 control-label">Disposition </label>
                                            <div class="col-lg-3">                                      
                                                <select id="dis1" name="dis1" class="js-example-basic-single" style="width: 100%">
                                                    <option value="" selected=""></option>
                                                    <c:forEach items="${Dis1List}" var="group">
                                                        <option value="${group.name}" ${group.selected}>${group.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <label for="dis1Remarks" class="col-lg-1 control-label">Disposition Remarks </label>
                                            <div class="col-lg-5">
                                                <textarea class="form-control" rows="5" id="dis1Remarks" name="dis1Remarks">${lotPenang.rmaDispo1Remarks}</textarea>
                                                <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                            </div>
                                        </div>
                                        <div class="pull-right">
                                            <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                            <button type="submit" id="submitRma" name="submitRma" class="btn btn-primary">Submit</button>
                                        </div>
                                    </c:if>
                                    <c:if test="${lotPenang.rmaFlag == '2'}">
                                        <hr class="mt-2 mb-2">
                                        <h2>RMA</h2>
                                        <input type="hidden" class="form-control" id="rmaId" name="rmaId" value="${lotPenang.rmaId}" readonly>
                                        <input type="hidden" class="form-control" id="rmaFlag" name="rmaFlag" value="${lotPenang.rmaFlag}" readonly>
                                        <input type="hidden" name="rmaCount" id="rmaCount" value="${lotPenang.rmaCount}" />
                                        <div class="form-group">
                                            <label for="report1Date" class="col-lg-1 control-label">Report Date </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="report1Date" name="report1Date" value="${lotPenang.rmaDate1View}" readonly>
                                            </div>
                                            <label for="report1Remarks" class="col-lg-1 control-label">Report Remarks </label>
                                            <div class="col-lg-5">
                                                <textarea class="form-control" rows="5" id="report1Remarks" name="report1Remarks" readonly>${lotPenang.rmaRemarks1}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="dis1" class="col-lg-1 control-label">Disposition </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="dis1" name="dis1" value="${lotPenang.rmaDispo1}" readonly>
                                            </div>
                                            <label for="dis1Remarks" class="col-lg-1 control-label">Disposition Remarks </label>
                                            <div class="col-lg-5">
                                                <textarea class="form-control" rows="5" id="dis1Remarks" name="dis1Remarks" readonly>${lotPenang.rmaDispo1Remarks}</textarea>
                                                <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="report2Date" class="col-lg-1 control-label">2nd Report Date </label>
                                            <div class="col-lg-3">
                                                <input type="text" class="form-control" id="report2Date" name="report2Date" value="${lotPenang.rmaDate2View}" readonly>
                                            </div>
                                            <label for="report2Remarks" class="col-lg-1 control-label">2nd Report Remarks </label>
                                            <div class="col-lg-5">
                                                <textarea class="form-control" rows="5" id="report2Remarks" name="report2Remarks" readonly>${lotPenang.rmaRemarks2}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="dis2" class="col-lg-1 control-label">Disposition </label>
                                            <div class="col-lg-3">                                      
                                                <select id="dis2" name="dis2" class="js-example-basic-single" style="width: 100%">
                                                    <option value="" selected=""></option>
                                                    <c:forEach items="${Dis2List}" var="group">
                                                        <option value="${group.name}" ${group.selected}>${group.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <label for="dis2Remarks" class="col-lg-1 control-label">Disposition Remarks </label>
                                            <div class="col-lg-5">
                                                <textarea class="form-control" rows="5" id="dis2Remarks" name="dis2Remarks">${lotPenang.rmaDispo2Remarks}</textarea>
                                                <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                            </div>
                                        </div>
                                        <div class="pull-right">
                                            <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                            <button type="submit" id="submitRma" name="submitRma" class="btn btn-primary">Submit</button>
                                        </div>
                                    </c:if>

                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!--tab for loading-->

                    <div id="ld" class="tab-pane fade ${ldActiveTab}">
                        <!--<br>-->
                        <h6></h6>
                        <div class="col-lg-9">
                            <div class="main-box">
                                <h2>Loading Details</h2>
                                <form id="bs_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRetrieval/updateScanBs" method="post">
                                    <input type="hidden" name="id" id="id" value="${lotPenang.id}" />
                                    <input type="hidden" name="status" id="statusBs" value="${lotPenang.status}" />
                                    <input type="hidden" name="tab" value="${ldActive}" />
                                    <div class="form-group">
                                        <label for="chamber" class="col-lg-1 control-label">Chamber </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="chamber" name="chamber" value="${lotPenang.chamberId}" readonly>
                                        </div>
                                        <label for="chamberLevel" class="col-lg-1 control-label">Chamber Level</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="chamberLevel" name="chamberLevel" value="${lotPenang.chamberLevel}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="loadingDate" class="col-lg-1 control-label">Loading Date </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="loadingDate" name="loadingDate" value="${lotPenang.loadingDateView}" readonly>
                                        </div>
                                        <label for="testCondition" class="col-lg-1 control-label">Test Condition </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="testCondition" name="testCondition" value="${lotPenang.testCondition}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group" id="remarksBarcodediv">
                                        <label for="loadingRemarks" class="col-lg-1 control-label">Remarks </label>
                                        <div class="col-lg-7">
                                            <textarea class="form-control" rows="5" id="loadingRemarks" name="loadingRemarks" readonly>${lotPenang.loadingRemarks}</textarea>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>

                                </form>
                            </div>
                        </div>
                    </div>

                    <!--tab for unloading-->

                    <div id="ud" class="tab-pane fade ${udActiveTab}">
                        <!--<br>-->
                        <h6></h6>
                        <div class="col-lg-9">
                            <div class="main-box">
                                <h2>Unloading Details</h2>
                                <form id="tt_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRetrieval/updateScanTt" method="post">
                                    <input type="hidden" name="id" id="id" value="${lotPenang.id}" />
                                    <input type="hidden" name="tab" value="${ulActiveTab}" />
                                    <div class="form-group">
                                        <label for="unloadingDate" class="col-lg-1 control-label">Unloading Date</label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="unloadingDate" name="unloadingDate" value="${lotPenang.unloadingDateView}" readonly>
                                        </div>
                                        <label for="unloadingRemarks" class="col-lg-1 control-label">Unloading Remarks </label>
                                        <div class="col-lg-5">
                                            <textarea class="form-control" rows="5" id="unloadingRemarks" name="unloadingRemarks" readonly>${lotPenang.unloadingRemarks}</textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="postVmDate" class="col-lg-1 control-label">Post Loading Date</label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="postVmDate" name="postVmDate" value="${lotPenang.postVmDateView}" readonly>
                                        </div>
                                        <label for="unloadingQty" class="col-lg-1 control-label">Unloading Quantity</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="unloadingQty" name="unloadingQty" value="${lotPenang.unloadingQty}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="mixUnloading" class="col-lg-1 control-label">VM(Mix) </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="mixUnloading" name="mixUnloading" value="${lotPenang.unloadingMixStatus}" readonly>
                                        </div>
                                        <label for="mixUnloadingRemarks" class="col-lg-1 control-label">VM(Mix) Remarks </label>
                                        <div class="col-lg-5">
                                            <textarea class="form-control" rows="5" id="mixUnloadingRemarks" name="mixUnloadingRemarks" readonly>${lotPenang.unloadingMixRemarks}</textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="demountUnloading" class="col-lg-1 control-label">VM(Demount) </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="demountUnloading" name="demountUnloading" value="${lotPenang.unloadingDemountStatus}" readonly>
                                        </div>
                                        <label for="demountUnloadingRemarks" class="col-lg-1 control-label">VM(Demount) Remarks </label>
                                        <div class="col-lg-5">
                                            <textarea class="form-control" rows="5" id="demountUnloadingRemarks" name="demountUnloadingRemarks" readonly>${lotPenang.unloadingDemountRemarks}</textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="brokenUnloading" class="col-lg-1 control-label">VM(Broken) </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="brokenUnloading" name="brokenUnloading" value="${lotPenang.unloadingBrokenStatus}" readonly>
                                        </div>
                                        <label for="brokenUnloadingRemarks" class="col-lg-1 control-label">VM(Broken) Remarks </label>
                                        <div class="col-lg-5">
                                            <textarea class="form-control" rows="5" id="brokenUnloadingRemarks" name="brokenUnloadingRemarks" readonly>${lotPenang.unloadingBrokenRemarks}</textarea>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!--tab for close lot-->

                    <div id="cl" class="tab-pane fade ${clActiveTab}">
                        <!--<br>-->
                        <h6></h6>
                        <div class="col-lg-7">
                            <div class="main-box">
                                <h2>CLose RMS_event</h2>
                                <form id="ver_form" class="form-horizontal" role="form" action="${contextPath}/lotPenang/close" method="post">
                                    <input type="hidden" name="statusforDisposition" id="statusforDisposition" value="${lotPenang.status}" />
                                    <input type="hidden" name="tab" value="${clActiveTab}" />
                                    <div class="form-group">
                                        <label for="closedVerification" class="col-lg-2 control-label">Scan Barcode (RMS#_Event)</label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="closedVerification" name="closedVerification" autofocus="" value="${lotPenang.closedVerification}">
                                            <input type="hidden" class="form-control" id="rmsEventV" name="rmsEventV" autofocus="" value="${lotPenang.rmsEvent}">
                                            <input type="hidden" class="form-control" id="id" name="id" value="${lotPenang.id}">
                                            <input type="hidden" class="form-control" id="requestId" name="requestId" autofocus="" value="${lotPenang.requestId}">
                                        </div>
                                    </div>

                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                        <button type="submit" id="verify" name="verify" class="btn btn-primary">Verify</button>
                                    </div>
                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-filestyle.min.js"></script>
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });


                $(":file").filestyle({buttonBefore: true});
                $(":file").filestyle({iconName: "glyphicon-inbox"});
                $(":file").filestyle({buttonName: "btn-info"});


                var statusF = $('#statusforDisposition'); //for finish button
                if (statusF.val() === "Closed") {
                    $(".finishBtn").show();
                    $("#backBtn").hide();
                } else {
                    $("#finishBtn").hide();
                    $("#backBtn").show();
                }

                var status = $('#statusF'); //for open verification input
//                if (status.val() !== "Ship to Rel Lab") {
                if (status.val() !== "Pending Close Lot") {
                    $("#closedVerification").attr("readonly", true);
                    $("#verify").attr("disabled", true);
                } else {
                    $("#closedVerification").attr("readonly", false);
                    $("#verify").attr("disabled", false);
                }


                $('#closedVerification').bind('copy paste cut', function (e) {
                    e.preventDefault(); //this line will help us to disable cut,copy,paste		
                });

                jQuery.extend(jQuery.validator.messages, {
                    required: "This field is required.",
                    equalTo: "Value is not match! Please re-scan.",
                    email: "Please enter a valid email.",
                });


                var validator1 = $("#ver_form").validate({
                    rules: {
                        closedVerification: {
                            required: true,
                            equalTo: "#rmsEventV"
                        }
                    }
                });

                var validator2 = $("#bs_form").validate({
                    rules: {
                        dis1: {
                            required: true
                        },
                        dis1Remarks: {
                            required: true,
                            maxlength: 150
                        },
                        dis2: {
                            required: true
                        },
                        dis2Remarks: {
                            required: true,
                            maxlength: 150
                        }
                    }
                });


                $(".cancel").click(function () {
                    validator1.resetForm();
                });
            });

        </script>
    </s:layout-component>
</s:layout-render>