package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;

public class Helpers {
    private static final Connection db = JDBC.getConnection();

    /**
     * @param dbQuery String query to push to DB
     * @return ResultSet of response from DB
     * @throws SQLException SQL exception handler
     */
    public static ResultSet DBQuery(String dbQuery) throws SQLException {
        PreparedStatement ps = db.prepareStatement(dbQuery);
        ResultSet rs;
        return rs = ps.executeQuery();
    }

    /**
     * By providing a table and key value, this function will return the creation date and user so it is not overwritten
     * @param table table to pull created by and created date from
     * @param key Primary Key to filter on
     * @return ResultSet of both Create_Date and Created_By
     * @throws SQLException SQL exception handler
     */
    public static ResultSet DBgetCreation(String table, int key) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        switch (table) {
            case "countries":
                ps = db.prepareStatement("SELECT Create_Date, Created_By FROM countries WHERE Country_ID = " + key);
                return rs = ps.executeQuery();
            case "first_level_divisions":
                ps = db.prepareStatement("SELECT Create_Date, Created_By FROM first_level_divisions WHERE Division_ID = " + key);
                return rs = ps.executeQuery();
            case "customers":
                ps = db.prepareStatement("SELECT Create_Date, Created_By FROM customers WHERE Customer_ID = Customer_ID" + key);
                return rs = ps.executeQuery();
            case "appointments":
                ps = db.prepareStatement("SELECT Create_Date, Created_By FROM appointments WHERE Appointment_ID" + key);
                return rs = ps.executeQuery();
            case "users":
                ps = db.prepareStatement("SELECT Create_Date, Created_By FROM users WHERE User_ID" + key);
                return rs = ps.executeQuery();
            case "contacts":
                ps = db.prepareStatement("SELECT Create_Date, Created_By FROM contacts WHERE Contact_ID" + key);
                return rs = ps.executeQuery();
        }
        return null;
    }

    /**
     * @param dbQuery String statement to push to DB
     * @throws SQLException SQL exception handler
     */
    public static void DBExec(String dbQuery) throws SQLException {
        PreparedStatement ps = db.prepareStatement(dbQuery);
        ps.execute();
    }

    /**
     * Retrieve Zone ID
     * @return Current Zone ID
     */
    public static ZoneId getZoneID() { return ZoneId.systemDefault(); }

    /**
     * Sets locale up to be used by other classes for language translation
     */
    public static void setLocale() {
        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);
    }

    /**
     * Helper menu switcher
     * @param event Event to engage menu switch
     * @param newMenu String value of new menu .fxml file to open
     * @throws IOException Handle if file opening error occurs
     */
    public static void openMenu(ActionEvent event, String newMenu) throws IOException {
        Parent parent = FXMLLoader.load(Helpers.class.getResource(newMenu));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * takes String input and displays that string in an alert.
     * @param message output of error screen
     */
    public static void WarningMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * takes String input and displays that string in a confirmation message.
     * @param message output of confirmation screen
     */
    public static boolean ConfirmationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }


    /**
     * @param ldt local timestamp to be converted to UTC
     * @return UTC timestamp
     */
    public static LocalDateTime getUTCTime(LocalDateTime ldt) {
        ZonedDateTime ldtZoned = ldt.atZone(getZoneID());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toLocalDateTime();
    }

    /**
     * @param utc UTC timestamp to be converted to user's local timezone
     * @return converted local timestamp
     */
    public static LocalDateTime getLocalTime(LocalDateTime utc) {
        ZonedDateTime utcZoned = utc.atZone(getZoneID());
        return utcZoned.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(getZoneID()).toLocalDateTime();
    }

    /**
     * @param utc UTC time to be converted to EST for business purposes
     * @return converted EST timestamp
     */
    public static LocalDateTime getBusinessTime(LocalDateTime utc) {
        ZonedDateTime utcZoned = utc.atZone(getZoneID());
        return utcZoned.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("US/Eastern")).toLocalDateTime();
    }
}
