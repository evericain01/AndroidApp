package com.example.quizapp.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="QuizAppDB.db";
    public static final int DATABASE_VERSION = 7;

    public static final String USER_TABLE = "user";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";

    public static final String PROFILE_TABLE = "profile";
    public static final String COL_PROFILE_ID = "profile_id";
    public static final String COL_USER_ID_PROFILE = "user_id";
    public static final String COL_FIRST_NAME = "first_name";
    public static final String COL_LAST_NAME = "last_name";
    public static final String COL_QUEUE_LIST = "queue_list";
    public static final String COL_EXPERIENCE_POINTS = "experience_points";

    public static final String QUEUE_LIST_TABLE = "queue_list";
    public static final String COL_QUEUE_LIST_ID = "queue_list_id";
    public static final String COL_USER_ID_QUEUE_LIST = "user_id";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_CATEGORY = "category";
    public static final String COL_DIFFICULTY = "difficulty";
    public static final String COL_TYPE = "type";
    public static final String COL_POSITION_ON_RECYCLER = "position_on_recycler";

    public static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + USER_TABLE + "("
            + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USERNAME + " TEXT NOT NULL UNIQUE, "
            + COL_PASSWORD + " TEXT NOT NULL "
            + ");";

    public static final String SQL_CREATE_TABLE_PROFILE = "CREATE TABLE " + PROFILE_TABLE + "("
            + COL_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USER_ID_PROFILE + " INTEGER NOT NULL, "
            + COL_FIRST_NAME + " TEXT NOT NULL, "
            + COL_LAST_NAME + " TEXT NOT NULL, "
            + COL_QUEUE_LIST + " TEXT, "
            + COL_EXPERIENCE_POINTS + " INTEGER DEFAULT 0, "
            + "FOREIGN KEY (user_id) REFERENCES " + USER_TABLE + " (user_id) "
            + ");";

    public static final String SQL_CREATE_TABLE_QUEUE_LIST = "CREATE TABLE " + QUEUE_LIST_TABLE + "("
            + COL_QUEUE_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USER_ID_QUEUE_LIST + " INTEGER NOT NULL, "
            + COL_AMOUNT + " INTEGER, "
            + COL_CATEGORY + " INTEGER, "
            + COL_DIFFICULTY + " TEXT, "
            + COL_TYPE + " TEXT, "
            + COL_POSITION_ON_RECYCLER + " INTEGER, "
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
        db.execSQL(SQL_CREATE_TABLE_QUEUE_LIST);
    }

    /**
     * Drops all tables of the current database.
     *
     * @param db The database.
     * @param oldVersion Previous database.
     * @param newVersion New database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QUEUE_LIST_TABLE);
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
        return result != -1;
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

        boolean valid;
        valid = cursor.getCount() > 0;

        db.close();
        cursor.close();

        return valid;
    }

    /**
     * Updates the current user's first name.
     *
     * @param id The user ID.
     * @param newFirstName The new first name.
     */
    public void updateFirstName(String id, String newFirstName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", newFirstName);
        db.update(PROFILE_TABLE, values, "user_id = ?", new String[]{id});
    }

    /**
     * Updates the current user's last name.
     *
     * @param id The user ID.
     * @param newLastName The new last name.
     */
    public void updateLastName(String id, String newLastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("last_name", newLastName);
        db.update(PROFILE_TABLE, values, "user_id = ?", new String[]{id});
    }

    /**
     * Updates the current user's password.
     *
     * @param id The user ID.
     * @param newPassword The new password.
     */
    public void updatePassword(String id, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        newPassword = md5(newPassword);
        values.put("password", newPassword);
        db.update(USER_TABLE, values, "user_id = ?", new String[]{id});
    }

    /**
     * Checks if the user's old password is correct.
     *
     * @param oldPassword The old password.
     * @return True (if correct). False (if not correct).
     */
    public boolean checkOldPassword(String oldPassword) {
        SQLiteDatabase db = getWritableDatabase();
        oldPassword = md5(oldPassword);
        String[] selectionArgs = {oldPassword};
        String[] columns = { COL_USER_ID };
        String selection = COL_PASSWORD + "=?";
        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null , null, null, null);

        boolean valid;
        valid = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return valid;
    }

    /**
     * Generates a hashed password.
     * @reference https://www.tutorialspoint.com/how-to-encrypt-password-and-store-in-android-sqlite
     *
     *
     * @param password The desired password to hash.
     * @return The password hash.
     */
    public static String md5(final String password) {
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
        SQLiteDatabase db = this.getReadableDatabase();

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

        String[] params = new String[]{ userID };

        Cursor cursor = db.rawQuery("SELECT * FROM profile WHERE user_id = ?", params);
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

        String[] params = new String[]{ userID };

        Cursor cursor = db.rawQuery("SELECT * FROM profile WHERE user_id = ?", params);
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
     */
    public void setExperiencePoints(String userID, String exp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EXPERIENCE_POINTS, exp);
        db.update(PROFILE_TABLE, values, "user_id = ?", new String[]{userID});
    }

    /**
     * Adds a quiz into the queue_list table.
     *
     * @param userID The desired user ID.
     * @param amount The amount.
     * @param category The category.
     * @param difficulty The difficulty.
     * @param type The type.
     * @return True (if added). False (if not added).
     */
    public boolean addQuizToQueue(String userID, int amount, int category, String difficulty, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("difficulty", difficulty);
        values.put("amount", amount);
        values.put("category", category);
        values.put("type", type);
        values.put("user_id", userID);

        long result = db.insert("queue_list", null, values);
        return result != -1;
    }

    /**
     * Converts the Queue List table into a List containing QuestionHandler objects.
     *
     * @param userID The desired user ID.
     * @return A List containing QuestionHandler objects.
     */
    public List<QuestionHandler> convertQueueTableToList(String userID) {
        List<QuestionHandler> list = new ArrayList<QuestionHandler>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM queue_list WHERE user_id=" + userID, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(COL_QUEUE_LIST_ID);
            int amount = cursor.getColumnIndex(COL_AMOUNT);
            int category = cursor.getColumnIndex(COL_CATEGORY);
            int difficulty = cursor.getColumnIndex(COL_DIFFICULTY);
            int type = cursor.getColumnIndex(COL_TYPE);

            do {
                int thisId = cursor.getInt(id);
                int thisAmount = cursor.getInt(amount);
                int thisCategory = cursor.getInt(category);
                String thisDifficulty = cursor.getString(difficulty);
                String thisType = cursor.getString(type);
                QuestionHandler data = new QuestionHandler(thisId, thisAmount, thisCategory, thisDifficulty, thisType);
                list.add(data);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Updates the position of the quiz record (object) from the table (List).
     *
     * @param id The desired user ID.
     * @param positionOnRecyclerView The quiz's position in the recyclerView.
     */
    public void updatePositionOnRecycler(String id, int positionOnRecyclerView) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_POSITION_ON_RECYCLER, positionOnRecyclerView);

        db.update(QUEUE_LIST_TABLE, values, "queue_list_id = ?", new String[]{id});
    }

    /**
     * Deletes a quiz from the the user's queue.
     *
     * @param id The id of the quiz.
     */
    public void deleteQuiz(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(QUEUE_LIST_TABLE, "queue_list_id = ?", new String[]{id});
    }
}