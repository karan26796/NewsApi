package com.example.karan.myapplication2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.HomeViewPagerAdapter;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;
import com.example.karan.myapplication2.fragment.MoviesFragment;
import com.example.karan.myapplication2.fragment.NewsFragment;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , View.OnClickListener {

    FloatingActionButton fab;
    DrawerLayout drawer;
    NavigationView navigationView;
    TabLayout tabLayout;
    ViewPager viewPager;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpToolbar(0);
        mAuth = FirebaseAuth.getInstance();

        tabLayout = findViewById(R.id.home_tab_layout);
        viewPager = findViewById(R.id.home_viewpager);
        fab = findViewById(R.id.fab);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        navigationView = findViewById(R.id.nav_view);
        setNavigationView();
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mAuth.getCurrentUser() != null) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(this);
            firebaseAuthentication.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getToolbarID() {
        return R.id.toolbar;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (id == R.id.nav_bookmarks) {
            startActivity(new Intent(this, BookmarkActivity.class));
        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void setupViewPager(ViewPager viewPager) {
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MoviesFragment(), "Movies");
        adapter.addFragment(new NewsFragment(), "News");
        viewPager.setAdapter(adapter);
    }

    private void setNavigationView() {
        TextView mUserEmail, mUserName;
        mUserEmail = navigationView.getHeaderView(0)
                .findViewById(R.id.text_email);
        mUserName = navigationView.getHeaderView(0)
                .findViewById(R.id.text_username);
        if (mAuth.getCurrentUser() != null) {
            mUserEmail.setText(mAuth.getCurrentUser().getEmail());
        }
    }
}
