package com.kichukkhon.android.travelpartner.Class;

/**
 * Created by Bridget on 8/28/2016.
 */
public class Note {
    private int id;
    private int tourId;
    private String title;
    private String note;
    private long createdAt;

    public Note(int id, int tourId, String title, String note, long createdAt) {
        this.id = id;
        this.tourId = tourId;
        this.title = title;
        this.note = note;
        this.createdAt = createdAt;
    }

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
