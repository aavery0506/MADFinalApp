package com.example.selfcareapp;
/*
UserModel class

Functionality:
    -utility class to create a user
        -userID
        -userName
        -userEmail
        -userPassword
Concepts from class:
    -Utilizing models classes to set up data structures for activities and fragments
 */

public class UserModel {
    //initialize class variables
    private String userID, userName, userEmail, userPassword;

    //constructor to create a new user object
    public UserModel(String userID, String userName, String userEmail, String userPassword){
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    //getters and setters
    public String getUserPassword() {return userPassword;}
    public void setUserPassword(String userPassword) {this.userPassword = userPassword;}
    public String getUserEmail() {return userEmail;}
    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}
    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}
    public String getUserID() {return userID;}
    public void setUserID(String userID) {this.userID = userID;}
}
