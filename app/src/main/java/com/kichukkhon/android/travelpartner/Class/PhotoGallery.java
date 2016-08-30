package com.kichukkhon.android.travelpartner.Class;

/**
 * Created by Bridget on 8/30/2016.
 */
public class PhotoGallery extends BaseEntity {
    private int tourId;
    private String title;
    private String path;
    private long dateTime;

    public PhotoGallery(int id, int tourId, String title, String path, long dateTime) {
        super(id);
        this.tourId = tourId;
        this.title = title;
        this.path = path;
        this.dateTime = dateTime;
    }

    public PhotoGallery(long dateTime, String path, String title, int tourId) {
        this.dateTime = dateTime;
        this.path = path;
        this.title = title;
        this.tourId = tourId;
    }

    public PhotoGallery() {
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
