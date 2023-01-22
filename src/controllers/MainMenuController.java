package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.Helpers;

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
}
