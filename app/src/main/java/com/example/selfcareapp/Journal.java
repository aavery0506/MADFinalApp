package com.example.selfcareapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfcareapp.databinding.ActivityJournalBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Journal extends AppCompatActivity {
    ActivityJournalBinding binding;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
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


        // Check if user is signed in, if not, sign in anonymously
        if (mAuth.getCurrentUser() == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // User is signed in anonymously
                                Log.d("Authentication", "signInAnonymously:success");
                            } else {
                                // Sign in failed
                                Log.w("Authentication", "signInAnonymously:failure", task.getException());
                                Toast.makeText(Journal.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        //floating action button
        binding.fabAddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewJournalEntry();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //save entry to data base when save button clicked
        binding.btnSaveJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                //add entry to the database
                JournalModel journal = new JournalModel(myRef.push().getKey(),binding.etTitleJournal.getText().toString(),
                        binding.etDateJournal.getText().toString(),
                        binding.etEntryJournal.getText().toString());

                //TODO: save journals based on user, currently all pushing to one place
                myRef.child("Journal Entries").push().setValue(journal);*/

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

    //get the data for the new journal entry
    private void addNewJournalEntry() {

        //Create custom dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_entry);
        dialog.setTitle("Add New Journal Entry");

        // Get the dialog components
        final EditText editDate = dialog.findViewById(R.id.edit_dialog_date);
        final EditText editTitle = dialog.findViewById(R.id.edit_dialog_title);
        final EditText editEntry = dialog.findViewById(R.id.edit_dialog_entry);
        Button buttonSave = dialog.findViewById(R.id.button_save);
        Button buttonCancel = dialog.findViewById(R.id.button_cancel);

        //set todays date as default
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String today = dateFormat.format(new Date());
        editDate.setText(today);

        // Set click listeners
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = editDate.getText().toString().trim();
                String title = editTitle.getText().toString().trim();
                String entry = editEntry.getText().toString().trim();

                if (!date.isEmpty() && !title.isEmpty() && !entry.isEmpty()) {
                    saveJournalEntry(title,date, entry);
                    dialog.dismiss();
                } else {
                    Toast.makeText(Journal.this, "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }
    private void saveJournalEntry(String title, String date, String entry){
        String id = myRef.push().getKey();
        if(id!=null){
            //create new entry
            JournalModel newEntry = new JournalModel(id,title,date,entry);
            //save to Firebase
            myRef.child(id).setValue(newEntry)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Journal.this, "Entry Saved!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Journal.this,"Failed to Save Entry",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}