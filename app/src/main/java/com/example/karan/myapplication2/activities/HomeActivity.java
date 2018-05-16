package com.example.karan.myapplication2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.HomeViewPagerAdapter;
import com.example.karan.myapplication2.adapter.OptionsAdapter;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;
import com.example.karan.myapplication2.fragment.MoviesFragment;
import com.example.karan.myapplication2.fragment.NewsFragment;
import com.example.karan.myapplication2.model.Options;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , View.OnClickListener {

    FloatingActionButton fab;
    DrawerLayout drawer;
    NavigationView navigationView;
    TabLayout tabLayout;
    ViewPager viewPager;
    FirebaseAuth mAuth;
    ArrayList<Options> mOptionsList;

    RecyclerView recyclerView;
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;

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
        recyclerView = layoutBottomSheet.findViewById(R.id.options_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupViewPager(viewPager);
        layoutBottomSheet = findViewById(R.id.bottom_sheet_options);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        //sets the behaviour of linear layout to a bottom sheet
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setPeekHeight(0);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {

                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //fabBottomSheet.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward));
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        //fabBottomSheet.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward));
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
        setRecyclerView();
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
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MainActivity.class));
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
        } else if (id == R.id.nav_bookmarks) {
        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
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

    private void setRecyclerView() {
        mOptionsList = new ArrayList<>();
        String optionsTitle[] = {"Bookmark", "History", "Exit", "Enter"};
        int optionsDrawable[] = {R.drawable.ic_bookmark, R.drawable.ic_info,
                R.drawable.ic_close, R.drawable.ic_menu_gallery};
        for (int i = 0; i < optionsTitle.length; i++) {
            mOptionsList.add(new Options(optionsTitle[i], optionsDrawable[i]));
        }
        recyclerView.setAdapter(new OptionsAdapter(mOptionsList, new OptionsAdapter.optionsClickListener() {
            @Override
            public void onOptionsClicked(View view, int position) {

            }
        }));
    }
}
