package com.seg2105project.mealerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.seg2105project.mealerapp.admin.AdminService;
import com.seg2105project.mealerapp.client.Client;
import com.seg2105project.mealerapp.client.ClientRegistration;
import com.seg2105project.mealerapp.client.ClientService;
import com.seg2105project.mealerapp.cook.Cook;
import com.seg2105project.mealerapp.cook.CookRegistration;
import com.seg2105project.mealerapp.cook.CookService;
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.meal.MealService;
import com.seg2105project.mealerapp.user.User;
import com.seg2105project.mealerapp.user.UserService;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!flag) {
            AdminService adminService = new AdminService(MainActivity.this);
            try {
                adminService.stimulateComplaints();
                MealService mealService = new MealService(MainActivity.this);
                CookService cookService = new CookService(MainActivity.this);
                UserService userService = new UserService(MainActivity.this);

                Cook cook = null;
                boolean isUpdated = false;

                if(!userService.existsUser("testcook1")){
                    User user = new User("testcook1", "test", "COOK_TABLE", "ACTIVE");
                    userService.addUser(user);
                    cook = new Cook("test", "test", "testcook1", "test", "test");
                    cookService.addCook(cook);

                    if(!cook.getActiveMeals().contains("Chicken Shawarma".toLowerCase())){
                        Meal meal = new Meal("Chicken Shawarma", "dinner", "Lebanese", "Ottawan", 20.99);
                        meal.addIngredient("chicken");
                        meal.addIngredient("hummus");
                        meal.addIngredient("garlic");
                        meal.addIngredient("tomato");
                        meal.addIngredient("lettuce");
                        meal.addAllergen("dairy");
                        meal.addCook(cook.getEmail());
                        cook.addMeal(meal.getMealName());
                        cookService.addMeal(meal);
                        isUpdated = true;
                    }

                    if(!cook.getActiveMeals().contains("Butter Chicken".toLowerCase())){
                        Meal meal2 = new Meal("Butter Chicken", "dinner", "Indian", "Tasty", 10.99);
                        meal2.addIngredient("chicken");
                        meal2.addIngredient("tomato sauce");
                        meal2.addIngredient("butter");
                        meal2.addAllergen("milk");

                        cook.addMeal(meal2.getMealName());
                        meal2.addCook(cook.getEmail());
                        cookService.addMeal(meal2);
                        isUpdated = true;
                    }

                    if(!cook.getActiveMeals().contains("BBQ Chicken Pizza".toLowerCase())){
                        Meal meal3 = new Meal("BBQ Chicken Pizza", "lunch", "Italian", "Okay", 15.99);
                        meal3.addIngredient("cheese");
                        meal3.addIngredient("dough");
                        meal3.addIngredient("chicken");
                        meal3.addIngredient("bbq sauce");
                        meal3.addAllergen("wheat");

                        cook.addMeal(meal3.getMealName());
                        meal3.addCook(cook.getEmail());
                        cookService.addMeal(meal3);
                        isUpdated = true;
                    }

                    ClientService clientService = new ClientService(this);

                    Client testclient1 = new Client("test",
                            "test",
                            "testclient1",
                            "test",
                            "1",
                            "1");

                    User testclient1user = new User(testclient1.getEmail(), "test", "CLIENT_TABLE", "ACTIVE");


                    boolean isAddedTestClient1 = clientService.addClient(testclient1);
                    boolean isAddedTestClient1User = userService.addUser(testclient1user);

                    Client testclient2 = new Client("test",
                            "test",
                            "testclient2",
                            "test",
                            "1",
                            "1");

                    User testclient2user = new User(testclient2.getEmail(), "test", "CLIENT_TABLE", "ACTIVE");

                    boolean isAddedTestClient2 = clientService.addClient(testclient2);
                    boolean isAddedTestClient2User = userService.addUser(testclient2user);

                    isUpdated = isUpdated && isAddedTestClient1 && isAddedTestClient2 && isAddedTestClient1User && isAddedTestClient2User;

                    if(isUpdated){
                        cookService.updateCook(cook);
                    }
                }


                Toast.makeText(MainActivity.this, "isUpdated? " + isUpdated, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
//                Toast.makeText(MainActivity.this, "Complaint stimulation failed.", Toast.LENGTH_SHORT).show();
            }
            flag = true;
        }

        setContentView(R.layout.activity_main);
    }

    public void goToClientSU(View view) {
        Intent intent = new Intent(this, ClientRegistration.class);
        startActivity(intent);
    }

    public void goToCookSU(View view) {
        Intent intent = new Intent(this, CookRegistration.class);
        startActivity(intent);
    }

    public void goToWelcome(View view) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}