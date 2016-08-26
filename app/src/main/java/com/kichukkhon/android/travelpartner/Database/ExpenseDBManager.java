package com.kichukkhon.android.travelpartner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kichukkhon.android.travelpartner.Class.Expense;
import com.kichukkhon.android.travelpartner.Database.Tables.ExpenseEntry;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/24/2016.
 */
public class ExpenseDBManager {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Expense expense;

    public ExpenseDBManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean addExpense(Expense expense) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseEntry.TOUR_ID, expense.getTourId());
        contentValues.put(ExpenseEntry.PURPOSE, expense.getPurpose());
        contentValues.put(ExpenseEntry.AMOUNT, expense.getAmount());
        contentValues.put(ExpenseEntry.DATE_TIME, expense.getDateTime());

        long inserted = database.insert(ExpenseEntry.EXPENSE_TABLE, null, contentValues);
        this.close();
        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public ArrayList<Expense> getExpenseInfoByTourId(int tourId) {
        this.open();

        ArrayList<Expense> expenseList = new ArrayList<>();

        Cursor cursor = database.query(ExpenseEntry.EXPENSE_TABLE,
                null,
                ExpenseEntry.TOUR_ID + "= " + tourId,
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(ExpenseEntry._ID));
                String purpose = cursor.getString(cursor.getColumnIndex(ExpenseEntry.PURPOSE));
                double amount = cursor.getDouble(cursor.getColumnIndex(ExpenseEntry.AMOUNT));
                long dateTime = cursor.getLong(cursor.getColumnIndex(ExpenseEntry.DATE_TIME));


                expense = new Expense(id, tourId, purpose, amount, dateTime);
                expenseList.add(expense);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return expenseList;
    }

    public Expense getExpenseInfoById(int id) {
        this.open();

        Cursor cursor = database.query(ExpenseEntry.EXPENSE_TABLE,
                null,
                ExpenseEntry._ID + "= " + id,
                null, null, null, null);

        int mid = cursor.getInt(cursor.getColumnIndex(ExpenseEntry._ID));
        int tourId = cursor.getInt(cursor.getColumnIndex(ExpenseEntry.TOUR_ID));
        String purpose = cursor.getString(cursor.getColumnIndex(ExpenseEntry.PURPOSE));
        double amount = cursor.getDouble(cursor.getColumnIndex(ExpenseEntry.AMOUNT));
        long dateTime = cursor.getLong(cursor.getColumnIndex(ExpenseEntry.DATE_TIME));

        expense = new Expense(mid, tourId, purpose, amount, dateTime);
        this.close();
        return expense;

    }

    public boolean updateExpense(int id) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseEntry.TOUR_ID, expense.getTourId());
        contentValues.put(ExpenseEntry.PURPOSE, expense.getPurpose());
        contentValues.put(ExpenseEntry.AMOUNT, expense.getAmount());
        contentValues.put(ExpenseEntry.DATE_TIME, expense.getDateTime());


        int updated = database.update(ExpenseEntry.EXPENSE_TABLE, contentValues, ExpenseEntry._ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else
            return false;
    }

    public boolean deleteExpense(int id) {

        this.open();
        int deleted = database.delete(ExpenseEntry.EXPENSE_TABLE, ExpenseEntry._ID + " = " + id, null);

        this.close();
        if (deleted > 0) {
            return true;
        } else
            return false;
    }
}
