package com.seg2105project.mealerapp.client;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Client implements Serializable, Parcelable {

    private static final long serialVersionUID = 1L;
    private String first, last, email, address, creditCardNumber, creditCardDate;

    public Client(String first, String last, String email, String address, String creditCardNumber, String creditCardDate) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.creditCardDate = creditCardDate;
    }

    public Client(Parcel in) {
        this.first = in.readString();
        this.last = in.readString();
        this.email = in.readString();
        this.address = in.readString();
        this.creditCardNumber = in.readString();
        this.creditCardDate = in.readString();
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
        dest.writeString(creditCardNumber);
        dest.writeString(creditCardDate);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardDate() {
        return creditCardDate;
    }

    public void setCreditCardDate(String creditCardDate) {
        this.creditCardDate = creditCardDate;
    }
}