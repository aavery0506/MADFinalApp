package com.example.selfcareapp;

public class JournalModel {
    private String id;
    private String title;
    private String date;
    private String entry;

    public JournalModel(){} //empty constructor for Firebase
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
