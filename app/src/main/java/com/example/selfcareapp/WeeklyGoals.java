package com.example.selfcareapp;
/*
WeeklyGoals Fragment

Functionality:
    -display WeeklyGoal fragment
    -display current weekly goal
    -tracks progress for current weekly goal based on breathe exercises completed
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

import com.example.selfcareapp.databinding.FragmentWeeklyGoalsBinding;

import java.time.LocalDate;


public class WeeklyGoals extends Fragment {
    //initialize variables
    private FragmentWeeklyGoalsBinding binding;
    public GoalHelper repository;

    public WeeklyGoals() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeeklyGoalsBinding.inflate(inflater,container,false);
        repository = new GoalHelper(getContext());

        updateProgress(); //update progress

        return binding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateProgress();
    }

    //Method to update weekly progress
    private void updateProgress(){
        GoalProgress progress = repository.getWeeklyProgress(LocalDate.now());

        if(progress == null){
            binding.viewCurrentWgoal.setText("No Weekly Goal Set");
            binding.weeklyProgressBar.setVisibility(View.INVISIBLE);
            binding.showTotalWmins.setVisibility(View.INVISIBLE);
            return;
        }
        //get current weekly goal
        GoalModel goal = progress.getGoal();
        int completedMins = progress.getCompletedMinutes();
        int targetMins = goal.getTargetMinutes();
        int percentComp = progress.getPercentCompleted();
        //update text and progress bar
        binding.viewCurrentWgoal.setText(targetMins + " Minutes");
        binding.showTotalWmins.setText(completedMins + " / " + targetMins + "Minutes");
        binding.weeklyProgressBar.setProgress(percentComp);
        binding.weeklyProgressBar.setVisibility(View.VISIBLE);
        binding.showTotalWmins.setVisibility(View.VISIBLE);
    }
}