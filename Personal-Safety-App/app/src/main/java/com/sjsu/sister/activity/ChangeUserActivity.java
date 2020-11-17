
package com.sjsu.sister.activity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.sjsu.sister.R;
import com.sjsu.sister.model.User;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.ChangePhotoDialog;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.Init;
import com.sjsu.sister.util.StringUtils;
import com.sjsu.sister.util.UniversalImagerLoader;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChangeUserActivity extends BaseActivity implements ChangePhotoDialog.OnPhotoReceivedListener{
    private TextView tvOldName;
    private EditText etNewName;
    private Button btnChange;
    private DatabaseHelper databaseHelper;
    private ImageView mCamera;
    private CircleImageView mImage;
    private String mSelectedImagePath;
    private static final int REQUEST_CODE = 3;
    private String mEmail;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeuser);
        //get data
        mEmail = getIntent().getStringExtra("Email");

        // Find mUser
        databaseHelper = new DatabaseHelper(this);
        mUser = databaseHelper.userOfEmail(mEmail);
        mSelectedImagePath = mUser.getProfileImage();

        // findViewById
        tvOldName = findViewById(R.id.old_name);
        etNewName= findViewById(R.id.new_name);
        btnChange = findViewById(R.id.change_username);
        mImage = findViewById(R.id.edit_image);
        mCamera = (ImageView)findViewById(R.id.icon_camera);

        tvOldName.setText(mUser.getName());
        UniversalImagerLoader.setImage(mSelectedImagePath, mImage, null, "");

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = etNewName.getText().toString().trim();
                String newImage = mSelectedImagePath;
                changeUser(newname , newImage );
            }
        });

        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Qin Wang + click camera");
                for(int i = 0; i < Init.PHOTO_PERMISSIONS.length; i++){
                    String[] permission = {Init.PHOTO_PERMISSIONS[i]};
                    if( checkPermission(permission)){
                        if(i == Init.PHOTO_PERMISSIONS.length - 1){
                            ChangePhotoDialog dialog = new ChangePhotoDialog();
                            dialog.show(getSupportFragmentManager(),"ChangePhotoDialog");
                        }
                    }else{
                        verifyPermission(permission);
                    }
                }
            }
        });
    }

    private void changeUser(String newname, String newImage) {
        if (StringUtils.isEmpty(newname )) {
            CustomToast.popWarning(this, "Pleas enter your new username", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseHelper.updateUser(mEmail,newname,newImage);
        updateSharedPreferences(newname, newImage);
        CustomToast.popSuccess(this,"Username changed successfully!", Toast.LENGTH_SHORT).show();
    }

    public void updateSharedPreferences(String name, String image){
        SharedPreferences sp = getSharedPreferences("Sister", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Name",name);
        editor.putString("Image",image);
        editor.commit();
    }

    //____________________________________________________________________________________________________________________________________________
    //Verify Permission
    public void verifyPermission(String[] permissions){
        ActivityCompat.requestPermissions(ChangeUserActivity.this, permissions,REQUEST_CODE);
    }

    public boolean checkPermission(String[] permission){
        int permissionRequest = ActivityCompat.checkSelfPermission(ChangeUserActivity.this, permission[0]);
        if(permissionRequest == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void getBitmapImage(Bitmap bitmap) {
        if( bitmap!=null){
            compressBitmap(bitmap,70);
            mImage.setImageBitmap(bitmap);
        }

    }

    @Override
    public void getImagePath(String imagePath) {
        if(!imagePath.equals("")){
            imagePath = imagePath.replace(":/", "://");
            mSelectedImagePath = imagePath;
            UniversalImagerLoader.setImage(imagePath, mImage, null, "");
        }
    }


    //Compress Bitmap
    public Bitmap compressBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,stream);
        return bitmap;
    }
}
