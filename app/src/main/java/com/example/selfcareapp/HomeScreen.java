package com.example.selfcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfcareapp.databinding.ActivityHomeScreenBinding;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    ActivityHomeScreenBinding binding;

    private HomeRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HomeRecyclerAdapter.HomeRecyclerViewClickListener listener;
    private ArrayList<String> details;
    private ArrayList<Integer> images;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        details.add("Breathe");
        images.add(R.drawable.lotus_simple);

        details.add("Journal");
        images.add(R.drawable.notebook);

        details.add("Goals");
        images.add(R.drawable.goals);

        setAdapter();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setAdapter(){
        setOnClickListener();
        layoutManager= new LinearLayoutManager(this);
        binding.HomeRView.setLayoutManager(layoutManager);
        adapter = new HomeRecyclerAdapter(details, images,listener);
        binding.HomeRView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new HomeRecyclerAdapter.HomeRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Intent intent = new Intent(getApplicationContext())
            }
        };
    }

}