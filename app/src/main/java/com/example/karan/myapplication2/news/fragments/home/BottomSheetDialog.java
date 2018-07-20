package com.example.karan.myapplication2.news.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.SharedPrefManager;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;
import com.example.karan.myapplication2.model.Options;
import com.example.karan.myapplication2.news.adapter.home.OptionsAdapter;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.example.karan.myapplication2.utils.ItemDivider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetDialog extends BottomSheetDialogFragment implements OptionsAdapter.optionsClickListener {
    @BindView(R.id.options_recycler)
    RecyclerView optionsRecycler;
    FirebaseAuth mAuth;
    FirebaseAuthentication firebaseAuth;
    private DatabaseReference mDatabaseBookmark;
    ArrayList<Options> optionsList;
    News newsItem;
    Bundle bundle;
    OnOptionClicked mListener;

    public interface OnOptionClicked {
        void onOptionClicked();
    }

    public BottomSheetDialog() {
    }

    public void setmListener(OnOptionClicked mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuth = new FirebaseAuthentication(getContext());
        if (getArguments() != null) {
            bundle = getArguments();
            if (bundle.containsKey("news"))
                newsItem = (News) bundle.get("news");
        }
        mDatabaseBookmark = FirebaseDatabase.getInstance().getReference()
                .child("Bookmarks")
                .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet, container, false);
        ButterKnife.bind(this, view);
        optionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        optionsRecycler.addItemDecoration(new ItemDivider(ContextCompat.getDrawable(getContext(), R.drawable.divider),
                50, 50));
        setOptionsRecycler();
        return view;
    }


    public void setOptionsRecycler() {
        optionsList = new ArrayList<>();
        String[] options = {mAuth.getCurrentUser().getEmail(),
                "Bookmark"
        };
        int[] drawable = {
                R.drawable.ic_account_circle
                , R.drawable.ic_bookmark
        };
        for (int i = 0; i < 2; i++) {
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
            case 2:
                if (newsItem != null) {
                    try {
                        mDatabaseBookmark.child(newsItem.getDate())
                                .setValue(newsItem)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Added, Not", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        mListener.onOptionClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnOptionClicked) context;
        } catch (ClassCastException e) {
            Log.e("Bottom fragment", e.getLocalizedMessage());
        }
    }
}
