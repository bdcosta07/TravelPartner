package com.kichukkhon.android.travelpartner.Class;

/**
 * Created by Ratul on 8/27/2016.
 */
public class PlaceBean {
    private int id;
    private String placeRef;
    private double latitude;
    private double longitude;
    private String name;
    private boolean isOpen;
    private float rating;
    private String vicinity;
    private String type;
    private String kind;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public boolean isOpen(){
        return isOpen;
    }

    public float getRating(){
        return rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlaceRef() {
        return placeRef;
    }

    public void setPlaceRef(String pubref) {
        this.placeRef = pubref;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
