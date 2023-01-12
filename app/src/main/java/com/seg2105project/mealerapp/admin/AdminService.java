package com.seg2105project.mealerapp.admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seg2105project.mealerapp.DatabaseHelper;
import com.seg2105project.mealerapp.client.ClientService;
import com.seg2105project.mealerapp.cook.Cook;
import com.seg2105project.mealerapp.cook.CookService;
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.meal.MealService;
import com.seg2105project.mealerapp.user.User;
import com.seg2105project.mealerapp.user.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminService extends DatabaseHelper {

    private static final String USER_TABLE = "USER_TABLE";
    private static final String COOK_TABLE = "COOK_TABLE";
    private static final String CLIENT_TABLE = "CLIENT_TABLE";
    private static final String MEAL_TABLE = "MEAL_TABLE";

    private static final String COMPLAINT_TABLE = "COMPLAINT_TABLE";
    private static final String COMPLAINT_COLUMN_ID = "ID";
    private static final String COMPLAINT_COLUMN_CLIENT = "CLIENT";
    private static final String COMPLAINT_COLUMN_COOK = "COOK";
    private static final String COMPLAINT_COLUMN_REASON = "REASON";
    private static final String COMPLAINT_COLUMN_STATUS = "STATUS";
    private static final String COMPLAINT_COLUMN_ORDERID = "ORDERID";

    UserService userService;
    CookService cookService;
    ClientService clientService;
    MealService mealService;

    public AdminService(@Nullable Context context) {
        super(context);
        this.userService = new UserService(context);
        this.cookService = new CookService(context);
        this.clientService = new ClientService(context);
        this.mealService = new MealService(context);
    }

    public void clearComplaintTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + COMPLAINT_TABLE);
        db.close();
    }

    public void clearCookTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + COOK_TABLE);
        db.close();
    }

    public void clearClientTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CLIENT_TABLE);
        db.close();
    }

    public void clearUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + USER_TABLE);

        ContentValues cv = new ContentValues();
        cv.put("ID", "admin");
        cv.put("PASSWORD", "admin");
        cv.put("TYPE", "ADMIN");
        db.insert(USER_TABLE, null, cv);

        db.close();
    }

    public void clearMealTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MEAL_TABLE);
        db.close();
    }

    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete(USER_TABLE, "ID=?", new String[]{username});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteCook(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete(COOK_TABLE, "ID=?", new String[]{username});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteClient(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete(CLIENT_TABLE, "ID=?", new String[]{username});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean suspendUserTemporarily(String username) throws IOException, ClassNotFoundException {
        User user = userService.getUser(username);
        Cook cook = cookService.getCook(username);

        boolean isAllMealsUnoffered = true;

        for(String meal : cook.getActiveMeals()){
            Meal activeMeal = mealService.getMeal(meal);

            boolean isMealUnoffered = activeMeal.unOfferCook(username);
            boolean isMealUpdated = mealService.updateMeal(activeMeal);

            isAllMealsUnoffered = isMealUnoffered && isMealUpdated;
        }

        user.setStatus(String.valueOf(System.currentTimeMillis() + 172800000));

        return userService.updateUser(user) && isAllMealsUnoffered;
    }

    public boolean suspendUserIndefinitely(String username) throws IOException, ClassNotFoundException {
        User user = userService.getUser(username);
        Cook cook = cookService.getCook(username);

        boolean isAllMealsUnoffered = true;

        for(String meal : cook.getActiveMeals()){
            Meal activeMeal = mealService.getMeal(meal);

            boolean isMealUnoffered = activeMeal.unOfferCook(username);
            boolean isMealUpdated = mealService.updateMeal(activeMeal);

            isAllMealsUnoffered = isMealUnoffered && isMealUpdated;
        }

        user.setStatus("SUSPENDED");

        return userService.updateUser(user) && isAllMealsUnoffered;
    }

    public boolean unSuspendUser(String username) throws IOException, ClassNotFoundException {
        User user = userService.getUser(username);
        Cook cook = cookService.getCook(username);

        boolean isAllMealsReoffered = true;

        for(String meal : cook.getActiveMeals()){
            Meal activeMeal = mealService.getMeal(meal);

            boolean isMealReoffered = activeMeal.reOfferCook(username);
            boolean isMealUpdated = mealService.updateMeal(activeMeal);

            isAllMealsReoffered = isMealReoffered && isMealUpdated;
        }

        user.setStatus("ACTIVE");

        return userService.updateUser(user) && isAllMealsReoffered;
    }

    public boolean addComplaint(@NonNull Complaint complaint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COMPLAINT_COLUMN_ID, complaint.getID());
        cv.put(COMPLAINT_COLUMN_CLIENT, complaint.getClient());
        cv.put(COMPLAINT_COLUMN_COOK, complaint.getCook());
        cv.put(COMPLAINT_COLUMN_REASON, complaint.getReason());
        cv.put(COMPLAINT_COLUMN_STATUS, "UNREVIEWED");
        cv.put(COMPLAINT_COLUMN_ORDERID, complaint.getOrder());

        long insert = db.insert(COMPLAINT_TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteComplaint(@NonNull Complaint complaint) {
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete(COMPLAINT_TABLE, "ID=?", new String[]{complaint.getID()});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean deleteComplaint(String complaint) {
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete(COMPLAINT_TABLE, "ID=?", new String[]{complaint});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean resolveComplaint(String complaintID) {
        Complaint complaint = getComplaint(complaintID);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COMPLAINT_COLUMN_ID, complaint.getID());
        cv.put(COMPLAINT_COLUMN_CLIENT, complaint.getClient());
        cv.put(COMPLAINT_COLUMN_COOK, complaint.getCook());
        cv.put(COMPLAINT_COLUMN_REASON, complaint.getReason());
        cv.put(COMPLAINT_COLUMN_STATUS, "RESOLVED");

        long insert = db.update(COMPLAINT_TABLE, cv, COMPLAINT_COLUMN_ID + " = ?", new String[]{complaintID});
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Complaint getComplaint(String complaintID) {
        Complaint complaint = null;

        String query = "SELECT * FROM " + COMPLAINT_TABLE + " where " + COMPLAINT_COLUMN_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{complaintID});

        if (cursor.moveToFirst()) {
            String ID1 = cursor.getString(0);
            String client1 = cursor.getString(1);
            String cook1 = cursor.getString(2);
            String reason1 = cursor.getString(3);
            String status1 = cursor.getString(4);
            String order = cursor.getString(5);
            complaint = new Complaint(ID1, client1, cook1, reason1, status1, order);
        }

        db.close();
        cursor.close();

        return complaint;
    }

    public List<Complaint> getAllComplaints() {
        List<Complaint> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + COMPLAINT_TABLE + " WHERE " + COMPLAINT_COLUMN_STATUS + " NOT LIKE 'RESOLVED'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String ID = cursor.getString(0);
                String client = cursor.getString(1);
                String cook = cursor.getString(2);
                String reason = cursor.getString(3);
                String status = cursor.getString(4);
                String order = cursor.getString(5);
                Complaint complaint = new Complaint(ID, client, cook, reason, status, order);

                returnList.add(complaint);

            } while (cursor.moveToNext());
        } else {
        }

        db.close();
        cursor.close();

        return returnList;
    }

    private void complaintStimulator(String username, Complaint complaint) throws IOException {
        if (!userService.existsUser(username)) {
            User user = new User(username, "test", COOK_TABLE, "ACTIVE");
            userService.addUser(user);
            Cook cook = new Cook("test", "test", username, "test", "test");
            cookService.addCook(cook);
            addComplaint(complaint);
        }
    }

    public void stimulateComplaints() throws IOException {
        Complaint complaint1 = new Complaint("billyjean@gmail.com", "testcook1", "Took cold");
        Complaint complaint2 = new Complaint("samsmith@outlook.com", "testcook2", "Too hot");
        Complaint complaint3 = new Complaint("janedoe@hotmail.com", "testcook3", "Forgot drink");
        Complaint complaint4 = new Complaint("abdulhakim@outlook.com", "testcook4", "Didn't recieve order");
        Complaint complaint5 = new Complaint("momo@gmail.com", "testcook5", "Gave me food poisioning.");

        complaintStimulator("testcook2", complaint2);
        complaintStimulator("testcook3", complaint3);
        complaintStimulator("testcook4", complaint4);
        complaintStimulator("testcook5", complaint5);

        if (!userService.existsUser("testcook1")) {
            addComplaint(complaint1);
        }
    }
}