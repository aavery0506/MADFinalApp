package com.example.selfcareapp;
/*
TimerSetting class

Functionality:
    -Utility class to set up settings for a timer instance
    -default to 1 min timer

Concepts from Class:
    -singleton utility class
 */
public class TimerSettings {
    private static TimerSettings instance;
    private long selectedTimeInMS = 60000; //default to 1 min

    private TimerSettings(){
        //private empty constructor for singleton
    }

    public static synchronized TimerSettings getInstance(){
        if(instance == null){
            instance = new TimerSettings();
        }
        return instance;
    }
    //getters and setters
    public void setSelectedTimeInMS (long selectedTimeInMS){this.selectedTimeInMS = selectedTimeInMS;}
    public long getSelectedTimeInMS() {return selectedTimeInMS;}
}
