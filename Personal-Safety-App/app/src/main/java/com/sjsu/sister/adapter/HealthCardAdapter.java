package com.sjsu.sister.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sjsu.sister.model.HealthCard;
import com.sjsu.sister.R;
import java.util.List;

public class HealthCardAdapter extends PagerAdapter {

    private List<HealthCard> healthCards;
    private LayoutInflater layoutInflater;
    private Context context;

    public HealthCardAdapter(List<HealthCard> healthCards, Context context) {
        this.healthCards = healthCards;
        this.context = context;
    }

    @Override
    public int getCount() {
        return healthCards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_healthcard, container, false);

        ImageView imageView;
        TextView title, desc;
        ProgressBar progressBar;
        HealthCard healthCard = healthCards.get(position);

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);
        progressBar = view.findViewById(R.id.progressBar);

        title.setText(healthCard.getTitle());
        desc.setText(healthCard .getDesc());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(healthCard.getUrl(), imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.GONE);
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
