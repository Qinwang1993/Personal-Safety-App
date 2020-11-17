package com.sjsu.sister.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.sister.R;
import com.sjsu.sister.model.Cafe;

import java.util.List;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.MyViewHolder>{
    private List<Cafe> cafeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView vicinity, rating, name;

        public MyViewHolder(View view) {
            super(view);
            vicinity = (TextView) view.findViewById(R.id.vicinity);
            name = (TextView) view.findViewById(R.id.name);
            rating = (TextView) view.findViewById(R.id.rating);
        }
    }

    public CafeAdapter(List<Cafe> cafeList) {

        this.cafeList = cafeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cafe_list, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cafe cafe = cafeList.get(position);
        holder.vicinity.setText(cafe.getVicinity());
        holder.name.setText(cafe.getName());
        holder.rating.setText(cafe.getRating());
    }

    @Override
    public int getItemCount() {

        return cafeList.size();
    }

}