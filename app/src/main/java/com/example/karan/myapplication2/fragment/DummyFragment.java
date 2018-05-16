package com.example.karan.myapplication2.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.HomeViewPagerAdapter;

/**
 * Created by karan on 5/3/2018.
 */

@SuppressLint("ValidFragment")
public class DummyFragment extends Fragment {
    String text;
    TabLayout tabLayout;
    ViewPager viewPager;
    String fragmentArray[] = {"Bookmarks", "History"};

    public DummyFragment(String text) {
        this.text = text;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dummy, container, false);
        tabLayout = getActivity().findViewById(R.id.main_tab_layout);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager = view.findViewById(R.id.favorites_viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupViewPager(ViewPager viewPager) {
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getActivity()
                .getSupportFragmentManager());
        for (int i = 0; i < fragmentArray.length; i++) {
            adapter.addFragment(new FavoritesFragment(fragmentArray[i]), fragmentArray[i]);
        }
        viewPager.setAdapter(adapter);
    }
}
