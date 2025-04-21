package com.example.selfcareapp;

import java.time.LocalDateTime;

//Class to represent a completed breathing exercise
public class SessionRecord {
    private long id;
    private LocalDateTime timeStamp;
    private int durationMinutes;
    private String exerciseType;

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
