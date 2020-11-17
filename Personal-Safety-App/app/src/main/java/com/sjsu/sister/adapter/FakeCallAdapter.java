package com.sjsu.sister.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.sister.R;
import com.sjsu.sister.model.FakeCall;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.entity.mime.content.ContentBody;

public class FakeCallAdapter extends RecyclerView.Adapter<FakeCallAdapter.FakeCallViewHolder>{
    private Context mContext;
    private List<FakeCall> fakeCallList;
    private onItemListPlayClickListener onItemListPlayClickListener;
    private onItemListDeleteClickListener onItemListDeleteClickListener;

    public FakeCallAdapter(List<FakeCall> fakeCallList, Context mContext){
        this.fakeCallList  = fakeCallList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FakeCallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fake_call_list,null);
        return new FakeCallViewHolder(itemView);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull FakeCallViewHolder holder, int position) {
        FakeCall fakeCall = fakeCallList.get(position);
        holder.fakeCallName.setText(fakeCall.getName());
        if(fakeCall.getIsPlay() == 0){
            holder.fakeCallIcon.setImageResource(R.drawable.ic_play);
            holder.oneFakeCallLayout.setBackgroundColor(Color.WHITE);
        }else{
            holder.fakeCallIcon.setImageResource(R.drawable.ic_pause);
            holder.oneFakeCallLayout.setBackgroundColor(Color.parseColor("#E6E6FA"));
        }

        holder.fakeCallIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onItemListPlayClickListener.onItemListPlayClick(fakeCall);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.fakeCallDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onItemListDeleteClickListener.onItemListDeleteClick(fakeCall);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class FakeCallViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout oneFakeCallLayout;
        public ImageView fakeCallIcon;
        public ImageView fakeCallDelete;
        public TextView fakeCallName;

        public FakeCallViewHolder(View itemView) {
            super(itemView);
            oneFakeCallLayout = itemView.findViewById(R.id.one_fake_call);
            fakeCallIcon = itemView.findViewById(R.id.radio_play);
            fakeCallDelete = itemView.findViewById(R.id.radio_delete);
            fakeCallName = itemView.findViewById(R.id.fake_call_name);
        }


    }

    @Override
    public int getItemCount() { return fakeCallList.size(); }

    public interface onItemListPlayClickListener {void onItemListPlayClick(FakeCall fakeCall) throws IOException;}
    public interface onItemListDeleteClickListener {void onItemListDeleteClick(FakeCall fakeCall) throws IOException;}

    public void setOnItemListPlayClickListener(onItemListPlayClickListener mOnItemListPlayClickListener) {
        this.onItemListPlayClickListener = mOnItemListPlayClickListener;
    }
    public void setOnItemDeleteClickListener(onItemListDeleteClickListener mOnItemDeleteClickListener) {
        this.onItemListDeleteClickListener = mOnItemDeleteClickListener;
    }
}

