package com.example.karan.myapplication2.news.activities.home;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipDrawable;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;
import com.example.karan.myapplication2.news.activities.BaseActivity;
import com.example.karan.myapplication2.news.fragments.home.HistoryFragment;
import com.example.karan.myapplication2.news.fragments.home.HomeBottomSheetDialog;
import com.example.karan.myapplication2.news.fragments.home.NewsAllFragment;
import com.example.karan.myapplication2.news.fragments.home.SearchFragment;
import com.example.karan.myapplication2.news.fragments.home.TopNewsFragment;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener, HomeBottomSheetDialog.OnOptionClicked {
    HomeBottomSheetDialog bottomSheetDialog;
    FirebaseAuthentication firebaseAuthentication;
    @BindView(R.id.main_tab_layout)
    public TabLayout tabLayout;
    @BindView(R.id.chipGroupMain)
    ChipGroup chipMain;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolbar(1);
        bottomSheetDialog = new HomeBottomSheetDialog();
        bottomSheetDialog.setmListener(this);
        if (savedInstanceState == null) {
            inflateFragment(new TopNewsFragment());
        }
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthentication = new FirebaseAuthentication(this);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initChipGroup(chipMain);
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

    private void initChipGroup(ChipGroup chipGroup) {
        chipGroup.removeAllViews();

        String[] textArray = getResources().getStringArray(R.array.cat_chip_group_text_array);
        for (String text : textArray) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.cat_chip_group_item_filter, chipGroup, false);
            chip.setChipText(text);
            ChipDrawable chipDrawable = ChipDrawable.createFromResource(this, R.xml.chip_drawable);
            chipDrawable.setBounds(0, 0, chipDrawable.getIntrinsicWidth(), chipDrawable.getIntrinsicHeight());
            //chip.setChipDrawable(chipDrawable);
            chipGroup.addView(chip);
        }
    }

    @Override
    public void onOptionClicked() {
        if (bottomSheetDialog.isVisible()) {
            bottomSheetDialog.dismiss();
        }
    }
}
