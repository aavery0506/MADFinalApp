package com.example.selfcareapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.selfcareapp.databinding.ActivityJournalBinding;

import java.util.ArrayList;

public class Journal extends AppCompatActivity {
    ActivityJournalBinding binding;
    private ArrayList<JournalModel> journalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_journal);
        binding = ActivityJournalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setAdapter(){
        layoutManager= new LinearLayoutManager(this);
        binding.HomeRView.setLayoutManager(layoutManager);
        adapter = new HomeRecyclerAdapter();
        binding.HomeRView.setAdapter(adapter);
    }
}