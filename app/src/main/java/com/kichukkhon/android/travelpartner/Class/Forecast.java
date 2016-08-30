package com.kichukkhon.android.travelpartner.Class;

/**
 * Created by Bdjobs on 26-Aug-16.
 */
public class Forecast {
    private int imageId;
    private int code;
    private int high;
    private int low;
    private String description;
    private String date;

    public Forecast(String date, int high, int low, String description, int code) {
        this.date = date;
        this.high = high;
        this.low = low;
        this.description = description;
        this.code = code;
    }

    public Forecast() {
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
