package com.example.karan.myapplication2.news.fragments.login;

import android.app.ProgressDialog;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karan on 5/14/2018.
 */

public class SignInFragment extends LoginBaseFragment implements View.OnClickListener, View.OnLongClickListener, TextWatcher {
    @BindView(R.id.input_text_email_sign_in)
    EditText mInputEmail;
    @BindView(R.id.input_text_password_sign_in)
    EditText mInputPassword;
    @BindView(R.id.login_btn)
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
        ButterKnife.bind(this, view);
        mInputEmail.addTextChangedListener(this);
        mLoginButton.setOnClickListener(this);
        mLoginButton.setOnLongClickListener(this);
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
            mListener.onLoginStart();
            FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(getActivity());
            firebaseAuthentication.loginUser(email, password, progressDialog);
            mListener.onLoginEnd();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        charSequence = mInputEmail.getText();
        if (charSequence.length() > 5) {
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
