package com.example.karan.myapplication2.news.fragments.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;

/**
 * Created by karan on 5/14/2018.
 */

public class SignInFragment extends Fragment implements View.OnClickListener {

    EditText mInputEmail, mInputPassword;
    Button mLoginButton;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mInputEmail = view.findViewById(R.id.input_text_email_sign_in);
        mInputPassword = view.findViewById(R.id.input_text_password_sign_in);
        mLoginButton = view.findViewById(R.id.login_btn);
        mLoginButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        String password = mInputPassword.getText().toString().trim();
        String email = mInputEmail.getText().toString().trim();
        if (!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))) {
            progressDialog.show();
            FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(getActivity());
            firebaseAuthentication.loginUser(email, password, progressDialog);
        }
    }
}
