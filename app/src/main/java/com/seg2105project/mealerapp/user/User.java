package com.seg2105project.mealerapp.user;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    private String ID;
    private String password;

    private String type;
    private String status;

    public User() {
    }

    public User(String username, String password, String type) {
        this.ID = username;
        this.password = password;
        this.type = type;
    }

    public User(String username, String password, String type, String status) {
        this.ID = username;
        this.password = password;
        this.type = type;
        this.status = status;
    }

    public User(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);

        this.ID = data[0];
        this.password = data[1];
        this.type = data[2];
        this.status = data[3];
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.ID,
                this.password,
                this.type,
                this.status});

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
