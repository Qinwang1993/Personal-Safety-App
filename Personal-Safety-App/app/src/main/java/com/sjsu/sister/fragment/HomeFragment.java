package com.sjsu.sister.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.sjsu.sister.R;
import com.sjsu.sister.Service.ShakeService;
import com.sjsu.sister.activity.AboutUsActivity;
import com.sjsu.sister.activity.ChangeUserActivity;
import com.sjsu.sister.activity.ContactsListActivity;
import com.sjsu.sister.activity.FAQActivity;
import com.sjsu.sister.activity.FakeCallActivity;
import com.sjsu.sister.activity.LoginActivity;
import com.sjsu.sister.activity.SettingActivity;
import com.sjsu.sister.model.User;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.Init;
import com.sjsu.sister.util.StringUtils;
import com.sjsu.sister.util.UniversalImagerLoader;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements  NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private View v;
    private View headerLayout;
    private ImageView emergencyCall;
    private static final int REQUEST_CODE = 7;
    private String mEmail;
    private Switch drawerSwitch;
    private User mUser;
    private DatabaseHelper databaseHelper;
    private static Location mLocation = null;

    private LoginButton loginButton;
    private GoogleSignInClient mGoogleSignInClient;
//    where I modify
    private MediaPlayer mediaPlayer;


    public HomeFragment() {

    }

    public static HomeFragment newInstance(String email) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("Email", email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mEmail = getArguments().getString("Email");
        }
        databaseHelper = new DatabaseHelper(getActivity());
        mUser = databaseHelper.userOfEmail(mEmail);

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);

        //Setting toolbar & navigation drawer
        toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) v.findViewById(R.id.nvView);
        nvDrawer.setNavigationItemSelectedListener(this);
        headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);
        drawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawer,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawer.addDrawerListener(drawerToggle );
        drawerToggle .setDrawerIndicatorEnabled(true);
        drawerToggle .syncState();

        //Switch Button
        Menu menu = nvDrawer.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nv_switch);
        View actionView = MenuItemCompat.getActionView(menuItem);
        drawerSwitch = (Switch) actionView.findViewById(R.id.switch_button);
        drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    System.out.println("nv_switch : on");
                    Intent intent = new Intent(getActivity(), ShakeService.class);
                    intent.putExtra("Phone", mUser.getEmergencyCall());
                    getActivity().startService(intent);
                } else {
                    System.out.println("nv_switch : off");
                    Intent intent = new Intent(getActivity(), ShakeService.class);
                    getActivity().stopService(intent);
                }
            }
        });

        // Emergency call function
        emergencyCall = (ImageView)v.findViewById(R.id.emergencyCall);
        emergencyCall.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mUser = databaseHelper.userOfEmail(mEmail);
                if(StringUtils.isEmpty(mUser.getEmergencyCall())){
                    CustomToast.popWarning(getActivity(),"Please set emergency phone number first.", Toast.LENGTH_LONG).show();
                }else{
                    if(checkPermission(Init.PHONE_PERMISSIONS)){
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ mUser.getEmergencyCall()));
                        startActivity(callIntent);
                    }else{
                        verifyPermission(Init.PHONE_PERMISSIONS);
                    }
                }
                return true;

            }
        });

        // Fake Call fuction
        ImageView fakeCall = (ImageView)v.findViewById(R.id.fakeCall);
        fakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add methods here ^_^
                Intent in = new Intent(getActivity(), FakeCallActivity.class);
                in.putExtra("Email",mEmail);
                startActivity(in);
            }
        });

//    where I modify
        // Alarm fuction
        ImageView alarm = (ImageView)v.findViewById(R.id.siren);
        alarm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Add methods here ^_^
                if(null != mediaPlayer){
                    CustomToast.popWarning(getActivity(),"Alarm is Playing !", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    mediaPlayer = MediaPlayer.create(getActivity(), R.raw.police);
//                    mediaPlayer.setLooping(false);
//                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    CustomToast.popSuccess(getActivity(),"Alarm Started !", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });
        alarm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(null!=mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    CustomToast.popSuccess(getActivity(),"Alarm Ended !", Toast.LENGTH_SHORT).show();
                }
            }
        });




        // Share Location fuction
        ImageView shareLocation = (ImageView)v.findViewById(R.id.shareLocation);
        shareLocation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String[] permission = Init.SMS_PERMISSIONS;
                verifyPermission(permission);

                Cursor cursor = databaseHelper.getAllContacts(mEmail);
                ArrayList<String> phones = new ArrayList<>();
                if( cursor.moveToNext()){
                    do{
                        phones.add(cursor.getString(2));
                    }while(cursor.moveToNext());
                }else {
                    CustomToast.popWarning(getActivity(),"Please set emergency phone number first.", Toast.LENGTH_LONG).show();
                    return true;
                }

                String location = getLocationAddress();
                if(!StringUtils.isEmpty(location)){
                    String message = "SOS! I am in danger, my current location is " + location + " Please help me!";
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            sendSMS(phones, message);
                        }
                    }, 1000);
                    //sendSMS(message);
                }
                return true;
            }
        });

        return v;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);
        switch(item.getItemId()){
            case R.id.about:
            navigateTo(AboutUsActivity.class);
            break;
            case R.id.faq:
                navigateTo(FAQActivity.class);
                break;
            case R.id.switch_button:
                return false;
            case R.id.setting:
                passTo(SettingActivity.class, mEmail);
                break;
            case R.id.emergency_contacts:
                passTo(ContactsListActivity.class, mEmail );
                break;
            case R.id.logout:
                showNotification();
                break;
        }
        return false;
    }

    @Override
    public void onResume() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        User user = databaseHelper.userOfEmail(mEmail);

        TextView userName = (TextView)headerLayout.findViewById(R.id.user_name);
        ImageView userImage  = (ImageView) headerLayout.findViewById(R.id.user_image);

        UniversalImagerLoader.setImage(user.getProfileImage(), userImage, null, "");
        userName.setText(user.getName());

        ImageView editUserName = (ImageView) headerLayout.findViewById(R.id.edit_UserName);
        editUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passTo(ChangeUserActivity.class, mEmail);
            }
        });
        super.onResume();
    }

    public void showNotification(){
        AlertDialog alert = new AlertDialog.Builder(getContext()).create();
        alert.setIcon(R.mipmap.edit);
        alert.setTitle("Hint：");
        alert.setMessage("Do you want to Log out?");
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CustomToast.popSuccess(getActivity(),"Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                LoginManager.getInstance().logOut();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                // Build a GoogleSignInClient with the options specified by gso.
                mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                mGoogleSignInClient.signOut();
                SharedPreferences sp = getActivity().getSharedPreferences("Sister", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("Status", false);
                editor.commit();
                navigateTo(LoginActivity.class);
                getActivity().finish();
            }
        });
        alert.show();
    }

    private String getLocationAddress() {
        String add = "";
        Geocoder geoCoder = new Geocoder(getActivity(), Locale.US);
        if(mLocation == null){
            CustomToast.popError(getActivity(), "Cannot get current location!" , Toast.LENGTH_LONG).show();
            return "";
        }
        try {
            List<Address> addresses = geoCoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(),1);
            Address address = addresses.get(0);
            int maxLine = address.getMaxAddressLineIndex();
            if (maxLine >= 2) {
                add = address.getAddressLine(0) + address.getAddressLine(1);
            } else {
                add = address.getAddressLine(0);
            }
        } catch (IOException e) {
            add = "";
            e.printStackTrace();
        }
        return add;
    }

    private void sendSMS(ArrayList<String> phones,String message){
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 0, sentIntent, 0);
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(getActivity(), 0, deliverIntent, 0);
        SmsManager smsManager = SmsManager.getDefault();

        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        CustomToast.popSuccess(getActivity(), "SMS sent" , Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        CustomToast.popError(getActivity(), "Generic failure" , Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        CustomToast.popError(getActivity(), "Error radio off" , Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        CustomToast.popError(getActivity(), "Null PDU" , Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        CustomToast.popError(getActivity(), "No service" , Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                CustomToast.popSuccess(getActivity(), "SMS delivered" , Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));

        for(String phoneNumber : phones){
            smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);
        }
    }

    //____________________________________________________________________________________________________________________________________________
    //Verify Permission
    public void verifyPermission(String[] permissions){
        ActivityCompat.requestPermissions(getActivity(), permissions,REQUEST_CODE);
    }

    public boolean checkPermission(String[] permission){
        int permissionRequest = ActivityCompat.checkSelfPermission(getActivity(), permission[0]);
        if(permissionRequest == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }
    public static void setmLocation(Location location){
        mLocation = location;
    }
}
