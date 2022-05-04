package model;

// Represents an alarm having a time, reminder text, days to repeat
// time is represented by 4 digits integers
// days to repeat is from this list: Mon, Tue, Wed, Thu, Fri, Sat, Sun

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class Alarm implements Writable {
    private int time;
    private String reminderText;
    private ArrayList<String> repeatDays;

    // REQUIRES: time must be positive integer and representing
    //           the 24 hours time (2359 is 23:59)
    // EFFECTS: creates an alarm with given time, empty reminder text, no days to repeat (empty)
    public Alarm(int time) {
        this.time = time;
        reminderText = "";
        repeatDays = new ArrayList<>();
    }

    // REQUIRES: text has non-zero length
    // MODIFIES: this
    // EFFECTS: sets the given reminder text to the alarm
    public void setReminderText(String text) {
        reminderText = text;
        EventLog.getInstance().logEvent(
                new Event("Reminder text for alarm " + this.toString() + " is set to : " + text));
    }

    // REQUIRES: day is one of ("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    // MODIFIES: this
    // EFFECTS: adds the given day to repeat to the alarm if the day is not already added
    public void addRepeatDay(String day) {
        if (!repeatDays.contains(day)) {
            repeatDays.add(day);
            EventLog.getInstance().logEvent(
                    new Event("Repeating day: " + day + " is added to " + this.toString()));
        }
    }

    // REQUIRES: day is one of ("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    // MODIFIES: this
    // EFFECTS: delete the given day to repeat to the alarm if the day exists
    public void deleteRepeatDay(String day) {
        if (repeatDays.contains(day)) {
            repeatDays.remove(day);
        }
    }

    public int getTime() {
        return time;
    }

    public String getReminderText() {
        return reminderText;
    }

    public ArrayList<String> getRepeatDays() {
        return repeatDays;
    }

    @Override
    public JSONObject toJson() { //Code extracted from JsonSerializationDemo
        JSONObject json = new JSONObject();
        json.put("time", time);
        json.put("reminder text", reminderText);
        json.put("repeat days", repeatDaysToJson());
        return json;
    }

    // EFFECTS: returns repreatDays in this alarm as a JSON array
    public JSONArray repeatDaysToJson() { //Code extracted from JsonSerializationDemo
        JSONArray jsonArray = new JSONArray();

        for (String t : repeatDays) {
            jsonArray.put(t);
        }

        return jsonArray;
    }

    @Override
    public String toString() {
        String adjustTime = String.valueOf(time);
        switch (adjustTime.length()) {
            case 1:
                adjustTime = "00:0" + adjustTime;
                break;
            case 2:
                adjustTime = "00:" + adjustTime;
                break;
            case 3:
                adjustTime = "0" + adjustTime.substring(0, 1) + ":" + adjustTime.substring(1, 3);
                break;
            case 4:
                adjustTime = adjustTime.substring(0, 2) + ":" + adjustTime.substring(2, 4);
                break;
            default:
                adjustTime = "Invalid time";
                break;
        }

        return adjustTime;
    }
}
