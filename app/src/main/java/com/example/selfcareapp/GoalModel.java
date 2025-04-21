package com.example.selfcareapp;

//utility class to set up the Model for the creation of a Goal
public class GoalModel {
    //set up class variables
    private static GoalModel instance;
    private int numDays;
    private int totalMins;


    public GoalModel() {
        //empty constructor
    }
    //constructor that takes in number of days and total minutes
    public GoalModel(int numDays, int totalMins){
        this.numDays = numDays;
        this.totalMins = totalMins;
    }

    //get instance of model or create a new instance
    public static GoalModel getInstance(){
        if(instance == null){
            instance = new GoalModel();
        }
        return instance;
    }

    //getters and setters
    public int getNumDays() {return numDays;}
    public void setNumDays(int numDays) {this.numDays = numDays;}
    public int getTotalMins() {return totalMins;}
    public void setTotalMins(int totalMins) {this.totalMins = totalMins;}

    //Method to calculate the number of minutes per day
    public int numMinsPerDay(){
        return this.totalMins/this.numDays;
    }
}
