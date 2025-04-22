package com.example.selfcareapp;
/*
Breathe Select Fragment

Functionality:
    -display BreatheSelect Fragment
    -Allow user to select a preset time or input custom time
    -On button click
        -save user selection
        -go to the BreatheAnimation fragment to start the timer for selected time
    -update the progress text for goals
    -uses GoalProgress, GoalHelper, and GoalModel classes
Concepts from Class:

 */
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfcareapp.databinding.FragmentBreatheSelectBinding;

import java.time.LocalDate;


public class BreatheSelect extends Fragment {
    private FragmentBreatheSelectBinding binding;
    private SelectListener activityCallback;
    private GoalHelper repository;


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

        //click on lotus to go home
        binding.selectLotus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeScreen.class);
                startActivity(intent);
            }
        });

        //initialize repository
        repository = new GoalHelper(getContext());
        updateProgressText();

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

    //Method to get the correct time selected or inputted when the button is clicked
    public void buttonClicked(View view){
        String time = "Please select a time"; //default display text
        //default to 1 min
        long timeInMS = 60000;
        int mins = 1;

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
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            if (!binding.customTimeEntry.getText().isEmpty()) {
                time = binding.customTimeEntry.getText().toString() + " Minutes";
                mins = Integer.parseInt(binding.customTimeEntry.getText().toString());
                timeInMS = mins * 60000L;
            }
        }
        //save selected time to singleton
        TimerSettings.getInstance().setSelectedTimeInMS(timeInMS);
        activityCallback.onButtonClick(time,mins);
    }

    //Method to handle navigation to animation fragment
    private void navigateToAnnimationFragment(){
        //using Navigation component
        NavController navController = NavHostFragment.findNavController(this);
    }

    //Method to update progress text
    private void updateProgressText(){
        GoalProgress dailyProgress = repository.getDailyProgress(LocalDate.now());
        if (dailyProgress != null){
            GoalModel goal = dailyProgress.getGoal();
            int completedMins = dailyProgress.getCompletedMinutes();
            int targetMins = goal.getTargetMinutes();

            binding.dailyProgressText.setText("Today's progress: " + completedMins + " / " + targetMins + " Minutes");
        }else{
            binding.dailyProgressText.setText("No daily goal set. Set one in Goals!");
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}