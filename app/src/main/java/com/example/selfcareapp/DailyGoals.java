package com.example.selfcareapp;
/*
DailyGoals Fragment

Functionality:
    -display DailyGoal fragment
    -display current daily goal
    -tracks progress for current daily goal based on breathe exercises completed
    -uses GoalHelper, GoalProgress, and GoalModel classes

Concepts from class:
    -Fragment binding
    -Fragment lifecycles
    -Design Elements
        -text view
        -constraint layout
        -guidelines
        -Progress Bar
 */
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfcareapp.databinding.FragmentDailyGoalsBinding;

import java.time.LocalDate;

public class DailyGoals extends Fragment {
    private FragmentDailyGoalsBinding binding;
    private GoalHelper repository;

    public DailyGoals() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDailyGoalsBinding.inflate(inflater,container,false);
        repository = new GoalHelper(getContext());
        //update goal progress
        updateProgress();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateProgress(); //update progress
    }

    //Method to update progress
    private void updateProgress(){
        GoalProgress progress = repository.getDailyProgress(LocalDate.now()); //get the progress for current day
        if(progress == null){
            binding.viewCurrentDgoal.setText("No Daily Goal Set");
            binding.dailyProgressBar.setVisibility(View.INVISIBLE);
            binding.showTotalDmins.setVisibility(View.INVISIBLE);
            return;
        }

        //get current goal
        GoalModel goal = progress.getGoal();
        int completedMins = progress.getCompletedMinutes();
        int targetMins = goal.getTargetMinutes();
        int percentComp = progress.getPercentCompleted();
        //update text views and progress bar
        binding.viewCurrentDgoal.setText(targetMins + " Minutes");
        binding.showTotalDmins.setText(completedMins + " / " + targetMins + "Minutes");
        binding.dailyProgressBar.setProgress(percentComp);
        binding.dailyProgressBar.setVisibility(View.VISIBLE);
        binding.showTotalDmins.setVisibility(View.VISIBLE);
    }
}