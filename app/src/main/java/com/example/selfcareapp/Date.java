package com.example.selfcareapp;


//Yes Dr.G I created my own Date class...I did not do my research until it was too late,
// I now know there is a java.time package I could have just used.
public class Date {
    private int day;
    private int month;
    private String strMonth;
    private int year;
    public Date(int month, int day, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.strMonth = setMonthWord(month);
    }

    public Date(String date){
        //parse string to get numerical values and then convert to Date object
    }
    //take int month and convert it to the word
    private String setMonthWord(int month){
        if (this.month == 1){
            this.strMonth = "January";
        }
        else if (this.month == 2){
            this.strMonth = "February";
        }
        else if (this.month == 3){
            this.strMonth = "March";
        }
        else if (this.month == 4){
            this.strMonth = "April";
        }
        else if (this.month == 5){
            this.strMonth = "May";
        }
        else if (this.month == 6){
            this.strMonth = "June";
        }
        else if (this.month == 7){
            this.strMonth = "July";
        }
        else if (this.month == 8){
            this.strMonth = "August";
        }
        else if (this.month == 9){
            this.strMonth = "September";
        }
        else if (this.month == 10){
            this.strMonth = "October";
        }
        else if (this.month == 11){
            this.strMonth = "November";
        }
        else if (this.month == 12){
            this.strMonth = "December";
        }
        else{
            this.strMonth = "Invalid Month";
        }
        return strMonth;
    }
    //getters and setters
    public String getStrMonth(){return this.strMonth;}
    public void setStrMonth(int month){this.strMonth = setMonthWord(month);}
    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}
    public int getMonth() {return month;}
    public void setMonth(int month) {this.month = month;}
    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}

    //compare method to see if two dates are the same
    public boolean compareTo(Date newDate){
        if(this.year == newDate.getYear()){
            if(this.month == newDate.getMonth()){
                return this.day == newDate.getDay();
            }
        }
       return false;
    }

    public String displayNumDate(){
        return this.month + "/" + this.day + "/" + this.year;
    }

    public String toString(){
        return this.strMonth + " " + this.day + ", " + this.year;
    }

}
