package com.example.karan.myapplication2.news.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.model.Sources;
import com.example.karan.myapplication2.news.activities.BaseActivity;
import com.example.karan.myapplication2.news.activities.home.MainActivity;
import com.example.karan.myapplication2.news.adapter.SourcesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SourcesActivity extends BaseActivity {

    @BindView(R.id.sources_recycler)
    RecyclerView recyclerView;
    List<Sources> mSourcesList;
    ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);
        ButterKnife.bind(this);
        setUpToolbar(0);
        mSourcesList = new ArrayList<>();
        chipGroup = new ChipGroup(this);
        for (int i = 0; i < 10; i++) {
            Chip chip = new Chip(this, null, R.style.Chip);
            chipGroup.setSingleLine(false);
            chip.setId(i);
            chip.setText("Chip" + i);
            chip.isCheckable();
            chip.setCloseIconEnabled(true);
            chipGroup.addView(chip);
        }
        ((ViewGroup) findViewById(R.id.chip_group)).addView(chipGroup);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS, 0));
        setRecyclerView();
        recyclerView.setAdapter(new SourcesAdapter(mSourcesList));
    }

    private void setRecyclerView() {
        String[] sources = {"abc-news", "bbc-sport", "buzzfeed", "cbs-news"};
        for (String source : sources)
            mSourcesList.add(new Sources(source));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_sources, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_done:
                startActivity(new Intent(this, MainActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getToolbarID() {
        return R.id.activity_sources_toolbar;
    }

}
