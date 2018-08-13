package com.onsemi.dots.model;

public class LotPenangTemp {

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
    private String receivedRemarks;
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
    private String expectedQuantity;
    private String gts;
    private String ShipmentFromRel;
    private String shipmentFromRelView;
    private String receivedVerificationStatus;
    private String receivedVerificationDate;
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

    private String dateAdded;
    private String preVmDate;
    private String postVmDate;
    private String unloadingQty;

    private String potsNotify;
    private String potsNotifyDate;
    private String potsNotifyBy;

    public LotPenangTemp() {
    }

    public LotPenangTemp(String requestId, String doNumber, String gts, String rmsEvent, String interval, String shipmentDate, String shipmentBy, String status) {

        super();
        this.requestId = requestId;
        this.doNumber = doNumber;
        this.gts = gts;
        this.rmsEvent = rmsEvent;
        this.interval = interval;
        this.shipmentDate = shipmentDate;
        this.shipmentBy = shipmentBy;
        this.status = status;
    }

    public LotPenangTemp(String requestId, String doNumber, String gts, String rmsEvent, String expectedQuantity, String interval, String dateAdded,
            String receivedQuantity, String receivedRemarks, String receivedBy, String receivedDate, String status) {

        super();
        this.requestId = requestId;
        this.doNumber = doNumber;
        this.gts = gts;
        this.rmsEvent = rmsEvent;
        this.expectedQuantity = expectedQuantity;
        this.interval = interval;
        this.dateAdded = dateAdded;
        this.receivedQuantity = receivedQuantity;
        this.receivedRemarks = receivedRemarks;
        this.receivedBy = receivedBy;
        this.receivedDate = receivedDate;
        this.status = status;
    }

    public LotPenangTemp(String requestId, String doNumber, String rmsEvent, String chamberId, String chamberLevel, String testCondition, String loadingRemarks, String loadingDate,
            String loadingBy, String unloadingRemarks, String unloadingDate, String unloadingBy, String status, String gts) {

        super();
        this.requestId = requestId;
        this.doNumber = doNumber;
        this.rmsEvent = rmsEvent;
        this.chamberId = chamberId;
        this.chamberLevel = chamberLevel;
        this.testCondition = testCondition;
        this.loadingRemarks = loadingRemarks;
        this.loadingDate = loadingDate;
        this.loadingBy = loadingBy;
        this.unloadingRemarks = unloadingRemarks;
        this.unloadingDate = unloadingDate;
        this.unloadingBy = unloadingBy;
        this.status = status;
        this.gts = gts;
    }

    //pots_process
    public LotPenangTemp(String requestId, String doNumber, String rmsEvent, String chamberId, String chamberLevel, String testCondition, String loadingRemarks, String loadingDate,
            String loadingBy, String unloadingRemarks, String unloadingDate, String unloadingBy, String status, String gts, String unloadingMixStatus, String unloadingMixRemarks,
            String unloadingDemountStatus, String unloadingDemountRemarks, String unloadingBrokenStatus, String unloadingBrokenRemarks, String unloadingQty, String postVmDate) {

        super();
        this.requestId = requestId;
        this.doNumber = doNumber;
        this.rmsEvent = rmsEvent;
        this.chamberId = chamberId;
        this.chamberLevel = chamberLevel;
        this.testCondition = testCondition;
        this.loadingRemarks = loadingRemarks;
        this.loadingDate = loadingDate;
        this.loadingBy = loadingBy;
        this.unloadingRemarks = unloadingRemarks;
        this.unloadingDate = unloadingDate;
        this.unloadingBy = unloadingBy;
        this.status = status;
        this.gts = gts;
        this.unloadingMixStatus = unloadingMixStatus;
        this.unloadingMixRemarks = unloadingMixRemarks;
        this.unloadingDemountStatus = unloadingDemountStatus;
        this.unloadingDemountRemarks = unloadingDemountRemarks;
        this.unloadingBrokenStatus = unloadingBrokenStatus;
        this.unloadingBrokenRemarks = unloadingBrokenRemarks;
        this.unloadingQty = unloadingQty;
        this.postVmDate = postVmDate;
    }

    //pots_received
    public LotPenangTemp(String requestId, String doNumber, String gts, String rmsEvent, String expectedQuantity, String interval, String dateAdded,
            String receivedQuantity, String receivedBy, String receivedDate, String status, String receivedVerificationStatus, String receivedVerificationDate,
            String receivedMixStatus, String receivedMixRemarks, String receivedDemountStatus, String receivedDemountRemarks, String receivedBrokenStatus, String receivedBrokenRemarks,
            String preVmDate) {

        super();
        this.requestId = requestId;
        this.doNumber = doNumber;
        this.gts = gts;
        this.rmsEvent = rmsEvent;
        this.expectedQuantity = expectedQuantity;
        this.interval = interval;
        this.dateAdded = dateAdded;
        this.receivedQuantity = receivedQuantity;
        this.receivedBy = receivedBy;
        this.receivedDate = receivedDate;
        this.status = status;
        this.receivedVerificationStatus = receivedVerificationStatus;
        this.receivedVerificationDate = receivedVerificationDate;
        this.receivedMixStatus = receivedMixStatus;
        this.receivedMixRemarks = receivedMixRemarks;
        this.receivedDemountStatus = receivedDemountStatus;
        this.receivedDemountRemarks = receivedDemountRemarks;
        this.receivedBrokenStatus = receivedBrokenStatus;
        this.receivedBrokenRemarks = receivedBrokenRemarks;
        this.preVmDate = preVmDate;
    }

    //pots_notify
    public LotPenangTemp(String requestId, String potsNotifyDate, String potsNotifyBy) {

        super();
        this.requestId = requestId;
        this.potsNotifyDate = potsNotifyDate;
        this.potsNotifyBy = potsNotifyBy;

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

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
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

}
