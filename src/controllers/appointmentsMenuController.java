package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Helpers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class appointmentsMenuController implements Initializable {
    @FXML
    public TableView aptsTable;
    @FXML
    public TableColumn aptsTable_ID;
    @FXML
    public TableColumn aptsTable_Title;
    @FXML
    public TableColumn aptsTable_Description;
    @FXML
    public TableColumn aptsTable_Location;
    @FXML
    public TableColumn aptsTable_Contact;
    @FXML
    public TableColumn aptsTable_Type;
    @FXML
    public TableColumn aptsTable_Start;
    @FXML
    public TableColumn aptsTable_End;
    @FXML
    public TableColumn aptsTable_CustomerID;
    @FXML
    public TableColumn aptsTable_UserID;
    @FXML
    public Button addButton;
    @FXML
    public Button modifyButton;
    @FXML
    public Button removeButton;
    @FXML
    public Button clearButton;
    @FXML
    public Button returnButton;
    @FXML
    public TextField id_field;
    @FXML
    public TextField title_field;
    @FXML
    public TextField description_field;
    @FXML
    public TextField location_field;
    @FXML
    public TextField type_field;
    @FXML
    public TextField start_field;
    @FXML
    public TextField end_field;
    @FXML
    public TextField customerID_field;
    @FXML
    public TextField userID_field;
    @FXML
    public ComboBox<String> contactComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    public void addAppointment(ActionEvent actionEvent) {
    }

    public void modifyAppointment(ActionEvent actionEvent) {
    }

    public void removeAppointment(ActionEvent actionEvent) {
    }

    public void clearFields(ActionEvent actionEvent) {
    }

    /**
     * Return to main menu
     * @param click Execute on button click
     * @throws IOException Handle menu open exception
     */
    public void previousMenu(ActionEvent click) throws IOException {
        Helpers.openMenu(click, "../mainMenu.fxml");
    }
}
