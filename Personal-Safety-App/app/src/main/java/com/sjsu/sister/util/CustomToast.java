package com.sjsu.sister.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.sjsu.sister.R;

public class CustomToast {
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    public CustomToast(){

    }

    public static Toast popSuccess(Context context, String message, int duration){
        Builder builder = new Builder(context);
        builder.setDuration(duration);
        builder.setMessage(message);
        builder.setLeftDrawable(R.drawable.ic_baseline_check_circle);
        builder.setLeftDrawableTint(R.color.dirty_green);
        builder.setStripTint(R.color.dirty_green);
        return builder.make();
    }

    public static Toast popError(Context context, String message, int duration){
        Builder builder = new Builder(context);
        builder.setDuration(duration);
        builder.setMessage(message);
        builder.setLeftDrawable(R.drawable.ic_baseline_error);
        builder.setLeftDrawableTint(R.color.red);
        builder.setStripTint(R.color.red);
        return builder.make();
    }

    public static Toast popWarning(Context context, String message, int duration){
        Builder builder = new Builder(context);
        builder.setDuration(duration);
        builder.setMessage(message);
        builder.setLeftDrawable(R.drawable.ic_baseline_warning);
        builder.setLeftDrawableTint(R.color.litght_purple);
        builder.setStripTint(R.color.litght_purple);
        return builder.make();
    }

    public static Toast pop(Context context, String message, int duration){
        Builder builder = new Builder(context);
        builder.setDuration(duration);
        builder.setMessage(message);
        builder.setLeftDrawable(R.drawable.ic_baseline_check_circle);
        builder.setLeftDrawableTint(R.color.purple);
        builder.setStripTint(R.color.purple);
        return builder.make();
    }

    private static class Builder{
        private View rootView;
        private Context mContext;
        private String mMessage;
        private int mDuration;
        private TextView messageTextView;
        private ImageView leftDrawableImageView;
        private int leftDrawableRes;
        private View colorStripView;

        public Builder(Context context){
            mContext = context;
            rootView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.layout_toast, null);
            messageTextView = rootView.findViewById(R.id.message_text_view);
            leftDrawableImageView = rootView.findViewById(R.id.left_drawable_image_view);
            colorStripView = rootView.findViewById(R.id.color_strip_view);
            messageTextView.setVisibility(View.GONE);
            leftDrawableImageView.setVisibility(View.GONE);
            colorStripView.setVisibility(View.GONE);
        }

        private void setMessage(String message){
            mMessage = message;
            messageTextView.setText(message);
            messageTextView.setVisibility(View.VISIBLE);

        }

        private void setDuration(int duration){
            mDuration = duration;
        }

        private void setLeftDrawable(int leftDrawableRes){
            this.leftDrawableRes = leftDrawableRes;
            leftDrawableImageView.setImageResource(leftDrawableRes);
            leftDrawableImageView.setVisibility(View.VISIBLE);
        }

        private void setLeftDrawableTint(int colorRes){
            leftDrawableImageView.setColorFilter(ContextCompat.getColor(mContext, colorRes));
            leftDrawableImageView.setVisibility(View.VISIBLE);
        }

        private void setStripTint(int colorRes){
            colorStripView.setBackgroundColor(ContextCompat.getColor(mContext, colorRes));
            colorStripView.setVisibility(View.VISIBLE);
        }

        private Toast make(){
           Toast toast = new Toast(mContext);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 800);
            toast.setDuration(mDuration);
            toast.setView(rootView);
            return toast;
        }

    }
}
