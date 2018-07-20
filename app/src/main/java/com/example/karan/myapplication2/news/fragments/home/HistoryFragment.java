package com.example.karan.myapplication2.news.fragments.home;

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

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.news.adapter.home.HomeViewPagerAdapter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karan on 5/3/2018.
 */

@SuppressLint("ValidFragment")
public class HistoryFragment extends Fragment {

    String text;
    TabLayout tabLayout;
    @BindView(R.id.favorites_viewpager)
    ViewPager viewPager;
    String fragmentArray[] = {"Bookmarks", "History", "ABC News"};

    public HistoryFragment(String text) {
        this.text = text;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common, container, false);
        ButterKnife.bind(this, view);
        tabLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.main_tab_layout);
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
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(Objects.requireNonNull(getActivity())
                .getSupportFragmentManager());

        adapter.addFragment(CommonFragment.newInstance(fragmentArray[0]), fragmentArray[0]);
        adapter.addFragment(CommonFragment.newInstance(fragmentArray[1]), fragmentArray[1]);
        adapter.addFragment(new SourcesFragment(), fragmentArray[2]);
        viewPager.setAdapter(adapter);
    }
}
