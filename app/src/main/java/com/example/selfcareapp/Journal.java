package com.example.selfcareapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

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
        //setContentView(R.layout.activity_journal);
        binding = ActivityJournalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnSaveJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Entry Saved!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
                JournalModel journal = new JournalModel(binding.etTitleJournal.getText().toString(),
                        binding.etDateJournal.getText().toString(),
                        binding.etEntryJournal.getText().toString());

                myRef.child("Journal Entries").push().setValue(journal);

                resetEntry();

            }
        });
    }

    private void resetEntry() {
        binding.etEntryJournal.setText("");
        binding.etDateJournal.setText("");
        binding.etTitleJournal.setText("");
    }

}