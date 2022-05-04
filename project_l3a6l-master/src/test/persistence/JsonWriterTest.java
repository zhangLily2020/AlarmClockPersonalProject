package persistence;

import model.Alarm;
import model.AlarmList;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() { //Code extracted from JsonSerializationDemo
        try {
            AlarmList al = new AlarmList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAlarmList() { //Code extracted from JsonSerializationDemo
        try {
            AlarmList al = new AlarmList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAlarmList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAlarmList.json");
            al = reader.read();
            assertEquals(0, al.getListSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAlarmList() { //Code extracted from JsonSerializationDemo
        try {
            AlarmList al = new AlarmList();
            Alarm a1 = new Alarm(1111);
            a1.setReminderText("make a wish");

            Alarm a2 = new Alarm(2311);
            a2.addRepeatDay("Wed");
            a2.addRepeatDay("Mon");

            al.addAlarm(a1);
            al.addAlarm(a2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAlarmList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAlarmList.json");
            al = reader.read();
            List<Alarm> alarms = al.getAlarms();

            ArrayList<String> dayList2 = new ArrayList<>(Arrays.asList("Wed", "Mon"));

            assertEquals(2, alarms.size());
            checkAlarm(1111, "make a wish", new ArrayList<>(), alarms.get(0));
            checkAlarm(2311, "", dayList2, alarms.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
