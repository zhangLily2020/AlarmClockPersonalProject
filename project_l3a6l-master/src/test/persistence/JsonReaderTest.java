package persistence;

import model.Alarm;
import model.AlarmList;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() { //Code extracted from JsonSerializationDemo
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AlarmList al = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAlarmList() { //Code extracted from JsonSerializationDemo
        JsonReader reader = new JsonReader("./data/testReaderEmptyAlarmList.json");
        try {
            AlarmList al = reader.read();
            assertEquals(0, al.getListSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAlarmList() { //Code extracted from JsonSerializationDemo
        JsonReader reader = new JsonReader("./data/testReaderGeneralAlarmList.json");
        try {
            AlarmList al = reader.read();
            List<Alarm> alarms = al.getAlarms();
            assertEquals(2, alarms.size());
            ArrayList<String> dayList = new ArrayList<>(Arrays.asList("Wed", "Mon"));

            checkAlarm(1111, "make a wish", new ArrayList<>(), alarms.get(0));
            checkAlarm(2311, "", dayList, alarms.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
