package com.example.karan.myapplication2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.model.Hero;
import com.example.karan.myapplication2.retrofit.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karan on 5/3/2018.
 */

public class SecondFragment extends Fragment {
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);
        listView = view.findViewById(R.id.listViewHeroes);

        getHeroes();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Hero>> call = api.getHeroes();

        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hero>> call, @NonNull Response<List<Hero>> response) {
                List<Hero> heroList = response.body();

                //Creating an String array for the ListView
                String[] heroes = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    heroes[i] = heroList.get(i).getName();
                }

                //displaying the string array into listview
                listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, heroes));

            }

            @Override
            public void onFailure(@NonNull Call<List<Hero>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
