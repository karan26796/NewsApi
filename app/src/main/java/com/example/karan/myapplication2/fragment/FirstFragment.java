package com.example.karan.myapplication2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.HomeAdapter;
import com.example.karan.myapplication2.model.Home;

import java.util.ArrayList;

/**
 * Created by karan on 5/3/2018.
 */

public class FirstFragment extends Fragment implements HomeAdapter.onClickListener {
    RecyclerView recyclerView;
    ArrayList<Home> mHomeList;
    String head[], subHead[], detail[], json;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        recyclerView = view.findViewById(R.id.recycler_first_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new HomeAdapter(mHomeList, this));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHomeList = new ArrayList<>();
        try {
            head = getActivity().getResources().getStringArray(R.array.head);
            subHead = getActivity().getResources().getStringArray(R.array.subhead);
            detail = getActivity().getResources().getStringArray(R.array.detail);

        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < head.length; i++) {
            mHomeList.add(new Home(head[i], subHead[i], detail[i]));
        }
    }

    @Override
    public void onItemClicked(View v, int position) {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
    }
}
