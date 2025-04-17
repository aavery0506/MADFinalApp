package com.example.selfcareapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfcareapp.databinding.FragmentWeeklyGoalsBinding;


public class WeeklyGoals extends Fragment {

    private FragmentWeeklyGoalsBinding binding;
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
        GoalModel model= GoalModel.getInstance();
        return binding.getRoot();
    }
}