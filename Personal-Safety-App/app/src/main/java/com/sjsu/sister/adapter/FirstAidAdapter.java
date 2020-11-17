package com.sjsu.sister.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.sister.R;
import com.sjsu.sister.activity.ViewFirstAidActivity;
import com.sjsu.sister.activity.ViewHealthTipsActivity;
import com.sjsu.sister.model.FirstAidEntry;
import com.sjsu.sister.model.News;

import java.util.List;

public class FirstAidAdapter extends RecyclerView.Adapter<FirstAidAdapter.MyViewHolder> {

    private List<FirstAidEntry> firstAidEntries;
    private Context mContext;

    public FirstAidAdapter(List<FirstAidEntry> firstAidEntries, Context mContext) {
        this.firstAidEntries = firstAidEntries;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_aboutus, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_first_aid, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FirstAidEntry mFirstAid = firstAidEntries.get(position);
        holder.title.setText(mFirstAid.getTitle());
        holder.icon.setImageResource(mFirstAid.getIcon());
    }

    @Override
    public int getItemCount() {
        return firstAidEntries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.icon);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);

            constraintLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    FirstAidEntry mFirstAid = firstAidEntries.get(getAdapterPosition());
                    Intent in = new Intent(mContext, ViewFirstAidActivity.class);
                    in.putExtra("Id", mFirstAid.getId());
                    mContext.startActivity(in);
                }
            });

        }
    }
}
