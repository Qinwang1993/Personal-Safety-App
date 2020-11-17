package com.sjsu.sister.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private String name;
    private String phoneNumber;
    private String device;
    private String email;
    private String profileImage;

    public Contact(){}

    public Contact(String name, String phoneNumber, String device, String  email, String profileImage){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.device = device;
        this.email = email;
        this.profileImage = profileImage;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        phoneNumber = in.readString();
        device = in.readString();
        email = in.readString();
        profileImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(device);
        dest.writeString(email);
        dest.writeString(profileImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getDevice(){
        return device;
    }

    public void setDevice(String device){
        this.device = device;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String name){
        this.email = email;
    }
    public String getProfileImage(){
        return profileImage;
    }

    public void setProfileImage(String profileImage){
        this.profileImage = profileImage;
    }
}
