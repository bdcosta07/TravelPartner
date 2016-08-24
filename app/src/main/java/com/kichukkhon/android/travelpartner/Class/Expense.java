package com.kichukkhon.android.travelpartner.Class;

/**
 * Created by Bridget on 8/23/2016.
 */
public class Expense extends BaseEntity {
    private int tourId;
    private String purpose;
    private double amount;
    private long dateTime;

    public Expense(int id, int tourId, String purpose, double amount, long dateTime) {
        super(id);
        this.tourId = tourId;
        this.purpose = purpose;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public Expense() {
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
