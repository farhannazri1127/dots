package com.onsemi.dots.model;

public class AbnormalLoadingTemp {

    private String id;
    private String requestId;
    private String suggestedLoadingTime;
    private String actualLoadingTime;
    private String suggestedUnloadingTime;
    private String newEstimateUnloadingTime;
    private String remarks;
    private String flag;

    private String suggestedLoadingTimeView;
    private String actualLoadingTimeView;
    private String suggestedUnloadingTimeView;
    private String newEstimateUnloadingTimeView;

    private String gts;
    private String doNumber;
    private String chamber;
    private String quantity;
    private String intervals;
    private String rmsEvent;
    private String receivedDate;
    private String receivedDateView;
    private String status;

    public AbnormalLoadingTemp() {

    }
//without test condition

    public AbnormalLoadingTemp(String requestId, String suggestedLoadingTime, String actualLoadingTime, String suggestedUnloadingTime,
            String newEstimateUnloadingTime, String remarks) {
        super();
        this.requestId = requestId;
        this.suggestedLoadingTime = suggestedLoadingTime;
        this.actualLoadingTime = actualLoadingTime;
        this.suggestedUnloadingTime = suggestedUnloadingTime;
        this.newEstimateUnloadingTime = newEstimateUnloadingTime;
        this.remarks = remarks;
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

    public String getSuggestedLoadingTime() {
        return suggestedLoadingTime;
    }

    public void setSuggestedLoadingTime(String suggestedLoadingTime) {
        this.suggestedLoadingTime = suggestedLoadingTime;
    }

    public String getActualLoadingTime() {
        return actualLoadingTime;
    }

    public void setActualLoadingTime(String actualLoadingTime) {
        this.actualLoadingTime = actualLoadingTime;
    }

    public String getSuggestedUnloadingTime() {
        return suggestedUnloadingTime;
    }

    public void setSuggestedUnloadingTime(String suggestedUnloadingTime) {
        this.suggestedUnloadingTime = suggestedUnloadingTime;
    }

    public String getNewEstimateUnloadingTime() {
        return newEstimateUnloadingTime;
    }

    public void setNewEstimateUnloadingTime(String newEstimateUnloadingTime) {
        this.newEstimateUnloadingTime = newEstimateUnloadingTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSuggestedLoadingTimeView() {
        return suggestedLoadingTimeView;
    }

    public void setSuggestedLoadingTimeView(String suggestedLoadingTimeView) {
        this.suggestedLoadingTimeView = suggestedLoadingTimeView;
    }

    public String getActualLoadingTimeView() {
        return actualLoadingTimeView;
    }

    public void setActualLoadingTimeView(String actualLoadingTimeView) {
        this.actualLoadingTimeView = actualLoadingTimeView;
    }

    public String getSuggestedUnloadingTimeView() {
        return suggestedUnloadingTimeView;
    }

    public void setSuggestedUnloadingTimeView(String suggestedUnloadingTimeView) {
        this.suggestedUnloadingTimeView = suggestedUnloadingTimeView;
    }

    public String getNewEstimateUnloadingTimeView() {
        return newEstimateUnloadingTimeView;
    }

    public void setNewEstimateUnloadingTimeView(String newEstimateUnloadingTimeView) {
        this.newEstimateUnloadingTimeView = newEstimateUnloadingTimeView;
    }

    public String getGts() {
        return gts;
    }

    public void setGts(String gts) {
        this.gts = gts;
    }

    public String getDoNumber() {
        return doNumber;
    }

    public void setDoNumber(String doNumber) {
        this.doNumber = doNumber;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIntervals() {
        return intervals;
    }

    public void setIntervals(String intervals) {
        this.intervals = intervals;
    }

    public String getRmsEvent() {
        return rmsEvent;
    }

    public void setRmsEvent(String rmsEvent) {
        this.rmsEvent = rmsEvent;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getReceivedDateView() {
        return receivedDateView;
    }

    public void setReceivedDateView(String receivedDateView) {
        this.receivedDateView = receivedDateView;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
