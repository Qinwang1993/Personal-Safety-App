package com.sjsu.sister.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sjsu.sister.R;

import java.io.File;


public class ChangePhotoDialog extends DialogFragment {
    public interface OnPhotoReceivedListener{
        public void getBitmapImage(Bitmap bitmap);
        public void getImagePath(String imagePath);

    }
    OnPhotoReceivedListener mOnPhotoReceived;

    private static final String TAG = "Qin Wang";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog_changephoto, container, false);


        TextView takePhoto = (TextView) view.findViewById(R.id.dialog_TakePhoto);
        TextView chosePhoto = (TextView) view.findViewById(R.id.dialog_ChosePhoto);
        TextView cancel = (TextView) view.findViewById(R.id.dialog_Cancel);
        takePhoto.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,Init.CAMERA_REQUEST_CODE);

            }
        });

        chosePhoto.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent chosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                chosePhotoIntent.setType("image/*");
                startActivityForResult(chosePhotoIntent, Init.CHOSE_PHOTO_REQUEST_CODE);


            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                getDialog().dismiss();

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mOnPhotoReceived = (OnPhotoReceivedListener) getActivity();

        } catch (Exception e) {
            Log.e(TAG,"onAttach: ClassCastException:" + e.getMessage());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        // Take photo
        if(requestCode == Init.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mOnPhotoReceived.getBitmapImage(bitmap);
            getDialog().dismiss();
        }

        // Chose Photo
        if(requestCode == Init.CHOSE_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();
            File file = new File(selectedImageUri.toString());
            mOnPhotoReceived.getImagePath(file.getPath());
            getDialog().dismiss();
        }
    }

}
