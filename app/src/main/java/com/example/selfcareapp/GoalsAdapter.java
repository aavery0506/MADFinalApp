package com.example.selfcareapp;
/*
GoalsAdapter class

Functionality:
    -extends FragmentStateAdapter
    -adapter to facilitate use of fragments for the Goal Activity
    -create a fragment
    -add a fragment
    -get the total count of fragments
    -get the title of a fragment

Concepts from class:
    -Fragment adapters

 */
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class GoalsAdapter extends FragmentStateAdapter {
    //initialize variables to hold fragment information
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitle = new ArrayList<>();

    //constructor
    public GoalsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    //create a new fragment
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrayList.get(position);
    }

    //get the count of fragments
    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }

    //add a new fragment to the activity
    public void addFragment(Fragment fragment, String title){
        fragmentArrayList.add(fragment);
        fragmentTitle.add(title);
    }

    //getter for the title of the fragment for tab display
    public String getPageTitle(int position){
        return fragmentTitle.get(position);
    }
}
