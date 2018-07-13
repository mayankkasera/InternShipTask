package com.example.mayank.internshiptask;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout TxtLoginUsername,TxtLoginPassword;
    private Button BtnLogin,BtnReg;
    private FirebaseAuth mAuth;
    private SpotsDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        dialog = (SpotsDialog) new SpotsDialog.Builder().setContext(this).build();

        TxtLoginUsername = findViewById(R.id.txt_login_username);
        TxtLoginPassword = findViewById(R.id.txt_login_password);

        BtnLogin = findViewById(R.id.btn_login);
        BtnReg = findViewById(R.id.btn_Registration);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.show();
                userLogin();



            }
        });

        BtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RagisterActivity.class));
            }
        });

    }

    private void userLogin() {

        final String email = TxtLoginUsername.getEditText().getText().toString();
        final String password = TxtLoginPassword.getEditText().getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            dialog.dismiss();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                           finish();

                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Authantication faild", Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });
    }


}
