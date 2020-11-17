package com.sjsu.sister.activity;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.sjsu.sister.adapter.HealthCardAdapter;
import com.sjsu.sister.adapter.VideoAdapter;
import com.sjsu.sister.model.HealthCard;
import com.sjsu.sister.R;
import com.sjsu.sister.model.News;
import com.sjsu.sister.model.Videos;
import com.sjsu.sister.util.HealthCardUtils;
import com.sjsu.sister.util.VideoUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewHealthTipsActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private HealthCardAdapter healthCardAdapter;
    private List<HealthCard> mHealthCards;
    private News mNews;
    private TextView title;
    private TextView description;
    private TextView instruction;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewhealthtips);

        mNews = (News) getIntent().getParcelableExtra("News");

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        instruction =  findViewById(R.id.instruction);
        title.setText(mNews.getTitle());
        description.setText(mNews.getDescription());
        instruction.setText(mNews.getInstruction());
        id = mNews.getId();
        mHealthCards = new ArrayList<>();

        initData();

        healthCardAdapter = new HealthCardAdapter(mHealthCards, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(healthCardAdapter);
//        viewPager.setPadding(130, 0, 130, 0);

    }

    private void initData(){
        List<HealthCard> healthCardList = HealthCardUtils.getHealthCards(id);
        mHealthCards.addAll(healthCardList);
    }
}
