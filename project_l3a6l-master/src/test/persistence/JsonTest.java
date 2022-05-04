package persistence;

import model.Alarm;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    protected void checkAlarm(int time, String reminderText, ArrayList<String> repeatDays, Alarm alarm) {
        //Code extracted from JsonSerializationDemo
        assertEquals(time, alarm.getTime());
        assertEquals(reminderText, alarm.getReminderText());
        assertTrue(repeatDays.equals(alarm.getRepeatDays()));
    }
}
