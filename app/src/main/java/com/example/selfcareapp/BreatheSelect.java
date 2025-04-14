package com.example.selfcareapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfcareapp.databinding.FragmentBreatheSelectBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BreatheSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BreatheSelect extends Fragment {
    private FragmentBreatheSelectBinding binding;
    private SelectListener activityCallback;


    public interface SelectListener{void onButtonClick(String text, int mins);}

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BreatheSelect() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BreatheSelect.
     */
    // TODO: Rename and change types and number of parameters
    public static BreatheSelect newInstance(String param1, String param2) {
        BreatheSelect fragment = new BreatheSelect();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            activityCallback = (SelectListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement SelectListener");
        }
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
        binding= FragmentBreatheSelectBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        binding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(view);
            }
        });
    }

    public void buttonClicked(View view){
        String time = "Please select a time"; //default display text
        int mins = 0; //default time for timer
        if (binding.oneMin.isChecked()){
            time = "1 Minute";
            mins = 1;
        } else if (binding.twoMin.isChecked()) {
            time = "2 Minutes";
            mins = 2;
        } else if (binding.fiveMin.isChecked()) {
            time = "5 Minutes";
            mins = 5;
        } else if (binding.tenMin.isChecked()) {
            time = "10 Minutes";
            mins = 10;
        }

        activityCallback.onButtonClick(time,mins);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}