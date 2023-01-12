package com.seg2105project.mealerapp.cook;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cook implements Serializable, Parcelable {

    private static final long serialVersionUID = 1L;
    private List<String> meals = new ArrayList<String>();
    private List<String> unOfferedMeals = new ArrayList<String>();
    private List<String> cuisines = new ArrayList<String>();
    private String first, last, email, address, description;
    private double rating;
    private int ratingCount;

    public Cook(String firstName, String lastName,
                String email, String address, String description) {
        this.first = firstName;
        this.last = lastName;
        this.email = email;
        this.address = address;
        this.description = description;
        this.rating = 0;
        this.ratingCount = 0;
    }

    public Cook(Parcel in) {
        this.first = in.readString();
        this.last = in.readString();
        this.email = in.readString();
        this.address = in.readString();
        this.description = in.readString();
        this.rating = in.readDouble();
        this.ratingCount = in.readInt();
        in.readStringList(meals);
        in.readStringList(unOfferedMeals);
        in.readStringList(cuisines);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(first);
        dest.writeString(last);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeDouble(rating);
        dest.writeInt(ratingCount);
        dest.writeStringList(meals);
        dest.writeStringList(unOfferedMeals);
        dest.writeStringList(cuisines);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Cook createFromParcel(Parcel in) {
            return new Cook(in);
        }

        public Cook[] newArray(int size) {
            return new Cook[size];
        }
    };

    public boolean addMeal(String mealName) {
        return !(meals.contains(mealName.toLowerCase())) && (meals.add(mealName.toLowerCase()));
    }

    public boolean deleteMeal(String mealName) {
        return unOfferedMeals.remove(mealName.toLowerCase());
    }

    public boolean unOfferMeal(String mealName) {
        return meals.remove(mealName.toLowerCase()) && unOfferedMeals.add(mealName.toLowerCase());
    }

    public boolean reOfferMeal(String mealName) {
        return meals.add(mealName.toLowerCase()) && unOfferedMeals.remove(mealName.toLowerCase());
    }

    public boolean contains(String mealName) {
        return (meals.contains(mealName.toLowerCase())) || (unOfferedMeals.contains(mealName.toLowerCase()));
    }

    public void addCuisine(String cuisine) {
        cuisines.add(cuisine.toLowerCase());
    }

    public void removeCuisine(String cuisine) {
        cuisines.remove(cuisine.toLowerCase());
    }

    public List<String> getActiveMeals() {
        return meals;
    }

    public List<String> getUnOfferedMeals() {
        return unOfferedMeals;
    }

    public int getMealSize() {
        return meals.size();
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return Double.parseDouble((String.format("%.2f", (rating/ratingCount))));
    }

    public void addRating(double rating) {
        this.rating += rating;
        ++ratingCount;
    }

    @Override
    public String toString() {
        return "Cook{" +
                "cuisines=" + cuisines +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}