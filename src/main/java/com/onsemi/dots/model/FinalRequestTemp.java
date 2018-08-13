package com.onsemi.dots.model;

public class FinalRequestTemp {

    private String id;
    private String requestId;
    private String gts;
    private String status;
    private String shippingDate;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
    private String flag;
    private String shippingDateView;
    private String doNumber;

    //from request
    private String rms;
    private String event;
    private String lotId;
    private String device;
    private String packages;
    private String interval;
    private String quantity;
    private String remarks;
    private String rmsEvent;
    private String chamber;
    private String chamberLevel;
    private String testCondition;
    private String loadingDate;
    private String unloadingDate;

    public FinalRequestTemp() {
    }

    public FinalRequestTemp(String requestId, String gts, String doNumber, String rmsEvent, String rms, String event, String lotId,
            String device, String packages, String quantity, String interval, String chamber, String chamberLevel, String loadingDate, String unloadingDate,
            String testCondition, String remarks, String shippingDate, String status) {
        super();
        this.requestId = requestId;
        this.gts = gts;
        this.doNumber = doNumber;
        this.rmsEvent = rmsEvent;
        this.rms = rms;
        this.event = event;
        this.lotId = lotId;
        this.device = device;
        this.packages = packages;
        this.quantity = quantity;
        this.interval = interval;
        this.chamber = chamber;
        this.chamberLevel = chamberLevel;
        this.loadingDate = loadingDate;
        this.unloadingDate = unloadingDate;
        this.testCondition = testCondition;
        this.remarks = remarks;
        this.shippingDate = shippingDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getGts() {
        return gts;
    }

    public void setGts(String gts) {
        this.gts = gts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getShippingDateView() {
        return shippingDateView;
    }

    public void setShippingDateView(String shippingDateView) {
        this.shippingDateView = shippingDateView;
    }

    public String getDoNumber() {
        return doNumber;
    }

    public void setDoNumber(String doNumber) {
        this.doNumber = doNumber;
    }

    public String getRmsEvent() {
        return rmsEvent;
    }

    public void setRmsEvent(String rmsEvent) {
        this.rmsEvent = rmsEvent;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getChamberLevel() {
        return chamberLevel;
    }

    public void setChamberLevel(String chamberLevel) {
        this.chamberLevel = chamberLevel;
    }

    public String getTestCondition() {
        return testCondition;
    }

    public void setTestCondition(String testCondition) {
        this.testCondition = testCondition;
    }

    public String getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        this.loadingDate = loadingDate;
    }

    public String getUnloadingDate() {
        return unloadingDate;
    }

    public void setUnloadingDate(String unloadingDate) {
        this.unloadingDate = unloadingDate;
    }

}
