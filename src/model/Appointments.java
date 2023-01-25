package model;

import DAO.AppointmentsDB;
import DAO.CustomersDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Time;

/**
 * Appointments model
 * @author William Nathan
 */

public class Appointments {
    private ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();
    public int appointmentID;
    public String title;
    public String description;
    public String location;
    public String type;
    public Time appointmentStart;
    public Time appointmentEnd;
    public int customerID;
    public int userID;
    public int contactID;

    /**
     * @param appointmentID PK
     * @param title Title of appointment
     * @param description Description of appointment
     * @param type Type of appointment
     * @param appointmentStart Start time of appointment
     * @param appointmentEnd End time of appointment
     * @param customerID FK
     * @param userID FK
     * @param contactID FK
     */
    public Appointments (int appointmentID, String title, String description, String location, String type, Time appointmentStart, Time appointmentEnd, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * @return Appointment ID
     */
    public int getAppointmentID() { return appointmentID; }

    /**
     * @return Appointment Title
     */
    public String getTitle() { return title; }

    /**
     * @return Appointment Description
     */
    public String getDescription() { return description; }

    /**
     * @return Appointment Location
     */
    public String getLocation() { return location; }

    /**
     * @return Appointment Type
     */
    public String getType() { return type; }

    /**
     * @return Appointment Start Time
     */
    public Time getAppointmentStart() { return appointmentStart; }

    /**
     * @return Appointment End Time
     */
    public Time getAppointmentEnd() { return appointmentEnd; }

    /**
     * @return Customer ID
     */
    public int getCustomerID() { return customerID; }

    /**
     * @return User ID
     */
    public int getUserID() { return userID; }

    /**
     * @return Contact ID
     */
    public int getContactID() { return contactID; }

     /**
     * Generates a new ID which is incremented from previous known ID's
     * @return lastMaxId
     */
    public int newAppointmentID() throws SQLException {
        ObservableList<Appointments> appointmentList = AppointmentsDB.getAllAppointments();
        int lastMaxId = 0;
        for(Appointments a : appointmentList) {
            if (a.getAppointmentID() > lastMaxId) {
                lastMaxId = a.getAppointmentID();
            }
        }
        return ++lastMaxId;
    }
}
