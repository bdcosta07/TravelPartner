package com.kichukkhon.android.travelpartner.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kichukkhon.android.travelpartner.Database.Tables.*;

/**
 * Created by Bridget on 8/24/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper dbInstance;

    public static final String DATABASE_NAME = "travel_partner.db";
    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String BOOLEAN_TYPE = "boolean";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_TOUR_TABLE = "CREATE TABLE " + TourEntry.TOUR_TABLE
            + "( " + TourEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
            + TourEntry.TOUR_NAME + TEXT_TYPE + COMMA_SEP
            + TourEntry.DESTINATION + TEXT_TYPE + COMMA_SEP
            + TourEntry.DESTINATION_LAT + REAL_TYPE + COMMA_SEP
            + TourEntry.DESTINATION_LON + REAL_TYPE + COMMA_SEP
            + TourEntry.STAR_DATE + INTEGER_TYPE + COMMA_SEP
            + TourEntry.END_DATE + INTEGER_TYPE + COMMA_SEP
            + TourEntry.START_LOCATION + TEXT_TYPE + COMMA_SEP
            + TourEntry.LOCATION_LAT + INTEGER_TYPE + COMMA_SEP
            + TourEntry.LOCATION_LON + INTEGER_TYPE + COMMA_SEP
            + TourEntry.TRANSPORT + TEXT_TYPE + COMMA_SEP
            + TourEntry.BUDGET + REAL_TYPE + COMMA_SEP
            + TourEntry.DELETED + BOOLEAN_TYPE + " )";

    public static final String CREATE_EXPENSE_TABLE="CREATE TABLE"+ ExpenseEntry.EXPENSE_TABLE
            +"( "+ ExpenseEntry._ID+"INTEGER PRIMARY KEY "+COMMA_SEP
            +ExpenseEntry.TOUR_ID+INTEGER_TYPE+COMMA_SEP
            +ExpenseEntry.PURPOSE+TEXT_TYPE+COMMA_SEP
            +ExpenseEntry.AMOUNT+REAL_TYPE+COMMA_SEP
            +ExpenseEntry.DATE_TIME+INTEGER_TYPE+" )";

    public static final String CREATE_ALARM_TABLE="CREATE TABLE"+ AlarmEntry.ALARM_TABLE
            +"( "+ AlarmEntry._ID+"INTEGER PRIMARY KEY "+COMMA_SEP
            +AlarmEntry.TOUR_ID+INTEGER_TYPE+COMMA_SEP
            +AlarmEntry.DATE_TIME+INTEGER_TYPE+COMMA_SEP
            +AlarmEntry.ALARM_NOTE+TEXT_TYPE+COMMA_SEP +" )";

    public static final String CREATE_NOTE_TABLE="CREATE TABLE"+ NoteEntry.NOTE_TABLE
            +"( "+ NoteEntry._ID+"INTEGER PRIMARY KEY "+COMMA_SEP
            +NoteEntry.TOUR_ID+INTEGER_TYPE+COMMA_SEP
            +NoteEntry.TITLE+TEXT_TYPE+COMMA_SEP
            +NoteEntry.NOTE+TEXT_TYPE+COMMA_SEP +" )";


    public static DatabaseHelper getDbInstance(Context context) {
        if (dbInstance == null)
            dbInstance = new DatabaseHelper(context);
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TOUR_TABLE);
        sqLiteDatabase.execSQL(CREATE_EXPENSE_TABLE);
        sqLiteDatabase.execSQL(CREATE_ALARM_TABLE);
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
