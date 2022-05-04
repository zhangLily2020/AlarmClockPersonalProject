package persistence;

import model.Alarm;
import model.AlarmList;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads alarm list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) { //Code extracted from JsonSerializationDemo
        this.source = source;
    } //Code extracted from JsonSerializationDemo

    // EFFECTS: reads alarm list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AlarmList read() throws IOException { //Code extracted from JsonSerializationDemo
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAlarmList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException { //Code extracted from JsonSerializationDemo
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses alarm list from JSON object and returns it
    private AlarmList parseAlarmList(JSONObject jsonObject) { //Code extracted from JsonSerializationDemo
        AlarmList al = new AlarmList();
        addAlarms(al, jsonObject);
        return al;
    }

    // MODIFIES: al
    // EFFECTS: parses alarms from JSON object and adds them to alarm list
    private void addAlarms(AlarmList al, JSONObject jsonObject) { //Code extracted from JsonSerializationDemo
        JSONArray jsonArray = jsonObject.getJSONArray("alarms");
        for (Object json : jsonArray) {
            JSONObject nextAlarm = (JSONObject) json;
            addAlarm(al, nextAlarm);
        }
    }

    // MODIFIES: al
    // EFFECTS: parses alarm from JSON object and adds it to alarm list
    private void addAlarm(AlarmList al, JSONObject jsonObject) {
        int time = jsonObject.getInt("time");
        String reminderText = jsonObject.getString("reminder text");
        JSONArray repeatDays = jsonObject.getJSONArray("repeat days");
        Alarm alarm = new Alarm(time);
        alarm.setReminderText(reminderText);
        for (int count = 0; count < repeatDays.length(); count++) {
            alarm.addRepeatDay(repeatDays.getString(count));
        }
        al.addAlarm(alarm);
    }

}
