package com.sjsu.sister.util;


import android.Manifest;

public class Init {
    public Init(){

    }

    public static final String[] PHOTO_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    public static final String[] PHONE_PERMISSIONS = {Manifest.permission.CALL_PHONE};
    public static final String[] SMS_PERMISSIONS = {Manifest.permission.SEND_SMS};

    public static final int CAMERA_REQUEST_CODE = 5;
    public static final int CHOSE_PHOTO_REQUEST_CODE = 6;
}
