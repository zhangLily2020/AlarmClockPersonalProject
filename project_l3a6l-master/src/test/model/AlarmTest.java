package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class AlarmTest {
    private Alarm testAlarm;

    @BeforeEach
    void runBefore() {
        testAlarm = new Alarm(1111);
    }

    @Test
    void testConstructor() {
        assertEquals(1111, testAlarm.getTime());
        assertEquals("", testAlarm.getReminderText());
        assertTrue(testAlarm.getRepeatDays().isEmpty());
    }

    @Test
    void testSetText() {
        testAlarm.setReminderText("Do homework!");
        assertEquals("Do homework!", testAlarm.getReminderText());

        testAlarm.setReminderText("Finish homework!");
        assertEquals("Finish homework!", testAlarm.getReminderText());
    }

    @Test
    void testAddDays() {
        testAlarm.addRepeatDay("Mon");
        assertEquals(1, testAlarm.getRepeatDays().size());
        assertTrue(testAlarm.getRepeatDays().contains("Mon"));

        testAlarm.addRepeatDay("Tue");
        assertEquals(2, testAlarm.getRepeatDays().size());
        assertTrue(testAlarm.getRepeatDays().contains("Tue"));

        testAlarm.addRepeatDay("Mon");
        assertEquals(2, testAlarm.getRepeatDays().size());
        assertTrue(testAlarm.getRepeatDays().contains("Tue"));
        assertTrue(testAlarm.getRepeatDays().contains("Mon"));
    }

    @Test
    void testDeleteDays() {
        testAlarm.addRepeatDay("Mon");

        testAlarm.deleteRepeatDay("Tue");
        assertTrue(testAlarm.getRepeatDays().contains("Mon"));
        assertEquals(1, testAlarm.getRepeatDays().size());

        testAlarm.deleteRepeatDay("Mon");
        assertFalse(testAlarm.getRepeatDays().contains("Mon"));
        assertEquals(0, testAlarm.getRepeatDays().size());
    }

    @Test
    void testToString() {
        assertEquals("11:11", testAlarm.toString());
        Alarm a3 = new Alarm(111);
        assertEquals("01:11", a3.toString());
        Alarm a2 = new Alarm(11);
        assertEquals("00:11", a2.toString());
        Alarm a1 = new Alarm(1);
        assertEquals("00:01", a1.toString());
        Alarm a0 = new Alarm(0);
        assertEquals("00:00", a0.toString());
        Alarm a = new Alarm(12221);
        assertEquals("Invalid time", a.toString());
    }


}