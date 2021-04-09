package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="QuizAppDB.db";
    public static final int DATABASE_VERSION = 1;

    public static final String USER_TABLE = "user";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";

    public static final String PROFILE_TABLE = "profile";
    public static final String COL_PROFILE_ID = "profile_id";
    public static final String COL_USER_ID_FK = "user_id";
    public static final String COL_FIRST_NAME = "first_name";
    public static final String COL_LAST_NAME = "last_name";
    public static final String COL_EXPERIENCE_POINTS = "experience_points";

    public static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + USER_TABLE + "("
            + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USERNAME + " TEXT NOT NULL UNIQUE, "
            + COL_PASSWORD + " TEXT NOT NULL "
            + ");";

    public static final String SQL_CREATE_TABLE_PROFILE = "CREATE TABLE " + PROFILE_TABLE + "("
            + COL_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USER_ID_FK + " INTEGER NOT NULL, "
            + COL_FIRST_NAME + " TEXT NOT NULL, "
            + COL_LAST_NAME + " TEXT NOT NULL, "
            + COL_EXPERIENCE_POINTS + " INTEGER DEFAULT 0, "
            + "FOREIGN KEY (user_id) REFERENCES " + USER_TABLE + " (user_id) "
            + ");";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        onCreate(db);
    }


    public boolean addUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        password = md5(password);
        values.put("username", username);
        values.put("password", password);

        long result = db.insert("user",null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean addProfile(String userID, String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("user_id", userID);
        values.put("first_name", firstName);
        values.put("last_name", lastName);

        long result = db.insert("profile",null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        password = md5(password);
        String[] selectionArgs = { username, password };
        String[] columns = { COL_USER_ID };
        String selection = COL_USERNAME + "=?" + " AND " + COL_PASSWORD + "=?";
        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);

        boolean exists;
        exists = cursor.getCount() > 0;

        db.close();
        cursor.close();

        return exists;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public String getCurrentUserID(String username) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT user_id FROM user WHERE username = ?";
        String[] parameters = new String[] { username };
        Cursor cursor = db.rawQuery(query, parameters);

        if (cursor.moveToFirst())
            return cursor.getString(0);
        else
            return null;
    }


    public String getFistAndLastName(String userID) {
        String result = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM profile WHERE user_id=" + userID, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex("first_name"));
            result += " ";
            result += cursor.getString(cursor.getColumnIndex("last_name"));
        }
        cursor.close();
        db.close();
        return result;
    }


    // for testing purposes:

//    public Cursor viewAllUsers() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor result = db.rawQuery("select * from " + USER_TABLE, null);
//        return result;
//    }
//
//    public Cursor viewAllProfiles() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor result = db.rawQuery("select * from " + PROFILE_TABLE, null);
//        return result;
//    }

}
