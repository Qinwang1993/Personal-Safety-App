package com.sjsu.sister.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.sjsu.sister.R;
import com.sjsu.sister.model.Contact;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.Init;
import com.sjsu.sister.util.UniversalImagerLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewContactsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Contact mContact;
    private CircleImageView mContactImage;
    private TextView mContactName;
    private TextView mContactPhoneNumber;
    private TextView mContactEmail;
    private ImageView callPhone;
    private ImageView sendMessage;
    private ImageView sendEmail;
    private String userEmail;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcontacts);

        mContactName = (TextView)findViewById(R.id.contact_detail_name);
        mContactImage = (CircleImageView)findViewById(R.id.contact_detail_image);
        mContactPhoneNumber = (TextView)findViewById(R.id.text_phone_number);
        mContactEmail  = (TextView)findViewById(R.id.text_email);
        callPhone = (ImageView) findViewById(R.id.icon_phone);
        sendMessage = (ImageView) findViewById(R.id.icon_message);
        sendEmail = (ImageView) findViewById(R.id.icon_email);
        mContact = (Contact)getIntent().getParcelableExtra("Contact");
        userEmail = getIntent().getStringExtra("Email");

        // Setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ViewContactsActivity.this, ContactsListActivity.class);
                System.out.println(userEmail);
                in.putExtra("Email", userEmail);
                startActivity(in);
                finish();
            }
        });
        //init();

        //On click
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "body...");
                emailIntent.setData(Uri.parse("mailto:" + mContact.getEmail()));
                startActivity(emailIntent);
            }
        });
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("Qin Wang: + click phone call");
                if(checkPermission(Init.PHONE_PERMISSIONS)){
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ mContact.getPhoneNumber()));
                    //Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mContact.getPhoneNumber(), null));
                    startActivity(callIntent);
                }else{
                    verifyPermission(Init.PHONE_PERMISSIONS);
                }
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Qin Wang: + click send message");
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",mContact.getPhoneNumber(),null));
                startActivity(smsIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        mContactName.setText(mContact.getName());
        mContactPhoneNumber.setText(mContact.getPhoneNumber());
        mContactEmail.setText(mContact.getEmail());
        UniversalImagerLoader.setImage(mContact.getProfileImage(), mContactImage, null, "");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.contact_edit:
                Intent in = new Intent(ViewContactsActivity.this, EditContactActivity.class);
                in.putExtra("Contact",mContact);
                in.putExtra("Email", userEmail);
                startActivity(in);
                finish();
                break;
            case R.id.contact_delete:
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                int contactID = databaseHelper.getContactID(mContact);
                if(contactID > -1){
                    if(databaseHelper.deleteContact(contactID) > 0){
                        CustomToast.popSuccess(this,"Contact Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ViewContactsActivity.this, ContactsListActivity.class);
                        intent.putExtra("Email", userEmail);
                        startActivity(intent);
                        finish();
                    }else{
                        CustomToast.popError(this,"Database Error", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    CustomToast.popError(this,"Database Error", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //____________________________________________________________________________________________________________________________________________
    //Verify Permission
    public void verifyPermission(String[] permissions){
        ActivityCompat.requestPermissions(ViewContactsActivity.this, permissions,REQUEST_CODE);
    }

    public boolean checkPermission(String[] permission){
        int permissionRequest = ActivityCompat.checkSelfPermission(ViewContactsActivity.this, permission[0]);
        if(permissionRequest == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
//        switch(requestCode){
//            case REQUEST_CODE:
//                for(int i = 0; i < permissions.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(ViewContactsActivity.this, "Permission Allowed", Toast.LENGTH_SHORT).show();
//                    } else {
//                        break;
//                    }
//                }
//                break;
//        }
//    }
}
