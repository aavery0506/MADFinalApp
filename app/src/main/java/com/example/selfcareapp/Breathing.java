package com.example.selfcareapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.selfcareapp.databinding.ActivityBreathingBinding;

public class Breathing extends AppCompatActivity implements BreatheSelect.SelectListener,BreatheAnimation.AnimateListener{

    ActivityBreathingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_breathing);

        binding = ActivityBreathingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    @Override
    public void onButtonClick(String text, int mins) {
        navigateToAnimationFragment();
    }

    private void navigateToAnimationFragment() {
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        navController.navigate(R.id.action_selectionFragment_to_timerFragment);
    }

    @Override
    public void goHome(){
        BreatheAnimation breatheAnimation = (BreatheAnimation) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (breatheAnimation != null) {
            breatheAnimation.goHome();
        }
    }
}