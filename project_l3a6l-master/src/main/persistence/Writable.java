package persistence;

import org.json.JSONObject;

public interface Writable { //Code extracted from JsonSerializationDemo
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
