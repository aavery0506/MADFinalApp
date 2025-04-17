package com.example.selfcareapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfcareapp.databinding.FragmentDailyGoalsBinding;


public class DailyGoals extends Fragment {

    private FragmentDailyGoalsBinding binding;

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
        GoalModel model = GoalModel.getInstance();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}