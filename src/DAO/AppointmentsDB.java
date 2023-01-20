package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        String query = "SELECT * FROM appointments";
        PreparedStatement ps = db.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String type = rs.getString("Type");
            Time appointmentStart = rs.getTime("Start");
            Time appointmentEnd = rs.getTime("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appointmentID, title, description, type, appointmentStart, appointmentEnd, customerID, userID, contactID);
            allAppointmentsList.add(appointment);
        }
        return allAppointmentsList;
    }
    public static boolean newAppointment() throws SQLException {
        String query = "SELECT * FROM appointments";
        PreparedStatement ps = db.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        return true;
    }
}
