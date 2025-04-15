package com.example.selfcareapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfcareapp.databinding.ActivityJournalBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Journal extends AppCompatActivity {
    ActivityJournalBinding binding;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ArrayList<JournalModel> journalList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityJournalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //connect to Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //save entry to data base when save button clicked
        binding.btnSaveJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set up for Toast when entry is saved
                Context context = getApplicationContext();
                CharSequence text = "Entry Saved!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
                //add entry to the database
                JournalModel journal = new JournalModel(binding.etTitleJournal.getText().toString(),
                        binding.etDateJournal.getText().toString(),
                        binding.etEntryJournal.getText().toString());

                //TODO: save journals based on user, currently all pushing to one place
                myRef.child("Journal Entries").push().setValue(journal);

                resetEntry(); //clear input text

            }
        });
        //click on search image to go to search activity
        binding.ivSearchJournalpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Journal.this, JournalSearch.class);
                startActivity(intent);
            }
        });
        //click on Lotus to go Home
        binding.ivLotusJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Journal.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }

    //reset text input fields
    private void resetEntry() {
        binding.etEntryJournal.setText("");
        binding.etDateJournal.setText("");
        binding.etTitleJournal.setText("");
    }

}