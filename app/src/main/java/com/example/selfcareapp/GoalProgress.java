package com.example.selfcareapp;

import java.time.LocalDate;

//utility class used to keep track of progress towards goals
public class GoalProgress {
    //instance variables
    private GoalModel goal;
    private int completedMinutes;
    private LocalDate date;

    public GoalProgress(GoalModel goal, LocalDate date) {
        this.goal = goal;
        this.date = date;
        this.completedMinutes = 0;
    }
    //getters and setters
    public GoalModel getGoal() {return goal;}
    public void setGoal(GoalModel goal) {this.goal = goal;}
    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}
    public int getCompletedMinutes() {return completedMinutes;}
    public void setCompletedMinutes(int completedMinutes) {this.completedMinutes = completedMinutes;}

    //functionality methods

    //add completed minutes
    public void addMinutes(int mins){
        this.completedMinutes += mins;
    }

    //Method to determine if the goal has been met
    public boolean isCompleted(){
        return completedMinutes>= goal.getTargetMinutes();
    }

    //Method to get percentage completed
    public int getPercentCompleted(){
        return (int)(((float)completedMinutes/ goal.getTargetMinutes())*100);
    }
}
