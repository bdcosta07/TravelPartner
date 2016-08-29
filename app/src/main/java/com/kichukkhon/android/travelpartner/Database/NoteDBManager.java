package com.kichukkhon.android.travelpartner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kichukkhon.android.travelpartner.Class.Note;
import com.kichukkhon.android.travelpartner.Database.Tables.NoteEntry;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/28/2016.
 */
public class NoteDBManager {

    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Note noteInfo;

    public NoteDBManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean addNote(Note noteInfo) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteEntry.TOUR_ID, noteInfo.getTourId());
        contentValues.put(NoteEntry.TITLE, noteInfo.getTitle());
        contentValues.put(NoteEntry.NOTE, noteInfo.getNote());
        contentValues.put(NoteEntry.CREATED_AT, noteInfo.getCreatedAt());

        long inserted = database.insert(NoteEntry.NOTE_TABLE, null, contentValues);
        this.close();
        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public ArrayList<Note> getNoteInfoByTourId(int tourId) {
        this.open();

        ArrayList<Note> noteList = new ArrayList<>();

        Cursor cursor = database.query(NoteEntry.NOTE_TABLE,
                null,
                NoteEntry.TOUR_ID + " = " + tourId,
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(NoteEntry._ID));
                String title = cursor.getString(cursor.getColumnIndex(NoteEntry.TITLE));
                String note = cursor.getString(cursor.getColumnIndex(NoteEntry.NOTE));
                long dateTime = cursor.getLong(cursor.getColumnIndex(NoteEntry.CREATED_AT));


                noteInfo = new Note(id, tourId, title, note, dateTime);
                noteList.add(noteInfo);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return noteList;
    }

    public Note getNoteInfoById(int id) {
        this.open();

        Cursor cursor = database.query(NoteEntry.NOTE_TABLE,
                null,
                Tables.ExpenseEntry._ID + "= " + id,
                null, null, null, null);

        int mid = cursor.getInt(cursor.getColumnIndex(NoteEntry._ID));
        int tourId = cursor.getInt(cursor.getColumnIndex(NoteEntry.TOUR_ID));
        String title = cursor.getString(cursor.getColumnIndex(NoteEntry.TITLE));
        String note = cursor.getString(cursor.getColumnIndex(NoteEntry.NOTE));
        long dateTime = cursor.getLong(cursor.getColumnIndex(NoteEntry.CREATED_AT));


        noteInfo = new Note(mid, tourId, title, note, dateTime);
        this.close();
        return noteInfo;

    }

    public boolean updateNote(int id) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteEntry.TOUR_ID, noteInfo.getTourId());
        contentValues.put(NoteEntry.TITLE, noteInfo.getTitle());
        contentValues.put(NoteEntry.NOTE, noteInfo.getNote());
        contentValues.put(NoteEntry.CREATED_AT, noteInfo.getCreatedAt());

        int updated = database.update(NoteEntry.NOTE_TABLE, contentValues, NoteEntry._ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else
            return false;
    }

    public boolean deleteNote(int id) {

        this.open();
        int deleted = database.delete(NoteEntry.NOTE_TABLE, NoteEntry._ID + " = " + id, null);

        this.close();
        if (deleted > 0) {
            return true;
        } else
            return false;
    }
}
