package com.seg2105project.mealerapp.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.seg2105project.mealerapp.DatabaseHelper;

public class UserService extends DatabaseHelper {

    private static final String TABLE = "USER_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_PASSWORD = "PASSWORD";
    private static final String COLUMN_TYPE = "TYPE";
    private static final String COLUMN_STATUS = "STATUS";
    private static final String COLUMN_ADMIN = "ADMIN";

    public UserService(@Nullable Context context) {
        super(context);
    }

    public boolean addUser(User user, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, user.getID());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_STATUS, "ACTIVE");

        long insert = db.insert(TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, user.getID());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_TYPE, user.getType());
        cv.put(COLUMN_STATUS, "ACTIVE");

        long insert = db.insert(TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, user.getID());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_TYPE, user.getType());
        cv.put(COLUMN_STATUS, user.getStatus());

        long insert = db.update(TABLE, cv, COLUMN_ID + " = ?", new String[]{user.getID()});
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validateUser(String username, String password) {
        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ? and " + COLUMN_PASSWORD + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        String[] strings = {username, password};
        Cursor cursor = db.rawQuery(query, strings);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return result;
    }

    public boolean existsUser(String username) {
        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return result;
    }

    public String getUserType(String username) {
        String type = null;

        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{username});


        if (cursor.moveToFirst()) {
            type = (String) cursor.getString(2);
        }

        cursor.close();
        db.close();
        return type;
    }

    public String getUserStatus(String username) {
        String type = null;

        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{username});


        if (cursor.moveToFirst()) {
            type = (String) cursor.getString(3);
        }

        cursor.close();
        db.close();
        return type;
    }

    public User getUser(String username) {
        User user = null;

        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            String username1 = cursor.getString(0);
            String password1 = cursor.getString(1);
            String type1 = cursor.getString(2);
            String status1 = cursor.getString(3);
            user = new User(username1, password1, type1, status1);
        }

        db.close();
        cursor.close();

        return user;
    }
}