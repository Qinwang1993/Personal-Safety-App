package com.sjsu.sister.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dueeeke.videocontroller.component.PrepareView;
import com.sjsu.sister.R;
import com.sjsu.sister.model.Videos;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.squareup.picasso.Picasso;

public class ViewSelfDefenseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Videos mVideo;
    private TextView title;
    private TextView description;
    private TextView instruction;
    private ImageView mThumb;
    private PrepareView mPrepareView;
    private FrameLayout mPlayerContainer;
    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewselfdefense);
        mVideo = (Videos) getIntent().getParcelableExtra("Video");
        title = findViewById(R.id.title);
        description = findViewById(R.id.video_description);
        instruction =  findViewById(R.id.video_instruction);
        mPlayerContainer = findViewById(R.id.player_container);
        mPrepareView = findViewById(R.id.prepare_view);
        mThumb = mPrepareView.findViewById(R.id.thumb);

        initVideoView();
        mPlayerContainer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startPlay();
            }
        });

        title.setText(mVideo.getTitle());
        description.setText(mVideo.getDescription());
        instruction.setText(mVideo.getInstruction());
        Picasso.get()
                .load(mVideo.getThumb())
                .into(mThumb);


        // Setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }


    protected int initLayout() {
        return R.layout.activity_viewselfdefense;
    }

    protected void initVideoView() {
        mVideoView = new VideoView(this);
        mController = new StandardVideoController(this);
        mErrorView = new ErrorView(this);
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(this);
        mController.addControlComponent(mCompleteView);
        mController.addControlComponent(new VodControlView(this));
        mController.addControlComponent(new GestureView(this));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    protected void pause() {
        releaseVideoView();
    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    protected void startPlay() {
        mVideoView.setUrl(mVideo.getUrl());
        mController.addControlComponent(mPrepareView, true);
        removeViewFormParent(mVideoView);
        mPlayerContainer.addView(mVideoView, 0);
        mVideoView.start();
    }

    public void removeViewFormParent(View v) {
        if (v == null) return;
        ViewParent parent = v.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(v);
        }
    }




}
