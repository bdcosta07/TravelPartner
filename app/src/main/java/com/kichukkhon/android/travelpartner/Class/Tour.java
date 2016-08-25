package com.kichukkhon.android.travelpartner.Class;

/**
 * Created by Bridget on 8/23/2016.
 */
public class Tour extends BaseEntity{
    private String tourName;
    private String destination;
    private String placeId;
    private double destLat;
    private double destLon;
    private long startDateTime;
    private long endDateTime;
    private String startLocation;
    private double locationLat;
    private double locationLon;
    private String transport;
    private double budget;
    private boolean deleted;

    public Tour(int id, String tourName, String destination, double destLat, double destLon, long startDateTime, long endDateTime, String startLocation, double locationLat, double locationLon, String transport, double budget) {
        super(id);
        this.tourName = tourName;
        this.destination = destination;
        this.destLat = destLat;
        this.destLon = destLon;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startLocation = startLocation;
        this.locationLat = locationLat;
        this.locationLon = locationLon;
        this.transport = transport;
        this.budget = budget;
    }

    public Tour() {
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public long getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(long startDateTime) {
        this.startDateTime = startDateTime;
    }

    public long getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(long endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getDestLat() {
        return destLat;
    }

    public void setDestLat(double destLat) {
        this.destLat = destLat;
    }

    public double getDestLon() {
        return destLon;
    }

    public void setDestLon(double destLon) {
        this.destLon = destLon;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(double locationLon) {
        this.locationLon = locationLon;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
