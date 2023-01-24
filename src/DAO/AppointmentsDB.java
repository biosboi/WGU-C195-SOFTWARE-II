package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Helpers;
import main.JDBC;
import model.Appointments;

import java.sql.*;

/**
 * Appointments Database Accessor
 * @author William Nathan
 */
public class AppointmentsDB {
    private static final Connection db = JDBC.getConnection();

    /**
     * Generate list of all Appointments in database.
     * @return allAppointmentsList
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> allAppointmentsList = FXCollections.observableArrayList();
        ResultSet rs = Helpers.DBQuery( "SELECT * FROM appointments");
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Time appointmentStart = rs.getTime("Start");
            Time appointmentEnd = rs.getTime("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appointmentID, title, description, location, type, appointmentStart, appointmentEnd, customerID, userID, contactID);
            allAppointmentsList.add(appointment);
        }
        return allAppointmentsList;
    }

    /**
     * Input appointment object and database will be updated with new appointment
     * @param apt Appointment object used to push into DB
     * @return boolean true if successful, false if failed
     * @throws SQLException SQL exception handler
     */
    public static boolean newAppointment(Appointments apt) throws SQLException {
        String queryBuild = apt.newAppointmentID() + ", " + apt.getTitle() + ", " + apt.getDescription() + ", " + apt.getLocation() + ", " + apt.getType() + ", " + apt.getAppointmentStart() + ", " + apt.getAppointmentEnd() + ", NOW(), CURRENT_USER, NOW(), CURRENT_USER" + ", " + apt.getCustomerID() + ", " + apt.getUserID() + ", " + apt.getContactID();
        Helpers.DBQuery( "INSERT INTO appointments VALUES (" + queryBuild + ");");
        return true;
    }

    public static boolean modifyAppointment(Appointments apt) {
        // Delete old appointment
        return true;
    }

    /**
     * Delete appointment based on appointment ID
     * @param apt Appointment ID to delete
     * @return boolean true if successful, false if failed
     */
    public static boolean deleteAppointment(int apt) throws SQLException {
        Helpers.DBQuery("DELETE FROM appointments WHERE Appointment_ID =" + apt);
        return true;
    }
}
