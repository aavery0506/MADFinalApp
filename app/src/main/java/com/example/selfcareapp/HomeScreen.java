package com.example.selfcareapp;
/*
HomeScreen class

Functionality:
    -extends AppCompatActivity
    -Sets up HomeScreen acitvity where all roads lead back to
    -Selection of the desired Activity using buttons

Concepts from class:
    -Activity binding
    -Listeners
        -setOnClickListener
    -Intents to start new Activities
    -Design Elements:
        -constraint layout
        -linear layout
        -textView
        -ImageView
        -Guidelines
        -Buttons
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selfcareapp.databinding.ActivityHomeScreenBinding;

public class HomeScreen extends AppCompatActivity {
    ActivityHomeScreenBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //set view
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //set up button to launch journal activity
        binding.HomeJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, Journal.class);
                startActivity(intent);
            }
        });

        //set up button to launch Breathe activity
        binding.HomeBreathe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, Breathe.class);
                startActivity(intent);
            }
        });

        //set up button to launch Goals activity
        binding.HomeGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, Goals.class);
                startActivity(intent);
            }
        });

        //set up button to launch Affirmations Activity
        binding.HomeAffirmations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this,Affirmations.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}