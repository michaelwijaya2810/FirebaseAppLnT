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
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        firebaseAuth = FirebaseAuth.getInstance();

        EditText etEmail = findViewById(R.id.et_email);
        Button btnResetPass = findViewById(R.id.btn_reset_pass);

        btnResetPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = etEmail.getEditableText().toString().trim();
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(ResetActivity.this, "Reset email failed to send! "+task.getException(), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ResetActivity.this, "Reset email has been sent, please check your inbox!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetActivity.this, LoginActivity.class));
                        }
                    }
                });
            }
        });
    }
}