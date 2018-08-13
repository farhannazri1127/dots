package com.onsemi.dots.model;

public class LotPenang {

    private String id;
    private String doNumber;
    private String requestId;
    private String rmsEvent;
    private String chamberId;
    private String chamberLevel;
    private String loadingDate;
    private String loadingRemarks;
    private String testCondition;
    private String loadingBy;
    private String unloadingDate;
    private String unloadingRemarks;
    private String unloadingBy;
    private String status;
    private String receivedQuantity;
    private String receivedDate;
    private String receivedBy;
    private String shipmentBy;
    private String shipmentDate;
    private String createdBy;
    private String createdDate;
    private String flag;
    private String loadingDateView;
    private String unloadingDateView;
    private String receivedDateView;
    private String shipmentDateView;
    private String closedVerification;
    private String closedBy;
    private String closedDate;
    private String closedDateView;
    private String receivedRemarks;
    private String receivedVerificationStatus;
    private String receivedVerificationDate;
    private String receivedVerificationDateView;
    private String receivedMixStatus;
    private String receivedMixRemarks;
    private String receivedDemountStatus;
    private String receivedDemountRemarks;
    private String receivedBrokenStatus;
    private String receivedBrokenRemarks;
    private String unloadingMixStatus;
    private String unloadingMixRemarks;
    private String unloadingDemountStatus;
    private String unloadingDemountRemarks;
    private String unloadingBrokenStatus;
    private String unloadingBrokenRemarks;

    //from requestTable
    private String rms;
    private String lot;
    private String event;
    private String device;
    private String packages;
    private String interval;
    private String expectedChamber;
    private String expectedChamberLevel;
    private String expectedCondition;
    private String expectedLoadingDate;
    private String expectedUnloadingDate;
    private String expectedUnloadingDateView;
    private String expectedQuantity;
    private String gts;
    private String ShipmentFromRel;
    private String shipmentFromRelView;

    private String shipToReceived24;
    private String shipToReceivedDetail;
    private String shipToReceivedIfNull24;
    private String shipToReceivedIfNullDetail;

    private String receivedToLoad24;
    private String receivedToLoadDetail;
    private String receivedToLoadIfNull24;
    private String receivedToLoadIfNullDetail;

    private String unloadToShip24;
    private String unloadToShipDetail;
    private String unloadToShipIfNull24;
    private String unloadToShipIfNullDetail;

    private String shipToClosed24;
    private String shipToClosedDetail;
    private String shipToClosedIfNull24;
    private String shipToClosedIfNullDetail;

    private String oldLotPenangId;
    private String nowDate;
    private String nowDateView;

    private String actualVsEstimateUnloading24;
    private String actualVsEstimateUnloadingDetail;
    private String actualVsEstimateUnloadingIfNull24;
    private String actualVsEstimateUnloadingIfNullDetail;

    private String tripTicketPath;
    private String preVmDate;
    private String postVmDate;
    private String preVmDateView;
    private String postVmDateView;
    private String unloadingQty;
    private String newLotPenangId;
    private String potsNotify;
    private String potsNotifyDate;
    private String potsNotifyBy;
    private String intervalRemarks;

    //rma
    private String rmaFlag;
    private String rmaId;

    private String rmaRemarks1;
    private String rmaDate1;
    private String rmaDispo1;
    private String rmaDispo1Remarks;
    private String rmaDispo1Date;
    private String rmaDispo1By;
    private String rmaRemarks2;
    private String rmaDate2;
    private String rmaDispo2;
    private String rmaDispo2Remarks;
    private String rmaDispo2Date;
    private String rmaDispo2By;

    private String rmaDate1View;
    private String rmaDate2View;
    private String rmaDispo1DateView;
    private String rmaDispo2DateView;

    private String rmaCount;

    private String postVmId;

    private String abnormalId;

    private String multiplier;

    public String getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoNumber() {
        return doNumber;
    }

    public void setDoNumber(String doNumber) {
        this.doNumber = doNumber;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRmsEvent() {
        return rmsEvent;
    }

    public void setRmsEvent(String rmsEvent) {
        this.rmsEvent = rmsEvent;
    }

    public String getChamberId() {
        return chamberId;
    }

    public void setChamberId(String chamberId) {
        this.chamberId = chamberId;
    }

    public String getChamberLevel() {
        return chamberLevel;
    }

    public void setChamberLevel(String chamberLevel) {
        this.chamberLevel = chamberLevel;
    }

    public String getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        this.loadingDate = loadingDate;
    }

    public String getLoadingRemarks() {
        return loadingRemarks;
    }

    public void setLoadingRemarks(String loadingRemarks) {
        this.loadingRemarks = loadingRemarks;
    }

    public String getTestCondition() {
        return testCondition;
    }

    public void setTestCondition(String testCondition) {
        this.testCondition = testCondition;
    }

    public String getLoadingBy() {
        return loadingBy;
    }

    public void setLoadingBy(String loadingBy) {
        this.loadingBy = loadingBy;
    }

    public String getUnloadingDate() {
        return unloadingDate;
    }

    public void setUnloadingDate(String unloadingDate) {
        this.unloadingDate = unloadingDate;
    }

    public String getUnloadingRemarks() {
        return unloadingRemarks;
    }

    public void setUnloadingRemarks(String unloadingRemarks) {
        this.unloadingRemarks = unloadingRemarks;
    }

    public String getUnloadingBy() {
        return unloadingBy;
    }

    public void setUnloadingBy(String unloadingBy) {
        this.unloadingBy = unloadingBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(String receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getShipmentBy() {
        return shipmentBy;
    }

    public void setShipmentBy(String shipmentBy) {
        this.shipmentBy = shipmentBy;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRms() {
        return rms;
    }

    public void setRms(String rms) {
        this.rms = rms;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getExpectedChamber() {
        return expectedChamber;
    }

    public void setExpectedChamber(String expectedChamber) {
        this.expectedChamber = expectedChamber;
    }

    public String getExpectedChamberLevel() {
        return expectedChamberLevel;
    }

    public void setExpectedChamberLevel(String expectedChamberLevel) {
        this.expectedChamberLevel = expectedChamberLevel;
    }

    public String getExpectedCondition() {
        return expectedCondition;
    }

    public void setExpectedCondition(String expectedCondition) {
        this.expectedCondition = expectedCondition;
    }

    public String getExpectedLoadingDate() {
        return expectedLoadingDate;
    }

    public void setExpectedLoadingDate(String expectedLoadingDate) {
        this.expectedLoadingDate = expectedLoadingDate;
    }

    public String getExpectedUnloadingDate() {
        return expectedUnloadingDate;
    }

    public void setExpectedUnloadingDate(String expectedUnloadingDate) {
        this.expectedUnloadingDate = expectedUnloadingDate;
    }

    public String getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(String expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public String getGts() {
        return gts;
    }

    public void setGts(String gts) {
        this.gts = gts;
    }

    public String getShipmentFromRel() {
        return ShipmentFromRel;
    }

    public void setShipmentFromRel(String ShipmentFromRel) {
        this.ShipmentFromRel = ShipmentFromRel;
    }

    public String getLoadingDateView() {
        return loadingDateView;
    }

    public void setLoadingDateView(String loadingDateView) {
        this.loadingDateView = loadingDateView;
    }

    public String getUnloadingDateView() {
        return unloadingDateView;
    }

    public void setUnloadingDateView(String unloadingDateView) {
        this.unloadingDateView = unloadingDateView;
    }

    public String getReceivedDateView() {
        return receivedDateView;
    }

    public void setReceivedDateView(String receivedDateView) {
        this.receivedDateView = receivedDateView;
    }

    public String getShipmentDateView() {
        return shipmentDateView;
    }

    public void setShipmentDateView(String shipmentDateView) {
        this.shipmentDateView = shipmentDateView;
    }

    public String getShipmentFromRelView() {
        return shipmentFromRelView;
    }

    public void setShipmentFromRelView(String shipmentFromRelView) {
        this.shipmentFromRelView = shipmentFromRelView;
    }

    public String getClosedVerification() {
        return closedVerification;
    }

    public void setClosedVerification(String closedVerification) {
        this.closedVerification = closedVerification;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public String getClosedDateView() {
        return closedDateView;
    }

    public void setClosedDateView(String closedDateView) {
        this.closedDateView = closedDateView;
    }

    public String getReceivedRemarks() {
        return receivedRemarks;
    }

    public void setReceivedRemarks(String receivedRemarks) {
        this.receivedRemarks = receivedRemarks;
    }

    public String getReceivedVerificationStatus() {
        return receivedVerificationStatus;
    }

    public void setReceivedVerificationStatus(String receivedVerificationStatus) {
        this.receivedVerificationStatus = receivedVerificationStatus;
    }

    public String getReceivedVerificationDate() {
        return receivedVerificationDate;
    }

    public void setReceivedVerificationDate(String receivedVerificationDate) {
        this.receivedVerificationDate = receivedVerificationDate;
    }

    public String getReceivedMixStatus() {
        return receivedMixStatus;
    }

    public void setReceivedMixStatus(String receivedMixStatus) {
        this.receivedMixStatus = receivedMixStatus;
    }

    public String getReceivedMixRemarks() {
        return receivedMixRemarks;
    }

    public void setReceivedMixRemarks(String receivedMixRemarks) {
        this.receivedMixRemarks = receivedMixRemarks;
    }

    public String getReceivedDemountStatus() {
        return receivedDemountStatus;
    }

    public void setReceivedDemountStatus(String receivedDemountStatus) {
        this.receivedDemountStatus = receivedDemountStatus;
    }

    public String getReceivedDemountRemarks() {
        return receivedDemountRemarks;
    }

    public void setReceivedDemountRemarks(String receivedDemountRemarks) {
        this.receivedDemountRemarks = receivedDemountRemarks;
    }

    public String getReceivedBrokenStatus() {
        return receivedBrokenStatus;
    }

    public void setReceivedBrokenStatus(String receivedBrokenStatus) {
        this.receivedBrokenStatus = receivedBrokenStatus;
    }

    public String getReceivedBrokenRemarks() {
        return receivedBrokenRemarks;
    }

    public void setReceivedBrokenRemarks(String receivedBrokenRemarks) {
        this.receivedBrokenRemarks = receivedBrokenRemarks;
    }

    public String getReceivedVerificationDateView() {
        return receivedVerificationDateView;
    }

    public void setReceivedVerificationDateView(String receivedVerificationDateView) {
        this.receivedVerificationDateView = receivedVerificationDateView;
    }

    public String getUnloadingMixStatus() {
        return unloadingMixStatus;
    }

    public void setUnloadingMixStatus(String unloadingMixStatus) {
        this.unloadingMixStatus = unloadingMixStatus;
    }

    public String getUnloadingMixRemarks() {
        return unloadingMixRemarks;
    }

    public void setUnloadingMixRemarks(String unloadingMixRemarks) {
        this.unloadingMixRemarks = unloadingMixRemarks;
    }

    public String getUnloadingDemountStatus() {
        return unloadingDemountStatus;
    }

    public void setUnloadingDemountStatus(String unloadingDemountStatus) {
        this.unloadingDemountStatus = unloadingDemountStatus;
    }

    public String getUnloadingDemountRemarks() {
        return unloadingDemountRemarks;
    }

    public void setUnloadingDemountRemarks(String unloadingDemountRemarks) {
        this.unloadingDemountRemarks = unloadingDemountRemarks;
    }

    public String getUnloadingBrokenStatus() {
        return unloadingBrokenStatus;
    }

    public void setUnloadingBrokenStatus(String unloadingBrokenStatus) {
        this.unloadingBrokenStatus = unloadingBrokenStatus;
    }

    public String getUnloadingBrokenRemarks() {
        return unloadingBrokenRemarks;
    }

    public void setUnloadingBrokenRemarks(String unloadingBrokenRemarks) {
        this.unloadingBrokenRemarks = unloadingBrokenRemarks;
    }

    public String getShipToReceived24() {
        return shipToReceived24;
    }

    public void setShipToReceived24(String shipToReceived24) {
        this.shipToReceived24 = shipToReceived24;
    }

    public String getShipToReceivedDetail() {
        return shipToReceivedDetail;
    }

    public void setShipToReceivedDetail(String shipToReceivedDetail) {
        this.shipToReceivedDetail = shipToReceivedDetail;
    }

    public String getShipToReceivedIfNull24() {
        return shipToReceivedIfNull24;
    }

    public void setShipToReceivedIfNull24(String shipToReceivedIfNull24) {
        this.shipToReceivedIfNull24 = shipToReceivedIfNull24;
    }

    public String getShipToReceivedIfNullDetail() {
        return shipToReceivedIfNullDetail;
    }

    public void setShipToReceivedIfNullDetail(String shipToReceivedIfNullDetail) {
        this.shipToReceivedIfNullDetail = shipToReceivedIfNullDetail;
    }

    public String getReceivedToLoad24() {
        return receivedToLoad24;
    }

    public void setReceivedToLoad24(String receivedToLoad24) {
        this.receivedToLoad24 = receivedToLoad24;
    }

    public String getReceivedToLoadDetail() {
        return receivedToLoadDetail;
    }

    public void setReceivedToLoadDetail(String receivedToLoadDetail) {
        this.receivedToLoadDetail = receivedToLoadDetail;
    }

    public String getReceivedToLoadIfNull24() {
        return receivedToLoadIfNull24;
    }

    public void setReceivedToLoadIfNull24(String receivedToLoadIfNull24) {
        this.receivedToLoadIfNull24 = receivedToLoadIfNull24;
    }

    public String getReceivedToLoadIfNullDetail() {
        return receivedToLoadIfNullDetail;
    }

    public void setReceivedToLoadIfNullDetail(String receivedToLoadIfNullDetail) {
        this.receivedToLoadIfNullDetail = receivedToLoadIfNullDetail;
    }

    public String getUnloadToShip24() {
        return unloadToShip24;
    }

    public void setUnloadToShip24(String unloadToShip24) {
        this.unloadToShip24 = unloadToShip24;
    }

    public String getUnloadToShipDetail() {
        return unloadToShipDetail;
    }

    public void setUnloadToShipDetail(String unloadToShipDetail) {
        this.unloadToShipDetail = unloadToShipDetail;
    }

    public String getUnloadToShipIfNull24() {
        return unloadToShipIfNull24;
    }

    public void setUnloadToShipIfNull24(String unloadToShipIfNull24) {
        this.unloadToShipIfNull24 = unloadToShipIfNull24;
    }

    public String getUnloadToShipIfNullDetail() {
        return unloadToShipIfNullDetail;
    }

    public void setUnloadToShipIfNullDetail(String unloadToShipIfNullDetail) {
        this.unloadToShipIfNullDetail = unloadToShipIfNullDetail;
    }

    public String getShipToClosed24() {
        return shipToClosed24;
    }

    public void setShipToClosed24(String shipToClosed24) {
        this.shipToClosed24 = shipToClosed24;
    }

    public String getShipToClosedDetail() {
        return shipToClosedDetail;
    }

    public void setShipToClosedDetail(String shipToClosedDetail) {
        this.shipToClosedDetail = shipToClosedDetail;
    }

    public String getShipToClosedIfNull24() {
        return shipToClosedIfNull24;
    }

    public void setShipToClosedIfNull24(String shipToClosedIfNull24) {
        this.shipToClosedIfNull24 = shipToClosedIfNull24;
    }

    public String getShipToClosedIfNullDetail() {
        return shipToClosedIfNullDetail;
    }

    public void setShipToClosedIfNullDetail(String shipToClosedIfNullDetail) {
        this.shipToClosedIfNullDetail = shipToClosedIfNullDetail;
    }

    public String getOldLotPenangId() {
        return oldLotPenangId;
    }

    public void setOldLotPenangId(String oldLotPenangId) {
        this.oldLotPenangId = oldLotPenangId;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public String getActualVsEstimateUnloading24() {
        return actualVsEstimateUnloading24;
    }

    public void setActualVsEstimateUnloading24(String actualVsEstimateUnloading24) {
        this.actualVsEstimateUnloading24 = actualVsEstimateUnloading24;
    }

    public String getActualVsEstimateUnloadingDetail() {
        return actualVsEstimateUnloadingDetail;
    }

    public void setActualVsEstimateUnloadingDetail(String actualVsEstimateUnloadingDetail) {
        this.actualVsEstimateUnloadingDetail = actualVsEstimateUnloadingDetail;
    }

    public String getActualVsEstimateUnloadingIfNull24() {
        return actualVsEstimateUnloadingIfNull24;
    }

    public void setActualVsEstimateUnloadingIfNull24(String actualVsEstimateUnloadingIfNull24) {
        this.actualVsEstimateUnloadingIfNull24 = actualVsEstimateUnloadingIfNull24;
    }

    public String getActualVsEstimateUnloadingIfNullDetail() {
        return actualVsEstimateUnloadingIfNullDetail;
    }

    public void setActualVsEstimateUnloadingIfNullDetail(String actualVsEstimateUnloadingIfNullDetail) {
        this.actualVsEstimateUnloadingIfNullDetail = actualVsEstimateUnloadingIfNullDetail;
    }

    public String getNowDateView() {
        return nowDateView;
    }

    public void setNowDateView(String nowDateView) {
        this.nowDateView = nowDateView;
    }

    public String getExpectedUnloadingDateView() {
        return expectedUnloadingDateView;
    }

    public void setExpectedUnloadingDateView(String expectedUnloadingDateView) {
        this.expectedUnloadingDateView = expectedUnloadingDateView;
    }

    public String getTripTicketPath() {
        return tripTicketPath;
    }

    public void setTripTicketPath(String tripTicketPath) {
        this.tripTicketPath = tripTicketPath;
    }

    public String getPreVmDate() {
        return preVmDate;
    }

    public void setPreVmDate(String preVmDate) {
        this.preVmDate = preVmDate;
    }

    public String getPostVmDate() {
        return postVmDate;
    }

    public void setPostVmDate(String postVmDate) {
        this.postVmDate = postVmDate;
    }

    public String getUnloadingQty() {
        return unloadingQty;
    }

    public void setUnloadingQty(String unloadingQty) {
        this.unloadingQty = unloadingQty;
    }

    public String getPreVmDateView() {
        return preVmDateView;
    }

    public void setPreVmDateView(String preVmDateView) {
        this.preVmDateView = preVmDateView;
    }

    public String getPostVmDateView() {
        return postVmDateView;
    }

    public void setPostVmDateView(String postVmDateView) {
        this.postVmDateView = postVmDateView;
    }

    public String getNewLotPenangId() {
        return newLotPenangId;
    }

    public void setNewLotPenangId(String newLotPenangId) {
        this.newLotPenangId = newLotPenangId;
    }

    public String getPotsNotify() {
        return potsNotify;
    }

    public void setPotsNotify(String potsNotify) {
        this.potsNotify = potsNotify;
    }

    public String getPotsNotifyDate() {
        return potsNotifyDate;
    }

    public void setPotsNotifyDate(String potsNotifyDate) {
        this.potsNotifyDate = potsNotifyDate;
    }

    public String getPotsNotifyBy() {
        return potsNotifyBy;
    }

    public void setPotsNotifyBy(String potsNotifyBy) {
        this.potsNotifyBy = potsNotifyBy;
    }

    public String getIntervalRemarks() {
        return intervalRemarks;
    }

    public void setIntervalRemarks(String intervalRemarks) {
        this.intervalRemarks = intervalRemarks;
    }

    public String getRmaFlag() {
        return rmaFlag;
    }

    public void setRmaFlag(String rmaFlag) {
        this.rmaFlag = rmaFlag;
    }

    public String getRmaId() {
        return rmaId;
    }

    public void setRmaId(String rmaId) {
        this.rmaId = rmaId;
    }

    public String getRmaRemarks1() {
        return rmaRemarks1;
    }

    public void setRmaRemarks1(String rmaRemarks1) {
        this.rmaRemarks1 = rmaRemarks1;
    }

    public String getRmaDate1() {
        return rmaDate1;
    }

    public void setRmaDate1(String rmaDate1) {
        this.rmaDate1 = rmaDate1;
    }

    public String getRmaDispo1() {
        return rmaDispo1;
    }

    public void setRmaDispo1(String rmaDispo1) {
        this.rmaDispo1 = rmaDispo1;
    }

    public String getRmaDispo1Remarks() {
        return rmaDispo1Remarks;
    }

    public void setRmaDispo1Remarks(String rmaDispo1Remarks) {
        this.rmaDispo1Remarks = rmaDispo1Remarks;
    }

    public String getRmaDispo1Date() {
        return rmaDispo1Date;
    }

    public void setRmaDispo1Date(String rmaDispo1Date) {
        this.rmaDispo1Date = rmaDispo1Date;
    }

    public String getRmaDispo1By() {
        return rmaDispo1By;
    }

    public void setRmaDispo1By(String rmaDispo1By) {
        this.rmaDispo1By = rmaDispo1By;
    }

    public String getRmaRemarks2() {
        return rmaRemarks2;
    }

    public void setRmaRemarks2(String rmaRemarks2) {
        this.rmaRemarks2 = rmaRemarks2;
    }

    public String getRmaDate2() {
        return rmaDate2;
    }

    public void setRmaDate2(String rmaDate2) {
        this.rmaDate2 = rmaDate2;
    }

    public String getRmaDispo2() {
        return rmaDispo2;
    }

    public void setRmaDispo2(String rmaDispo2) {
        this.rmaDispo2 = rmaDispo2;
    }

    public String getRmaDispo2Remarks() {
        return rmaDispo2Remarks;
    }

    public void setRmaDispo2Remarks(String rmaDispo2Remarks) {
        this.rmaDispo2Remarks = rmaDispo2Remarks;
    }

    public String getRmaDispo2Date() {
        return rmaDispo2Date;
    }

    public void setRmaDispo2Date(String rmaDispo2Date) {
        this.rmaDispo2Date = rmaDispo2Date;
    }

    public String getRmaDispo2By() {
        return rmaDispo2By;
    }

    public void setRmaDispo2By(String rmaDispo2By) {
        this.rmaDispo2By = rmaDispo2By;
    }

    public String getRmaDate1View() {
        return rmaDate1View;
    }

    public void setRmaDate1View(String rmaDate1View) {
        this.rmaDate1View = rmaDate1View;
    }

    public String getRmaDate2View() {
        return rmaDate2View;
    }

    public void setRmaDate2View(String rmaDate2View) {
        this.rmaDate2View = rmaDate2View;
    }

    public String getRmaDispo1DateView() {
        return rmaDispo1DateView;
    }

    public void setRmaDispo1DateView(String rmaDispo1DateView) {
        this.rmaDispo1DateView = rmaDispo1DateView;
    }

    public String getRmaDispo2DateView() {
        return rmaDispo2DateView;
    }

    public void setRmaDispo2DateView(String rmaDispo2DateView) {
        this.rmaDispo2DateView = rmaDispo2DateView;
    }

    public String getRmaCount() {
        return rmaCount;
    }

    public void setRmaCount(String rmaCount) {
        this.rmaCount = rmaCount;
    }

    public String getPostVmId() {
        return postVmId;
    }

    public void setPostVmId(String postVmId) {
        this.postVmId = postVmId;
    }

    public String getAbnormalId() {
        return abnormalId;
    }

    public void setAbnormalId(String abnormalId) {
        this.abnormalId = abnormalId;
    }

}
