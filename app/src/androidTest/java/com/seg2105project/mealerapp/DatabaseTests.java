package com.seg2105project.mealerapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.seg2105project.mealerapp.admin.AdminService;
import com.seg2105project.mealerapp.admin.Complaint;
import com.seg2105project.mealerapp.client.Client;
import com.seg2105project.mealerapp.client.ClientService;
import com.seg2105project.mealerapp.cook.Cook;
import com.seg2105project.mealerapp.cook.CookService;
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.meal.MealService;
import com.seg2105project.mealerapp.user.User;
import com.seg2105project.mealerapp.user.UserService;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class DatabaseTests {
    private static final String COOK_TABLE = "COOK_TABLE";
    private static final String CLIENT_TABLE = "CLIENT_TABLE";

    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    AdminService adminService = new AdminService(context);
    UserService userService = new UserService(context);
    CookService cookService = new CookService(context);
    ClientService clientService = new ClientService(context);
    MealService mealService = new MealService(context);

    @Test
    public void useAppContext() {
        assertEquals("com.seg2105project.mealerapp", context.getPackageName());
    }

    @Test
    public void testUserDatabase() {
        User user = new User("testUserDatabase", "test", "test", "test");
        if (userService.existsUser(user.getID())) {
            adminService.deleteUser(user.getID());
        }
        boolean bool = userService.addUser(user, COOK_TABLE);
        assertEquals(bool, userService.existsUser(user.getID()));
    }

    @Test
    public void testCookDatabase() throws IOException {
        User user = new User("testCookDatabase", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");

        if (userService.existsUser(user.getID())) {
            adminService.deleteUser(user.getID());
            adminService.deleteCook(user.getID());
        }

        boolean success = userService.addUser(user, COOK_TABLE) && cookService.addCook(cook);
        assertEquals((true && success), userService.existsUser(user.getID()));
    }

    @Test
    public void testClientDatabase() throws IOException {
        User user = new User("testClientDatabase", "test", "test", "test");
        Client client = new Client("test", "test", user.getID(), "test", "test", "test");

        if (userService.existsUser(user.getID())) {
            adminService.deleteUser(user.getID());
            adminService.deleteClient(user.getID());
        }

        boolean success = userService.addUser(user, CLIENT_TABLE) && clientService.addClient(client);

        assertEquals((true && success), userService.existsUser(user.getID()));
    }

    @Test
    public void testMealDatabase() throws IOException, ClassNotFoundException {
        Meal meal = new Meal("test", "test", "test", "test", 6.66);

        if (mealService.existsMeal("test")) {
            mealService.deleteMeal(meal.getMealName());
        }

        boolean success = mealService.addMeal(meal);
        ;

        assertEquals("test", mealService.getMeal("test").getMealName());
    }


    @Test
    public void testAdminSuspendIndefinitely() throws IOException, ClassNotFoundException {
        User user = new User("testAdminSuspendIndefinitely", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");

        if (userService.existsUser(user.getID())) {
            adminService.deleteUser(user.getID());
            adminService.deleteCook(user.getID());
        }

        boolean success = userService.addUser(user, COOK_TABLE) && cookService.addCook(cook);
        adminService.suspendUserIndefinitely(user.getID());
        boolean isSuspended = userService.getUserStatus(user.getID()).equals("SUSPENDED");

        assertEquals((success && isSuspended), userService.existsUser(user.getID()));
    }

    @Test
    public void testAdminSuspendTemporarily() throws IOException, ClassNotFoundException {
        User user = new User("testAdminSuspendTemporarily", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");

        if (userService.existsUser(user.getID())) {
            adminService.deleteUser(user.getID());
            adminService.deleteCook(user.getID());
        }

        boolean success = userService.addUser(user, COOK_TABLE) && cookService.addCook(cook);
        adminService.suspendUserTemporarily(user.getID());
        boolean isSuspended = (!userService.getUserStatus(user.getID()).equals("SUSPENDED"))
                && (!userService.getUserStatus(user.getID()).equals("ACTIVE"));

        assertEquals((success && isSuspended), userService.existsUser(user.getID()));
    }

    @Test
    public void testAdminDismissComplaint() throws IOException {
        User user = new User("testAdminDismissComplaint", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Complaint complaint = new Complaint("testAdminDismissComplaint", "billyjean@gmail.com", user.getID(), "Took cold", "UNREVIEWED");
        adminService.addComplaint(complaint);

        if (userService.existsUser(user.getID())) {
            adminService.deleteUser(user.getID());
            adminService.deleteCook(user.getID());
        }

        boolean success = userService.addUser(user, COOK_TABLE) && cookService.addCook(cook);
        adminService.resolveComplaint(complaint.getID());
        boolean isActive = userService.getUserStatus(user.getID()).equals("ACTIVE");

        assertEquals((success && isActive), userService.existsUser(user.getID()));

        adminService.deleteComplaint(complaint);
    }

    @Test
    public void testAdminUnsuspend() throws IOException, ClassNotFoundException {
        User user = new User("testAdminUnsuspend", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");

        if (userService.existsUser(user.getID())) {
            adminService.deleteUser(user.getID());
            adminService.deleteCook(user.getID());
        }

        boolean success = userService.addUser(user, COOK_TABLE) && cookService.addCook(cook);
        adminService.suspendUserTemporarily(user.getID());
        boolean isSuspended = (!userService.getUserStatus(user.getID()).equals("SUSPENDED"))
                && (!userService.getUserStatus(user.getID()).equals("ACTIVE"));
        boolean isUnsuspended = adminService.unSuspendUser(user.getID());

        assertEquals(((success && isSuspended) && isUnsuspended), userService.existsUser(user.getID()));
    }
}