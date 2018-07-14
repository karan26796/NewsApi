package com.example.karan.myapplication2.news.fragments.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.SharedPrefManager;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;
import com.example.karan.myapplication2.model.Options;
import com.example.karan.myapplication2.news.activities.ViewPagerActivity;
import com.example.karan.myapplication2.news.activities.login.SourcesActivity;
import com.example.karan.myapplication2.news.adapter.home.OptionsAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetDialog extends BottomSheetDialogFragment implements OptionsAdapter.optionsClickListener {
    @BindView(R.id.options_recycler)
    RecyclerView optionsRecycler;
    FirebaseAuth mAuth;
    FirebaseAuthentication firebaseAuth;
    ArrayList<Options> optionsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuth = new FirebaseAuthentication(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet, container, false);
        ButterKnife.bind(this, view);
        optionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        setOptionsRecycler();
        return view;
    }


    public void setOptionsRecycler() {
        optionsList = new ArrayList<>();
        String[] options = {mAuth.getCurrentUser().getEmail(),
                "Logout",
                "Settings",
                "Extra"
        };
        int[] drawable = {
                R.drawable.ic_account_circle
                , R.drawable.ic_logout
                , R.drawable.ic_settings,
                R.drawable.ic_menu_gallery
        };
        for (int i = 0; i < 4; i++) {
            optionsList.add(new Options(options[i], drawable[i]));
        }
        OptionsAdapter adapter = new OptionsAdapter(optionsList, this);
        optionsRecycler.setAdapter(adapter);
    }

    @Override
    public void onOptionsClicked(View view, int position, OptionsAdapter.OptionsViewHolder viewHolder) {
        switch (position) {
            case 0:
                if (SharedPrefManager.getInstance(getContext()).getToken() != null)
                    Toast.makeText(getContext(), SharedPrefManager.getInstance(getContext()).getToken(),
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Token not found", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("Sure to Logout?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.logoutUser();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                alertDialog.show();
                break;
            case 2:
                startActivity(new Intent(getContext(), ViewPagerActivity.class));
                break;
            case 3:
                startActivity(new Intent(getContext(), SourcesActivity.class));
                break;
        }
    }
}
