package com.example.selfcareapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Journal extends AppCompatActivity {
    ActivityJournalBinding binding;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ArrayList<JournalModel> journalList;
    private ArrayList<JournalModel> filteredList;
    RecyclerView recyclerView;
    JournalRecyclerAdapter adapter;




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

        //recycler view set up
        //recycler View set up
        recyclerView =findViewById(R.id.rv_journal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize lists
        journalList = new ArrayList<>();
        filteredList = new ArrayList<>();

        //initialize adapter
        adapter = new JournalRecyclerAdapter(Journal.this,journalList);
        recyclerView.setAdapter(adapter);


        //floating action button for new entry
        binding.fabAddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewJournalEntry();
            }
        });

        //floating action button for searching
        binding.fabDateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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




    //adding journal entry methods
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
    //Method to save journal to database
    private void saveJournalEntry(String title, String date, String entry){
        Log.d("journal","Saving entry with date: "+ date);
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

    //Search by date methods
    //Method to search by date
    private void searchByDate(final String dateToSearch) {
        Log.d("journal","Searching for date: "+dateToSearch);
        // Method 1: Firebase Query (more efficient)
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        dbRef.orderByChild("date").equalTo(dateToSearch)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("journal","total entries in db:"+dataSnapshot.getChildrenCount());
                        List<JournalModel> searchResults = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            JournalModel entry = snapshot.getValue(JournalModel.class);
                            if (entry != null) {
                                Log.d("journal","entry date in db: "+ entry.getDate());
                                searchResults.add(entry);
                            }
                        }

                        // Update the RecyclerView with search results
                        adapter.updateJournalList((ArrayList<JournalModel>) searchResults);

                        // Show message if no entries found for this date
                        if (searchResults.isEmpty()) {
                            Toast.makeText(Journal.this, "No entries found for " + dateToSearch,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Journal.this, "Error: " + databaseError.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Journal.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String dateStr = String.format(Locale.US,"%04d-%02d-%02d",year,month+1,dayOfMonth);
                searchByDate(dateStr);

                Snackbar.make(findViewById(R.id.coordinator_layout),"Showing entries for: "+dateStr,Snackbar.LENGTH_LONG).setAction("Show All", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadJournalEntries();
                    }
                }).show();
            }
        }, year,month,day);
        datePickerDialog.show();
    }

    //recycler view methods
    private void loadJournalEntries(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<JournalModel> allEntries = new ArrayList<>();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    JournalModel entry = snapshot.getValue(JournalModel.class);
                    if(entry != null){
                        allEntries.add(entry);
                    }
                }
                Collections.sort(allEntries, new Comparator<JournalModel>() {
                    @Override
                    public int compare(JournalModel o1, JournalModel o2) {
                        // Add null checks before comparison
                        if (o1.getDate() == null && o2.getDate() == null) {
                            return 0; // Both dates are null, consider them equal
                        } else if (o1.getDate() == null) {
                            return 1; // Move entries with null dates to the end
                        } else if (o2.getDate() == null) {
                            return -1; // Move entries with null dates to the end
                        }
                        // If neither date is null, compare normally (newest first)
                        return o2.getDate().compareTo(o1.getDate());
                    }
                });
                journalList = (ArrayList<JournalModel>) allEntries;
                //update adapter
                adapter.updateJournalList((ArrayList<JournalModel>) allEntries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Journal.this,"Error loading entries: "+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }




}//ending bracket DON'T DELETE!


