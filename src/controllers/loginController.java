package controllers;

import DB_access.AppointmentsDB;
import DB_access.UsersDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Helpers;
import main.JDBC;
import main.Logger;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Login Menu Controller
 * @author William Nathan
 */

public class loginController implements Initializable {
    @FXML
    private Label titleLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label location;
    @FXML
    private Button exitButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private String emptyCreds = "";
    @FXML
    private String badCreds = "";
    public static Users loggedIn;

    /**
     * Initialize data to be used in menu
     * @param url init url reference
     * @param resourceBundle init resources reference
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Init Zone ID and Locale
        Helpers.setLocale();
        ZoneId zone = Helpers.getZoneID();
        location.setText(String.valueOf(zone));

        // Set login menu labels based on language. Currently supports French and English
        ResourceBundle languageResources = ResourceBundle.getBundle("Bundle", Locale.getDefault());
        titleLabel.setText(languageResources.getString("label.title"));
        usernameLabel.setText(languageResources.getString("label.username"));
        passwordLabel.setText(languageResources.getString("label.password"));
        loginButton.setText(languageResources.getString("button.login"));
        exitButton.setText(languageResources.getString("button.exit"));
        locationLabel.setText(languageResources.getString("label.location"));
        emptyCreds = languageResources.getString("error.emptyLogin");
        badCreds = languageResources.getString("error.invalidLogin");
    }

    /**
     * Verify password and username are correct. If so, load main menu
     * @param click Execute on button click
     * @throws IOException Handle menu open exception
     */
    public void loginButton_click(ActionEvent click) throws IOException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Logger.log(username, false);
            Helpers.WarningMessage(emptyCreds);
        } else if (UsersDB.loginVerification(username, password) > -1) { // Success
            // Store logged-in user for later use
            loggedIn = new Users(UsersDB.getUserID(username), username, password);

            // Check if appointment within 15 minutes. Checks local time of appointment
            boolean late = false;
            List<Integer> userApts = UsersDB.getUserAppointments(UsersDB.getUserID(username));
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            for (Integer a : userApts) {
                Timestamp start = Timestamp.valueOf(Helpers.getLocalTime(AppointmentsDB.getStartTime(a)));
                if (Math.abs(start.getTime() - now.getTime()) < TimeUnit.MINUTES.toMillis(15)) {
                    late = true;
                    String msg = "You have an appointment within 15 minutes!\nID: " + a + "\nTime: " + Helpers.getLocalTime(AppointmentsDB.getStartTime(a));
                    Helpers.WarningMessage(msg);
                }
            }
            if (!late) {
                Helpers.WarningMessage("There are no upcoming appointments.");
            }

            // Log and continue
            Logger.log(username, true);
            Helpers.openMenu(click, "../view/mainMenu.fxml");
        } else {
            Logger.log(username, false);
            Helpers.WarningMessage(badCreds);
        }
    }

    /**
     * Retrieve Users object of user that has logged in successfully.
     */
    public static Users getLoggedIn() {
        return loggedIn;
    }

    /**
     * Close menu / exit program
     * @param click cancel button click
     */
    public void exitButton_click(ActionEvent click) {
        JDBC.closeConnection();
        Stage stage = (Stage) ((Node) click.getSource()).getScene().getWindow();
        stage.close();
    }

}
