package controllers;

import DAO.AppointmentsDB;
import DAO.ContactsDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Helpers;
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class appointmentsMenuController implements Initializable {
    private final ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    private final ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
    private final ArrayList<String> allContactsNames = new ArrayList<>();
    @FXML
    public TableView<Appointments> aptsTable;
    @FXML
    public TableColumn<Appointments, Integer> aptsTable_ID;
    @FXML
    public TableColumn<Appointments, String> aptsTable_Title;
    @FXML
    public TableColumn<Appointments, String> aptsTable_Description;
    @FXML
    public TableColumn<Appointments, String> aptsTable_Location;
    @FXML
    public TableColumn<Appointments, String> aptsTable_Contact;
    @FXML
    public TableColumn<Appointments, String> aptsTable_Type;
    @FXML
    public TableColumn<Appointments, Time> aptsTable_Start;
    @FXML
    public TableColumn<Appointments, Time> aptsTable_End;
    @FXML
    public TableColumn<Appointments, Integer> aptsTable_CustomerID;
    @FXML
    public TableColumn<Appointments, Integer> aptsTable_UserID;
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
    public DatePicker start_field;
    @FXML
    public DatePicker end_field;
    @FXML
    public TextField customerID_field;
    @FXML
    public TextField userID_field;
    @FXML
    public ComboBox<String> contactComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize Name and object lists for Customers / Countries / Divisions
        try {
            allAppointments.setAll(AppointmentsDB.getAllAppointments());
            allContacts.setAll(ContactsDB.getAllContacts());
            for (Contacts c : allContacts) {
                allContactsNames.add(c.getContactName());
            }
            contactComboBox.getItems().addAll(allContactsNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        aptsTable.setItems(allAppointments);
        aptsTable.refresh();

        aptsTable_ID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        aptsTable_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptsTable_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptsTable_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptsTable_Contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        aptsTable_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptsTable_Start.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        aptsTable_End.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        aptsTable_CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        aptsTable_UserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        // Add listener to table to update text fields on selection
        aptsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldA, newA) -> {
            if (newA != null) {
                try {
                    id_field.setText(Integer.toString(newA.getAppointmentID()));
                    title_field.setText(newA.getTitle());
                    description_field.setText(newA.getDescription());
                    location_field.setText(newA.getLocation());
                    type_field.setText(newA.getType());
                    //start_field.setValue(newA.getAppointmentStart());
                    //end_field.(newA.getAppointmentEnd());
                    customerID_field.setText(Integer.toString(newA.getCustomerID()));
                    userID_field.setText(Integer.toString(newA.getUserID()));
                    contactComboBox.setValue(ContactsDB.getContactName(newA.getContactID()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



    public void addAppointment(ActionEvent actionEvent) {
    }

    public void modifyAppointment(ActionEvent actionEvent) {
    }

    public void removeAppointment(ActionEvent actionEvent) {
    }

    /**
     * Clears out text fields for cleanup after customer data manipulation
     */
    public void clearFields() {
        id_field.clear();
        title_field.clear();
        description_field.clear();
        location_field.clear();
        type_field.clear();
        start_field.setValue(null);
        end_field.setValue(null);
        customerID_field.clear();
        userID_field.clear();
        contactComboBox.getSelectionModel().clearSelection();
        contactComboBox.getItems().clear();
        aptsTable.getSelectionModel().clearSelection();
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
