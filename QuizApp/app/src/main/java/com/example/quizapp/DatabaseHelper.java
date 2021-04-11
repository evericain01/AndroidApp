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

    /**
     * Constructor that takes in the current context.
     *
     * @param context The context.
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the user and profile table on create.
     *
     * @param db The database that will contain the user and profile tables.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_PROFILE);
    }

    /**
     * Drops all tables of the current database.
     *
     * @param db The database.
     * @param oldVersion Previous database.
     * @param newVersion New datanase.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        onCreate(db);
    }

    /**
     * Adds a user into the database in the user table.
     *
     * @param username The desired username.
     * @param password The desired password
     * @return True (if added). False (if not added).
     */
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

    /**
     * Adds a profile in the database in the profile table.
     *
     * @param userID The user ID.
     * @param firstName The desired first name.
     * @param lastName The desired last name.
     * @return True (if added). False (if not added).
     */
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

    /**
     * Checks if the login is valid.
     *
     * @param username The username to be checked.
     * @param password The password to be checked.
     * @return True (if logged in). False (if not logged in).
     */
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

    /**
     * Generates a hashed password.
     * @reference https://www.tutorialspoint.com/how-to-encrypt-password-and-store-in-android-sqlite
     *
     *
     * @param password The desired password to hash.
     * @return The password hash.
     */
    public static final String md5(final String password) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(password.getBytes());
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

    /**
     * Gets the current user ID.
     *
     * @param username The username of the current user.
     * @return The current user ID.
     */
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

    /**
     * Gets the first and last name of the current user.
     *
     * @param userID The current user ID.
     * @return The first and last name of the user.
     */
    public String getFirstAndLastName(String userID) {
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

    /**
     * Gets the total experience points of the current user.
     *
     * @param userID The current user ID.
     * @return The total experience points.
     */
    public String getExperiencePoints(String userID) {
        String result = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM profile WHERE user_id=" + userID, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex("experience_points"));
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * Sets the experience points of a user.
     *
     * @param userID The user.
     * @param exp The desired experience points to be set.
     * @return True.
     */
    public boolean setExperiencePoints(String userID, String exp) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strFilter = "user_id=" + userID;
        ContentValues args = new ContentValues();
        args.put(COL_EXPERIENCE_POINTS, exp);
        db.update("profile", args, strFilter, null);
        return true;
    }

}
