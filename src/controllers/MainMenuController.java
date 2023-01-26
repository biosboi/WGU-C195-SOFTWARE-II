package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Helpers;
import main.JDBC;

import java.io.IOException;

/**
 * Main Menu Controller
 * @author William Nathan
 */

public class MainMenuController {
    @FXML
    public Button customerMenuButton;
    @FXML
    public Button appointmentsMenuButton;
    @FXML
    public Button reportMenuButton;
    @FXML
    public Button logoutButton;
    @FXML
    public Button exitButton;

    /**
     * Opens customer menu
     * @param click Execute on button click
     * @throws IOException Handle menu open exception
     */
    public void customerMenu(ActionEvent click) throws IOException { Helpers.openMenu(click, "../view/customerMenu.fxml"); }

    /**
     * Opens appointments menu
     * @param click Execute on button click
     * @throws IOException Handle menu open exception
     */
    public void appointmentsMenu(ActionEvent click) throws IOException { Helpers.openMenu(click, "../view/appointmentsMenu.fxml"); }

    /**
     * Opens report menu
     * @param click Execute on button click
     * @throws IOException Handle menu open exception
     */
    public void reportMenu(ActionEvent click) throws IOException { Helpers.openMenu(click, "../view/reportMenu.fxml"); }

    /**
     * Return to login menu
     * @param click Execute on button click
     * @throws IOException Handle menu open exception
     */
    public void logout(ActionEvent click) throws IOException { Helpers.openMenu(click, "../view/login.fxml"); }

    /**
     * Exit program
     * @param click Execute on button click
     */
    public void exit(ActionEvent click) {
        JDBC.closeConnection();
        Stage stage = (Stage) ((Node) click.getSource()).getScene().getWindow();
        stage.close();
    }
}
