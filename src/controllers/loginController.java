package controllers;
/**
 * Login menu controller
 * @author William Nathan
 */

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    public Label titleLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label locationLabel;
    public Button exitButton;
    public Button loginButton;
    public TextField usernameField;
    public TextField passwordField;

    /**
     * Initialize data to be used in menu
     * @param url init url reference
     * @param resourceBundle init resources reference
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginButton_click(ActionEvent actionEvent) {

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
