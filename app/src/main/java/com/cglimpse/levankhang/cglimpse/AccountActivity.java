package com.cglimpse.levankhang.cglimpse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        connectView();
        setupToolbar();
    }

    private void connectView(){
        toolbar = findViewById(R.id.toolbar);
    }

    private void setupToolbar(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        toolbar.setTitle(user.getEmail());
    }
}
