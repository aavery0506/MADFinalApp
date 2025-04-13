package com.example.selfcareapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder> {
    private ArrayList<JournalModel> journals;

    public JournalRecyclerAdapter(){
        super();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = (String) journals.get(position).getTitle();
        String entry = journals.get(position).getEntry();

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public JournalRecyclerAdapter(ArrayList<JournalModel> journals) {
        super();
        this.journals = journals;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView entry;
        public ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.jcard_show_title);
            entry = view.findViewById(R.id.TV_jcard_entry);
        }
    }
}
