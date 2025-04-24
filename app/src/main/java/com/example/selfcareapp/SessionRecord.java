package com.example.selfcareapp;

import java.time.LocalDateTime;

/*
Session Record Class

Functionality:
    -Utility Class to represent a completed breathing exercise
        -id
        -timestamp
        -durationMinutes
        -exerciseType (for future additional functions)

Concepts from class:
    -Utilizing models classes to set up data structures for activities and fragments
 */
public class SessionRecord {
    //initialize variables
    private long id;
    private LocalDateTime timeStamp;
    private int durationMinutes;
    private String exerciseType;

    //constructor that accepts durationsMins and exerciseType
    public SessionRecord(int durationMinutes, String exerciseType) {
        this.durationMinutes = durationMinutes;
        this.exerciseType = exerciseType;
        this.timeStamp = LocalDateTime.now();
    }

    //getters and setters
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public LocalDateTime getTimeStamp() {return timeStamp;}
    public void setTimeStamp(LocalDateTime timeStamp) {this.timeStamp = timeStamp;}
    public int getDurationMinutes() {return durationMinutes;}
    public void setDurationMinutes(int durationMinutes) {this.durationMinutes = durationMinutes;}
    public String getExerciseType() {return exerciseType;}
    public void setExerciseType(String exerciseType) {this.exerciseType = exerciseType;}
}
