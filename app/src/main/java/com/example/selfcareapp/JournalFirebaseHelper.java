package com.example.selfcareapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JournalFirebaseHelper {
    //set up helper functions for Firebase database
    //connection to database lives here
private DatabaseReference myRef;

public interface FirebaseCallback{void onCallback(List<JournalModel> journalModelList);}
    public JournalFirebaseHelper() {
        this.myRef = FirebaseDatabase.getInstance().getReference("Journal Entries");
    }

    public void addEntry(JournalModel journal){
        //add journal to database
    }

    public void getAllEntries(FirebaseCallback callback){
        //get all journal entries
        //addValueEventListener
        //onDataChange, snapshot stuff, callback
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<JournalModel> journalList = new ArrayList<>();
                for(DataSnapshot temp : snapshot.getChildren()){
                    JournalModel journal = temp.getValue(JournalModel.class);
                    if(journal!=null){
                        journalList.add(journal);
                    }
                }
                callback.onCallback(journalList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getEntryByDate(String date, FirebaseCallback callback){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<JournalModel> filteredJournals = new ArrayList<>();
                for(DataSnapshot temp: snapshot.getChildren()){
                    JournalModel journal = temp.getValue(JournalModel.class);
                    if(journal!= null&& journal.getDate()!=null){
                        if(journal.getDate().contains(date)){
                            filteredJournals.add(journal);
                        }
                    }
                }
                callback.onCallback(filteredJournals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
