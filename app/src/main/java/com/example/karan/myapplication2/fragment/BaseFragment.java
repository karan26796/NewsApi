package com.example.karan.myapplication2.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by karan on 5/16/2018.
 */

public abstract class BaseFragment extends Fragment {
    public Toolbar mToolbar;

    protected void setUpToolbar(int i) {

        mToolbar = getActivity().findViewById(getToolbarID());
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            if (i == 0)
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (i == 1)
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    protected abstract int getToolbarID();
}
