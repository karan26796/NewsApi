package com.example.karan.myapplication2.news.activities.home;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;
import com.example.karan.myapplication2.news.activities.BaseActivity;
import com.example.karan.myapplication2.news.fragments.home.BottomSheetDialog;
import com.example.karan.myapplication2.news.fragments.home.HistoryFragment;
import com.example.karan.myapplication2.news.fragments.home.NewsAllFragment;
import com.example.karan.myapplication2.news.fragments.home.SearchFragment;
import com.example.karan.myapplication2.news.fragments.home.TopNewsFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener {
    BottomSheetDialog bottomSheetDialog;
    FirebaseAuthentication firebaseAuthentication;
    public TabLayout tabLayout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar(1);
        bottomSheetDialog = new BottomSheetDialog();
        if (savedInstanceState == null) {
            inflateFragment(new TopNewsFragment());
        }

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthentication = new FirebaseAuthentication(this);

        tabLayout = findViewById(R.id.main_tab_layout);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //BottomNavigationViewHelper.disableShiftMode(navigation);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    inflateFragment(new TopNewsFragment());
                    return true;
                case R.id.navigation_dashboard:
                    inflateFragment(new NewsAllFragment());
                    return true;
                case R.id.navigation_notifications:
                    inflateFragment(new HistoryFragment("Notifications"));
                    return true;
                case R.id.navigation_search:
                    inflateFragment(new SearchFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected int getToolbarID() {
        return R.id.activity_main_toolbar;
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_account:
                if (bottomSheetDialog.isVisible()) {
                    bottomSheetDialog.dismiss();
                } else bottomSheetDialog.show(getSupportFragmentManager(), "");

        }
        return super.onOptionsItemSelected(item);
    }


    private void inflateFragment(android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_tab, fragment)
                .commit();
    }
}
