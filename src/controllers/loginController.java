package controllers;

import DAO.UsersDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Helpers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

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
            Helpers.WarningMessage(emptyCreds);
        } else if (UsersDB.loginVerification(username, password) > -1) {
            Helpers.openMenu(click, "../view/mainMenu.fxml");
        } else {
            Helpers.WarningMessage(badCreds);
        }
    }

    /**
     * Close menu / exit program
     * @param buttonClick cancel button click
     */
    public void exitButton_click(ActionEvent buttonClick) {
        Stage stage = (Stage) ((Node) buttonClick.getSource()).getScene().getWindow();
        stage.close();
    }

}
