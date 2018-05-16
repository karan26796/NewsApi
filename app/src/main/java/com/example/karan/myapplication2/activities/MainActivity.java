package com.example.karan.myapplication2.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.OptionsAdapter;
import com.example.karan.myapplication2.fragment.DummyFragment;
import com.example.karan.myapplication2.fragment.NewsAllFragment;
import com.example.karan.myapplication2.fragment.SearchFragment;
import com.example.karan.myapplication2.fragment.TopNewsFragment;
import com.example.karan.myapplication2.model.Options;
import com.example.karan.myapplication2.utils.BottomNavigationViewHelper;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener
        , OptionsAdapter.optionsClickListener {
    public TabLayout tabLayout;
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    ArrayList<Options> mOptionsList;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar(1);
        if (savedInstanceState == null) {
            inflateFragment(new TopNewsFragment());
        }
        tabLayout = findViewById(R.id.main_tab_layout);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        layoutBottomSheet = findViewById(R.id.bottom_sheet_options);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        recyclerView = layoutBottomSheet.findViewById(R.id.options_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setRecyclerView();

        //sets the behaviour of linear layout to a bottom sheet
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setPeekHeight(0);
        sheetBehavior.setBottomSheetCallback(bottomSheetCallback);
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
                    inflateFragment(new DummyFragment("Notifications"));
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

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback =
            new BottomSheetBehavior.BottomSheetCallback() {
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
            };

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_account) {
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            /*FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(this);
            firebaseAuthentication.logoutUser();*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void inflateFragment(android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_tab, fragment)
                .commit();
    }

    private void setRecyclerView() {
        mOptionsList = new ArrayList<>();
        String optionsTitle[] = {"Bookmark", "History", "Exit", "Enter"};
        int optionsDrawable[] = {R.drawable.ic_bookmark, R.drawable.ic_info,
                R.drawable.ic_close, R.drawable.ic_menu_gallery};
        for (int i = 0; i < optionsTitle.length; i++) {
            mOptionsList.add(new Options(optionsTitle[i], optionsDrawable[i]));
        }
        recyclerView.setAdapter(new OptionsAdapter(mOptionsList, this));
    }

    @Override
    public void onOptionsClicked(View view, int position) {
    }
}
