package com.seg2105project.mealerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String USER_TABLE = "USER_TABLE";
    private static final String USER_COLUMN_ID = "ID";
    private static final String USER_COLUMN_PASSWORD = "PASSWORD";
    private static final String USER_COLUMN_TYPE = "TYPE";
    private static final String USER_COLUMN_STATUS = "STATUS";
    private static final String USER_COLUMN_ADMIN = "ADMIN";

    private static final String COOK_TABLE = "COOK_TABLE";
    private static final String COOK_COLUMN_ID = "ID";
    private static final String COOK_COLUMN_FIRSTNAME = "FIRSTNAME";
    private static final String COOK_COLUMN_LASTNAME = "LASTNAME";
    private static final String COOK_COLUMN_ADDRESS = "ADDRESS";
    private static final String COOK_COLUMN_OBJECT = "OBJECT";

    private static final String CLIENT_TABLE = "CLIENT_TABLE";
    private static final String CLIENT_COLUMN_ID = "ID";
    private static final String CLIENT_COLUMN_FIRSTNAME = "FIRSTNAME";
    private static final String CLIENT_COLUMN_LASTNAME = "LASTNAME";
    private static final String CLIENT_COLUMN_ADDRESS = "ADDRESS";
    private static final String CLIENT_COLUMN_CREDITCARDNUMBER = "CREDITCARDNUMBER";
    private static final String CLIENT_COLUMN_CREDITCARDDATE = "CREDITCARDDATE";
    private static final String CLIENT_COLUMN_OBJECT = "OBJECT";

    private static final String COMPLAINT_TABLE = "COMPLAINT_TABLE";
    private static final String COMPLAINT_COLUMN_ID = "ID";
    private static final String COMPLAINT_COLUMN_CLIENT = "CLIENT";
    private static final String COMPLAINT_COLUMN_COOK = "COOK";
    private static final String COMPLAINT_COLUMN_REASON = "REASON";
    private static final String COMPLAINT_COLUMN_STATUS = "STATUS";
    private static final String COMPLAINT_COLUMN_ORDERID = "ORDERID";

    private static final String MEAL_TABLE = "MEAL_TABLE";
    private static final String MEAL_COLUMN_ID = "ID";
    private static final String MEAL_COLUMN_TYPE = "TYPE";
    private static final String MEAL_COLUMN_CUISINE = "CUISINE";
    private static final String MEAL_COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String MEAL_COLUMN_PRICE = "PRICE";
    private static final String MEAL_COLUMN_OFFERED = "OFFERED";
    private static final String MEAL_COLUMN_OBJECT = "OBJECT";

    private static final String CUISINE_TABLE = "CUISINE_TABLE";
    private static final String CUISINE_COLUMN_ID = "ID";
    private static final String CUISINE_COLUMN_COUNT = "COUNT";

    private static final String ORDER_TABLE = "ORDER_TABLE";
    private static final String ORDER_COLUMN_ID = "ID";
    private static final String ORDER_COLUMN_CLIENT = "CLIENT";
    private static final String ORDER_COLUMN_COOK = "COOK";
    private static final String ORDER_COLUMN_MEAL = "MEAL";
    private static final String ORDER_COLUMN_STATUS = "STATUS";
    private static final String ORDER_COLUMN_RATING = "RATING";
    private static final String ORDER_COLUMN_COMPLAINT = "COMPLAINT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableStatement = "CREATE TABLE " + USER_TABLE + " (" + USER_COLUMN_ID + " TEXT PRIMARY KEY, "
                + USER_COLUMN_PASSWORD + " TEXT, "
                + USER_COLUMN_TYPE + " TEXT, "
                + USER_COLUMN_STATUS + " TEXT)";

        String createCookTableStatement = "CREATE TABLE " + COOK_TABLE + " (" + COOK_COLUMN_ID + " TEXT PRIMARY KEY, "
                + COOK_COLUMN_FIRSTNAME + " TEXT, "
                + COOK_COLUMN_LASTNAME + " TEXT, "
                + COOK_COLUMN_ADDRESS + " TEXT, "
                + COOK_COLUMN_OBJECT + " BLOB)";

        String createClientTableStatement = "CREATE TABLE " + CLIENT_TABLE + " (" + CLIENT_COLUMN_ID + " TEXT PRIMARY KEY, "
                + CLIENT_COLUMN_FIRSTNAME + " TEXT, "
                + CLIENT_COLUMN_LASTNAME + " TEXT, "
                + CLIENT_COLUMN_ADDRESS + " TEXT, "
                + CLIENT_COLUMN_CREDITCARDNUMBER + " TEXT, "
                + CLIENT_COLUMN_CREDITCARDDATE + " TEXT, "
                + CLIENT_COLUMN_OBJECT + " BLOB)";

        String createComplaintTableStatement = "CREATE TABLE " + COMPLAINT_TABLE + " (" + COMPLAINT_COLUMN_ID + " TEXT PRIMARY KEY, "
                + COMPLAINT_COLUMN_CLIENT + " TEXT, "
                + COMPLAINT_COLUMN_COOK + " TEXT, "
                + COMPLAINT_COLUMN_REASON + " TEXT, "
                + COMPLAINT_COLUMN_STATUS + " TEXT, "
                + COMPLAINT_COLUMN_ORDERID + " TEXT)";

        String createMealTableStatement = "CREATE TABLE " + MEAL_TABLE + " (" + MEAL_COLUMN_ID + " TEXT PRIMARY KEY, "
                + MEAL_COLUMN_TYPE + " TEXT, "
                + MEAL_COLUMN_CUISINE + " TEXT, "
                + MEAL_COLUMN_DESCRIPTION + " TEXT, "
                + MEAL_COLUMN_PRICE + " REAL, "
                + MEAL_COLUMN_OFFERED + " TEXT, "
                + MEAL_COLUMN_OBJECT + " BLOB)";

        String creatCuisineTableStatement = "CREATE TABLE " + CUISINE_TABLE + " (" + CUISINE_COLUMN_ID + " TEXT PRIMARY KEY, "
                + CUISINE_COLUMN_COUNT + " INTEGER)";

        String createOrderTableStatement = "CREATE TABLE " + ORDER_TABLE + " (" + ORDER_COLUMN_ID + " TEXT PRIMARY KEY, "
                + ORDER_COLUMN_CLIENT + " TEXT, "
                + ORDER_COLUMN_COOK + " TEXT, "
                + ORDER_COLUMN_MEAL + " TEXT, "
                + ORDER_COLUMN_STATUS + " TEXT, "
                + ORDER_COLUMN_RATING + " INT, "
                + ORDER_COLUMN_COMPLAINT + " TEXT)";

        db.execSQL(createUserTableStatement);
        db.execSQL(createCookTableStatement);
        db.execSQL(createClientTableStatement);
        db.execSQL(createComplaintTableStatement);
        db.execSQL(createMealTableStatement);
        db.execSQL(creatCuisineTableStatement);
        db.execSQL(createOrderTableStatement);

        ContentValues cv = new ContentValues();
        cv.put(USER_COLUMN_ID, "admin");
        cv.put(USER_COLUMN_PASSWORD, "admin");
        cv.put(USER_COLUMN_TYPE, USER_COLUMN_ADMIN);
        db.insert(USER_TABLE, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}