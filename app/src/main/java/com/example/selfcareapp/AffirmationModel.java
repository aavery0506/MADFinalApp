package com.example.selfcareapp;

/*
Class to create a new affirmation for database
Not being used in current iteration of app
 */
public class AffirmationModel {
    private String id;
    private String affirmation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAffirmation() {
        return affirmation;
    }

    public void setAffirmation(String affirmation) {
        this.affirmation = affirmation;
    }

    @Override
    public String toString(){
        return affirmation;
    }
}
