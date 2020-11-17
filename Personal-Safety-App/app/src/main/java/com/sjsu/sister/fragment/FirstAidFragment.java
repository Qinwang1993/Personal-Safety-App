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
import com.sjsu.sister.adapter.FirstAidAdapter;
import com.sjsu.sister.adapter.NewsAdapter;
import com.sjsu.sister.model.FirstAidEntry;
import com.sjsu.sister.model.News;
import com.sjsu.sister.util.NewsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstAidFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstAidFragment extends Fragment {
    private View v;
    private RecyclerView recyclerView;
    private List<FirstAidEntry> firstAidEntries;

    public static FirstAidFragment newInstance() {
        FirstAidFragment fragment = new FirstAidFragment();

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
        v = inflater.inflate(R.layout.fragment_first_aid, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = v.findViewById(R.id.recycler_view);
        firstAidEntries = new ArrayList<>();
        firstAidEntries.add(new FirstAidEntry("CPR", R.mipmap.heart_attack,0));
        firstAidEntries.add(new FirstAidEntry("Bleeding", R.mipmap.diabetic_emergency,1));
        firstAidEntries.add(new FirstAidEntry("Burns", R.mipmap.burns,2));
        firstAidEntries.add(new FirstAidEntry("Bites", R.mipmap.sting,3));
        firstAidEntries.add(new FirstAidEntry("Frostbite", R.mipmap.snowflake,4));
        firstAidEntries.add(new FirstAidEntry("Wound", R.mipmap.wound,5));
        firstAidEntries.add(new FirstAidEntry("Choking", R.mipmap.choking,6));


        FirstAidAdapter firstAidAdapter = new FirstAidAdapter(firstAidEntries,getActivity());
        recyclerView.setAdapter(firstAidAdapter);
        recyclerView.setHasFixedSize(true);
    }
}
