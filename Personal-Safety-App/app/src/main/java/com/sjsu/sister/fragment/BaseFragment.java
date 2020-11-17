package com.sjsu.sister.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * @author: wei
 * @date: 2020-07-06
 **/
public abstract class BaseFragment extends Fragment {


//    public void showToast(String msg) {
//        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//    }

    public void navigateTo(Class cls) {
        Intent in = new Intent(getActivity(), cls);
        startActivity(in);
    }

    public void navigateToWithBundle(Class cls, Bundle bundle) {
        Intent in = new Intent(getActivity(), cls);
        in.putExtras(bundle);
        startActivity(in);
    }

    public void passTo(Class cls, String message){
        Intent in = new Intent(getActivity(), cls);
        in.putExtra("Email",message);
        startActivity(in);
    }

}
