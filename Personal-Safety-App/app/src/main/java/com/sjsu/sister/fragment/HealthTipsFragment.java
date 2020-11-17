package com.sjsu.sister.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.sjsu.sister.R;
import com.sjsu.sister.adapter.NewsAdapter;
import com.sjsu.sister.model.News;
import com.sjsu.sister.util.NewsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HealthTipsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthTipsFragment extends Fragment {
    private View v;
    private RecyclerView recyclerView;
    private List<News> mNewsList;

    public static HealthTipsFragment newInstance() {
        HealthTipsFragment fragment = new HealthTipsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_health_tips, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageSlider imageSlider = v.findViewById(R.id.image_slider);
        List<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide5e0395db5c8a4411381b3dd02f92b2511.jpg?sfvrsn=f59d1cb7_2" , null,ScaleTypes.FIT));
        imageList.add(new SlideModel("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide46ba5a3307dd14edea38ef30405aefa0e.jpg?sfvrsn=e574ede0_4" , null,ScaleTypes.FIT));
        imageList.add(new SlideModel("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide3fbe8a4e79bd44a389d26a4a71be7fcef.jpg?sfvrsn=c43cee30_4" , null,ScaleTypes.FIT));
        imageList.add(new SlideModel("https://www.who.int/images/default-source/wpro/health-topic/covid-19/slide27fb9b4e942424bf2af66a7d40c055f94.jpg?sfvrsn=9afbff8f_4" , null,ScaleTypes.FIT));
        imageSlider.setImageList(imageList);

        recyclerView = v.findViewById(R.id.health_recycleView);
        mNewsList = new ArrayList<>();
        initData();
        setRecyclerView();
    }

    private void setRecyclerView(){
        NewsAdapter newsAdapter = new NewsAdapter(mNewsList,getActivity());
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData(){
        List<News> newsList = NewsUtils.getNewsList();
        mNewsList.addAll(newsList);
    }
}
