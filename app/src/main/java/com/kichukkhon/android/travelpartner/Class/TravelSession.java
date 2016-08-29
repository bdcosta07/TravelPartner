package com.kichukkhon.android.travelpartner.Class;

/**
 * Created by Bridget on 8/17/2016.
 */
public class TravelSession {
    int id;
    int tourId;
    long startTime;
    long stopTime;

    public TravelSession(int id, int tourId, long startTime, long stopTime) {
        this.id = id;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public TravelSession(int tourId, long startTime, long stopTime) {
        this.tourId = tourId;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public TravelSession() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }
}
