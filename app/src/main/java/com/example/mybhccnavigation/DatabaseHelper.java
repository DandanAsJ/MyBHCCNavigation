package com.example.mybhccnavigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "myBHCC51124";
    private static final int DB_VERSION = 1;

    DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE ACADEMICS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "NAME TEXT, "
        + "LOCATION TEXT, "
        + "OPENHOUR TEXT);");
        insertRecord(db, "Language Lab", "E-144", "Monday - Thursday: 10 am - 4 pm Friday: 10 am - 2 pm");
        insertRecord(db, "Learning Communities/Ace Mentors", "N-216", "Appointments needed");
        insertRecord(db, "Library", "N-300", "Monday – Thursday: 9 am – 7 pm Friday: 9 am - 4 pm Saturday: 11:00 am - 3:30 pm");
        insertRecord(db, "MathSpace", "D-114", "Monday- Friday 10 am to 4 pm");
        insertRecord(db, "The Writing Place", "N-311", "Monday- Thursday 10 am- 3 pm");
        insertRecord(db, "TRIO Student Success Program", "N-217", "Monday - Thursday 9 am to 5 pm");
        insertRecord(db, "Tutoring Center(TASC)", "N-114", "Appointments needed");

        db.execSQL("CREATE TABLE SUPPORTS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "LOCATION TEXT, "
                + "OPENHOUR TEXT);");
        insertRecord1(db, "Academic Computing Center", "D-113", "");
        insertRecord1(db, "Academic Innovation and Distance Education", "E-230", "Monday – Thursday: 12 pm - 5 pm.");
        insertRecord1(db, "Admissions Office", "B-200", "Monday - Thursday: 9 am - 5 pm. Friday: 11 am - 4 pm.");
        insertRecord1(db, "Advising & LifeMap", "N-210", "Monday–Thursday 9 am - 6 pm. Friday 12 – 4 pm.");
        insertRecord1(db, "Assessment Center", "B-118", "Monday - Thursday 9 am to 5 pm. Fridays 11 am to 4 pm.");
        insertRecord1(db, "Bookstore", "E-120", "Monday - Thursday: 9 am - 5 pm. Friday: 9 am - 3 pm.");
        insertRecord1(db, "Center for Self-Directed Learning", "N-114", "Monday - Thursday: 10 am - 4 pm. Friday: 10 am - 1 pm.");
        insertRecord1(db, "Commonwealth Honors", "E-145", "");
        insertRecord1(db, "Disability Support Services", "E-222", "Monday - Friday 8 am – 4 pm.");
        insertRecord1(db, "DISH Food Pantry", "B-101", "Monday-Friday 11 am-3 pm.G-Building Lounge: Monday-Friday 3-7 pm");
        insertRecord1(db, "Health Services", "E-154", "Monday - Friday: 10 am - 3 pm.");
        insertRecord1(db, "HOPE Initiative", "E-142", "Monday - Thursday : 9:30 am - 5:30 pm. Friday: 9:30 am - 4 pm.");
        insertRecord1(db, "International Center", "N-111", "Monday - Friday 9 am - 5 pm.");
        insertRecord1(db, "Internship and Career Development", "N-114", "Monday – Friday 10 am - 4 pm.");
        insertRecord1(db, "Single Stop", "N-113B", "Monday - Friday: 9 am - 5 pm.");
        insertRecord1(db, "Student Activities", "D-106H", "Monday - Thursday: 9 am - 4 pm");
        insertRecord1(db, "Student Central", "B-Building Lobby", "Monday-Thursday: 8:30 am - 5:30 pm. Friday: 11 am - 3:30 pm.");
        insertRecord1(db, "Student Counseling Prevention and Wellness Center", "B-138", "Monday - Tuesday: 8 am-3 pm. Wednesday: 8 am-12 pm. Thursday: 9-10:30 am & 1-3 pm. Friday: 9-11:30 am & 1-3 pm.");
        insertRecord1(db, "Veterans Center", "N-116", "Monday – Thursday: 9 am – 5 pm. Fridays: 9 am – 4 pm.");

        db.execSQL("CREATE TABLE USER (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "BHCCID TEXT, "
                + "NAME TEXT, "
                + "EMAIL TEXT);");

        db.execSQL("CREATE TABLE RECORDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "BHCCID TEXT, "
                + "NAME TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

    public static void insertRecord(SQLiteDatabase db, String name,
                                    String location, String hours) {
        ContentValues recordValues = new ContentValues();
        recordValues.put("NAME", name);
        recordValues.put("LOCATION", location);
        recordValues.put("OPENHOUR", hours);
        db.insert("ACADEMICS", null, recordValues);

    }
    public static void insertRecord1(SQLiteDatabase db, String name,
                                    String location, String hours) {
        ContentValues recordValues = new ContentValues();
        recordValues.put("NAME", name);
        recordValues.put("LOCATION", location);
        recordValues.put("OPENHOUR", hours);
        db.insert("SUPPORTS", null, recordValues);

    }

    public long insertUser(String bhccID, String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("BHCCID", bhccID);
        values.put("NAME", name);
        values.put("EMAIL", email);

        // Inserting Row
        long result = db.insert("USER", null, values);

        Log.e("insert", String.valueOf(result));
        // Closing database connection
        db.close();
        return result;
    }

    public boolean checkUser(String studentID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_id"};
        String selection = "BHCCID" + " = ?";
        String[] selectionArgs = {studentID};

        Cursor cursor = db.query("USER", columns, selection, selectionArgs, null, null, null);
        boolean userExists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return userExists;
    }

    public long insertRecords(String student, String officeName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("BHCCID", student);
        values.put("NAME", officeName);

        long result = db.insert("RECORDS", null, values);

        db.close();

        return result;
    }

    public void deleteRecords(String student, String officeName) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("RECORDS", "BHCCID" + " = ? AND " + "NAME" + " = ?",
                new String[]{student, officeName});

        db.close();
    }

    public ArrayList<String> getFavoriteOffices(String studentid) {
        ArrayList<String> favoriteOffices = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"NAME"};
        String selection = "BHCCID" + " = ?";
        String[] selectionArgs = {studentid};

        Cursor cursor = db.query("RECORDS", columns, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex("NAME");
            if (columnIndex >= 0) {
                // Column exists in the result set
                String officeName = cursor.getString(columnIndex);
                favoriteOffices.add(officeName);
            } else {
                // Handle the case when the column does not exist
                Log.e("DatabaseHelper", "Column does not exist.");
            }

        }
        cursor.close();
        db.close();

        return favoriteOffices;
    }

}
