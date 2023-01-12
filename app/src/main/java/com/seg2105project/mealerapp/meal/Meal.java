package com.seg2105project.mealerapp.meal;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meal implements Serializable, Parcelable {
    private static final long serialVersionUID = 1L;
    private String mealName;
    private String mealType;
    private String cuisineType;
    private String description;
    private int count;
    private double price;
    private boolean offered;
    private List<String> ingredients = new ArrayList<>();
    private List<String> cooks = new ArrayList<>();
    private List<String> allergens = new ArrayList<>();

    public Meal() {
    }

    public Meal(String mealName, String mealType, String cuisineType, String description, double price) {
        this.mealName = mealName.toLowerCase();
        this.mealType = mealType.toLowerCase();
        this.cuisineType = cuisineType.toLowerCase();
        this.description = description.toLowerCase();
        this.price = price;
        this.offered = true;
        this.count = 0;
    }

    public Meal(String mealName, String mealType, String cuisineType, String description, double price, boolean offered) {
        this.mealName = mealName.toLowerCase();
        this.mealType = mealType.toLowerCase();
        this.cuisineType = cuisineType.toLowerCase();
        this.description = description.toLowerCase();
        this.price = price;
        this.offered = offered;
        this.count = 0;
    }

    public Meal(Parcel in) {
        this.mealName = in.readString();
        this.mealType = in.readString();
        this.cuisineType = in.readString();
        this.description = in.readString();
        this.price = in.readDouble();
        this.offered = in.readByte() != 0;
        ;
        this.count = in.readInt();
        in.readStringList(cooks);
        in.readStringList(ingredients);
        in.readStringList(allergens);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(mealName);
        dest.writeString(mealType);
        dest.writeString(cuisineType);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeByte((byte) (offered ? 1 : 0));
        dest.writeInt(count);
        dest.writeStringList(cooks);
        dest.writeStringList(ingredients);
        dest.writeStringList(allergens);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient.toLowerCase());
    }

    public void removeIngredient(String ingredient) {
        ingredients.remove(ingredient.toLowerCase());
    }

    public void addAllergen(String ingredient) {
        allergens.add(ingredient.toLowerCase());
    }

    public void removeAllergen(String ingredient) {
        allergens.remove(ingredient.toLowerCase());
    }

    public boolean addCook(String cook) {
        if (cooks.add(cook.toLowerCase())) {
            ++count;
            return true;
        }
        return false;
    }

    public boolean reOfferCook(String cook) {
        return cooks.add(cook.toLowerCase());
    }

    public boolean deleteCook(String cook) {
        int currentCount = count;
        --count;
        return count == (currentCount - 1);

    }

    public boolean unOfferCook(String cook) {
        return cooks.remove(cook.toLowerCase());
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName.toLowerCase();
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType.toLowerCase();
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType.toLowerCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toLowerCase();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public boolean isOffered() {
        return cooks.size() > 0;
    }

    public List<String> getCooks() {
        return cooks;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public int getCount() {
        return count;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealName='" + mealName + '\'' +
                ", mealType='" + mealType + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
