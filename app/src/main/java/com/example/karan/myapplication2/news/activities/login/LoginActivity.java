package com.example.karan.myapplication2.news.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.news.activities.BaseActivity;
import com.example.karan.myapplication2.news.activities.home.MainActivity;
import com.example.karan.myapplication2.news.adapter.home.HomeViewPagerAdapter;
import com.example.karan.myapplication2.news.fragments.login.SignInFragment;
import com.example.karan.myapplication2.news.fragments.login.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpToolbar(1);

        mTabLayout = findViewById(R.id.tab_layout_login);
        mViewPager = findViewById(R.id.login_viewpager);

        setmViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected int getToolbarID() {
        return R.id.login_activity_toolbar;
    }

    @Override
    public void onClick(View v) {
    }

    private void setmViewPager(ViewPager viewPager) {
        HomeViewPagerAdapter viewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new SignInFragment(), "Sign In");
        viewPagerAdapter.addFragment(new SignUpFragment(), "Sign Up");

        viewPager.setAdapter(viewPagerAdapter);
    }
}
