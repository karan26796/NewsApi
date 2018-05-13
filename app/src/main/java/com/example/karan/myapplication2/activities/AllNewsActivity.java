package com.example.karan.myapplication2.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.HomeViewPagerAdapter;
import com.example.karan.myapplication2.fragment.AllNewsFragment;
import com.example.karan.myapplication2.fragment.NewsFragment;

public class AllNewsActivity extends BaseActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    String fragmentArray[] = {"Top News",
            "Business", "Health", "Sports"}, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);
        setUpToolbar(0);
        mToolbar.setTitle("All News");

        viewPager = findViewById(R.id.all_news_viewpager);
        tabLayout = findViewById(R.id.all_news_tab_layout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        tabLayout.getTabAt(getCategory()).select();
                    }
                }, 100);
    }

    @Override
    protected int getToolbarID() {
        return R.id.activity_all_news_toolbar;
    }

    private void setupViewPager(ViewPager viewPager) {
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < 4; i++) {
            adapter.addFragment(new AllNewsFragment(), fragmentArray[i]);
        }
        viewPager.setAdapter(adapter);
    }

    private int getCategory() {
        if (getIntent().getStringExtra("category") != null) {
            category = getIntent().getStringExtra("category");
            Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
            if (category.equals(NewsFragment.Categories.TOP_NEWS.toString()))
                return 0;
            else if (category.equals(NewsFragment.Categories.BUSINESS.toString()))
                return 1;
            else if (category.equals(NewsFragment.Categories.HEALTH.toString()))
                return 2;
            else if (category.equals(NewsFragment.Categories.SPORTS.toString()))
                return 3;
        }
        return 0;
    }
}
