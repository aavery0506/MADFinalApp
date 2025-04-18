package com.example.selfcareapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfcareapp.databinding.FragmentBreatheSelectBinding;


public class BreatheSelect extends Fragment {
    private FragmentBreatheSelectBinding binding;
    private SelectListener activityCallback;


    public interface SelectListener{void onButtonClick(String text, int mins);}

    public BreatheSelect() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            activityCallback = (SelectListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement SelectListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentBreatheSelectBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        binding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(view);
            }
        });
    }

    public void buttonClicked(View view){
        String time = "Please select a time"; //default display text
        long timeInMS;
        int mins;

        if (binding.oneMin.isChecked()){
            time = "1 Minute";
            mins = 1;
            timeInMS = 60000;
        } else if (binding.twoMin.isChecked()) {
            time = "2 Minutes";
            mins = 2;
            timeInMS = 120000;
        } else if (binding.fiveMin.isChecked()) {
            time = "5 Minutes";
            mins = 5;
            timeInMS = 300000;
        } else if (binding.tenMin.isChecked()) {
            time = "10 Minutes";
            mins = 10;
            timeInMS = 600000;
        }else{
            mins = 1;
            timeInMS = 60000;
        }
        //save selected time to singleton
        TimerSettings.getInstance().setSelectedTimeInMS(timeInMS);
        activityCallback.onButtonClick(time,mins);
    }

    private void navigateToAnnimationFragment(){
        //using Navigation component
        NavController navController = NavHostFragment.findNavController(this);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}