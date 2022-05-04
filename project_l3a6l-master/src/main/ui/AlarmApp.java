package ui;

import model.Alarm;
import model.AlarmList;
import model.CurrentTimeDay;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


// Alarm clock application
public class AlarmApp {
    private static final String JSON_STORE = "./data/alarmList.json";
    private AlarmList alarms;
    private Scanner input;
    private CurrentTimeDay currentTD;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the alarm application
    public AlarmApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runAlarmApp();
    }


    // MODIFIES: this
    // EFFECTS: processes the user input
    private void runAlarmApp() {  //Code extracted from TellerApp
        boolean keepGoing = true;
        String command = null;

        initialize();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) { //Code extracted from TellerApp
        if (command.equals("n")) {
            setAlarm();
        } else if (command.equals("d")) {
            deleteAlarm();
        } else if (command.equals("c")) {
            makeChanges();
        } else if (command.equals("a")) {
            saveAlarmList();
        } else if (command.equals("l")) {
            loadAlarmList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes alarm list
    private void initialize() { //Code extracted from TellerApp
        alarms = new AlarmList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of alarm options
    private void displayMenu() { //Code extracted from TellerApp
        printCurrentTD();
        printAlarms();
        System.out.println("\nSelect from:");
        System.out.println("\tn -> new alarm");
        System.out.println("\td -> delete alarm");
        System.out.println("\tc -> change alarm information");
        System.out.println("\ta -> save alarm list to file");
        System.out.println("\tl -> load alarm list from file");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: conducts the alarm setting process
    private void setAlarm() {
        printCurrentTD();
        System.out.println("\nSet a time using 4 digits (ex. 2359 is 11:59 PM)");
        Alarm newAlarm = new Alarm(Integer.parseInt(input.next()));
        setTextAndDays(newAlarm);
        alarms.addAlarm(newAlarm);
    }

    // MODIFIES: this
    // EFFECTS: conducts the reminder text and repeating day setting process
    private void setTextAndDays(Alarm alarm) {
        System.out.println("\nSet a reminder text or s to skip ");
        String dayCommand1 = input.next();

        if (!dayCommand1.equals("s")) {
            alarm.setReminderText(dayCommand1);
        }

        System.out.println("\nType the repeating day to add or delete (first three letters of days in the week"
                + " first capitalized, like Mon) or s to skip");
        String dayCommand2 = input.next();

        if (!dayCommand2.equals("s")) {
            if (alarm.getRepeatDays().isEmpty()) {
                alarm.addRepeatDay(dayCommand2);
            } else {
                System.out.println("\nSelect from: \ta -> add day \td -> delete day");
                String dayCommand3 = input.next();
                if (dayCommand3.equals("a")) {
                    alarm.addRepeatDay(dayCommand2);
                } else {
                    alarm.deleteRepeatDay(dayCommand2);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the process to delete an alarm
    private void deleteAlarm() {
        printCurrentTD();
        System.out.println("\nSelect the number to delete");
        printAlarms();
        alarms.deleteAlarm(alarms.getAlarmList().get(Integer.parseInt(input.next()) - 1));
        printAlarms();
    }

    // MODIFIES: this
    // EFFECTS: conducts the process to change the alarm information
    private void makeChanges() {
        printCurrentTD();
        System.out.println("\nSelect the number to change alarm information");
        printAlarms();
        setTextAndDays(alarms.getAlarmList().get(Integer.parseInt(input.next()) - 1));
    }

    // EFFECTS: prints the list of alarms and their information
    private void printAlarms() {
        System.out.println("\nCurrent alarm(s):");
        for (int x = 0; x < alarms.getListSize(); x++) {
            System.out.println(x + 1 + ". " + alarms.getAlarmList().get(x).getTime()
                    + (" REMINDER: ") + alarms.getAlarmList().get(x).getReminderText() + (" REPEATING ON: ")
                    + alarms.getAlarmList().get(x).getRepeatDays());
        }
    }

    // EFFECT: prints the current time and day of the week
    private void printCurrentTD() {
        currentTD = new CurrentTimeDay();

        int currentTimeDigit = String.valueOf(currentTD.getCurrentTime()).length();
        String currentTime = String.valueOf(currentTD.getCurrentTime());
        switch (currentTimeDigit) {
            case 0:
                currentTime = "00:00";
                break;
            case 1:
                currentTime = "00:0" + currentTime;
                break;
            case 2:
                currentTime = "00:" + currentTime;
                break;
            case 3:
                currentTime = "0" + currentTime.substring(0, 1) + ":" + currentTime.substring(1, 3);
                break;
            case 4:
                currentTime = currentTime.substring(0, 2) + ":" + currentTime.substring(2, 4);
                break;
        }
        System.out.println("\nCurrent time: " + currentTime);
        System.out.println("Current day: " + currentTD.getCurrentDay());
    }

    // EFFECTS: saves the alarm list to file
    private void saveAlarmList() {
        try {
            jsonWriter.open();
            jsonWriter.write(alarms);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads alarm list from file
    private void loadAlarmList() {
        try {
            alarms = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
