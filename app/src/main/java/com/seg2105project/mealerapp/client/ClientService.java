package com.seg2105project.mealerapp.client;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seg2105project.mealerapp.DatabaseHelper;
import com.seg2105project.mealerapp.Serializer;
import com.seg2105project.mealerapp.admin.AdminService;
import com.seg2105project.mealerapp.admin.Complaint;
import com.seg2105project.mealerapp.order.Order;
import com.seg2105project.mealerapp.order.OrderService;

import java.io.IOException;
import java.util.List;

public class ClientService extends DatabaseHelper {

    private static final String TABLE = "CLIENT_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_FIRSTNAME = "FIRSTNAME";
    private static final String COLUMN_LASTNAME = "LASTNAME";
    private static final String COLUMN_ADDRESS = "ADDRESS";
    private static final String COLUMN_CREDITCARDNUMBER = "CREDITCARDNUMBER";
    private static final String COLUMN_CREDITCARDDATE = "CREDITCARDDATE";
    private static final String COLUMN_OBJECT = "OBJECT";

    public ClientService(@Nullable Context context) {
        super(context);
    }

    public boolean addClient(@NonNull Client client) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, client.getEmail());
        cv.put(COLUMN_FIRSTNAME, client.getFirst());
        cv.put(COLUMN_LASTNAME, client.getLast());
        cv.put(COLUMN_ADDRESS, client.getAddress());
        cv.put(COLUMN_CREDITCARDNUMBER, client.getCreditCardNumber());
        cv.put(COLUMN_CREDITCARDDATE, client.getCreditCardDate());
        cv.put(COLUMN_OBJECT, Serializer.serialize(client));


        long insert = db.insert(TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Client getClient(String username) throws IOException, ClassNotFoundException {
        Client client = null;

        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            client = Serializer.deserialize(cursor.getBlob(6));
        }

        db.close();
        cursor.close();

        return client;
    }
}