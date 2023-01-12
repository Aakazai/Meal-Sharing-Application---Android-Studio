package com.seg2105project.mealerapp;

import org.junit.Test;

import static org.junit.Assert.*;

import com.seg2105project.mealerapp.admin.Complaint;
import com.seg2105project.mealerapp.client.Client;
import com.seg2105project.mealerapp.cook.Cook;
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.order.Order;

import java.io.IOException;

public class MealerAppUnitTests {
    @Test
    public void testCook() {
        Cook cook = new Cook("Sam",
                "Smith",
                "sam@gmail.com",
                "123 Main Street",
                "Specialzies in Thai and Japanese food.");
        assertEquals("sam@gmail.com", cook.getEmail());
    }

    @Test
    public void testClient() {
        Client client = new Client("Abdul",
                "Hakim",
                "abdulhakim@outlook.com",
                "1 Lees Avenue",
                "0",
                "0");

        assertEquals("1 Lees Avenue", client.getAddress());
    }

    @Test
    public void testComplaint() {
        Complaint complaint = new Complaint(String.valueOf(System.currentTimeMillis()),
                "Billy Jean",
                "Sam Smith",
                "Food too cold",
                "UNRESOLVED");

        assertEquals("Food too cold", complaint.getReason());
    }

    @Test
    public void testMeal() throws IOException, ClassNotFoundException {
        Meal meal = new Meal("test", "test", "test", "test", 6.66);

        byte[] bytes = Serializer.serialize(meal);

        Meal deserializedMeal = Serializer.deserialize(bytes);

        assertEquals("test", deserializedMeal.getMealName());
    }

    @Test
    public void testMealAddCook() throws IOException, ClassNotFoundException {
        Meal meal = new Meal("test", "test", "test", "test", 6.66);
        Cook cook = new Cook("Sam",
                "Smith",
                "sam@gmail.com",
                "123 Main Street",
                "Specialzies in Thai and Japanese food.");

        boolean success = meal.addCook(cook.getEmail());

        assertEquals(true, success);
    }

    @Test
    public void testMealRemoveCook() throws IOException, ClassNotFoundException {
        Meal meal = new Meal("test", "test", "test", "test", 6.66);
        Cook cook = new Cook("Sam",
                "Smith",
                "sam@gmail.com",
                "123 Main Street",
                "Specialzies in Thai and Japanese food.");

        boolean success = meal.addCook(cook.getEmail());
        boolean success2 = meal.deleteCook(cook.getEmail());

        assertEquals(true, success && success2);
    }

    @Test
    public void testSerializer() throws IOException, ClassNotFoundException {
        Client client = new Client("Abdul",
                "Hakim",
                "abdulhakim@outlook.com",
                "1 Lees Avenue",
                "0",
                "0");

        byte[] bytes = Serializer.serialize(client);

        Client deserializedClient = Serializer.deserialize(bytes);

        assertEquals(client.getEmail(), deserializedClient.getEmail());
    }

    @Test
    public void testSerializerWrongClass() throws IOException {
        Cook cook = new Cook("Sam",
                "Smith",
                "sam@gmail.com",
                "123 Main Street",
                "Specialzies in Thai and Japanese food.");

        byte[] bytes = Serializer.serialize(cook);

        assertThrows(ClassCastException.class,
                () -> {
                    Client deserializedCook = Serializer.deserialize(bytes);
                });
    }

    @Test
    public void testOrder() {
        Order order = new Order("test", "test1", "test2", "test3", "test4", 5, "test6");
        assertEquals("test1", order.getClient());
    }

    @Test
    public void testOrderComplaint() {
        Complaint complaint = new Complaint(String.valueOf(System.currentTimeMillis()),
                "Billy Jean",
                "Sam Smith",
                "Food too cold",
                "UNRESOLVED");
        Order order = new Order("test", "test1", "test2", "test3", "test4", 5, complaint.getID());
        assertEquals(complaint.getID(), order.getComplaint());
    }
}