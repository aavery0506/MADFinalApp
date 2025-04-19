package com.example.selfcareapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfcareapp.databinding.FragmentBreatheAnimationBinding;

import java.util.Locale;


public class BreatheAnimation extends Fragment {
    FragmentBreatheAnimationBinding binding;
    private BreatheAnimation.AnimateListener activityCallback;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;
    private long timeLeftInMS;

    //haptics
    private Vibrator vibrator;

    public interface AnimateListener{void goHome();}

    public BreatheAnimation() {
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
        binding = FragmentBreatheAnimationBinding.inflate(inflater,container,false);
        //get the selected time
        timeLeftInMS = TimerSettings.getInstance().getSelectedTimeInMS();
        updateCountDownText();

        //initalize vibrator
        vibrator = (Vibrator)requireActivity().getSystemService(Context.VIBRATOR_SERVICE);

        //start timer
        binding.btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapticFeedback(true);
                if(timerRunning){
                    pauseTimer();
                }else{
                    startTimer();

                }
            }
        });

        //reset timer
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        //click on lotus to go home
        binding.animationLotus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeScreen.class);
                startActivity(intent);
            }
        });

        //select new time, go back to selection fragment
        binding.btnSelectNewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBack();
            }
        });

        return binding.getRoot();
    }

    private void navigateBack() {
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        NavController navController = Navigation.findNavController(getView());
        navController.navigateUp();
    }

    @Override
    public void onStop(){
        super.onStop();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    //Timer methods
    private void updateCountDownText() {
        int mins = (int)(timeLeftInMS/1000) / 60;
        int secs = (int)(timeLeftInMS/1000) % 60;

        String formatedTime = String.format(Locale.getDefault(),"%02d:%02d",mins,secs);
        binding.textViewTimer.setText(formatedTime);
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMS,1000) {
            @Override
            public void onTick(long msUnitlFinish) {
                timeLeftInMS = msUnitlFinish;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning =false;
                binding.btnStartPause.setText("Start");
                binding.btnStartPause.setVisibility(View.INVISIBLE);
                binding.btnReset.setVisibility(View.VISIBLE);
                hapticFeedback(false);

            }
        }.start();

        timerRunning = true;
        binding.btnStartPause.setText("Pause");
        binding.btnReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        binding.btnStartPause.setText("Start");
        binding.btnReset.setVisibility(View.VISIBLE);
    }


    public void resetTimer(){
        timeLeftInMS = TimerSettings.getInstance().getSelectedTimeInMS();
        updateCountDownText();
        binding.btnReset.setVisibility(View.INVISIBLE);
        binding.btnStartPause.setVisibility(View.VISIBLE);
    }

    //helper methods
    public void goHome(){
        binding.animationLotus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeScreen.class);
                startActivity(intent);
            }
        });
    }

    private void hapticFeedback(boolean isButtonPressed){
        //check to see if vibrator is available
        if(vibrator == null || !vibrator.hasVibrator()){
            return;
        }
        if (isButtonPressed) {
            //short vibration for button press
            VibrationEffect effect = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(effect);
        } else {
            //pattern for timer completion: vibrate 250ms, sleep 100ms, vibrate 250ms
            long[] pattern = {0, 250, 100, 250};
            VibrationEffect effect = VibrationEffect.createWaveform(pattern, -1);
            vibrator.vibrate(effect);
        }
    }

}