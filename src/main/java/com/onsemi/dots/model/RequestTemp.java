package com.onsemi.dots.model;

public class RequestTemp {

    private String id;
    private String rms;
    private String event;
    private String lotId;
    private String device;
    private String packages;
    private String interval;
    private String quantity;
    private String gts;
    private String remarks;
    private String status;
    private String shippingDate;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
    private String flag;

    private String expectedLoading;
    private String expectedUnloading;
    private String expectedMinCondition;
    private String expectedMaxCondition;
    private String expectedCondition;

    private String rmsId;

    public RequestTemp() {

    }
//without test condition

    public RequestTemp(String rms, String event, String lotId, String device, String packages, String interval, String quantity, String expectedLoading, String expectedUnloading) {
        super();
        this.rms = rms;
        this.event = event;
        this.lotId = lotId;
        this.device = device;
        this.packages = packages;
        this.interval = interval;
        this.quantity = quantity;
        this.expectedLoading = expectedLoading;
        this.expectedUnloading = expectedUnloading;
    }
//without rms id

    public RequestTemp(String rms, String event, String lotId, String device, String packages, String interval, String quantity, String expectedLoading, String expectedUnloading,
            String expectedMinCondition, String expectedMaxCondition) {
        super();
        this.rms = rms;
        this.event = event;
        this.lotId = lotId;
        this.device = device;
        this.packages = packages;
        this.interval = interval;
        this.quantity = quantity;
        this.expectedLoading = expectedLoading;
        this.expectedUnloading = expectedUnloading;
        this.expectedMinCondition = expectedMinCondition;
        this.expectedMaxCondition = expectedMaxCondition;
    }

    //with rms id
    public RequestTemp(String rms, String event, String lotId, String device, String packages, String interval, String quantity, String expectedLoading, String expectedUnloading,
            String expectedMinCondition, String expectedMaxCondition, String rmsId) {
        super();
        this.rms = rms;
        this.event = event;
        this.lotId = lotId;
        this.device = device;
        this.packages = packages;
        this.interval = interval;
        this.quantity = quantity;
        this.expectedLoading = expectedLoading;
        this.expectedUnloading = expectedUnloading;
        this.expectedMinCondition = expectedMinCondition;
        this.expectedMaxCondition = expectedMaxCondition;
        this.rmsId = rmsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGts() {
        return gts;
    }

    public void setGts(String gts) {
        this.gts = gts;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getExpectedLoading() {
        return expectedLoading;
    }

    public void setExpectedLoading(String expectedLoading) {
        this.expectedLoading = expectedLoading;
    }

    public String getExpectedUnloading() {
        return expectedUnloading;
    }

    public void setExpectedUnloading(String expectedUnloading) {
        this.expectedUnloading = expectedUnloading;
    }

    public String getExpectedMinCondition() {
        return expectedMinCondition;
    }

    public void setExpectedMinCondition(String expectedMinCondition) {
        this.expectedMinCondition = expectedMinCondition;
    }

    public String getExpectedMaxCondition() {
        return expectedMaxCondition;
    }

    public void setExpectedMaxCondition(String expectedMaxCondition) {
        this.expectedMaxCondition = expectedMaxCondition;
    }

    public String getExpectedCondition() {
        return expectedCondition;
    }

    public void setExpectedCondition(String expectedCondition) {
        this.expectedCondition = expectedCondition;
    }

    public String getRmsId() {
        return rmsId;
    }

    public void setRmsId(String rmsId) {
        this.rmsId = rmsId;
    }

}
