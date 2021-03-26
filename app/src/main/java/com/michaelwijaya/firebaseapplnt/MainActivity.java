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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener fireAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        fireAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();

                if(user1 == null){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        };

        Button btnUpdateEmail = findViewById(R.id.btn_updateEmail);
        Button btnUpdatePass = findViewById(R.id.btn_updatePass);
        Button btnDeleteUser = findViewById(R.id.btn_delete_user);
        Button btnLogout = findViewById(R.id.btn_logout);
        EditText newEmail = findViewById(R.id.et_newEmail);
        EditText newPass = findViewById(R.id.et_newPass);

        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = newEmail.getEditableText().toString().trim();

                if(email.isEmpty()){
                    newEmail.setError("Email is empty!");
                    return;
                }


                if(firebaseUser != null){
                    firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,
                                        "Email failed to change. "+task.getException(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this,
                                        "Email has been changed, login again with your new email!",
                                        Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                            }
                        }
                    });
                }
            }
        });

        btnUpdatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = newPass.getEditableText().toString().trim();

                if(pass.isEmpty()){
                    newPass.setError("Password is empty!");
                    return;
                }

                if(firebaseUser != null){
                    firebaseUser.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Password failed to change. "+task.getException(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Password has been changed. Login with your new password!", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                            }
                        }
                    });
                }
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseUser != null){
                    firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Failed to remove user!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "User removed, bye-bye", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                            }
                        }
                    });
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(fireAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        firebaseAuth.removeAuthStateListener(fireAuthListener);
    }
}