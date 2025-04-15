package com.example.selfcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class JournalSearch extends AppCompatActivity {
    ActivityJournalSearchBinding binding;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ArrayList<JournalModel> journalList;
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
        //setContentView(R.layout.activity_journal_search);
        binding = ActivityJournalSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //connect to Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Journal Entries");

        //recycler View set up
        recyclerView =findViewById(R.id.rv_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        journalList = new ArrayList<JournalModel>();
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
                eventChangeListener();
            }
        });

        binding.btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = binding.etSearchDate.getText().toString();
            }
        });

        
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