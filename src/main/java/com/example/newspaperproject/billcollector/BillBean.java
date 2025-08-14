package com.example.newspaperproject.billcollector;

public class BillBean {
    String startC, endC, daysC, amountC;

    public BillBean( String startC, String endC, String daysC, String amountC) {

        this.startC = startC;
        this.endC = endC;
        this.daysC = daysC;
        this.amountC = amountC;
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

}
