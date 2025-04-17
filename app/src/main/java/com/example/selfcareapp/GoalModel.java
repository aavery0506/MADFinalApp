package com.example.selfcareapp;


public class GoalModel {
    private static GoalModel instance;
    public GoalModel() {
        //do the things
    }
    public static GoalModel getInstance(){
        if(instance == null){
            instance = new GoalModel();
        }
        return instance;
    }
}
