package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameInput, emailInputRegister, passwordInputRegister;
    Button btnRegisterSubmit;
    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.usernameInput);
        emailInputRegister = findViewById(R.id.emailInputRegister);
        passwordInputRegister = findViewById(R.id.passwordInputRegister);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);
        progressBar = findViewById(R.id.registerProgressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String username = usernameInput.getText().toString().trim();
                String email = emailInputRegister.getText().toString().trim();
                String password = passwordInputRegister.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailInputRegister.setError("Email in required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordInputRegister.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    passwordInputRegister.setError("Password has to be at least 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration succeeded", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(RegisterActivity.this, "Sorry, something went wrong :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}