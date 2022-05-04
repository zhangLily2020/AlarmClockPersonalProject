package ui;

import model.Alarm;
import model.AlarmList;
import model.EventLog;
import model.Event;

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Alarm clock GUI
public class AlarmListGUI extends JPanel
        implements ListSelectionListener {
    private static final String JSON_STORE = "./data/alarmList.json";
    private JList<Alarm> list;
    private DefaultListModel<Alarm> listModel;
    private AlarmList alarms;

    private static final String addAlarm = "Add Alarm (type 4 integers)";
    private static final String removeAlarm = "Remove ";
    private static final String loadAlarm = "Load";
    private static final String saveAlarm = "Save";
    private static final String setText = "Set Reminder Text";
    private static final String viewInfo = "Get Alarm Info";
    private static final String setDays = "Add Repeating Day (ex. Wed)";

    private ImageIcon clockImage;
    private JLabel imageLabel;
    private JButton removeButton;
    private JButton addButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton setTextButton;
    private JButton setDaysButton;
    private JButton viewInfoButton;
    private JTextField alarmTime;
    private JTextField alarmText;
    private JTextField alarmDays;
    private JLabel label = new JLabel();
    private JPanel panel = new JPanel();
    private JPanel picturePanel = new JPanel();

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Creates the alarm GUI
    public AlarmListGUI() {
        super(new BorderLayout());

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        alarms = new AlarmList();

        listModel = new DefaultListModel();

        for (int n = 0; n < alarms.getListSize(); n++) {
            listModel.addElement(alarms.getAlarms().get(n));
        }

        //Create a list in a scroll pane.
        // SOURCE: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        buttonSetUp();

        //Create a panel using BoxLayout.
        panelSetUp(listScrollPane);

    }

    // MODIFIES: this
    // EFFECTS: Add buttons and text fields in a panel and add it to the frame
    private void panelSetUp(JScrollPane listScrollPane) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,
                BoxLayout.LINE_AXIS));

        buttonPanel.setLayout(new GridLayout(2,5));
        buttonPanel.add(removeButton);
        buttonPanel.add(alarmTime);
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        buttonPanel.add(viewInfoButton);
        buttonPanel.add(alarmText);
        buttonPanel.add(setTextButton);
        buttonPanel.add(alarmDays);
        buttonPanel.add(setDaysButton);
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);

        panel.add(label);
        add(panel, BorderLayout.EAST);

        addPicture();

    }

    // MODIFIES: this
    // EFFECTS: Add picture to the frame
    private void addPicture() {
        setLabelClockImage();
        picturePanel.add(imageLabel);
        add(picturePanel, BorderLayout.PAGE_START);
    }


    // MODIFIES: this
    // EFFECTS: Set the image to clock image
    private void setLabelClockImage() {
        String sep = System.getProperty("file.separator");
        // picture downloaded
        // from https://www.pinclipart.com/pindetail/iTomboh_alarm-clock-clipart-clock-face-png-download/
        clockImage = new ImageIcon(System.getProperty("user.dir")
                + sep + "data" + sep + "AlarmClock.png");
        Image image = clockImage.getImage();
        Image resizedImage = image.getScaledInstance(400, 350,  Image.SCALE_SMOOTH);
        clockImage = new ImageIcon(resizedImage);
        imageLabel  = new JLabel(clockImage);
    }

    // MODIFIES: this
    // EFFECTS: Set up the button and their actions
    private void buttonSetUp() {

        loadButton = new JButton(loadAlarm);
        LoadListener loadListener = new LoadListener();
        loadButton.setActionCommand(loadAlarm);
        loadButton.addActionListener(loadListener);

        removeButton = new JButton(removeAlarm);
        removeButton.setActionCommand(removeAlarm);
        removeButton.addActionListener(new RemoveListener());

        saveButton = new JButton(saveAlarm);
        saveButton.setActionCommand(saveAlarm);
        saveButton.addActionListener(new SaveListener());

        viewInfoButton = new JButton(viewInfo);
        viewInfoButton.setActionCommand(viewInfo);
        viewInfoButton.addActionListener(new ViewInfoListener());

        setTimeTextDayButtonTextField();
    }

    // MODIFIES: this
    // EFFECTS: Set up the set time, text, and days buttons and text fields
    private void setTimeTextDayButtonTextField() {
        addButton = new JButton(addAlarm);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addAlarm);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        alarmTime = new JTextField(5);
        alarmTime.addActionListener(addListener);
        alarmTime.getDocument().addDocumentListener(addListener);

        setTextButton = new JButton(setText);
        SetTextListener setTextListener = new SetTextListener();
        setTextButton.setActionCommand(setText);
        setTextButton.addActionListener(setTextListener);

        alarmText = new JTextField(10);
        alarmText.addActionListener(setTextListener);

        setDaysButton = new JButton(setDays);
        SetDaysListener setDaysListener = new SetDaysListener();
        setDaysButton.setActionCommand(setDays);
        setDaysButton.addActionListener(setDaysListener);

        alarmDays = new JTextField(5);
        alarmDays.addActionListener(setDaysListener);
    }

    // Action listener for view information button
    class ViewInfoListener implements ActionListener {
        @Override
        // MODIFIES: this
        // EFFECTS: set label to the alarm's information
        public void actionPerformed(ActionEvent e) {
            label.setText("REMINDER TEXT: " + list.getSelectedValue().getReminderText() + "    REPEATING DAYS "
                    + list.getSelectedValue().getRepeatDays());
        }
    }

    // Action listener for the remove button
    class RemoveListener implements ActionListener {
        // SOURCE: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java

        // MODIFIES: this
        // EFFECTS: remove the alarm selected and set the clock image
        public void actionPerformed(ActionEvent e) {
            alarms.deleteAlarm(list.getSelectedValue());
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) {
                removeButton.setEnabled(false);
            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
            setClockImage();
        }
    }

    // MODIFIES: this
    // EFFECTS: repaint the clock image
    private void setClockImage() {
        removeExistingImage();
        setLabelClockImage();
        picturePanel.add(imageLabel);
        validate();
        repaint();
    }

    // Action listener for save button
    class SaveListener implements ActionListener {
        @Override
        // this
        // EFFECTS: saves the alarm list to file and change the image to the save image
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(alarms);
                jsonWriter.close();
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }

            String sep = System.getProperty("file.separator");
            // picture downloaded
            // from https://www.pinclipart.com/maxpin/oxoTw/
            ImageIcon saveImage = new ImageIcon(System.getProperty("user.dir")
                    + sep + "data" + sep + "SavedClock.png");
            Image image = saveImage.getImage();
            Image resizedImage = image.getScaledInstance(300, 350,  java.awt.Image.SCALE_SMOOTH);
            saveImage = new ImageIcon(resizedImage);

            removeExistingImage();
            imageLabel = new JLabel(saveImage);
            picturePanel.add(imageLabel);
            validate();
            repaint();

            JOptionPane.showMessageDialog(null, "Successfully Saved!", "Saved",
                    JOptionPane.INFORMATION_MESSAGE, saveImage);
        }
    }

    // MODIFIES: this
    // EFFECTS: remove the current image
    private void removeExistingImage() {
        if (imageLabel != null) {
            picturePanel.remove(imageLabel);
        }
    }

    // action listener for the load button
    class LoadListener implements ActionListener {
        @Override
        // MODIFIES: this
        // EFFECTS: loads alarm list from file
        public void actionPerformed(ActionEvent e) {
            try {
                alarms = null;
                alarms = jsonReader.read();
                listModel.removeAllElements();
                for (int n = 0; n < alarms.getListSize(); n++) {
                    listModel.addElement(alarms.getAlarms().get(n));
                }
            } catch (IOException ex) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // action listener for the set days button
    class SetDaysListener implements ActionListener {

        @Override
        // MODIFIES: this
        // EFFECTS: set the day to the selected alarm and change to the clock image
        public void actionPerformed(ActionEvent e) {
            list.getSelectedValue().addRepeatDay(alarmDays.getText());

            //Reset the text field.
            alarmDays.requestFocusInWindow();
            alarmDays.setText("");

            setClockImage();
        }
    }

    // action listener for the set text button
    class SetTextListener implements ActionListener {

        @Override
        // MODIFIES: this
        // EFFECTS: set the text to the selected alarm and change to the clock image
        public void actionPerformed(ActionEvent e) {
            list.getSelectedValue().setReminderText(alarmText.getText());

            //Reset the text field.
            alarmText.requestFocusInWindow();
            alarmText.setText("");

            setClockImage();
        }
    }

    //action listener for the text field and the add button.
    class AddListener implements ActionListener, DocumentListener {
        // SOURCE: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        private boolean alreadyEnabled = false;
        private JButton button;

        // EFFECTS: set the button
        public AddListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: add the alarm from the text field and set the clock image
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }
            Alarm newAlarm = new Alarm(Integer.parseInt(alarmTime.getText()));
            alarms.addAlarm(newAlarm);

            listModel.insertElementAt(newAlarm, index);

            //Reset the text field.
            alarmTime.requestFocusInWindow();
            alarmTime.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

            setClockImage();
        }

        // EFFECTS: enable button
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // EFFECTS: check is text field is empty
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // EFFECTS: enable button when text field is not empty
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // EFFECTS: enable button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // EFFECTS: check if text field is empty and disable button
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // EFFECTS: enable button if an alarm is selected else disable
    public void valueChanged(ListSelectionEvent e) {
        // SOURCE: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }

    //EFFECTS: create and run the GUI
    private static void createAndShowGUI() {
        // SOURCES: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        //          https://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
        //Create and set up the window.
        JFrame frame = new JFrame("AlarmApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        };
        frame.addWindowListener(exitListener);


        //Create and set up the content pane.
        JComponent newContentPane = new AlarmListGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    //EFFECTS: prints the log
    public static void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }


    //create and show alarm application's GUI.
    public static void main(String[] args) {
        // SOURCE: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }

}
