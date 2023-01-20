package model;

import java.sql.Time;

/**
 * Appointments model
 * @author William Nathan
 */

public class Appointments {
    public int appointmentID;
    public String title;
    public String description;
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
    public Appointments (int appointmentID, String title, String description, String type, Time appointmentStart, Time appointmentEnd, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
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
}
