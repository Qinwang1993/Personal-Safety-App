package com.sjsu.sister.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sjsu.sister.R;
import com.sjsu.sister.model.User;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.StringUtils;

import java.util.Arrays;


public class RegisterActivity extends BaseActivity {
    private EditText etName;
    private EditText etEmail;
    private EditText etPwd;
    private EditText etConfirmPwd;
    private Button btnRegister;
    private TextView alreadyMember;
    private SQLiteDatabase DB;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        etConfirmPwd = findViewById(R.id.et_confirmPwd);
        etName = findViewById(R.id.et_name);

        btnRegister = findViewById(R.id.btn_signup);
        alreadyMember = findViewById(R.id.already_member);
        databaseHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                String cPwd = etConfirmPwd.getText().toString().trim();
                String name = etName.getText().toString().trim();
                register(name,email, pwd,cPwd);
            }
        });

        alreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(LoginActivity.class);
            }
        });
    }

    private void register(String name,String email, String pwd, String cPwd) {
        if (StringUtils.isEmpty(email)) {
            CustomToast.popWarning(this,"Pleas enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            CustomToast.popError(this,"Pleas enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtils.isEmpty(pwd)) {
            CustomToast.popWarning(this,"Pleas enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(cPwd)) {
            CustomToast.popWarning(this,"Pleas confirm your password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pwd.equals(cPwd)){
            CustomToast.popError(this,"Password dose not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!databaseHelper.checkUser(email)) {
            user = new User(name, email, pwd, null,null);

            databaseHelper.addUser(user);
            Intent in = new Intent(RegisterActivity.this, HomeActivity.class);
            SharedPreferences sp = getSharedPreferences("Sister", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("Status", true);
            editor.putString("Email", email);
            editor.commit();

            in.putExtra("Email", email);
            startActivity(in);
            finish();

        } else {
            CustomToast.popError(this,"Email Already Exists", Toast.LENGTH_SHORT).show();
        }
    }

}

