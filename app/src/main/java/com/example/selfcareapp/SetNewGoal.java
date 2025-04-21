package com.example.selfcareapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfcareapp.databinding.FragmentSetNewGoalBinding;

import java.util.List;

public class SetNewGoal extends Fragment {

    private FragmentSetNewGoalBinding binding;
    private GoalHelper repository;


    public SetNewGoal() {
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
        binding = FragmentSetNewGoalBinding.inflate(inflater,container,false);
        GoalModel model = GoalModel.getInstance();

        repository = new GoalHelper(getContext());

        //Load any existing goals
        loadExisitingGoals();

        binding.btnSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGoals();
            }
        });
        return binding.getRoot();
    }

    //helper methods

    private void loadExisitingGoals(){
        List<GoalModel> activeGoals = new GoalDbHelper(getContext()).getActiveGoals();

        for (GoalModel goal : activeGoals){
            if(goal.getType()==GoalModel.GoalType.DAILY){

            }
        }
    }
}//ENDING BRACKET DON'T DELETE