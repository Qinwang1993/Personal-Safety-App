package com.sjsu.sister.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.sister.R;
import com.sjsu.sister.activity.ChangeUserActivity;
import com.sjsu.sister.activity.ContactsListActivity;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.UniversalImagerLoader;

public class UserFragment extends BaseFragment {
    private View v;


    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_user, container, false);

        ImageView addContacts = (ImageView) v.findViewById(R.id.add_contacts);
        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(ContactsListActivity.class);
            }
        });

        ImageView editUserName = (ImageView) v.findViewById(R.id.edit);
        editUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Qin Wang : click pen", null);
                showNotification();
            }
        });
        return v;
    }

    public void showNotification(){
        AlertDialog alert = new AlertDialog.Builder(getContext()).create();
        alert.setIcon(R.mipmap.edit);
        alert.setTitle("Hint：");
        alert.setMessage("Do you want to modify your username?");
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                CustomToast.popSuccess(getActivity(),"Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                navigateTo(ChangeUserActivity.class);
            }
        });
        alert.show();
    }

    @Override
    public void onResume() {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("Sister", Context.MODE_PRIVATE);
        String user_name= sharedPreferences.getString("Name","");
        String imagePath = sharedPreferences.getString("Image","");
        TextView userName = (TextView)v.findViewById(R.id.user_name);
        userName.setText(user_name);
        ImageView userImage  = (ImageView) v.findViewById(R.id.img_header);
        UniversalImagerLoader.setImage(imagePath, userImage, null, "");
        super.onResume();
    }

}
