package com.seg2105project.mealerapp.meal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seg2105project.mealerapp.DatabaseHelper;
import com.seg2105project.mealerapp.Serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MealService extends DatabaseHelper {
    private static final String TABLE = "MEAL_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TYPE = "TYPE";
    private static final String COLUMN_CUISINE = "CUISINE";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String COLUMN_PRICE = "PRICE";
    private static final String COLUMN_OFFERED = "OFFERED";
    private static final String COLUMN_OBJECT = "OBJECT";

    private static final String CUISINE_TABLE = "CUISINE_TABLE";
    private static final String CUISINE_COLUMN_ID = "ID";
    private static final String CUISINE_COLUMN_COUNT = "COUNT";

    public MealService(@Nullable Context context) {
        super(context);
    }

    public boolean addMeal(@NonNull Meal meal) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, meal.getMealName());
        cv.put(COLUMN_TYPE, meal.getMealType());
        cv.put(COLUMN_CUISINE, meal.getCuisineType());
        cv.put(COLUMN_DESCRIPTION, meal.getDescription());
        cv.put(COLUMN_PRICE, meal.getPrice());
        cv.put(COLUMN_OFFERED, meal.isOffered());
        cv.put(COLUMN_OBJECT, Serializer.serialize(meal));

        long insert = db.insert(TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteMeal(@NonNull Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete(TABLE, "ID=?", new String[]{meal.getMealName()});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteMeal(@NonNull String meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete(TABLE, "ID=?", new String[]{meal});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Meal getMeal(@NonNull String mealName) throws IOException, ClassNotFoundException {
        Meal meal = null;

        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{mealName});

        if (cursor.moveToFirst()) {
            meal = Serializer.deserialize(cursor.getBlob(6));
        }

        db.close();
        cursor.close();

        return meal;
    }

    public boolean updateMeal(@NonNull Meal meal) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, meal.getMealName());
        cv.put(COLUMN_TYPE, meal.getMealType());
        cv.put(COLUMN_CUISINE, meal.getCuisineType());
        cv.put(COLUMN_DESCRIPTION, meal.getDescription());
        cv.put(COLUMN_PRICE, meal.getPrice());
        cv.put(COLUMN_OFFERED, meal.isOffered());
        cv.put(COLUMN_OBJECT, Serializer.serialize(meal));

        long insert = db.update(TABLE, cv, COLUMN_ID + " = ?", new String[]{meal.getMealName()});
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean existsMeal(@NonNull String meal) {
        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{meal});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return result;
    }

    public boolean isOffered(@NonNull String meal) {
        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_ID + " = ? " + " and " + COLUMN_OFFERED + " LIKE " + 1;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{meal});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return result;
    }

    public List<String> getCooks(@NonNull String mealName) throws IOException, ClassNotFoundException {
        if (!existsMeal(mealName)) {
            return null;
        }

        Meal meal = getMeal(mealName);

        return meal.getCooks();
    }

    public boolean existsCuisine(@NonNull String cuisineName) {
        String query = "SELECT * FROM " + CUISINE_TABLE + " where " + CUISINE_COLUMN_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{cuisineName});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return result;
    }

    public boolean addCuisine(@NonNull Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUISINE_COLUMN_ID, cuisine.getCusineName());
        cv.put(CUISINE_COLUMN_COUNT, cuisine.getCount());

        long insert = db.insert(CUISINE_TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cuisine getCuisine(@NonNull String cuisineName) {
        Cuisine cuisine = null;

        String query = "SELECT * FROM " + CUISINE_TABLE + " where " + CUISINE_COLUMN_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{cuisineName});

        if (cursor.moveToFirst()) {
            String cuisineID = cursor.getString(0);
            int cuisineCount = cursor.getInt(1);

            cuisine = new Cuisine(cuisineID, cuisineCount);
        }

        db.close();
        cursor.close();

        return cuisine;
    }

    public boolean updateCuisine(@NonNull Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUISINE_COLUMN_ID, cuisine.getCusineName());
        cv.put(CUISINE_COLUMN_COUNT, cuisine.getCount());

        long insert = db.update(CUISINE_TABLE, cv, CUISINE_COLUMN_ID + " = ?", new String[]{cuisine.getCusineName()});
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteCuisine(@NonNull String cuisineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete(CUISINE_TABLE, "ID=?", new String[]{cuisineName});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getAllCuisines() {
        List<String> list = new ArrayList<>();
        String query = "SELECT * FROM " + CUISINE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                String cuisine = cursor.getString(0);
                list.add(cuisine.substring(0,1).toUpperCase() + cuisine.substring(1).toLowerCase());

            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return list;
    }

    public List<Meal> getAllMeals() throws IOException, ClassNotFoundException {
        List<Meal> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_OFFERED + " LIKE " + 1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Meal meal = Serializer.deserialize(cursor.getBlob(6));

                list.add(meal);

            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return list;
    }

    public List<Meal> getAllCuisineMeals(@NonNull String cuisine) throws IOException, ClassNotFoundException {
        List<Meal> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_CUISINE + " LIKE '" + cuisine + "' and " + COLUMN_OFFERED + " LIKE " + 1;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Meal meal = Serializer.deserialize(cursor.getBlob(6));

                list.add(meal);

            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return list;
    }

    public List<Meal> getAllMealType(@NonNull String type) throws IOException, ClassNotFoundException {
        List<Meal> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_TYPE + " LIKE '" + type + "' and " + COLUMN_OFFERED + " LIKE " + 1;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Meal meal = Serializer.deserialize(cursor.getBlob(6));

                list.add(meal);

            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return list;
    }

    public List<Meal> getAllOfferedMealsFromCook(List<String> meals) throws IOException, ClassNotFoundException {
        List<Meal> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_OFFERED + " LIKE " + 1;
//        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                if (meals.contains(cursor.getString(0))) {
                    Meal meal = Serializer.deserialize(cursor.getBlob(6));
                    list.add(meal);
                }
            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return list;
    }

    public List<Meal> getAllUnOfferedMealsFromCook(List<String> meals) throws IOException, ClassNotFoundException {
        List<Meal> list = new ArrayList<>();

//        String query = "SELECT * FROM " + TABLE + " where " + COLUMN_OFFERED + " LIKE " + 0;
        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                if (meals.contains(cursor.getString(0))) {
                    Meal meal = Serializer.deserialize(cursor.getBlob(6));
                    list.add(meal);
                }
            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return list;
    }

    public List<Meal> getAllMealsFromCook(List<String> meals) throws IOException, ClassNotFoundException {
        List<Meal> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                if (meals.contains(cursor.getString(0))) {
                    Meal meal = Serializer.deserialize(cursor.getBlob(6));
                    list.add(meal);
                }
            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return list;
    }
}