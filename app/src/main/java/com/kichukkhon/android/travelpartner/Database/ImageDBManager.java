package com.kichukkhon.android.travelpartner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kichukkhon.android.travelpartner.Class.Image;
import com.kichukkhon.android.travelpartner.Database.Tables.ImageEntry;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/30/2016.
 */
public class ImageDBManager {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Image imageInfo;

    public ImageDBManager(Context context) {
        helper=new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean addImage(Image imageInfo) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageEntry.TOUR_ID, imageInfo.getTourId());
        contentValues.put(ImageEntry.TITLE, imageInfo.getTitle());
        contentValues.put(ImageEntry.PATH, imageInfo.getPath());
        contentValues.put(ImageEntry.DATETIME, imageInfo.getDateTime());

        long inserted = database.insert(ImageEntry.IMAGE_TABLE, null, contentValues);
        this.close();
        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public ArrayList<Image> getImageInfoByTourId(int tourId) {
        this.open();

        ArrayList<Image> imageList = new ArrayList<>();

        Cursor cursor = database.query(ImageEntry.IMAGE_TABLE,
                null,
                ImageEntry.TOUR_ID + "= " + tourId,
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(ImageEntry._ID));
                String title = cursor.getString(cursor.getColumnIndex(ImageEntry.TITLE));
                String path = cursor.getString(cursor.getColumnIndex(ImageEntry.PATH));
                long dateTime = cursor.getLong(cursor.getColumnIndex(ImageEntry.DATETIME));


                imageInfo = new Image(id,tourId,title,path,dateTime);
                imageList.add(imageInfo);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return imageList;
    }
}
