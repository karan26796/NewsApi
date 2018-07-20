package com.example.karan.myapplication2.news.fragments.home;

import android.app.AlertDialog;
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

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;
import com.example.karan.myapplication2.model.Options;
import com.example.karan.myapplication2.news.adapter.home.OptionsAdapter;
import com.example.karan.myapplication2.utils.ItemDivider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBottomSheetDialog extends BottomSheetDialogFragment implements OptionsAdapter.optionsClickListener {
    @BindView(R.id.options_recycler)
    RecyclerView optionsRecycler;
    FirebaseAuth mAuth;
    FirebaseAuthentication firebaseAuth;
    ArrayList<Options> optionsList;
    OnOptionClicked mListener;

    public interface OnOptionClicked {
        void onOptionClicked();
    }

    public HomeBottomSheetDialog() {
    }

    public void setmListener(OnOptionClicked mListener) {
        this.mListener = mListener;
    }

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
        optionsRecycler.addItemDecoration(new ItemDivider(ContextCompat.getDrawable(getContext(), R.drawable.divider),
                50, 50));
        setOptionsRecycler();
        return view;
    }


    public void setOptionsRecycler() {
        optionsList = new ArrayList<>();
        String email = Objects.requireNonNull(mAuth.getCurrentUser())
                .getEmail();
        assert email != null;
        String[] options = {email.substring(0, 1).toUpperCase()
                .concat(email.substring(1, email.length())),
                "Logout",
                "Settings"
        };
        int[] drawable = {
                R.drawable.ic_account_circle
                , R.drawable.ic_logout
                , R.drawable.ic_settings
        };
        for (int i = 0; i < 3; i++) {
            optionsList.add(new Options(options[i], drawable[i]));
        }
        OptionsAdapter adapter = new OptionsAdapter(optionsList, this);
        optionsRecycler.setAdapter(adapter);
    }

    @Override
    public void onOptionsClicked(View view, int position, OptionsAdapter.OptionsViewHolder viewHolder) {
        switch (position) {
            case 0:
                break;
            case 1:
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                View alertLayout = getLayoutInflater()
                        .inflate(R.layout.dialog_alert, null);
                alertDialog.setView(alertLayout);
                alertLayout.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        firebaseAuth.logoutUser();
                    }
                });
                alertLayout.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                alertDialog.create();
                alertDialog.show();
                break;
            case 2:
                break;
        }
        //mListener.onOptionClicked();
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
