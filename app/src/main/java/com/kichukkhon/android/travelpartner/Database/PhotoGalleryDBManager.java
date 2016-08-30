package com.kichukkhon.android.travelpartner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kichukkhon.android.travelpartner.Class.PhotoGallery;
import com.kichukkhon.android.travelpartner.Database.Tables.PhotoGalleryEntry;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/30/2016.
 */
public class PhotoGalleryDBManager {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private PhotoGallery photoGalleryInfo;

    public PhotoGalleryDBManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean addImage(PhotoGallery imageInfo) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PhotoGalleryEntry.TOUR_ID, imageInfo.getTourId());
        contentValues.put(PhotoGalleryEntry.TITLE, imageInfo.getTitle());
        contentValues.put(PhotoGalleryEntry.PATH, imageInfo.getPath());
        contentValues.put(PhotoGalleryEntry.DATETIME, imageInfo.getDateTime());

        long inserted = database.insert(PhotoGalleryEntry.IMAGE_TABLE, null, contentValues);
        this.close();
        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public ArrayList<PhotoGallery> getImageInfoByTourId(int tourId) {
        this.open();

        ArrayList<PhotoGallery> imageList = new ArrayList<>();

        String sortOrder = PhotoGalleryEntry._ID + " DESC";

        Cursor cursor = database.query(PhotoGalleryEntry.IMAGE_TABLE,
                null,
                PhotoGalleryEntry.TOUR_ID + "= " + tourId,
                null, null, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(PhotoGalleryEntry._ID));
                String title = cursor.getString(cursor.getColumnIndex(PhotoGalleryEntry.TITLE));
                String path = cursor.getString(cursor.getColumnIndex(PhotoGalleryEntry.PATH));
                long dateTime = cursor.getLong(cursor.getColumnIndex(PhotoGalleryEntry.DATETIME));


                photoGalleryInfo = new PhotoGallery(id, tourId, title, path, dateTime);
                imageList.add(photoGalleryInfo);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return imageList;
    }

    public PhotoGallery getImageInfoById(int id) {
        this.open();

        Cursor cursor = database.query(PhotoGalleryEntry.IMAGE_TABLE,
                null,
                PhotoGalleryEntry._ID + "= " + id,
                null, null, null, null);

        cursor.moveToFirst();

        int mid = cursor.getInt(cursor.getColumnIndex(PhotoGalleryEntry._ID));
        int tourId = cursor.getInt(cursor.getColumnIndex(PhotoGalleryEntry.TOUR_ID));
        String title = cursor.getString(cursor.getColumnIndex(PhotoGalleryEntry.TITLE));
        String path = cursor.getString(cursor.getColumnIndex(PhotoGalleryEntry.PATH));
        long dateTime = cursor.getLong(cursor.getColumnIndex(PhotoGalleryEntry.DATETIME));

        photoGalleryInfo = new PhotoGallery(mid, tourId, title, path, dateTime);
        this.close();
        return photoGalleryInfo;

    }

    public boolean updateExpense(int id) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PhotoGalleryEntry.TOUR_ID, photoGalleryInfo.getTourId());
        contentValues.put(PhotoGalleryEntry.TITLE, photoGalleryInfo.getTitle());
        contentValues.put(PhotoGalleryEntry.PATH, photoGalleryInfo.getPath());
        contentValues.put(PhotoGalleryEntry.DATETIME, photoGalleryInfo.getDateTime());
        ;


        int updated = database.update(PhotoGalleryEntry.IMAGE_TABLE, contentValues, PhotoGalleryEntry._ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else
            return false;
    }

    public boolean deleteImage(int id) {

        this.open();
        int deleted = database.delete(PhotoGalleryEntry.IMAGE_TABLE, PhotoGalleryEntry._ID + " = " + id, null);

        this.close();
        if (deleted > 0) {
            return true;
        } else
            return false;
    }
}
