package com.example.karan.myapplication2.news.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.news.adapter.home.HomeViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karan on 5/3/2018.
 */

public class NewsAllFragment extends Fragment {

    TabLayout tabLayout;
    @BindView(R.id.fragment_viewpager)
    ViewPager viewPager;
    String fragmentArray[] = {"Top News", "Business", "Sports", "Health", "Technology"},
            category, categoryArray[] = {"general", "business", "sports", "health", "technology"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_al_news, container, false);
        ButterKnife.bind(this, view);
        tabLayout = getActivity().findViewById(R.id.main_tab_layout);
        tabLayout.setVisibility(View.VISIBLE);
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
            adapter.addFragment(AllNewsFragment.newInstance("in", categoryArray[i]), fragmentArray[i]);
        }
        viewPager.setAdapter(adapter);
    }
}
