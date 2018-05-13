package com.example.karan.myapplication2.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.karan.myapplication2.fragment.AllNewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan on 5/13/2018.
 */

public class AllNewsFragmentAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();

    private final List<String> mFragmentTitleList = new ArrayList<>();
    private AllNewsFragment fragA;
    private AllNewsFragment fragB;
    private AllNewsFragment fragC;

    public AllNewsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(Context c) {
        // Set up the simple base fragments
        fragA = new AllNewsFragment();
        fragB = new AllNewsFragment();
        fragC = new AllNewsFragment();

        Resources res = c.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        // TODO: Make this more efficient, use a list or such, also comment more
        Fragment frag = null;
        if (position == 0) {
            frag = fragA;
        } else if (position == 1) {
            frag = fragB;
        } else if (position == 2) {
            frag = fragC;
        }

        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
