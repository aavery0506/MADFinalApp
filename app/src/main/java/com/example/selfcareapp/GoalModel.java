package com.example.selfcareapp;

//utility class to set up the Model for the creation of a Goal
public class GoalModel {
    //set up class variables
    private static GoalModel instance;


    public GoalModel() {
        //empty constructor
    }

    //get instance of model or create a new instance
    public static GoalModel getInstance(){
        if(instance == null){
            instance = new GoalModel();
        }
        return instance;
    }
}
