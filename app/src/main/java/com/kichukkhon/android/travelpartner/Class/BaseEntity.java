package com.kichukkhon.android.travelpartner.Class;

/**
 * Created by Bridget on 8/24/2016.
 */
public class BaseEntity {
    private int id;

    public BaseEntity(int id) {
        this.id = id;
    }

    public BaseEntity(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
