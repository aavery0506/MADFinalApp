package com.example.selfcareapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>{
    final private ArrayList<String> details;
    final private ArrayList<Integer> images;
    private HomeRecyclerViewClickListener listener;

    public HomeRecyclerAdapter(){
        super();
        details = new ArrayList<>();
        images = new ArrayList<>();

        details.add("Breathe");
        images.add(R.drawable.lotus_simple);

        details.add("Journal");
        images.add(R.drawable.notebook);

        details.add("Goals");
        images.add(R.drawable.goals);

    }

    public HomeRecyclerAdapter(HomeRecyclerViewClickListener listener){
        super();
        this.listener = listener;
        details = new ArrayList<>();
        images = new ArrayList<>();

        details.add("Breathe");
        images.add(R.drawable.lotus_simple);

        details.add("Journal");
        images.add(R.drawable.notebook);

        details.add("Goals");
        images.add(R.drawable.goals);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView cardImage;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.IV_card);
            description = itemView.findViewById(R.id.TV_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardImage.setImageResource(images.get(position));
        holder.description.setText(details.get(position));

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public interface HomeRecyclerViewClickListener{
        void onClick(View view, int position);
    }
}
