package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CurrentDayTimeTest {
    private CurrentTimeDay currentTimeDayTest;

    @BeforeEach
    void runBefore() {
        currentTimeDayTest = new CurrentTimeDay();
    }

    @Test
    void testConstructor() {
        currentTimeDayTest = new CurrentTimeDay();
        assertEquals(3, currentTimeDayTest.getCurrentDay().length());
        assertTrue(currentTimeDayTest.getCurrentTime() == (int)currentTimeDayTest.getCurrentTime());
    }
}
