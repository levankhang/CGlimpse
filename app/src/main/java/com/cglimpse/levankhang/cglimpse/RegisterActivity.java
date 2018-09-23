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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

//    View
    private EditText editTextEmai;
    private EditText editTextPassword;
    private EditText editTextRePassword;
    private Button btnRegister;
    private TextView textLogin;
    private ProgressBar progressBarRegister;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        connectView();
        addEvent();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void connectView(){
        editTextEmai = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextRePassword = findViewById(R.id.edit_text_repassword);
        textLogin = findViewById(R.id.text_login);
        btnRegister = findViewById(R.id.btn_register);
        progressBarRegister = findViewById(R.id.progress_bar_register);
    }

    private void addEvent(){
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
    }

    private void startLoginActivity(){
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void doRegister(){
        String email = editTextEmai.getText().toString();
        String password = editTextPassword.getText().toString();
        String repassword = editTextRePassword.getText().toString();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            if(TextUtils.equals(password, repassword)){
                progressBarRegister.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công",
                                    Toast.LENGTH_SHORT).show();

                            String uid = mAuth.getCurrentUser().getUid();
                            Map<String, String> userMap = new HashMap<>();
                            userMap.put("name", null);
                            userMap.put("image", null);

                            db.collection("users").document(uid).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        startSetupActivity();
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Failed: setup store for user failed", Toast.LENGTH_SHORT).show();
                                    }
                                    progressBarRegister.setVisibility(View.INVISIBLE);

                                }
                            });



                        }else{
                            String messageError = task.getException().getMessage();
                            Toast.makeText(RegisterActivity.this, "Register failed: " + messageError,
                                    Toast.LENGTH_SHORT).show();
                            progressBarRegister.setVisibility(View.INVISIBLE);
                        }


                    }
                });
            }else{
                Toast.makeText(RegisterActivity.this, "Lỗi: Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void startSetupActivity(){
        Intent setupIntent = new Intent(RegisterActivity.this, SetupActivity.class);
        startActivity(setupIntent);
    }

}
