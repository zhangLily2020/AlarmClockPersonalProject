package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlarmListTest {
    private AlarmList alarmListTest;
    private final Alarm alarm1 = new Alarm(1111);
    private final Alarm alarm2 = new Alarm(2222);

    @BeforeEach
    void runBefore() {
        alarmListTest = new AlarmList();
    }

    @Test
    void testConstructor() {
        alarmListTest = new AlarmList();
        assertTrue(alarmListTest.getAlarmList().isEmpty());
    }

    @Test
    void testAddAlarm() {
        alarmListTest.addAlarm(alarm1);
        assertTrue(alarmListTest.getAlarmList().contains(alarm1));
        assertEquals(1,alarmListTest.getListSize());

        alarmListTest.addAlarm(alarm2);
        assertTrue(alarmListTest.getAlarmList().contains(alarm2));
        assertEquals(2,alarmListTest.getListSize());

        alarmListTest.addAlarm(alarm1);
        assertEquals(2,alarmListTest.getListSize());
    }

    @Test
    void testDeleteAlarm() {
        alarmListTest.addAlarm(alarm1);

        alarmListTest.deleteAlarm(alarm2);
        assertEquals(1, alarmListTest.getListSize());
        assertTrue(alarmListTest.getAlarmList().contains(alarm1));

        alarmListTest.deleteAlarm(alarm1);
        assertEquals(0, alarmListTest.getListSize());
    }
}
