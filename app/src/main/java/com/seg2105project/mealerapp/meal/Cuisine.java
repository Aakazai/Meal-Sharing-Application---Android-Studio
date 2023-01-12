package com.seg2105project.mealerapp.meal;

public class Cuisine {
    private final String cusineName;
    private int count;

    public Cuisine(String cusineName) {
        this.cusineName = cusineName;
        this.count = 1;
    }
    public Cuisine(String cusineName, int count) {
        this.cusineName = cusineName;
        this.count = count;
    }

    public String getCusineName() {
        return cusineName;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        ++count;
    }
    public void decrementCount() {
        --count;
    }
}
