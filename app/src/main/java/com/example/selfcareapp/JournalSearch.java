package com.example.selfcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfcareapp.databinding.ActivityJournalSearchBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class JournalSearch extends AppCompatActivity {
    ActivityJournalSearchBinding binding;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ArrayList<JournalModel> journalList;

    private ArrayList<JournalModel> filteredList;
    RecyclerView recyclerView;
    JournalRecyclerAdapter adapter;
    String date, entry, title;
/*TODO:
    add retrieve all button
        show all entries in the journal in date order
        https://firebase.google.com/docs/database/admin/retrieve-data#section-queries
    get journal entry based on date entered
        -link database
        -get date from input
        -get entries with that date
        -display entries matching that date in Recycler View
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityJournalSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //connect to Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Journal Entries");

        //recycler View set up
        recyclerView =findViewById(R.id.rv_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize lists
        journalList = new ArrayList<>();
        filteredList = new ArrayList<>();

        //initialize adapter
        adapter = new JournalRecyclerAdapter(JournalSearch.this,journalList);
        recyclerView.setAdapter(adapter);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //click on Lotus to go Home
        binding.ivLotusSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JournalSearch.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        //click search image for journal entries
        binding.searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateFilter = binding.etSearchDate.getText().toString().trim();
                if(!dateFilter.isEmpty()){
                    filterJournalByDate(dateFilter);
                }else{
                    Toast.makeText(JournalSearch.this, "Please enter a date",Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllEntries();
            }
        });

        //load all entries initially
        loadJournalEntries();
        
    }

    private void loadJournalEntries(){
        myRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                journalList.clear();

                for (DataSnapshot snpshot : dataSnapshot.getChildren()) {
                    JournalModel entry = snpshot.getValue(JournalModel.class);
                    if (entry != null) {
                        journalList.add(entry);
                    }
                }

                //sort entries by date (newest first)
                Collections.sort(journalList, new Comparator<JournalModel>() {
                    @Override
                    public int compare(JournalModel t0, JournalModel t1) {

                        return t1.getDate().compareTo(t0.getDate());
                    }

                    @Override
                    public boolean equals(Object o) {
                        return false;
                    }
                });
                adapter.updateJournalList(journalList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JournalSearch.this, "Error: "+ error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void filterJournalByDate(final String dateFilter){
        filteredList.clear();

        for(JournalModel entry : journalList){
            if(entry.getDate().equals(dateFilter)){
                filteredList.add(entry);
            }
        }

        if(filteredList.isEmpty()){
            Toast.makeText(JournalSearch.this,"No entries found for this date",Toast.LENGTH_SHORT).show();
        }
        adapter.updateJournalList(filteredList);
    }

    private void showAllEntries(){
        //update to full list
        adapter.updateJournalList(journalList);
    }

    private void eventChangeListener() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    JournalModel journal = dataSnapshot.getValue(JournalModel.class);
                    journalList.add(journal);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}