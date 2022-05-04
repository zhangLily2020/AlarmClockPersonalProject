package persistence;

import model.AlarmList;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of alarm list to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) { //Code extracted from JsonSerializationDemo
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException { //Code extracted from JsonSerializationDemo
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of alarm list to file
    public void write(AlarmList al) { //Code extracted from JsonSerializationDemo
        JSONObject json = al.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    } //Code extracted from JsonSerializationDemo

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    } //Code extracted from JsonSerializationDemo
}
