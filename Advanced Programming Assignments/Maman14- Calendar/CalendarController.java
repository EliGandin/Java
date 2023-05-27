import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class CalendarController {

    @FXML
    private GridPane datesArray;

    private Button[] btns;
    private Appointments<Calendar, String> appointments;
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private String currMonth = "";
    private int currYear = -1;
    private final String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};


    @FXML
    private ChoiceBox<String> month;

    @FXML
    private ChoiceBox<Integer> year;

    public void initialize() {
        appointments = new Appointments<Calendar, String>();
        setCalendar();
        populateCalendar();
    }

    private void setCalendar() {
        month.getItems().addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"); // Initializing the ChoiceBox with months.
        month.setValue("DECEMBER");
        for (int i = 1950; i <= 2100; i++) {
            year.getItems().add(i);
        }
        year.setValue(2022);
    }

    private void populateCalendar() {
        datesArray.getChildren().clear();
        int i = 0;
        int numOfDays = getDaysInMonth();
        int temp = getFirstDayOfMonth() - 1;
        int dayOfMonth = 0;
        btns = new Button[numOfDays];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (col < temp && row == 0) {
                    continue;
                }
                dayOfMonth++;
                if (dayOfMonth > numOfDays) {
                    continue;
                }
                btns[i] = new Button(String.valueOf(dayOfMonth));
                btns[i].setPrefHeight(datesArray.getPrefHeight());
                btns[i].setPrefWidth(datesArray.getPrefWidth());
                datesArray.add(btns[i], col, row);
                btns[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setAppointment(event);
                    }
                });
                i++;
            }
        }
    }


    private void setAppointment(ActionEvent event) {
        Button btn = (Button) event.getSource();
        Calendar c = Calendar.getInstance();
        c.set(year.getValue(), monthToNum(), Integer.parseInt(btn.getText()));
        Date date = getDateWithoutTime(c);
        if (!appointments.foundInApp(date)) {
            addAction(date);
        } else {
            String[] options = {"Edit", "Append"};
            int x = JOptionPane.showOptionDialog(null, "The date: " + date + ", is already added to your schedule\n" +
                            "With the following meeting: " + appointments.searchKey(date) + "\n" + "Would you like to edit or append your appointment in that day instead?",
                    "An appointment has already been set - please select an option",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (x == 0) {
                editAction(date, x);
            }
            if (x == 1) {
                editAction(date, x);
            }
        }
    }

    private void addAction(Date key) {
        String appointment = JOptionPane.showInputDialog("Enter an appointment please"); // The input inserted is the value.
        if (appointment != null && !appointment.equals("")) {
            String message = "The following appointment has been added successfully:" + '\n' + appointment;
            JOptionPane.showMessageDialog(null, message);
            appointments.add(key, appointment);
        }
    }

    private void editAction(Date key, int actionCode) {
        String appointment = JOptionPane.showInputDialog("Appointment details:");
        if (appointment != null && !appointment.equals("")) {
            if (actionCode == 0) {
                appointments.update(key, appointment);
                String message = "Your calendar was updated successfully: " + '\n' + appointment;
                JOptionPane.showMessageDialog(null, message);
            }
            if (actionCode == 1) {
                String newAppointment = appointments.searchKey(key) + "\n" + appointment;
                appointments.update(key, newAppointment);
                String message = "Your calendar was updated successfully: " + '\n' + newAppointment;
                JOptionPane.showMessageDialog(null, message);
            }
        }
    }

    public Date getDateWithoutTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    private Calendar getMonth() {
        month.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currMonth = month.getValue();
                populateCalendar();
            }
        });

        year.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currYear = year.getValue();
                populateCalendar();
            }
        });

        currMonth = month.getValue();
        int monthNum = monthToNum();
        Calendar c = Calendar.getInstance();
        c.set(year.getValue(), monthNum, 1);
        return c;
    }


    private int monthToNum() {
        int monthNum = -1;
        String relevantMonth = currMonth;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(relevantMonth))
                monthNum = i;
        }
        return monthNum;
    }

    private int getDaysInMonth() {
        int monthNum = monthToNum();
        Calendar c = Calendar.getInstance();
        c.set(year.getValue(), monthNum, 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private int getFirstDayOfMonth() {
        int monthNum = monthToNum();
        Calendar c = getMonth();
        return c.get(Calendar.DAY_OF_WEEK);
    }

    private int getFirstDayOfMonthOffset() {
        Calendar c = getMonth();
        int firstDayOfWeek = c.getFirstDayOfWeek();
        return firstDayOfWeek - 1;
    }
}
