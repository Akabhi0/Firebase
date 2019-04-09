package com.example.firebase;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebase.databinding.ActivityAuthenticationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String TAG = Authentication.class.getName();
    private ActivityAuthenticationBinding binding;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.getApps(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //This is the onClick method for taking the email and password
        binding.acbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = binding.tietEmail.getText().toString();
                password = binding.tietPassword.getText().toString();
                signInAccount(email, password);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signInAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this
                , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Authentication.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    /**
     * This is the function for updating the UI if current user is exist
     *
     * @param currentUser
     */
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent intent = new Intent(Authentication.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "user is exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }
}
