package com.cglimpse.levankhang.cglimpse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

//    View
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnLogin;
    private TextView textRegister;
    private ProgressBar progressBarLogin;

//    Authentication firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connectView();
        addEvent();

        mAuth = FirebaseAuth.getInstance();
    }

    private void connectView(){
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        btnLogin = findViewById(R.id.btn_login);
        textRegister = findViewById(R.id.text_register);
        progressBarLogin = findViewById(R.id.progress_bar_login);
    }

    private void addEvent(){
//        btn login click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

//        btn register click
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegiserActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            startMainActivity();
        }

    }

    private void doLogin(){

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // check input value: true -> login, false
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressBarLogin.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
//                        login success back to main activity
                        startMainActivity();

                    }else{
                        String errorMessage = task.getException().getMessage();

//                      Toase login failed error message
                        Toast.makeText(LoginActivity.this,
                                "Login failed: "+errorMessage,
                                Toast.LENGTH_SHORT).show();
                    }

                    // set progressBarLogin hide when login end
                    progressBarLogin.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

    private void startMainActivity(){
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void startRegiserActivity(){
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
        finish();
    }
}
