package com.example.selfcareapp;

import java.time.LocalDate;

//utility class to set up the Model for the creation of a Goal
public class GoalModel {
    //set up class variables
    private long id;
    private GoalType type; //DAILY or WEEKLY goal
    private int targetMinutes;
    private boolean isActive;
    private LocalDate createdDate;
    private static GoalModel instance;

    //Enum for goal types
    public enum GoalType{
        DAILY,WEEKLY
    }

    //empty constructor
    public GoalModel(){}

    public GoalModel(GoalType type, int targetMinutes) {
        this.type= type;
        this.targetMinutes = targetMinutes;
        this.isActive = true;
        this.createdDate = LocalDate.now();//get todays date
    }


    //getters and setters
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public GoalType getType() {return type;}
    public void setType(GoalType type) {this.type = type;}
    public int getTargetMinutes() {return targetMinutes;}
    public void setTargetMinutes(int targetMinutes) {this.targetMinutes = targetMinutes;}
    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {isActive = active;}
    public LocalDate getCreatedDate() {return createdDate;}
    public void setCreatedDate(LocalDate createdDate) {this.createdDate = createdDate;}


}
