package com.sjsu.sister.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.sister.R;
import com.sjsu.sister.adapter.AboutUsAdapter;
import com.sjsu.sister.model.AboutUsElement;

import java.util.ArrayList;
import java.util.List;

public class AboutUsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<AboutUsElement> aboutUsElementList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        recyclerView = findViewById(R.id.recycleView);

        initData();
        setRecyclerView();

    }

    private void setRecyclerView(){
        AboutUsAdapter aboutUsAdapter = new AboutUsAdapter(aboutUsElementList,this);
        recyclerView.setAdapter(aboutUsAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData(){
        aboutUsElementList = new ArrayList<>();
        aboutUsElementList.add(new AboutUsElement("Version 1.0",R.drawable.about_ic_version));
        aboutUsElementList.add(new AboutUsElement("Contact with us",R.drawable.about_ic_email));
        aboutUsElementList.add(new AboutUsElement("Facebook",R.drawable.about_ic_facebook));
        aboutUsElementList.add(new AboutUsElement("GitHub",R.drawable.about_ic_github));
        aboutUsElementList.add(new AboutUsElement("Visit our website",R.drawable.about_ic_link));
        aboutUsElementList.add(new AboutUsElement("Twitter",R.drawable.about_ic_twitter));
        aboutUsElementList.add(new AboutUsElement("Youtube",R.drawable.about_ic_youtube));
        aboutUsElementList.add(new AboutUsElement("Play Store",R.drawable.about_ic_google_play));
        aboutUsElementList.add(new AboutUsElement("Instagram",R.drawable.about_ic_instagram));

    }
}
