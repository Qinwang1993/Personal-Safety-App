package com.sjsu.sister.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.sister.R;
import com.sjsu.sister.model.AboutUsElement;
import com.sjsu.sister.util.AboutUsUtils;
import com.sjsu.sister.util.CustomToast;

import java.util.List;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.MyViewHolder> {

        List<AboutUsElement> aboutUsElementList;
        Context mContext;

        public AboutUsAdapter(List<AboutUsElement> aboutUsElementList, Context mContext){
            this.aboutUsElementList = aboutUsElementList;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_aboutus, parent, false);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_aboutus, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            AboutUsElement aboutUsElement = aboutUsElementList.get(position);
            holder.title.setText(aboutUsElement.getTitle());
            holder.iconDrawable.setImageResource(aboutUsElement.getIconDrawable());
        }

        @Override
        public int getItemCount() {
            return aboutUsElementList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView title;
            ImageView iconDrawable;
            RelativeLayout relativeLayout;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.text_middle);
                iconDrawable = itemView.findViewById(R.id.icon_left);
                relativeLayout = itemView.findViewById(R.id.relative_layout);
                relativeLayout.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        AboutUsElement aboutUsElement = aboutUsElementList.get(getAdapterPosition());
                        String element = aboutUsElement.getTitle();
                        switch(element){
                            case "Visit our website":
                                CustomToast.pop(mContext, "Waiting to be build our website", Toast.LENGTH_SHORT).show();
                                break;

                            case "Instagram":
                                Intent instagramIntent = new Intent();
                                instagramIntent.setAction(Intent.ACTION_VIEW);
                                instagramIntent.setData(Uri.parse("http://instagram.com/_u/" + "xiaoshuixinzr"));

                                if (AboutUsUtils.isAppInstalled(mContext, "com.instagram.android")) {
                                    instagramIntent.setPackage("com.instagram.android");
                                }
                                mContext.startActivity(instagramIntent);
                                break;

                            case "Twitter":
                                Intent twitterIntent = new Intent();
                                twitterIntent.setAction(Intent.ACTION_VIEW);
                                twitterIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                                if (AboutUsUtils.isAppInstalled(mContext, "com.twitter.android")) {
                                    twitterIntent.setPackage("com.twitter.android");
                                    twitterIntent.setData(Uri.parse(String.format("twitter://user?screen_name=%s", "Xiaoshuixin")));
                                } else {
                                    twitterIntent.setData(Uri.parse(String.format("http://twitter.com/intent/user?screen_name=%s", "Xiaoshuixin")));
                                }
                                mContext.startActivity(twitterIntent);
                                break;

                            case "Youtube":
                                Intent youtubeIntent = new Intent();
                                youtubeIntent.setAction(Intent.ACTION_VIEW);
                                youtubeIntent.setData(Uri.parse(String.format("http://youtube.com/channel/%s", "UCOfNQwWju_ZcumRoLGYRF-w")));

                                if (AboutUsUtils.isAppInstalled(mContext, "com.google.android.youtube")) {
                                    youtubeIntent.setPackage("com.google.android.youtube");
                                }
                                mContext.startActivity(youtubeIntent);
                                break;

                            case "Play Store":
                                CustomToast.pop(mContext, "Waiting to be added to the play store", Toast.LENGTH_SHORT).show();
                                break;

                            case "GitHub":
                                Intent githubIntent = new Intent();
                                githubIntent.setAction(Intent.ACTION_VIEW);
                                githubIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                                githubIntent.setData(Uri.parse(String.format("https://github.com/%s","Qinwang1993/PersonalSafety-App")));
                                mContext.startActivity(githubIntent);
                                break;
                            case "Facebook":
                                Intent facebookIntent = new Intent();
                                facebookIntent.setAction(Intent.ACTION_VIEW);
                                facebookIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                                if (AboutUsUtils.isAppInstalled(mContext, "com.facebook.katana")) {
                                    facebookIntent.setPackage("com.facebook.katana");
                                    int versionCode = 0;
                                    try {
                                        versionCode = mContext.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    if (versionCode >= 3002850) {
                                        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + "http://m.facebook.com/" + "qin.wang.14661261");
                                        facebookIntent.setData(uri);
                                    } else {
                                        Uri uri = Uri.parse("fb://page/" + "qin.wang.14661261");
                                        facebookIntent.setData(uri);
                                    }
                                } else {
                                    facebookIntent.setData(Uri.parse("http://m.facebook.com/" + "qin.wang.14661261"));
                                }
                                mContext.startActivity(facebookIntent);
                                break;

                            case "Contact with us":
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                                emailIntent.setData(Uri.parse("mailto:" + "sisiter@gmail.com"));
                                mContext.startActivity(emailIntent);
                                break;
                        }
                        notifyItemChanged(getAdapterPosition());
                    }
                });

            }
        }
}
