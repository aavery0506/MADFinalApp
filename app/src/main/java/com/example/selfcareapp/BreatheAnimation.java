package com.example.selfcareapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfcareapp.databinding.FragmentBreatheAnimationBinding;

/*TODO:
   figure out animation:
    -set up timer
    -change image based on time
   set up lotus to go to home screen:
    -start new activity from fragment?
 */
public class BreatheAnimation extends Fragment {
FragmentBreatheAnimationBinding binding;
    private BreatheAnimation.AnimateListener activityCallback;


    public interface AnimateListener{void goHome();}

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BreatheAnimation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BreatheAnimation.
     */
    // TODO: Rename and change types and number of parameters
    public static BreatheAnimation newInstance(String param1, String param2) {
        BreatheAnimation fragment = new BreatheAnimation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        activityCallback = (BreatheAnimation.AnimateListener) context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBreatheAnimationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    public void updateText(String text){
        binding.tvAnnTime.setText(text);
    }

    public void goHome(){
        binding.animationLotus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(BreatheAnimation.this, HomeScreen.class);
            }
        });
    }
}