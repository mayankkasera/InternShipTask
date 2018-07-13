package com.example.mayank.internshiptask;

import android.location.Address;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RagisterActivity extends AppCompatActivity {

    TextInputLayout Email,Password,Name,LastName,MobileNo,Address;
    Button Register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister);

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Name = findViewById(R.id.name);
        LastName = findViewById(R.id.lastname);
        MobileNo = findViewById(R.id.mobileno);
        Address = findViewById(R.id.address);
        Register = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });


    }

    void Register (){
        mAuth.createUserWithEmailAndPassword(Email.getEditText().getText().toString(), Password.getEditText().getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users").push();
                            HashMap<String,String> map = new HashMap<>();
                            map.put("email",Email.getEditText().getText().toString());
                            map.put("password",Password.getEditText().getText().toString());
                            map.put("name",Name.getEditText().getText().toString());
                            map.put("lastname",LastName.getEditText().getText().toString());
                            map.put("address",Address.getEditText().getText().toString());
                            map.put("mobileno",MobileNo.getEditText().getText().toString());
                            myRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RagisterActivity.this, "Successfully Register", Toast.LENGTH_SHORT).show();
                                }
                            });

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RagisterActivity.this, "createUserWithEmail:success", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RagisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users");
        HashMap<String,String> map = new HashMap<>();
        map.put("name","m");
        myRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RagisterActivity.this, "added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
