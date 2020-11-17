package com.sjsu.sister.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sjsu.sister.R;
import com.sjsu.sister.activity.ViewHealthTipsActivity;
import com.sjsu.sister.activity.ViewSelfDefenseActivity;
import com.sjsu.sister.model.News;
import com.sjsu.sister.model.Videos;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

        List<News> newsList;
        Context mContext;

    public NewsAdapter(List<News> videoList, Context mContext){
            this.newsList = videoList;
            this.mContext = mContext;
            }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_aboutus, parent, false);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news, null);
            return new MyViewHolder(view);
            }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            News mNews = newsList.get(position);
            holder.title.setText(mNews.getTitle());
            holder.description.setText(mNews.getDescription());
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(mNews.getUrl(), holder.coverImage, new ImageLoadingListener() {
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
            return newsList.size();
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
            description = itemView.findViewById(R.id.description);
            coverImage = itemView.findViewById(R.id.coverImage);
            mProgressBar = itemView.findViewById(R.id.progressBar);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);

            constraintLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    News mNews = newsList.get(getAdapterPosition());
                    Intent in = new Intent(mContext, ViewHealthTipsActivity.class);
                    in.putExtra("News", mNews);
                    mContext.startActivity(in);
//                    System.out.println("News send");
                    //CustomToast.pop(mContext,"click on" + mVideo.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
