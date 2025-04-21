package com.example.selfcareapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
        repository = new GoalHelper(getContext());


        binding.btnSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGoals();
            }
        });
        return binding.getRoot();
    }

    //helper methods
    private void saveGoals(){
        try{
            //get values from inputs
            int dailyMins = Integer.parseInt(binding.dailyEntry.getText().toString());
            int weeklyMins = Integer.parseInt(binding.weeklyEntry.getText().toString());

            //validate inputs make sense
            if(dailyMins<=0||weeklyMins<=0){
                Toast.makeText(getContext(),"Goals must be greater than 0",Toast.LENGTH_SHORT).show();
                return;
            }
            if(dailyMins*7 > weeklyMins){
                Toast.makeText(getContext(),"Weekly goal should be at least 7 times daily goal",Toast.LENGTH_SHORT).show();
                return;
            }

            //deactivate existing goals
            deactivateExistingGoals();

            //create new goals
            repository.createGoal(GoalModel.GoalType.DAILY,dailyMins);
            repository.createGoal(GoalModel.GoalType.WEEKLY,weeklyMins);

            Toast.makeText(getContext(),"Goals saved!",Toast.LENGTH_SHORT).show();
        }
        catch(NumberFormatException e){
            Toast.makeText(getContext(),"Please enter valid whole numbers",Toast.LENGTH_LONG).show();
        }
    }

    private void deactivateExistingGoals(){
        SQLiteDatabase db = new GoalDbHelper(getContext()).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("is_active",0); //0 = false, 1 = true

        //update all to inactive
        db.update("goals",values,"is_active =1",null);
        db.close();
    }
}//ENDING BRACKET DON'T DELETE