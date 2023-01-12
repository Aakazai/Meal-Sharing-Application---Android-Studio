package com.seg2105project.mealerapp.admin;

import android.icu.text.SimpleDateFormat;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Date;

public class Complaint implements Parcelable {
    private String ID;
    private String client;
    private String cook;
    private String reason;
    private String status;
    private String order;

    public Complaint(String client, String cook, String reason) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(System.currentTimeMillis());
        this.ID = String.valueOf(sdf.format(resultdate) + " " + System.nanoTime());
        this.client = client;
        this.cook = cook;
        this.reason = reason;
        this.order = null;
    }

    public Complaint(String client, String cook, String reason, String order) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(System.currentTimeMillis());
        this.ID = String.valueOf(sdf.format(resultdate) + " " + System.nanoTime());
        this.client = client;
        this.cook = cook;
        this.reason = reason;
        this.order = order;
    }

    public Complaint(String ID, String client, String cook, String reason, String status) {
        this.ID = ID;
        this.client = client;
        this.cook = cook;
        this.reason = reason;
        this.status = status;
        this.order = null;
    }

    public Complaint(String ID, String client, String cook, String reason, String status, String order) {
        this.ID = ID;
        this.client = client;
        this.cook = cook;
        this.reason = reason;
        this.status = status;
        this.order = order;
    }

    public Complaint(Parcel in) {
        String[] data = new String[6];
        in.readStringArray(data);

        this.ID = data[0];
        this.client = data[1];
        this.cook = data[2];
        this.reason = data[3];
        this.status = data[4];
        this.order = data[5];
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "ID='" + ID + '\'' +
                ", client='" + client + '\'' +
                ", cook='" + cook + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.ID,
                this.client,
                this.cook,
                this.reason,
                this.status,
                this.order});

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Complaint createFromParcel(Parcel in) {
            return new Complaint(in);
        }

        public Complaint[] newArray(int size) {
            return new Complaint[size];
        }
    };
}