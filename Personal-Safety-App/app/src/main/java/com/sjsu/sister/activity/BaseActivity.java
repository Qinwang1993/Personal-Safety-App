package com.sjsu.sister.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public void navigateTo(Class cls){
        Intent in = new Intent(mContext, cls);
        startActivity(in);
    }

    public void passTo(Class cls, String message){
            Intent in = new Intent(mContext, cls);
            in.putExtra("Email",message);
            startActivity(in);
    }
}
