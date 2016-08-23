package com.kichukkhon.android.travelpartner.Class;

import android.provider.BaseColumns;

/**
 * Created by Bridget on 8/23/2016.
 */
public class Tour extends BaseEntity{
    private String tripName;
    private long StartDateTime;
    private long EndDateTime;
    private String transport;
    private ExpenseTracker budget;

    public Tour(int id, String tripName, long startDateTime, long endDateTime, String transport, ExpenseTracker budget) {
        super(id);
        this.tripName = tripName;
        StartDateTime = startDateTime;
        EndDateTime = endDateTime;
        this.transport = transport;
        this.budget = budget;
    }

    public Tour() {
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public long getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(long startDateTime) {
        StartDateTime = startDateTime;
    }

    public long getEndDateTime() {
        return EndDateTime;
    }

    public void setEndDateTime(long endDateTime) {
        EndDateTime = endDateTime;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public ExpenseTracker getBudget() {
        return budget;
    }

    public void setBudget(ExpenseTracker budget) {
        this.budget = budget;
    }
}
