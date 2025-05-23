package com.example.selfcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selfcareapp.databinding.ActivityLoginScreenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

/*
Login Screen is the start of the app where users can login or create an account.

Functionality:
    -User can enter credentials to login to their account
    -User can enter credentials and sign up for an account
    -User verification through FirebaseAuthentication
    -On successful login, brings you to the HomeScreen
    -Utilizes Firebase functions for storage and authentication

Concepts from class:
    -Activity binding
    -Firebase
        -Realtime Database
        -Authentication
    -Listeners
        -setOnClickListener
        -OnSuccessListener
    -Design Elements:
        -LinearLayout
        -TextView
        -ImageView
        -CardView
        -EditText
        -Buttons
    -Intents for launching a new activity

 */
public class LoginScreen extends AppCompatActivity {

    //initialize class variables
    ActivityLoginScreenBinding binding;
    String name, email, password;

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding= ActivityLoginScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up login button, gets information from textEdits, runs login function
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();
                login();
            }
        });

        //set up signup button, gets information from textEdits, runs signUp function
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name= binding.name.getText().toString();
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();
                signUp();
            }
        });

    }

    //Login function, uses FirebaseAuth to verify login credentials, if successful login into app
    private void login() {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(LoginScreen.this,HomeScreen.class));
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        finish();
                    }

                });
    }

    //signUp function, uses FirebaseAuth to create a new user with email and password credentials, logs into app
    private void signUp() {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build();

                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        assert firebaseUser != null;
                        firebaseUser.updateProfile(userProfileChangeRequest);
                        UserModel userModel = new UserModel(FirebaseAuth.getInstance().getUid(),name,email,password);
                        databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(userModel);

                        startActivity(new Intent(LoginScreen.this,HomeScreen.class));
                        finish();
                    }
                });
    }
}