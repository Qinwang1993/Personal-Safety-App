package com.sjsu.sister.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.sister.R;
import com.sjsu.sister.adapter.VideoAdapter;
import com.sjsu.sister.model.Videos;
import com.sjsu.sister.util.VideoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelfDefenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelfDefenseFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Videos> mVideoList;
    private View v;

    public static SelfDefenseFragment newInstance() {
        SelfDefenseFragment fragment = new SelfDefenseFragment();

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
        v = inflater.inflate(R.layout.fragment_self_defense, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = v.findViewById(R.id.recyclerView);
        mVideoList = new ArrayList<>();
        initData();
        setRecyclerView();

    }

    private void setRecyclerView(){
        VideoAdapter videoAdapter = new VideoAdapter(mVideoList,getActivity());
        recyclerView.setAdapter(videoAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData(){
        List<Videos> videoList = VideoUtils.getVideoList();
        mVideoList.addAll(videoList);
    }
}
