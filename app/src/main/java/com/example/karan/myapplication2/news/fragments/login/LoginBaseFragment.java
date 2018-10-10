package com.example.karan.myapplication2.news.fragments.login;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

public class LoginBaseFragment extends Fragment {
    OnLoginClickListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnLoginClickListener) context;
        }catch (ClassCastException e){
            Log.e(LoginBaseFragment.class.getName(),e.getLocalizedMessage());
        }
    }
}
