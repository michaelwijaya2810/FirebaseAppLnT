package com.michaelwijaya.firebaseapplnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        EditText etEmail = findViewById(R.id.et_email);
        EditText etPass = findViewById(R.id.et_pass);
        Button btnRegister = findViewById(R.id.btn_register);
        Button btnLogin = findViewById(R.id.btn_login);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getEditableText().toString().trim();
                String pass = etPass.getEditableText().toString().trim();

                if(email.isEmpty()){
                    etEmail.setError("Email is empty!");
                    return;
                }

                if(pass.isEmpty()){
                    etPass.setError("Password is empty!");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this,
                                            "Registration failed. "+task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "Registration is successful, login with your account now!", Toast.LENGTH_SHORT).show();

                                    Users userInfo = new Users(email, pass);

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseRef = database.getReference("users");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();

                                    databaseRef.child(user.getUid()).setValue(userInfo);

                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,
                        LoginActivity.class));
            }
        });
    }
}