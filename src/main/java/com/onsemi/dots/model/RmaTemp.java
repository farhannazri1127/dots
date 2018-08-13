package com.onsemi.dots.model;

public class RmaTemp {

    private String id;
    private String requestId;
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
    private String flag;

    public RmaTemp() {
    }

    public RmaTemp(String requestId, String flag, String rmaRemarks1, String rmaDate1, String rmaRemarks2, String rmaDate2) {
        super();
        this.requestId = requestId;
        this.flag = flag;
        this.rmaRemarks1 = rmaRemarks1;
        this.rmaDate1 = rmaDate1;
        this.rmaRemarks2 = rmaRemarks2;
        this.rmaDate2 = rmaDate2;
    }

    public RmaTemp(String requestId, String rmaDispo1, String rmaDispo1Remarks, String rmaDispo1Date, String rmaDispo2, String rmaDispo2Remarks, String rmaDispo2Date) {
        super();
        this.requestId = requestId;
        this.rmaDispo1 = rmaDispo1;
        this.rmaDispo1Remarks = rmaDispo1Remarks;
        this.rmaDispo1Date = rmaDispo1Date;
        this.rmaDispo2 = rmaDispo2;
        this.rmaDispo2Remarks = rmaDispo2Remarks;
        this.rmaDispo2Date = rmaDispo2Date;
    }

    //include disBy
    public RmaTemp(String requestId, String rmaDispo1, String rmaDispo1Remarks, String rmaDispo1Date, String rmaDispo2, String rmaDispo2Remarks, String rmaDispo2Date, String rmaDispo1By, String rmaDispo2By) {
        super();
        this.requestId = requestId;
        this.rmaDispo1 = rmaDispo1;
        this.rmaDispo1Remarks = rmaDispo1Remarks;
        this.rmaDispo1Date = rmaDispo1Date;
        this.rmaDispo2 = rmaDispo2;
        this.rmaDispo2Remarks = rmaDispo2Remarks;
        this.rmaDispo2Date = rmaDispo2Date;
        this.rmaDispo1By = rmaDispo1By;
        this.rmaDispo2By = rmaDispo2By;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
