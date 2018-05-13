package com.example.karan.myapplication2.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.retrofit.usr.User;
import com.example.karan.myapplication2.retrofit.usr.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    EditText mName, mEmail, mAge, mTopics;
    Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpToolbar(1);

        mName = findViewById(R.id.edit_name);
        mAge = findViewById(R.id.edit_age);
        mEmail = findViewById(R.id.edit_email);
        mTopics = findViewById(R.id.edit_topics);

        mLogin = findViewById(R.id.login_btn);
        mLogin.setOnClickListener(this);

    }

    @Override
    protected int getToolbarID() {
        return R.id.login_activity_toolbar;
    }

    @Override
    public void onClick(View v) {
        String name = mName.getText().toString().trim();
        int age = Integer.parseInt(mAge.getText().toString().trim());
        String email = mEmail.getText().toString().trim();
        String[] topics = mTopics.getText().toString().split(",");
        if (!(TextUtils.isEmpty(name) ||
                age == 0 ||
                TextUtils.isEmpty(email) ||
                topics.length == 0)) {
            User user = new User(name, email, topics, age);
            sendNetworkRequest(user);
        } else {
            Toast.makeText(this, "Kya hua", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNetworkRequest(User user) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://e5a2a8b1-eb77-4d62-9c52-05ceb8c01005.mock.pstmn.io/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<User> account = client.createAccount(user);

        account.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(LoginActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
