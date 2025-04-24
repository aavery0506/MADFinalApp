package com.example.selfcareapp;
/*
JournalModel class

Functionality:
    -Utility class to set up a Journal Entry
        -id
        -title
        -date
        -entry
    -getters and setters for attributes
Concepts from class:
    -Empty constructor for Firebase usage
    -Utilizing models classes to set up data structures for activities and fragments
 */
public class JournalModel {
    private String id;
    private String title;
    private String date;
    private String entry;

    //Constructors
    public JournalModel(){} //empty constructor for Firebase
    //contractor to set up new Journal with given information for id, title, date, and entry
    public JournalModel(String id, String title, String date, String entry){
        this.id = id;
        this.title=title;
        this.date=date;
        this.entry=entry;
    }
    //getters and setters
    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getEntry() {return entry;}

    public void setEntry(String entry) {this.entry = entry;}

}
