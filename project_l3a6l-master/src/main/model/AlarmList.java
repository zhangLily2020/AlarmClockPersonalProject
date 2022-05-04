package model;

// Represents an alarm list that has a list of alarms

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlarmList implements Writable {
    private ArrayList<Alarm> alarmList;

    // EFFECT: creates an empty list for alarms
    public AlarmList() {
        alarmList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add the alarm to list if alarm doesn't already exist in list
    public void addAlarm(Alarm alarm) {
        if (!alarmList.contains(alarm)) {
            alarmList.add(alarm);
            EventLog.getInstance().logEvent(new Event("Alarm " + alarm.toString() + " added."));
        }
    }

    // MODIFIES: this
    // EFFECTS: delete the alarm in the list if alarm is in the list
    public void deleteAlarm(Alarm alarm) {
        if (alarmList.contains(alarm)) {
            alarmList.remove(alarm);
            EventLog.getInstance().logEvent(new Event("Alarm " + alarm.toString() + " removed."));
        }
    }

    // EFFECTS: returns an unmodifiable list of alarms in this alarm list
    public List<Alarm> getAlarms() {
        return Collections.unmodifiableList(alarmList);
    }

    public ArrayList<Alarm> getAlarmList() {
        return alarmList;
    }

    // EFFECTS: get the size of the alarmList.json
    public int getListSize() {
        return alarmList.size();
    }


    @Override
    public JSONObject toJson() { //Code extracted from JsonSerializationDemo
        JSONObject json = new JSONObject();
        json.put("alarms", alarmsToJson());
        return json;
    }

    // EFFECTS: returns alarms in this alarm list as a JSON array
    private JSONArray alarmsToJson() { //Code extracted from JsonSerializationDemo
        JSONArray jsonArray = new JSONArray();

        for (Alarm t : alarmList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
