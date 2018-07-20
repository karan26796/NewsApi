package com.example.karan.myapplication2.news.fragments.home;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.firabasemanager.FirebaseAuthentication;

public class CustomAlertDialog extends Dialog implements
        android.view.View.OnClickListener {
    Activity c;
    public Dialog d;
    public Button yes, no;
    FirebaseAuthentication firebaseAuth;


    public CustomAlertDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alert);

        firebaseAuth = new FirebaseAuthentication(getContext());
        yes = findViewById(R.id.btn_yes);
        no = findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                firebaseAuth.logoutUser();
                c.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}