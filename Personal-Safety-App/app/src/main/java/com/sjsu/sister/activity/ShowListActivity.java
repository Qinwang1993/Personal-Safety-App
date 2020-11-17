package com.sjsu.sister.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.sister.R;
import com.sjsu.sister.adapter.CafeAdapter;
import com.sjsu.sister.model.Cafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ShowListActivity extends BaseActivity{
    private String url;
    private List<HashMap<String, String>> dataList;
    private RecyclerView recyclerView;
    private CafeAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Cafe> cafeList = new ArrayList<>();
//    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        Intent intent = getIntent();
        dataList = (List<HashMap<String, String>>) intent.getSerializableExtra("data");
        processData(dataList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new CafeAdapter(cafeList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void processData(List<HashMap<String, String>> data) {
        for (int i = 0; i < data.size(); i++) {
            HashMap<String, String> dataList = data.get(i);
            String name = dataList.get("name");
            String rating = dataList.get("rating");
            String vicinity = dataList.get("vicinity");
            String lat = dataList.get("lat");
            String lng = dataList.get("lng");
            cafeList.add(new Cafe(vicinity, name, rating,lat, lng));
        }
        Collections.sort(cafeList, new Comparator<Cafe>(){
            public int compare (Cafe o1, Cafe o2){
                if (Double.parseDouble(o2.getRating())- Double.parseDouble(o1.getRating()) > 0)
                    return 1;
                else if (Double.parseDouble(o2.getRating()) - Double.parseDouble(o1.getRating()) == 0)
                    return 0;
                else
                    return -1;
            }
        });
    }
}