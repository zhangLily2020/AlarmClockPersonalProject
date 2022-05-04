package model;

// Represents the real time and current day of the week

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CurrentTimeDay {
    private int time;
    private String day;

    // EFFECTS: set time and day to current time and day of the week
    public CurrentTimeDay() {
        // extracted from https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        // and https://docs.oracle.com/javase/8/docs/api/java/util/Date.html
        java.util.Date date = new java.util.Date();
        String timePattern = "HHmm";
        DateFormat tf = new SimpleDateFormat(timePattern);
        time = Integer.parseInt(tf.format(date));

        String dayPattern = "E";
        SimpleDateFormat df = new SimpleDateFormat(dayPattern);
        day = df.format(date);
    }

    public int getCurrentTime() {
        return time;
    }

    public String getCurrentDay() {
        return day;
    }


}
