//package com.sjsu.sister.activity;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.ActivityCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Color;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.sjsu.sister.R;
//import com.sjsu.sister.adapter.FakeCallAdapter;
//import com.sjsu.sister.model.Contact;
//import com.sjsu.sister.model.FakeCall;
//import com.sjsu.sister.sqlite.DatabaseHelper;
//import com.sjsu.sister.util.CustomToast;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.loopj.android.http.AsyncHttpClient.log;
//import static com.sjsu.sister.R.raw.police;
//
//public class FakeCallActivity extends AppCompatActivity implements FakeCallAdapter.onItemListClick,FakeCallAdapter.onItemListLongClick{
//    //public class FakeCallActivity extends AppCompatActivity{
//    private String userEmail;
//    private Toolbar mToolbar;
//
//    private RecyclerView fakeCallList;
//    private final ArrayList<FakeCall> fakeCalls = new ArrayList<>();;
//    private FakeCallAdapter fakeCallAdapter;
//    private DatabaseHelper databaseHelper;
//
//
//    private MediaPlayer mediaPlayer = null;
//    private Boolean isPlaying = false;
//    private String fileToPlay = "";
//    private String fileName = "";
//    private RelativeLayout selectedItem;
//
//    private FloatingActionButton fab;
//
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//    protected void onResume() {
//        super.onResume();
//        initView();
//        initData();
//    }
//    private void initView(){
//        setContentView(R.layout.activity_fake_call);
//        userEmail = getIntent().getStringExtra("Email");
//        fakeCallList = (RecyclerView)findViewById(R.id.fakeCall_recycler_view);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(FakeCallActivity.this, HomeActivity.class);
//                System.out.println(userEmail);
//                in.putExtra("Email", userEmail);
//                startActivity(in);
//                finish();
//            }
//        });
//        fab=findViewById(R.id.fabAddFakeCall);
//        fab.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                // Respond to FAB click
//                Intent in = new Intent(FakeCallActivity.this, AddFakeCallActivity.class);
//                in.putExtra("Email",userEmail);
//                startActivity(in);
//                finish();
//            }
//        });
//    }
//    private void initData(){
//        databaseHelper = new DatabaseHelper(this);
//        Cursor cursor = databaseHelper.getAllFakeCalls(userEmail);
//        if( cursor.moveToNext()){
//            do{
//                System.out.println("ContactsList: " +cursor.getString(1));
//                System.out.println("ContactsList: " +cursor.getString(2));
//                fakeCalls.add(new FakeCall(
//                        cursor.getString(1),// name
//                        cursor.getString(2)));// filePath
//            }while(cursor.moveToNext());
//        }
//        fakeCallAdapter = new FakeCallAdapter(fakeCalls, (FakeCallAdapter.onItemListClick) this, (FakeCallAdapter.onItemListLongClick) this);
//        fakeCallList.setHasFixedSize(false);
//        fakeCallList.setLayoutManager(new LinearLayoutManager(this));
//        fakeCallList.setAdapter(fakeCallAdapter);
//    }
//
//    @Override
//    public void onItemClickListener(FakeCall file, int position) throws IOException {
//        log.d("play_log","file_palying"+file.getName());
//        fileToPlay = file.getFilePath();
//        fileName = file.getName();
//        if(isPlaying){
//            stopAudio();
//
//        } else {
//            log.d("Iam here","isplay = false");
//            playAudio(fileToPlay);
//        }
//
//    }
//
//    @Override
//    public void onItemLongClickListener(FakeCall fakeCall, int position) throws IOException {
//        log.d("LongClick","yes");
//        showNotification(fakeCall,position);
//    }
//    public void showNotification(FakeCall fakeCall, int position){
//        AlertDialog alert = new AlertDialog.Builder(this).create();
//        alert.setIcon(R.mipmap.edit);
//        alert.setTitle("Hint：");
//        alert.setMessage("Delete This Fake Call?");
//        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                CustomToast.pop(FakeCallActivity.this, "Cancel", Toast.LENGTH_LONG).show();
//            }
//        });
//        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Sure", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int position) {
////                long click and remove from database
//
//                DatabaseHelper databaseHelper = new DatabaseHelper(FakeCallActivity.this);
//                int fakeCallID = databaseHelper.getFakeCallID(fakeCall);
//                if(fakeCallID > -1){
//                    if(databaseHelper.deleteFakeCall(fakeCallID) > 0){
//                        CustomToast.popSuccess(getBaseContext(),"Fake Call Deleted", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
//                        intent.putExtra("Email", userEmail);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        CustomToast.popError(FakeCallActivity.this,"Database Error", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    CustomToast.popError(FakeCallActivity.this,"Database Error", Toast.LENGTH_SHORT).show();
//                }
//
//                Intent in = new Intent(FakeCallActivity.this, FakeCallActivity.class);
//                in.putExtra("Email",userEmail);
//                startActivity(in);
//                alert.dismiss();
//            }
//        });
//        alert.show();
//    }
//
//
//    private void stopAudio() {
//        isPlaying = false;
//        mediaPlayer.stop();
//        mediaPlayer.release();
//        mediaPlayer = null;
//        CustomToast.popSuccess(this,fileName +" Is Stopped!", Toast.LENGTH_SHORT).show();
//    }
//    private void playAudio(String fileToPlay) throws IOException {
//        mediaPlayer = new MediaPlayer();
//        try{
//            mediaPlayer.setDataSource(fileToPlay);
//            log.d("log_Iam here",fileToPlay);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            CustomToast.popSuccess(this,fileName + " Is Playing " , Toast.LENGTH_SHORT).show();
//            log.d("log_Iam here","startAudio");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//        isPlaying = true;
//    }
//}
//
//






package com.sjsu.sister.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sjsu.sister.R;
import com.sjsu.sister.adapter.FakeCallAdapter;
import com.sjsu.sister.model.Contact;
import com.sjsu.sister.model.FakeCall;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.CustomToast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.loopj.android.http.AsyncHttpClient.log;
import static com.sjsu.sister.R.raw.police;

public class FakeCallActivity extends AppCompatActivity implements FakeCallAdapter.onItemListPlayClickListener,FakeCallAdapter.onItemListDeleteClickListener{
    //public class FakeCallActivity extends AppCompatActivity{
    private String userEmail;
    private Toolbar mToolbar;
    private RelativeLayout emptyView;
    private RecyclerView fakeCallList;
    private FakeCallAdapter fakeCallAdapter;
    private DatabaseHelper databaseHelper;
    private FakeCall currentFakeCall;
    private MediaPlayer mediaPlayer = null;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call);
        userEmail = getIntent().getStringExtra("Email");
        fakeCallList = (RecyclerView)findViewById(R.id.fakeCall_recycler_view);
        emptyView = findViewById(R.id.empty_fake_call_view);
        currentFakeCall = new FakeCall();
        fab = findViewById(R.id.fabAddFakeCall);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Respond to FAB click
                Intent in = new Intent(FakeCallActivity.this, AddFakeCallActivity.class);
                in.putExtra("Email",userEmail);
                startActivity(in);
            }
        });
    }

    protected void onResume() {
        final ArrayList<FakeCall> fakeCalls = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllFakeCalls(userEmail);
        if( cursor.moveToNext()){
            do{
                fakeCalls.add(new FakeCall(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3)));
            }while(cursor.moveToNext());
        }

        if(fakeCalls.size() == 0){
            emptyView.setVisibility(View.VISIBLE);
            fakeCallList.setVisibility(View.INVISIBLE);
        }else{
            emptyView.setVisibility(View.INVISIBLE);
            fakeCallList.setVisibility(View.VISIBLE);
        }
        fakeCallAdapter = new FakeCallAdapter(fakeCalls, this);
        fakeCallAdapter.setOnItemListPlayClickListener(this);
        fakeCallAdapter.setOnItemDeleteClickListener(this);
        fakeCallList.setHasFixedSize(false);
        fakeCallList.setLayoutManager(new LinearLayoutManager(this));
        fakeCallList.setAdapter(fakeCallAdapter);
        super.onResume();
    }

    @Override
    public void onItemListPlayClick(FakeCall fakeCall) throws IOException {
        if(currentFakeCall == null || currentFakeCall.getIsPlay() == 0){
            currentFakeCall = fakeCall;
            playAudio();
        }else{
            if(currentFakeCall.getName().equals(fakeCall.getName())){
                stopAudio();
            }else{
                CustomToast.popError(FakeCallActivity.this, "Another radio is playing...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemListDeleteClick(FakeCall fakeCall) throws IOException {
        if(currentFakeCall == null || currentFakeCall.getIsPlay() == 0){
            showNotification(fakeCall);
        }else{
            CustomToast.popError(FakeCallActivity.this, "An radio is playing, Please try later", Toast.LENGTH_SHORT).show();
        }
    }

    public void showNotification(FakeCall fakeCall){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setIcon(R.mipmap.edit);
        alert.setTitle("Hint：");
        alert.setMessage("Delete This Radio?");
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CustomToast.pop(FakeCallActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                int fakeCallID = databaseHelper.getFakeCallID(fakeCall);
                if(fakeCallID > -1){
                    if(databaseHelper.deleteFakeCall(fakeCallID) > 0){
                        CustomToast.popSuccess(getBaseContext(),"Deleted", Toast.LENGTH_SHORT).show();
                    }else{
                        CustomToast.popError(FakeCallActivity.this,"Database Error", Toast.LENGTH_SHORT).show();
                    }
                    onResume();
                }else{
                    CustomToast.popError(FakeCallActivity.this,"Database Error", Toast.LENGTH_SHORT).show();
                }
                alert.dismiss();
            }
        });
        alert.show();
    }

    private void stopAudio() {
        currentFakeCall.setIsPlay(0);
        int fakeCallID = databaseHelper.getFakeCallID(currentFakeCall);
        databaseHelper.updateFakeCall(userEmail,currentFakeCall,fakeCallID);
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        onResume();
        currentFakeCall = null;
        //CustomToast.popSuccess(this,fileName +" Is Stopped!", Toast.LENGTH_SHORT).show();
    }

    private void playAudio() throws IOException {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                stopAudio();
            }
        });
        try{
            mediaPlayer.setDataSource(currentFakeCall.getFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            //CustomToast.popSuccess(this,fileName + " Is Playing " , Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        int fakeCallID = databaseHelper.getFakeCallID(currentFakeCall);
        currentFakeCall.setIsPlay(1);
        databaseHelper.updateFakeCall(userEmail,currentFakeCall,fakeCallID);
        onResume();
    }
}



