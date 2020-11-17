
package com.sjsu.sister.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sjsu.sister.R;
import com.sjsu.sister.model.User;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.StringUtils;


public class SettingActivity extends BaseActivity {
    private EditText etOldPwd;
    private EditText etNewPwd;
    private EditText etConfirmPwd;
    private Button btnSet;
    private SQLiteDatabase DB;
    private DatabaseHelper databaseHelper;
    private User user;
    private String mEmail;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mEmail = getIntent().getStringExtra("Email");
        etOldPwd = findViewById(R.id.old_pwd);
        etNewPwd= findViewById(R.id.new_pwd);
        etConfirmPwd = findViewById(R.id.confirm_pwd);
        btnSet = findViewById(R.id.set_pwd);

        databaseHelper = new DatabaseHelper(this);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpwd = etOldPwd.getText().toString().trim();
                String newpwd = etNewPwd.getText().toString().trim();
                String cPwd = etConfirmPwd.getText().toString().trim();
                setPassword(oldpwd,newpwd,cPwd, v);
            }
        });
    }

    private void setPassword(String oldpwd, String newpwd, String cPwd, View v) {
        if (StringUtils.isEmpty(oldpwd)) {
            CustomToast.popWarning(this,"Pleas enter your old password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(newpwd)) {
            CustomToast.popWarning(this,"Pleas enter your new password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(cPwd)) {
            CustomToast.popWarning(this,"Pleas confirm your new password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(newpwd.equals(oldpwd)){
            CustomToast.popError(this,"New password must be different", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!newpwd.equals(cPwd)){
            CustomToast.popError(this,"Password dose not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (databaseHelper.checkUser(mEmail,oldpwd)) {
            databaseHelper.updateUserPwd(mEmail, newpwd);
            CustomToast.popSuccess(this,"Password set successfully!", Toast.LENGTH_SHORT).show();

        } else {
            CustomToast.popError(this,"Wrong password, please try again!", Toast.LENGTH_SHORT).show();
        }
    }
}
