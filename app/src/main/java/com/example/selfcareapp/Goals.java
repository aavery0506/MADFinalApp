package com.example.selfcareapp;
/*
Goals Class
Functionality:
    -Creates the Goal Activity and extends AppCompatActivity
    -facilitates  TabLayout to display dailyGoals, weeklyGoals, and setNewGoal Fragments
    -utilizes NavController, GoalsAdapter, and TabLayoutMediator classes
    -goes back to the home screen when the lotus is clicked

Concepts from Class:
    -Activity binding
    -Fragments
    -Design Elements:
        -constraint layout
        -guidelines
        -textView
        -ImageView
        -TabLayout
        -TabItem
        -ViewPager2
    -Listeners
        -setOnClickListener
    -Intent to start new activity
 */
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.selfcareapp.databinding.ActivityGoalsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayoutMediator;

public class Goals extends AppCompatActivity {
ActivityGoalsBinding binding;
private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityGoalsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        GoalsAdapter adapter = new GoalsAdapter(this);
        adapter.addFragment(new DailyGoals(), "Daily Goals");
        adapter.addFragment(new WeeklyGoals(),"Weekly Goals");
        adapter.addFragment(new SetNewGoal(),"Set New Goals");
        binding.goalsVp.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayoutGoals,binding.goalsVp,(tab,position)->
                tab.setText(adapter.getPageTitle(position))).attach();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //click lotus to go home
        binding.goalsLotus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Goals.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }
}