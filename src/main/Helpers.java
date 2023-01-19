package main;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Helpers {
    private Connection db = JDBC.getConnection();

    /**
     * takes String input and displays that string in an alert.
     * @param message output of error screen
     */
    public void WarningMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * takes String input and displays that string in a confirmation message.
     * @param message output of confirmation screen
     */
    public boolean ConfirmationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setContentText(message);
        alert.showAndWait();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        } else if (result.isPresent() && result.get() != ButtonType.OK) {
            return false;
        }
    }

    /**
     * Retrieve contact name based on contact ID
     * @param contactId find name with this id
     * @return associated contact name
     */
    public String getContactName(int contactId) throws SQLException {
        Statement stmt = db.createStatement();
        String query = "SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contactId;
        JDBC.makePreparedStatement(query, db);
        ResultSet rs = stmt.executeQuery(query);
        contactName.
        return contactName;
    }

    /**
     * Retrieve contact ID based on contact name
     * @param contactName find name with this id
     * @return associated contact ID
     */
    public int getContactName(String contactName) throws SQLException  {
        int contactId = 0;
        return contactId;
    }

    // take a username and change it to UserID & vice versa.

    public void () {

    }
    // takes First and Second level country data and turn it into a Division ID & Vice Versa.
    public void  () {

    }

    // converts between all the timezones. UTC -> Local, UTC -> Eastern, System -> UTC etc. This will save you lots of time and headache. Make these, test these and you will save the hardest part of your project. Use LocalDateTime objects and ZoneID’s to do this. There are resources online.
    public void timeZoneConvert() {

    }
}
