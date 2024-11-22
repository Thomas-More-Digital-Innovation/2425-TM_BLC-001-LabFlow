package com.thomasmore.blc.labflow.entity;

public class PrintRequest {
    private String staalId;
    private int amountOfCopies;

    public PrintRequest() {

    }

    public String getStaalId() {
        return staalId;
    }

    public void setStaalId(String staalId) {
        this.staalId = staalId;
    }

    public int getAmountOfCopies() {
        return amountOfCopies;
    }

    public void setAmountOfCopies(int amountOfCopies) {
        this.amountOfCopies = amountOfCopies;
    }
}
