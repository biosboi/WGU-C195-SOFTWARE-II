package DB_access;

import controllers.loginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Helpers;
import model.Appointments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Appointments Database Accessor
 * @author William Nathan
 */
public class AppointmentsDB {

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
            LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
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
     *
     * @param apt Appointment object used to push into DB
     * @throws SQLException SQL exception handler
     */
    public static void addAppointment(Appointments apt) throws SQLException {
        String queryBuild = Appointments.newAppointmentID() + ", '" + apt.getTitle() + "', '" + apt.getDescription() + "', '" + apt.getLocation() + "', '" + apt.getType() + "', '" + apt.getAppointmentStart() + "', '" + apt.getAppointmentEnd() + "', NOW(),'" + loginController.getLoggedIn().getUserName() + "', NOW(),'" + loginController.getLoggedIn().getUserName() + "', " + apt.getCustomerID() + ", " + apt.getUserID() + ", " + apt.getContactID();
        Helpers.DBExec( "INSERT INTO appointments VALUES (" + queryBuild + ");");
    }

    /**
     * @param apt Appointment object to be updated. Will retain appointment ID
     * @throws SQLException SQL exception handler
     */
    public static void modifyAppointment(Appointments apt) throws SQLException {
        Helpers.DBExec(
                "UPDATE appointments SET " +
                        "Title = '" + apt.getTitle() +
                        "', Description = '" + apt.getDescription() +
                        "', Location = '" + apt.getLocation() +
                        "', Type = '" + apt.getType() +
                        "', Start = '" + apt.getAppointmentStart() +
                        "', End = '" + apt.getAppointmentEnd() +
                        "', Last_Update = NOW(), Last_Updated_By = '" + loginController.getLoggedIn().getUserName() +
                        "', Customer_ID = " + apt.getCustomerID() +
                        ", User_ID = " + apt.getUserID() +
                        ", Contact_ID = " + apt.getContactID() +
                        " WHERE Appointment_ID = " + apt.getAppointmentID()
        );
    }

    /**
     * Delete appointment based on appointment ID
     * @param apt Appointment ID to delete
     * @return boolean true if successful, false if failed
     */
    public static boolean deleteAppointment(int apt) throws SQLException {
        Helpers.DBExec("DELETE FROM appointments WHERE Appointment_ID =" + apt);
        return true;
    }

    /**
     * @param aptIDs Appointment ID to grab start time with
     * @return LocalDateTime value of appointment start
     * @throws SQLException SQL exception handler
     */
    public static LocalDateTime getStartTime (Integer aptIDs) throws SQLException {
        LocalDateTime startTime = null;
        ResultSet rs = Helpers.DBQuery( "SELECT Start FROM appointments WHERE Appointment_ID = " + aptIDs);
        while (rs.next()) {
            startTime = rs.getTimestamp("Start").toLocalDateTime();
        }
        return startTime;
    }

    /**
     * @param aptIDs Appointment ID to grab end time from
     * @return LocalDateTime value of appointment end
     * @throws SQLException SQL exception handler
     */
    public static LocalDateTime getEndTime (Integer aptIDs) throws SQLException {
        LocalDateTime endTime = null;
        ResultSet rs = Helpers.DBQuery( "SELECT End FROM appointments WHERE Appointment_ID = " + aptIDs);
        while (rs.next()) {
            endTime = rs.getTimestamp("End").toLocalDateTime();
        }
        return endTime;
    }
}