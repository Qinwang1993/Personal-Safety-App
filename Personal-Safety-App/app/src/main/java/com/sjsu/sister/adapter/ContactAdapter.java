package com.sjsu.sister.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sjsu.sister.R;
import com.sjsu.sister.model.Contact;
import com.sjsu.sister.model.User;
import com.sjsu.sister.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
public class ContactAdapter extends ArrayAdapter<Contact> {
    private LayoutInflater mInflater;
    private List<Contact> mContacts = null;
    private ArrayList<Contact> arrayList;
    private int layoutResource;
    private Context mContext;
    private String mAppend;
    private String userEmail;

    public ContactAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Contact> contacts, String append, String userEmail){
        super(context,resource,contacts);
       mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       layoutResource = resource;
       this.mContext = context;
       mAppend = append;
       this.mContacts = contacts;
       arrayList = new ArrayList<>();
       this.arrayList.addAll(mContacts);
       this.userEmail = userEmail;
    }

    private static class ContactViewHolder {
        TextView name;
        CircleImageView contactImage;
        ProgressBar mProgressBar;
        ImageView heart;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final ContactViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ContactViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.contact_name);
            holder.contactImage = (CircleImageView)convertView.findViewById(R.id.contact_image);
            holder.mProgressBar = (ProgressBar)convertView.findViewById(R.id.contact_progressBar);
            holder.heart = (ImageView)convertView.findViewById(R.id.heart);
            convertView.setTag(holder);

        }else{
            holder = (ContactViewHolder) convertView.getTag();
        }
        String name = getItem(position).getName();
        String imagePath = getItem(position).getProfileImage();
        String phoneNumber = getItem(position).getPhoneNumber();

        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        User mUser = databaseHelper.userOfEmail(userEmail);
        System.out.println(userEmail);
        System.out.println(mUser.getEmergencyCall());
        if(phoneNumber.equals(mUser.getEmergencyCall())){
            System.out.println("get Emergency Call:" + phoneNumber);
            holder.heart.setVisibility(View.VISIBLE);
           // holder.heart.setImageResource(R.drawable.ic_heart);
        }
        else{
            holder.heart.setVisibility(View.INVISIBLE);
        }
        holder.name.setText(name);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mAppend + imagePath, holder.contactImage, new ImageLoadingListener() {
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
        return convertView;
    }

    public void filter(String text){
        text = text.toLowerCase(Locale.getDefault());
        mContacts.clear();
        if( text.length() == 0){
            mContacts.addAll(arrayList);
        }else{
            mContacts.clear();
            for(Contact contact : arrayList){
                if(contact.getName().toLowerCase(Locale.getDefault()).contains(text)){
                    mContacts.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }

}
