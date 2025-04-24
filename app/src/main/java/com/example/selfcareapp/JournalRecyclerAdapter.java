package com.example.selfcareapp;
/*
JournalRecyclerAdapter class

Functionality:
    -class for Journal recyclerView
    -extends RecyclerView.Adapter
    -ability to add journals
    -displays journals for a recyclerView

Concepts from Class:
    -RecyclerView implementation

 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder> {
    private ArrayList<JournalModel> journals;
    Context context;

    //super constructor implementation
    public JournalRecyclerAdapter(){
        super();
    }

    //constructor the takes in the context and an ArrayList of journals to display
    public JournalRecyclerAdapter(Context context, ArrayList<JournalModel> journals){
        this.context = context;
        this.journals = journals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalModel journalModel = journals.get(position);
        holder.title.setText(journalModel.getTitle());
        holder.date.setText(journalModel.getDate());
        holder.entry.setText(journalModel.getEntry());

    }

    //get the count of total journals
    @Override
    public int getItemCount() {
        return journals.size();
    }

    //replace journals with a new list
    public void updateJournalList(ArrayList<JournalModel> newJournalList){
        this.journals = newJournalList;
        notifyDataSetChanged();
    }
    //add to journals
    public void updateAdapter(ArrayList<JournalModel> journalList){
        journals.addAll(journalList);
        notifyDataSetChanged();
    }

    //ViewHolder class to create the cards
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView entry;
        private  TextView date;

        public ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.jcard_show_title);
            entry = view.findViewById(R.id.TV_jcard_entry);
            date = view.findViewById(R.id.jcard_show_date);
        }
    }


}
