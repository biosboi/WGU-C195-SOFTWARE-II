package controllers;

import DAO.AppointmentsDB;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.Appointments;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class reportMenuController implements Initializable {
    @FXML
    public TextArea customerText;
    @FXML
    public TextArea contactsText;
    @FXML
    public TextArea ADDITIONALText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // the total number of customer appointments by type and month
        try {
            ObservableList<Appointments> allApts = AppointmentsDB.getAllAppointments();
            // SELECT Start,Type FROM appointments ORDER BY Start,Type;
            // SELECT COUNT(Appointment_ID) FROM Appointments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // a schedule for each contact in your organization that includes
        // appointment ID, title, type and description, start date and time, end date and time, and customer ID


        // an additional report of your choice that is different from the two other required reports
        // in this prompt and from the user log-in date and time stamp that will be tracked in part C

    }
}
