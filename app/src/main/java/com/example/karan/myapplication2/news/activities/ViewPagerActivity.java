package com.example.karan.myapplication2.news.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.news.adapter.ViewPagerImageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends AppCompatActivity {
    @BindView(R.id.viewPagerTest)
    ViewPager viewPager;
    ArrayList<Integer> images = new ArrayList<>();
    int[] image = {R.drawable.ic_menu_gallery,
            R.drawable.ic_menu_gallery,
            R.drawable.ic_menu_gallery
            , R.drawable.ic_menu_gallery
            , R.drawable.ic_menu_gallery};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);
        for (int i = 0; i < 5; i++) {
            images.add(image[i]);
        }
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(10);
        viewPager.setPadding(50, 8, 50, 8);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new ViewPagerImageAdapter(this, images));

    }
}
