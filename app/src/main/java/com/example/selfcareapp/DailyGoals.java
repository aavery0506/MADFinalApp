package com.example.selfcareapp;

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

        updateProgress();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateProgress();
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

        GoalModel goal = progress.getGoal();
        int completedMins = progress.getCompletedMinutes();
        int targetMins = goal.getTargetMinutes();
        int percentComp = progress.getPercentCompleted();

        binding.viewCurrentDgoal.setText(targetMins + " Minutes");
        binding.showTotalDmins.setText(completedMins + " / " + targetMins + "Minutes");
        binding.dailyProgressBar.setProgress(percentComp);
        binding.dailyProgressBar.setVisibility(View.VISIBLE);
        binding.showTotalDmins.setVisibility(View.VISIBLE);
    }
}