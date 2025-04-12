package com.example.selfcareapp;

public class JournalModel {
    private String title;
    private String date;
    private String entry;
    public JournalModel(String title, String date, String entry){
        this.title=title;
        this.date=date;
        this.entry=entry;

    }
    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

}
