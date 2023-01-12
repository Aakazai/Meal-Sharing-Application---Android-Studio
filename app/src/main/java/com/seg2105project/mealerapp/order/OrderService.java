package com.seg2105project.mealerapp.order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seg2105project.mealerapp.DatabaseHelper;
import com.seg2105project.mealerapp.user.User;

import java.util.ArrayList;
import java.util.List;

public class OrderService extends DatabaseHelper {
    private static final String TABLE = "ORDER_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_CLIENT = "CLIENT";
    private static final String COLUMN_COOK = "COOK";
    private static final String COLUMN_MEAL = "MEAL";
    private static final String COLUMN_STATUS = "STATUS";
    private static final String COLUMN_RATING = "RATING";
    private static final String COLUMN_COMPLAINT = "COMPLAINT";

    public OrderService(@Nullable Context context) {
        super(context);
    }

    public boolean addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, order.getId());
        cv.put(COLUMN_CLIENT, order.getClient());
        cv.put(COLUMN_COOK, order.getCook());
        cv.put(COLUMN_MEAL, order.getMeal());
        cv.put(COLUMN_STATUS, order.getStatus());
        cv.put(COLUMN_RATING, order.getRating());
        cv.put(COLUMN_COMPLAINT, order.getComplaint());

        long insert = db.insert(TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Order getOrder(String ID) {
        Order order = null;

        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{ID});

        if (cursor.moveToFirst()) {
            String id = cursor.getString(0);
            String client = cursor.getString(1);
            String cook = cursor.getString(2);
            String meal = cursor.getString(3);
            String status = cursor.getString(4);
            int rating = cursor.getInt(5);
            String complaint = cursor.getString(6);
            order = new Order(id, client, cook, meal, status, rating, complaint);
        }

        db.close();
        cursor.close();

        return order;
    }

    public boolean updateOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, order.getId());
        cv.put(COLUMN_CLIENT, order.getClient());
        cv.put(COLUMN_COOK, order.getCook());
        cv.put(COLUMN_MEAL, order.getMeal());
        cv.put(COLUMN_STATUS, order.getStatus());
        cv.put(COLUMN_RATING, order.getRating());
        cv.put(COLUMN_COMPLAINT, order.getComplaint());

        long insert = db.update(TABLE, cv, COLUMN_ID + " = ?", new String[]{order.getId()});
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Order> getAllCookOrders(@NonNull String cook) {
        List<Order> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_COOK + " LIKE '" + cook + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String ID = cursor.getString(0);
                String client = cursor.getString(1);
                String cookID = cursor.getString(2);
                String meal = cursor.getString(3);
                String status = cursor.getString(4);
                int rating = cursor.getInt(5);
                String complaint = cursor.getString(6);
                Order order = new Order(ID, client, cookID, meal, status, rating, complaint);

                list.add(order);

            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return list;
    }

    public List<Order> getAllClientOrders(@NonNull String client) {
        List<Order> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_CLIENT + " LIKE '" + client + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String ID = cursor.getString(0);
                String clientID = cursor.getString(1);
                String cookID = cursor.getString(2);
                String meal = cursor.getString(3);
                String status = cursor.getString(4);
                int rating = cursor.getInt(5);
                String complaint = cursor.getString(6);
                Order order = new Order(ID, client, cookID, meal, status, rating, complaint);

                list.add(order);

            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return list;
    }
}
