package com.seg2105project.mealerapp.cook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seg2105project.mealerapp.DatabaseHelper;
import com.seg2105project.mealerapp.Serializer;
import com.seg2105project.mealerapp.meal.Cuisine;
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.meal.MealService;
import com.seg2105project.mealerapp.order.Order;
import com.seg2105project.mealerapp.order.OrderService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CookService extends DatabaseHelper {
    private static final String TABLE = "COOK_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_FIRSTNAME = "FIRSTNAME";
    private static final String COLUMN_LASTNAME = "LASTNAME";
    private static final String COLUMN_ADDRESS = "ADDRESS";
    private static final String COLUMN_OBJECT = "OBJECT";

    MealService mealService;
    OrderService orderService;

    public CookService(@Nullable Context context) {
        super(context);
        this.mealService = new MealService(context);
        this.orderService = new OrderService(context);
    }

    public boolean addCook(@NonNull Cook cook) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, cook.getEmail());
        cv.put(COLUMN_FIRSTNAME, cook.getFirst());
        cv.put(COLUMN_LASTNAME, cook.getLast());
        cv.put(COLUMN_ADDRESS, cook.getAddress());
        cv.put(COLUMN_OBJECT, Serializer.serialize(cook));

        long insert = db.insert(TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateCook(@NonNull Cook cook) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, cook.getEmail());
        cv.put(COLUMN_FIRSTNAME, cook.getFirst());
        cv.put(COLUMN_LASTNAME, cook.getLast());
        cv.put(COLUMN_ADDRESS, cook.getAddress());
        cv.put(COLUMN_OBJECT, Serializer.serialize(cook));

        long insert = db.update(TABLE, cv, COLUMN_ID + " = ?", new String[]{cook.getEmail()});
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateCook(@NonNull String cookID) throws IOException, ClassNotFoundException {
        Cook cook = getCook(cookID);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, cook.getEmail());
        cv.put(COLUMN_FIRSTNAME, cook.getFirst());
        cv.put(COLUMN_LASTNAME, cook.getLast());
        cv.put(COLUMN_ADDRESS, cook.getAddress());
        cv.put(COLUMN_OBJECT, Serializer.serialize(cook));

        long insert = db.update(TABLE, cv, COLUMN_ID + " = ?", new String[]{cook.getEmail()});
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cook getCook(@NonNull String username) throws IOException, ClassNotFoundException {
        Cook cook = null;

        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            cook = Serializer.deserialize(cursor.getBlob(4));
        }

        db.close();
        cursor.close();

        return cook;
    }

    public boolean unOfferMeal(@NonNull String mealName, @NonNull String cookID) throws IOException, ClassNotFoundException {
        Cook cook = getCook(cookID);
        if (!mealService.existsMeal(mealName)) {
            return false;
        }

        Meal meal = mealService.getMeal(mealName);

        boolean isCookUnOffered = cook.unOfferMeal(mealName);
        boolean isCookUpdated = updateCook(cook);
        boolean isMealUnoffered = meal.unOfferCook(cookID);
        boolean isMealUpdated = mealService.updateMeal(meal);

        return isCookUnOffered && isCookUpdated && isMealUnoffered && isMealUpdated;
    }

    public boolean existsMeal(@NonNull String mealName) {
        return mealService.existsMeal(mealName);
    }


    public boolean reOfferMeal(@NonNull String mealName, @NonNull String cookID) throws IOException, ClassNotFoundException {
        Cook cook = getCook(cookID);
        if (!mealService.existsMeal(mealName)) {
            return false;
        }

        Meal meal = mealService.getMeal(mealName);

        boolean isCookUnOffered = cook.reOfferMeal(mealName);
        boolean isCookUpdated = updateCook(cook);
        boolean isMealReoffered = meal.reOfferCook(cookID);
        boolean isMealUpdated = mealService.updateMeal(meal);

        return isCookUnOffered && isCookUpdated && isMealReoffered && isMealUpdated;
    }

    public boolean deleteMeal(@NonNull String mealName, @NonNull String cookID) throws IOException, ClassNotFoundException {
        Cook cook = getCook(cookID);

        if (!mealService.existsMeal(mealName)) {
            return false;
        }

        Meal meal = mealService.getMeal(mealName);

        Cuisine cuisine = mealService.getCuisine(meal.getCuisineType());

        boolean isDeletedMeal = meal.deleteCook(cookID);
        boolean isDeletedCook = cook.deleteMeal(mealName);

        boolean isMealUpdated = mealService.updateMeal(meal);

        if (meal.getCount() <= 0) {
            mealService.deleteMeal(meal);
        }
        else {}

        if(cuisine.getCount() == 1){
            mealService.deleteCuisine(cuisine.getCusineName());
        }
        else {
            cuisine.decrementCount();
            mealService.updateCuisine(cuisine);
        }

        boolean isCookUpdated = updateCook(cook);

        return isDeletedCook && isDeletedMeal && isCookUpdated && isMealUpdated;
    }

    public boolean addMeal(@NonNull Meal meal) throws IOException {
        if (mealService.existsMeal(meal.getMealName())) {
            return false;
        }

        if(mealService.existsCuisine(meal.getCuisineType())){
            Cuisine cuisine = mealService.getCuisine(meal.getCuisineType());
            cuisine.incrementCount();
            mealService.updateCuisine(cuisine);
        } else {
            Cuisine cuisine = new Cuisine(meal.getCuisineType());
            mealService.addCuisine(cuisine);
        }



        return mealService.addMeal(meal);
    }

    public boolean addCookToMeal(@NonNull Meal meal) throws IOException, ClassNotFoundException {
        if (!mealService.existsMeal(meal.getMealName())) {
            return false;
        }

        return mealService.updateMeal(meal);
    }

    public List<Cook> getAllCooks() throws IOException, ClassNotFoundException {
        List<Cook> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Cook cook = Serializer.deserialize(cursor.getBlob(5));

                returnList.add(cook);

            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return returnList;
    }

    public boolean approveOrder(@NonNull Order order){
        order.setStatus("APPROVED");
        return orderService.updateOrder(order);
    }

    public boolean rejectOrder(@NonNull Order order){
        order.setStatus("REJECTED");
        return orderService.updateOrder(order);
    }
}