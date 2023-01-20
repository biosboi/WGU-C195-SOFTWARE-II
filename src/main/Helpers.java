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
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;

public class Helpers {
    private static final Connection db = JDBC.getConnection();

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
        alert.showAndWait();
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Convert one time zone to another
     * @param oldTime Previous time zone
     * @param newTime Updated time zone
     */
    public void timeZoneConvert(String oldTime, String newTime) {

    }
}
