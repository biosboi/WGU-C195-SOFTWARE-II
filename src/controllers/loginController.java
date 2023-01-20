package controllers;

import DBControllers.ContactsDB;
import DBControllers.UsersDB;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Login Menu Controller
 * @author William Nathan
 */
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
        try {
            System.out.println(ContactsDB.getContactName(1));
            System.out.println(ContactsDB.getContactName(2));
            System.out.println(ContactsDB.getContactName(3));
            System.out.println((UsersDB.getUsername(1)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
