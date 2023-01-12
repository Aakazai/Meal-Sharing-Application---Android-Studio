package com.seg2105project.mealerapp;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.seg2105project.mealerapp.admin.AdminService;
import com.seg2105project.mealerapp.cook.Cook;
import com.seg2105project.mealerapp.cook.CookService;
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.meal.MealService;
import com.seg2105project.mealerapp.user.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class MealTableTests {

    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    MealService mealService = new MealService(context);
    AdminService adminService = new AdminService(context);
    CookService cookService = new CookService(context);

    @Test
    public void testMealDatabase() throws IOException, ClassNotFoundException {
        Meal meal = new Meal("test", "test", "test", "test", 6.66);

        if (mealService.existsMeal(meal.getMealName())) {
            mealService.deleteMeal(meal.getMealName());
        }

        cookService.addMeal(meal);
        ;

        assertEquals("test", mealService.getMeal("test").getMealName());
    }

    @Test
    public void testUnOfferMeal() throws IOException, ClassNotFoundException {
        User user = new User("testReOfferMeal", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Meal meal = new Meal("Butter Chicken", "Dinner", "Indian", "Tasty", 10.99);
        meal.addCook(cook.getEmail());

        if (mealService.existsMeal(meal.getMealName())) {
            mealService.deleteMeal(meal.getMealName());
        }

        cookService.addCook(cook);
        cookService.addMeal(meal);
        cookService.unOfferMeal(meal.getMealName(), cook.getEmail());

        Meal updatedMeal = mealService.getMeal(meal.getMealName());

        assertEquals(false, mealService.isOffered(meal.getMealName()));
    }

    @Test
    public void testReOfferMeal() throws IOException, ClassNotFoundException {
        User user = new User("testReOfferMeal", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Meal meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        meal.addCook(cook.getEmail());

        if (mealService.existsMeal(meal.getMealName())) {
            mealService.deleteMeal(meal.getMealName());
        }

        cookService.addMeal(meal);
        cookService.addCook(cook);
        cookService.unOfferMeal(meal.getMealName(), cook.getEmail());
        cookService.reOfferMeal(meal.getMealName(), cook.getEmail());

        assertEquals(true, mealService.isOffered(meal.getMealName()));
    }

    @Test
    public void testDeleteMealWithoutDatabase() throws IOException, ClassNotFoundException {
        Meal meal;
        User user = new User("testDeleteMealWithoutDatabase", "test", "test", "test");
        User user2 = new User("testDeleteMealWithoutDatabase2", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Cook cook2 = new Cook("test", "test", user2.getID(), "test", "test");

        meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        meal.addCook(cook.getEmail());
        meal.addCook(cook2.getEmail());
        cook.addMeal(meal.getMealName());
        cook2.addMeal(meal.getMealName());

        cook.unOfferMeal(meal.getMealName());
        meal.unOfferCook(cook.getEmail());

        cook2.unOfferMeal(meal.getMealName());
        meal.unOfferCook(cook2.getEmail());

        cook.deleteMeal(meal.getMealName());
        meal.deleteCook(cook.getEmail());

        cook2.deleteMeal(meal.getMealName());
        meal.deleteCook(cook2.getEmail());

        assertEquals(false, meal.isOffered());
    }

    @Test
    public void testDeleteMealWithDatabase() throws IOException, ClassNotFoundException {
        adminService.clearMealTable();
        adminService.clearCookTable();
        Meal meal;
        User user = new User("testDeleteDB1", "test", "test", "test");
        User user2 = new User("testDeleteDB2", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Cook cook2 = new Cook("test", "test", user2.getID(), "test", "test");

        meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        meal.addCook(cook.getEmail());
        meal.addCook(cook2.getEmail());
        cook.addMeal(meal.getMealName());
        cook2.addMeal(meal.getMealName());

        boolean isCook1Added = cookService.addCook(cook);
        boolean isCook2Added = cookService.addCook(cook2);
        boolean isMealAdded = cookService.addMeal(meal);

        boolean isUnofferedCook1 = cookService.unOfferMeal(meal.getMealName(), cook.getEmail());
        boolean isUnofferedCook2 = cookService.unOfferMeal(meal.getMealName(), cook2.getEmail());

//        String isMealDeletedCook1 = cookService.deleteMealTest(meal.getMealName(), cook.getEmail());
//        String isMealDeletedCook2 = cookService.deleteMealTest(meal.getMealName(), cook2.getEmail());

        boolean isMealDeletedCook1 = cookService.deleteMeal(meal.getMealName(), cook.getEmail());
        boolean isMealDeletedCook2 = cookService.deleteMeal(meal.getMealName(), cook2.getEmail());

        assertEquals(isMealDeletedCook2, !mealService.existsMeal(meal.getMealName()));
    }

    @Test
    public void testUnOfferMeal2() throws IOException, ClassNotFoundException {
        adminService.clearMealTable();
        adminService.clearCookTable();

        Meal meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        User user = new User("testReOfferMeal", "test", "test", "test");
        User user2 = new User("testReOfferMeal2", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Cook cook2 = new Cook("test", "test", user2.getID(), "test", "test");


        meal.addCook(cook.getEmail());
        meal.addCook(cook2.getEmail());
        cook.addMeal(meal.getMealName());
        cook2.addMeal(meal.getMealName());
        cookService.addMeal(meal);
        cookService.addCook(cook);
        cookService.addCook(cook2);
        cookService.unOfferMeal(meal.getMealName(), cook.getEmail());
        cookService.unOfferMeal(meal.getMealName(), cook2.getEmail());

        Meal updatedMeal = mealService.getMeal(meal.getMealName());

        assertEquals(true, mealService.existsMeal(meal.getMealName()) && (!updatedMeal.isOffered()));
    }


    @Test
    public void testReOfferMeal2() throws IOException, ClassNotFoundException {
        adminService.clearMealTable();
        Meal meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        User user = new User("testReOfferMeal", "test", "test", "test");
        User user2 = new User("testReOfferMeal2", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Cook cook2 = new Cook("test", "test", user2.getID(), "test", "test");

        meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        meal.addCook(cook.getEmail());
        meal.addCook(cook2.getEmail());
        cookService.addMeal(meal);
        cookService.addCook(cook);
        cookService.addCook(cook2);
        cookService.unOfferMeal(meal.getMealName(), cook.getEmail());
        cookService.unOfferMeal(meal.getMealName(), cook2.getEmail());
        cookService.reOfferMeal(meal.getMealName(), cook.getEmail());
        cookService.reOfferMeal(meal.getMealName(), cook2.getEmail());

        Meal updatedMeal = mealService.getMeal(meal.getMealName());

        assertEquals(true, mealService.existsMeal(meal.getMealName()) && (updatedMeal.isOffered()));
    }

    @Test
    public void testAddMultipleCooksToMeal() throws IOException, ClassNotFoundException {
        adminService.clearMealTable();
        Meal meal;
        User user = new User("testReOfferMeal", "test", "test", "test");
        User user2 = new User("testReOfferMeal2", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Cook cook2 = new Cook("test", "test", user2.getID(), "test", "test");

        meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        meal.addCook(cook.getEmail());
        meal.addCook(cook2.getEmail());
        cookService.addMeal(meal);

        Meal updatedMeal = mealService.getMeal(meal.getMealName());

        assertEquals(2, updatedMeal.getCount());
    }


    @Test
    public void testExistsMeal() throws IOException, ClassNotFoundException {
        adminService.clearMealTable();
        Meal meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        Meal meal2 = new Meal("Butter Chicken", "Dinner", "Indian", "Tasty", 10.99);

        cookService.addMeal(meal);
        cookService.addMeal(meal2);

        assertEquals(true, mealService.existsMeal(meal.getMealName()) && mealService.existsMeal(meal2.getMealName()));
    }

    @Test
    public void testCookGetAllMeals() throws IOException, ClassNotFoundException {
        adminService.clearMealTable();
        User user = new User("testCookGetAllMeals", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Meal meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        Meal meal2 = new Meal("Butter Chicken", "Dinner", "Indian", "Tasty", 10.99);
        cook.addMeal(meal.getMealName());
        cook.addMeal(meal2.getMealName());
        cookService.addMeal(meal);
        cookService.addMeal(meal2);

        Set<String> cooksMeals = new HashSet(cook.getActiveMeals());

        Set<Meal> meals = new HashSet<>(mealService.getAllMealsFromCook(cook.getActiveMeals()));
        Set<String> mealNames = new HashSet();


        meals.forEach(i -> mealNames.add(i.getMealName()));

        assertEquals(true, mealNames.equals(cooksMeals));
    }

    @Test
    public void testCookGetAllOfferedMeals() throws IOException, ClassNotFoundException {
        adminService.clearMealTable();
        User user = new User("testReOfferMeal", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Meal meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        Meal meal2 = new Meal("Butter Chicken", "Dinner", "Indian", "Tasty", 10.99);
        Meal meal3 = new Meal("BBQ Chicken Pizza2", "Lunch", "Italian", "Okay", 15.99);
        meal.addCook(cook.getEmail());
        meal2.addCook(cook.getEmail());
        meal3.addCook(cook.getEmail());
        cook.addMeal(meal.getMealName());
        cook.addMeal(meal2.getMealName());
        cook.addMeal(meal3.getMealName());
        cookService.addMeal(meal);
        cookService.addMeal(meal2);
        cookService.addMeal(meal3);
        cookService.unOfferMeal(meal3.getMealName(), cook.getEmail());

        Set<Meal> meals = new HashSet<>(mealService.getAllOfferedMealsFromCook(cook.getActiveMeals()));

        Set<String> mealNames = new HashSet();

        meals.forEach(i -> mealNames.add(i.getMealName()));

        assertEquals(true, mealNames.size() == 2);
    }

    @Test
    public void testGetAllMealTypes() throws IOException, ClassNotFoundException {
        adminService.clearMealTable();
        User user = new User("testReOfferMeal", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Meal meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        Meal meal2 = new Meal("Butter Chicken", "Dinner", "Indian", "Tasty", 10.99);
        Meal meal3 = new Meal("BBQ Chicken Pizza2", "Lunch", "Italian", "Okay", 15.99);
        meal.addCook(cook.getEmail());
        meal2.addCook(cook.getEmail());
        meal3.addCook(cook.getEmail());
        cook.addMeal(meal.getMealName());
        cook.addMeal(meal2.getMealName());
        cook.addMeal(meal3.getMealName());
        cookService.addMeal(meal);
        cookService.addMeal(meal2);
        cookService.addMeal(meal3);
        cookService.unOfferMeal(meal3.getMealName(), cook.getEmail());

        Set<Meal> meals = new HashSet<>(mealService.getAllMealType("Dinner"));

        assertEquals(true, meals.size() == 2);
    }

    @Test
    public void testGetAllCuisineMeals() throws IOException, ClassNotFoundException {
        adminService.clearMealTable();
        User user = new User("testReOfferMeal", "test", "test", "test");
        Cook cook = new Cook("test", "test", user.getID(), "test", "test");
        Meal meal = new Meal("Chicken Shawarma", "Dinner", "Lebanese", "Ottawan", 20.99);
        Meal meal2 = new Meal("Butter Chicken", "Dinner", "Indian", "Tasty", 10.99);
        Meal meal3 = new Meal("BBQ Chicken Pizza2", "Lunch", "Italian", "Okay", 15.99);
        Meal meal4 = new Meal("Biryani", "Dinner", "Indian", "Tasty", 30.99);
        meal.addCook(cook.getEmail());
        meal2.addCook(cook.getEmail());
        meal3.addCook(cook.getEmail());
        meal4.addCook(cook.getEmail());
        cook.addMeal(meal.getMealName());
        cook.addMeal(meal2.getMealName());
        cook.addMeal(meal3.getMealName());
        cook.addMeal(meal4.getMealName());
        cookService.addMeal(meal);
        cookService.addMeal(meal2);
        cookService.addMeal(meal3);
        cookService.addMeal(meal4);
        cookService.unOfferMeal(meal.getMealName(), cook.getEmail());

        Set<Meal> meals = new HashSet<>(mealService.getAllCuisineMeals("Indian"));

        assertEquals(true, meals.size() == 2);
    }
}