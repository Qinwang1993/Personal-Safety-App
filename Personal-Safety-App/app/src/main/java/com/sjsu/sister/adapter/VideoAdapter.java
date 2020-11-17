package com.sjsu.sister.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sjsu.sister.R;
import com.sjsu.sister.activity.ViewSelfDefenseActivity;
import com.sjsu.sister.model.Videos;

import java.util.List;

public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    List<Videos> mVideoList;
    Context mContext;

    public VideoAdapter(List<Videos> videoList, Context mContext){
        this.mVideoList = videoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_aboutus, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_self_defense, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Videos mVideo = mVideoList.get(position);
        holder.title.setText(mVideo.getTitle());
        holder.description.setText(mVideo.getDescription());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mVideo.getThumb(), holder.coverImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        ImageView coverImage;
        ProgressBar mProgressBar;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.video_description);
            coverImage = itemView.findViewById(R.id.coverImage);
            mProgressBar = itemView.findViewById(R.id.video_progressBar);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);

            constraintLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Videos mVideo = mVideoList.get(getAdapterPosition());
                    Intent in = new Intent(mContext, ViewSelfDefenseActivity.class);
                    in.putExtra("Video", mVideo);
                    mContext.startActivity(in);
                    //CustomToast.pop(mContext,"click on" + mVideo.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
