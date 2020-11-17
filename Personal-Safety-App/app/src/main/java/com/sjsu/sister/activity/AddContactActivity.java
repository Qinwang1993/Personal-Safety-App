package com.sjsu.sister.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.sjsu.sister.R;
import com.sjsu.sister.model.Contact;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.ChangePhotoDialog;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.Init;
import com.sjsu.sister.util.StringUtils;
import com.sjsu.sister.util.UniversalImagerLoader;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddContactActivity extends AppCompatActivity implements ChangePhotoDialog.OnPhotoReceivedListener{
    private Toolbar mToolbar;
    private Contact mContact;
    private CircleImageView mContactImage;
    private EditText mContactName;
    private EditText mContactPhoneNumber;
    private EditText mContactEmail;
    private ImageView mCamera;
    private Spinner mSpinner;
    private static final int REQUEST_CODE = 2;
    public Bitmap newBitmap;
    private String mSelectedImagePath;
    private int mPreviousKeyStroke;
    private String userEmail;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        userEmail = getIntent().getStringExtra("Email");
        mContactName = (EditText)findViewById(R.id.addName);
        mContactImage = (CircleImageView)findViewById(R.id.add_image);
        mContactPhoneNumber = (EditText)findViewById(R.id.addPhoneNumber);
        mContactEmail  = (EditText)findViewById(R.id.addEmail);
        mSpinner = (Spinner) findViewById(R.id.select_device);
        mCamera = (ImageView)findViewById(R.id.icon_camera);
        mSelectedImagePath = null;

        // Setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(AddContactActivity.this, ContactsListActivity.class);
//                in.putExtra("Email", userEmail);
//                startActivity(in);
//                finish();
//            }
//        });

        //On click
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
        initOnTextChangeLister();
    }


    // PhoneNumber format setting
    private void initOnTextChangeLister(){

        mContactPhoneNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                mPreviousKeyStroke = keyCode;
                return false;
            }
        });

        mContactPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String number = editable.toString();
                if(number.length() == 3 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL && !number.contains("(")){
                    number = String.format("(%s", editable.toString().substring(0,3));
                    mContactPhoneNumber.setText(number);
                    mContactPhoneNumber.setSelection(number.length());
                }else if(number.length() == 5 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL && !number.contains(")")){
                    number = String.format("(%s)%s", editable.toString().substring(1,4),editable.toString().substring(4,5));
                    mContactPhoneNumber.setText(number);
                    mContactPhoneNumber.setSelection(number.length());
                }else if(number.length() == 10 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL && !number.contains("-")){
                    number = String.format("(%s) %s-%s", editable.toString().substring(1,4),editable.toString().substring(5,8),editable.toString().substring(8,10));
                    mContactPhoneNumber.setText(number);
                    mContactPhoneNumber.setSelection(number.length());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_contact_done, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.contact_edit_done:
                if(!StringUtils.isEmpty(mContactName.getText().toString().trim())){
                        DatabaseHelper databaseHelper = new DatabaseHelper(this);
                        Contact contact = new Contact(mContactName.getText().toString().trim(),
                                mContactPhoneNumber.getText().toString().trim(),
                                mSpinner.getSelectedItem().toString().trim(),
                                mContactEmail.getText().toString().trim(),
                                mSelectedImagePath);
                        if(databaseHelper.addContact(userEmail, contact)){
                            CustomToast.popSuccess(AddContactActivity.this, "Added Successfully !", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(AddContactActivity.this, ContactsListActivity.class);
                            in.putExtra("Email", userEmail);
                            startActivity(in);
                            finish();
                        }else{
                            CustomToast.popError(AddContactActivity.this, "Error Saving", Toast.LENGTH_SHORT).show();
                        }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //____________________________________________________________________________________________________________________________________________
    //Verify Permission
    public void verifyPermission(String[] permissions){
        ActivityCompat.requestPermissions(AddContactActivity.this, permissions,REQUEST_CODE);
    }

    public boolean checkPermission(String[] permission){
        int permissionRequest = ActivityCompat.checkSelfPermission(AddContactActivity.this, permission[0]);
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
            mContactImage.setImageBitmap(bitmap);
        }

    }

    @Override
    public void getImagePath(String imagePath) {
        if(!imagePath.equals("")){
            imagePath = imagePath.replace(":/", "://");
            mSelectedImagePath = imagePath;
            UniversalImagerLoader.setImage(imagePath, mContactImage, null, "");
        }
    }


    //Compress Bitmap
    public Bitmap compressBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,stream);
        return bitmap;
    }


}
