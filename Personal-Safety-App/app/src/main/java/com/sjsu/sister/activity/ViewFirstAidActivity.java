package com.sjsu.sister.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sjsu.sister.R;
import com.sjsu.sister.adapter.HealthCardAdapter;
import com.sjsu.sister.model.FirstAids;
import com.sjsu.sister.model.HealthCard;
import com.sjsu.sister.model.News;
import com.sjsu.sister.util.FirstAidUtils;
import com.sjsu.sister.util.HealthCardUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewFirstAidActivity extends AppCompatActivity {
    private FirstAids mFirstAid;
    private int id;
    private ImageView image;
    private TextView title;
    private TextView content1;
    private TextView subtitle;
    private TextView content2;
    private TextView content3;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfirstaid);

        id = getIntent().getIntExtra("Id",0);

        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        content1 = findViewById(R.id.content1);
        content2 = findViewById(R.id.content2);
        content3 = findViewById(R.id.content3);
        image = findViewById(R.id.img_header);
        mProgressBar = findViewById(R.id.progressBar);

        mFirstAid = FirstAidUtils.getFirstAid(id);

        title.setText(mFirstAid.getTitle());
        subtitle.setText(mFirstAid.getSubtitle());
        content1.setText(mFirstAid.getContent1());
        content2.setText(mFirstAid.getContent2());
        content3.setText(mFirstAid.getContent3());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mFirstAid.getUrl(), image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
