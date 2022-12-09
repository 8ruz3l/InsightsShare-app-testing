package com.example.insightsshare;

public class DateStringFormatter {

    //for DatePicker: called in methode initDatePicker()
    public String makeDateString(int day, int month, int year) {

        return getMonthFormat(month) + " " + day +  " " +year;
    }

    //for DatePicker: called in methode makeDateString()
    private String getMonthFormat(int month) {

        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MAR";
        if(month==4)
            return "APR";
        if(month==5)
            return "MAY";
        if(month==6)
            return "JUN";
        if(month==7)
            return "AUG";
        if(month==8)
            return "SEP";
        if(month==9)
            return "SEP";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEZ";

        //default should never happen
        return "JAN";
    }
}
