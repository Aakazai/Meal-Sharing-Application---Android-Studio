package com.seg2105project.mealerapp.order;

import android.icu.text.SimpleDateFormat;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Date;

public class Order implements Parcelable {
    private String id, client, cook, meal, status;
    private int rating;
    private String complaint;

    public Order(String id, String client, String cook, String meal, String status, int rating,
                 String complaint) {
        this.id = id;
        this.client = client;
        this.cook = cook;
        this.meal = meal;
        this.status = status;
        this.rating = rating;
        this.complaint = complaint;
    }

    public Order(String client, String cook, String meal) {
        android.icu.text.SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        java.sql.Date resultdate = new Date(System.currentTimeMillis());
        this.id = String.valueOf(sdf.format(resultdate) + " " + System.nanoTime());
        this.client = client;
        this.cook = cook;
        this.meal = meal;
        this.status = "PENDING";
        this.rating = -1;
        this.complaint = null;
    }

    public Order(Parcel in) {
        this.id = in.readString();
        this.client = in.readString();
        this.cook = in.readString();
        this.meal = in.readString();
        this.status = in.readString();
        this.rating = in.readInt();
        this.complaint = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(client);
        dest.writeString(cook);
        dest.writeString(meal);
        dest.writeString(status);
        dest.writeInt(rating);
        dest.writeString(complaint);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCook() {
        return cook;
    }

    public void setCook(String cook) {
        this.cook = cook;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", client='" + client + '\'' +
                ", cook='" + cook + '\'' +
                ", meal='" + meal + '\'' +
                ", status='" + status + '\'' +
                ", rating=" + rating +
                ", complaint='" + complaint + '\'' +
                '}';
    }
}
