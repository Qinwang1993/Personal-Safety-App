package com.sjsu.sister.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.sister.R;
import com.sjsu.sister.model.FakeCall;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.StringUtils;
import com.sjsu.sister.view.CustomCircleProgressBar;
import com.sjsu.sister.view.CustomPlayProgressBar;

import java.io.IOException;
import java.util.Timer;

import static com.loopj.android.http.AsyncHttpClient.log;


public class AddFakeCallActivity extends AppCompatActivity{
    private Toolbar mToolbar;
    private String userEmail;

    private ImageView retry;
    private ImageView save;
    private TextView textView;
    private EditText inputFakeCallName;
    private CustomCircleProgressBar mCircleRecordView;
    private CustomPlayProgressBar mCirclePlayView;
    private Button addButton;
    private boolean isRecording = false;
    private boolean isPlay = false;
    private boolean isComplete = true;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String recordPathDir;
    private String recordFileName = "";
    private String filePath = "";

    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fake_call);
        userEmail = getIntent().getStringExtra("Email");
        textView = findViewById(R.id.record_tips);
        inputFakeCallName = findViewById(R.id.input_fakecall_name);
        checkPermissions();
        mCircleRecordView = findViewById(R.id.record);
        mediaPlayer = new MediaPlayer();

        mCircleRecordView.setDuration(60);
        mCircleRecordView.setStartBitmap(R.mipmap.audio_record_mic_btn);
        mCircleRecordView.setStopBitmap(R.mipmap.audio_record_mic_btn_press);
        mCircleRecordView.setArcColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        mCircleRecordView.setSmallCircleColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        mCircleRecordView.setDefaultRadius(100);
        mCircleRecordView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mCircleRecordView.startDraw();
                        startRecording();//Start Recording
                        break;
                    case MotionEvent.ACTION_UP:
                        mCircleRecordView.reset();
                        mCircleRecordView.stopDraw();
                        stopRecording();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        mCirclePlayView = findViewById(R.id.play);
        retry = findViewById(R.id.icon_retry);
        save = findViewById(R.id.icon_save);
        retry.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        mCirclePlayView.setVisibility(View.INVISIBLE);
        mCirclePlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlay) {
                    playAudio();
                } else {
                    pauseAudio();
                }
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);
                mCirclePlayView.setVisibility(View.INVISIBLE);
                mCircleRecordView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordFileName = inputFakeCallName.getText().toString().trim().replaceAll(" ", "_");
                if(StringUtils.isEmpty(recordFileName)){
                    CustomToast.popWarning(getBaseContext(),"Please Input Fake Call Name!", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseHelper databaseHelper = new DatabaseHelper(AddFakeCallActivity.this);
                    if(databaseHelper.checkFakeCall(recordFileName,userEmail)){
                        CustomToast.popError(AddFakeCallActivity.this, "Fake Call Name already Exist!", Toast.LENGTH_SHORT).show();
                    }else{
                        databaseHelper = new DatabaseHelper(AddFakeCallActivity.this);
                        FakeCall fakeCall = new FakeCall(recordFileName,filePath,0);
                        if(databaseHelper.addFakeCall(userEmail, fakeCall)){
                            CustomToast.popSuccess(AddFakeCallActivity.this, "Added Successfully !", Toast.LENGTH_SHORT).show();
                        }else{
                            CustomToast.popError(AddFakeCallActivity.this, "Error Saving", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    private void stopRecording() {
        //Stop media recorder and set it to null for further use to record new audio
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        mCircleRecordView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        mCirclePlayView.setVisibility(View.VISIBLE);
        retry.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
    }

    private void startRecording() {
        recordPathDir = this.getExternalFilesDir("/").getAbsolutePath();
        filePath = recordPathDir + "/" + recordFileName + ".3gp";
        //Setup Media Recorder for recording
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(filePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Start Recording
        mediaRecorder.start();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    private void playAudio() {
        try {
            if (isComplete) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepare();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopAudio();
                    }
                });
                mCirclePlayView.clearDuration();
                isComplete = false;
            }
            mediaPlayer.start();
            mCirclePlayView.setDuration(mediaPlayer.getDuration());
            mCirclePlayView.play();
            isPlay = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pauseAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mCirclePlayView.pause();
            isPlay = false;
        }
    }

    private void stopAudio() {
        mCirclePlayView.stop();
        isPlay = false;
        isComplete = true;
    }

    private boolean checkPermissions() {//Check permission
        if (ActivityCompat.checkSelfPermission(this, recordPermission) == PackageManager.PERMISSION_GRANTED) {return true;}//Permission Granted
        else {ActivityCompat.requestPermissions(this, new String[]{recordPermission}, PERMISSION_CODE); return false; }//Permission not granted, ask for permission

    }
}
