package com.cglimpse.levankhang.cglimpse;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView();
        setupToolbar();
        addEvent();

    }

    private void connectView(){
        toolbar = findViewById(R.id.toolbar);
    }

    private void setupToolbar(){

        toolbar.setTitle("CGlimpse");
        toolbar.inflateMenu(R.menu.main_menu);
    }

    private void addEvent(){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.action_logout:
                        doLogout(); break;
                    case R.id.action_account:
                        startAccountActivity(); break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
           startLoginActivity();
        }
    }

    private void startLoginActivity(){
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void startAccountActivity(){
        Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(accountIntent);
//        finish();
    }

    private void doLogout(){
        FirebaseAuth.getInstance().signOut();
        startLoginActivity();
    }


}
