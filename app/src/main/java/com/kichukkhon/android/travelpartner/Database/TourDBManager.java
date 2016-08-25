package com.kichukkhon.android.travelpartner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.Tables.TourEntry;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/24/2016.
 */
public class TourDBManager {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Tour tour;

    public TourDBManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean addTour(Tour tour) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TourEntry.TOUR_NAME, tour.getTourName());
        contentValues.put(TourEntry.PLACE_ID, tour.getPlaceId());
        contentValues.put(TourEntry.DESTINATION, tour.getDestination());
        contentValues.put(TourEntry.DESTINATION_LAT, tour.getDestLat());
        contentValues.put(TourEntry.DESTINATION_LON, tour.getDestLon());
        contentValues.put(TourEntry.STAR_DATE, tour.getStartDateTime());
        contentValues.put(TourEntry.END_DATE, tour.getEndDateTime());
        contentValues.put(TourEntry.START_LOCATION, tour.getStartLocation());
        contentValues.put(TourEntry.LOCATION_LAT, tour.getLocationLat());
        contentValues.put(TourEntry.LOCATION_LON, tour.getLocationLon());
        contentValues.put(TourEntry.TRANSPORT, tour.getTransport());
        contentValues.put(TourEntry.BUDGET, tour.getBudget());
        contentValues.put(TourEntry.DELETED, tour.isDeleted());

        long inserted = database.insert(TourEntry.TOUR_TABLE, null, contentValues);
        this.close();
        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public ArrayList<Tour> getAllTourInfo() {
        this.open();

        ArrayList<Tour> tourList = new ArrayList<>();

        Cursor cursor = database.query(TourEntry.TOUR_TABLE, null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(TourEntry._ID));
                String tourName = cursor.getString(cursor.getColumnIndex(TourEntry.TOUR_NAME));
                String destination = cursor.getString(cursor.getColumnIndex(TourEntry.DESTINATION));
                double destinationLat = cursor.getDouble(cursor.getColumnIndex(TourEntry.DESTINATION_LAT));
                double destinationLon = cursor.getDouble(cursor.getColumnIndex(TourEntry.DESTINATION_LON));
                long startDate = cursor.getLong(cursor.getColumnIndex(TourEntry.STAR_DATE));
                long endDate = cursor.getLong(cursor.getColumnIndex(TourEntry.END_DATE));
                String starLocation = cursor.getString(cursor.getColumnIndex(TourEntry.START_LOCATION));
                double locationLat = cursor.getDouble(cursor.getColumnIndex(TourEntry.LOCATION_LAT));
                double locationLon = cursor.getDouble(cursor.getColumnIndex(TourEntry.LOCATION_LON));
                String transport = cursor.getString(cursor.getColumnIndex(TourEntry.TRANSPORT));
                double budget = cursor.getDouble(cursor.getColumnIndex(TourEntry.BUDGET));

                tour = new Tour(id, tourName, destination, destinationLat, destinationLon, startDate, endDate, starLocation, locationLat, locationLon, transport, budget);
                tourList.add(tour);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return tourList;
    }

    public Tour getTourInfoById(int id) {
        this.open();

        Cursor cursor = database.query(TourEntry.TOUR_TABLE, null,
                TourEntry._ID + "= " + id,
                null, null, null, null);

        int mid = cursor.getInt(cursor.getColumnIndex(TourEntry._ID));
        String tourName = cursor.getString(cursor.getColumnIndex(TourEntry.TOUR_NAME));
        String destination = cursor.getString(cursor.getColumnIndex(TourEntry.DESTINATION));
        double destinationLat = cursor.getDouble(cursor.getColumnIndex(TourEntry.DESTINATION_LAT));
        double destinationLon = cursor.getDouble(cursor.getColumnIndex(TourEntry.DESTINATION_LON));
        long startDate = cursor.getLong(cursor.getColumnIndex(TourEntry.STAR_DATE));
        long endDate = cursor.getLong(cursor.getColumnIndex(TourEntry.END_DATE));
        String starLocation = cursor.getString(cursor.getColumnIndex(TourEntry.START_LOCATION));
        double locationLat = cursor.getDouble(cursor.getColumnIndex(TourEntry.LOCATION_LAT));
        double locationLon = cursor.getDouble(cursor.getColumnIndex(TourEntry.LOCATION_LON));
        String transport = cursor.getString(cursor.getColumnIndex(TourEntry.TRANSPORT));
        double budget = cursor.getDouble(cursor.getColumnIndex(TourEntry.BUDGET));

        tour = new Tour(mid, tourName, destination, destinationLat, destinationLon, startDate, endDate, starLocation, locationLat, locationLon, transport, budget);
        this.close();
        return tour;
    }

    public boolean updateTour(int id) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TourEntry.TOUR_NAME, tour.getTourName());
        contentValues.put(TourEntry.DESTINATION, tour.getDestination());
        contentValues.put(TourEntry.PLACE_ID, tour.getPlaceId());
        contentValues.put(TourEntry.DESTINATION_LAT, tour.getDestLat());
        contentValues.put(TourEntry.DESTINATION_LON, tour.getDestLon());
        contentValues.put(TourEntry.STAR_DATE, tour.getStartDateTime());
        contentValues.put(TourEntry.END_DATE, tour.getEndDateTime());
        contentValues.put(TourEntry.START_LOCATION, tour.getStartLocation());
        contentValues.put(TourEntry.LOCATION_LAT, tour.getLocationLat());
        contentValues.put(TourEntry.LOCATION_LON, tour.getLocationLon());
        contentValues.put(TourEntry.TRANSPORT, tour.getTransport());
        contentValues.put(TourEntry.BUDGET, tour.getBudget());
        contentValues.put(TourEntry.DELETED, tour.isDeleted());

        int updated = database.update(TourEntry.TOUR_TABLE, contentValues, TourEntry._ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else
            return false;
    }

    public boolean deleteTour(int id) {

        this.open();
        int deleted = database.delete(TourEntry.TOUR_TABLE, TourEntry._ID + " = " + id, null);

        this.close();
        if (deleted > 0) {
            return true;
        } else
            return false;
    }

}
