package com.kichukkhon.android.travelpartner.Class;

/**
 * Created by Bridget on 8/23/2016.
 */
public class ExpenseTracker {
    private int id;
    private double totalAmount;
    private String purpose;
    private double expenseAmount;
    private long expenseDateTime;
    private double currentAmount;

    public ExpenseTracker(int id, double totalAmount, String purpose, double expenseAmount, long expenseDateTime, double currentAmount) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.purpose = purpose;
        this.expenseAmount = expenseAmount;
        this.expenseDateTime = expenseDateTime;
        this.currentAmount = currentAmount;
    }

    public ExpenseTracker() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public long getExpenseDateTime() {
        return expenseDateTime;
    }

    public void setExpenseDateTime(long expenseDateTime) {
        this.expenseDateTime = expenseDateTime;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }
}
