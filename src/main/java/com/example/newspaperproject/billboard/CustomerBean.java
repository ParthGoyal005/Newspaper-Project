package com.example.newspaperproject.billboard;

public class CustomerBean {
    String mobileC, startC, endC, daysC, amountC,statusC;

    public CustomerBean(String mobileC, String startC, String endC, String daysC, String amountC, String statusC) {
        this.mobileC = mobileC;
        this.startC = startC;
        this.endC = endC;
        this.daysC = daysC;
        this.amountC = amountC;
        this.statusC = statusC;
    }

    public String getMobileC() {
        return mobileC;
    }

    public void setMobileC(String mobileC) {
        this.mobileC = mobileC;
    }

    public String getStartC() {
        return startC;
    }

    public void setStartC(String startC) {
        this.startC = startC;
    }

    public String getEndC() {
        return endC;
    }

    public void setEndC(String endC) {
        this.endC = endC;
    }

    public String getDaysC() {
        return daysC;
    }

    public void setDaysC(String daysC) {
        this.daysC = daysC;
    }

    public String getAmountC() {
        return amountC;
    }

    public void setAmountC(String amountC) {
        this.amountC = amountC;
    }

    public String getStatusC() {
        return statusC;
    }

    public void setStatusC(String statusC) {
        this.statusC = statusC;
    }
}
