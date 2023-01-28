package controllers;

import DAO.AppointmentsDB;
import DAO.ContactsDB;
import DAO.CustomersDB;
import DAO.UsersDB;
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
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class appointmentsMenuController implements Initializable {
    private final ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    private final ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
    private final ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
    private final ObservableList<Users> allUsers = FXCollections.observableArrayList();
    private final ArrayList<String> allContactsNames = new ArrayList<>();
    private final ArrayList<Integer> allUsersIDs = new ArrayList<>();
    private final ArrayList<Integer> allCustomersIDs = new ArrayList<>();
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
    public TableColumn<Appointments, LocalDateTime> aptsTable_Start;
    @FXML
    public TableColumn<Appointments, LocalDateTime> aptsTable_End;
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
    public ComboBox<String> customerIDComboBox;
    @FXML
    public ComboBox<String> userIDComboBox;
    @FXML
    public ComboBox<String> contactComboBox;
    @FXML
    public ComboBox<Integer>  startTimeMin;
    @FXML
    public ComboBox<Integer> startTimeHour;
    @FXML
    public ComboBox<Integer> endTimeHour;
    @FXML
    public ComboBox<Integer> endTimeMin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize Name and object lists for Appointments / Contacts / Customers / Users
        Helpers.setLocale();

        try {
            // Fill out combo boxes
            allAppointments.setAll(AppointmentsDB.getAllAppointments());
            allContacts.setAll(ContactsDB.getAllContacts());
            allCustomers.setAll(CustomersDB.getAllCustomers());
            allUsers.setAll(UsersDB.getAllUsers());

            for (Contacts c : allContacts) {
                allContactsNames.add(c.getContactName());
            }
            for (Users u : allUsers) {
                allUsersIDs.add(u.getUserID());
            }
            for (Customers c : allCustomers) {
                allCustomersIDs.add(c.getCustomerID());
            }

            contactComboBox.getItems().addAll(allContactsNames);
            for (Integer i : allUsersIDs) { userIDComboBox.getItems().add(String.valueOf(i)); }
            for (Integer i : allCustomersIDs) { customerIDComboBox.getItems().add(String.valueOf(i)); }

            List<Integer> hours = IntStream.rangeClosed(0, 23)
                    .boxed().toList();
            List<Integer> minutes = IntStream.rangeClosed(0, 59)
                    .boxed().toList();

            startTimeHour.getItems().addAll(hours);
            startTimeMin.getItems().addAll(minutes);
            endTimeHour.getItems().addAll(hours);
            endTimeMin.getItems().addAll(minutes);

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
                    start_field.setValue(LocalDate.from(newA.getAppointmentStart())); // convert to local time
                    end_field.setValue(LocalDate.from(newA.getAppointmentEnd())); // convert to local time
                    customerIDComboBox.setValue((Integer.toString(newA.getCustomerID())));
                    userIDComboBox.setValue(Integer.toString(newA.getUserID()));
                    contactComboBox.setValue(ContactsDB.getContactName(newA.getContactID()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void addAppointment() throws SQLException {
        if (id_field.getText().isEmpty()){
            Object[] toValidate = new Object[]{
                    title_field.getText(),
                    description_field.getText(),
                    location_field.getText(),
                    type_field.getText(),
                    //start_field.,
                    //end_field.,
                    customerIDComboBox.getSelectionModel().getSelectedItem(),
                    userIDComboBox.getSelectionModel().getSelectedItem()
            };
            if (validateFields(toValidate)){
                Appointments a = new Appointments(
                        Appointments.newAppointmentID(),
                        (String) toValidate[0], // Title
                        (String) toValidate[1], // Description
                        (String) toValidate[2], // Location
                        (String) toValidate[3], // Type
                        (LocalDateTime) toValidate[4], // Start Time
                        (LocalDateTime) toValidate[5], // End Time
                        (Integer) toValidate[6], // Customer ID
                        (Integer) toValidate[7], // User ID
                        ContactsDB.getContactId(contactComboBox.getSelectionModel().getSelectedItem())); // Contact ID
                AppointmentsDB.addAppointment(a);
                clearFields();
                allAppointments.setAll(AppointmentsDB.getAllAppointments());
                aptsTable.refresh();
            }
        } else {
            Helpers.WarningMessage("Please remove Appointment ID if you\nwould like to create a new appointment.");
        }
    }

    public void modifyAppointment() throws SQLException {
        /*Appointments oldA = aptsTable.getSelectionModel().getSelectedItem();
        Appointments modAppointment = new Appointments(
                oldA.getAppointmentID(),
                title_field.getText(),
                description_field.getText(),
                location_field.getText(),
                type_field.getText(),
                start_field.,
                end_field.getChronology(),
                customerIDComboBox.getSelectionModel().getSelectedItem(),
                userIDComboBox.getSelectionModel().getSelectedItem(),
                contactComboBox.getSelectionModel().getSelectedItem()
        );
        AppointmentsDB.modifyAppointment(modAppointment);
*/
        clearFields();
        allAppointments.setAll(AppointmentsDB.getAllAppointments());
        aptsTable.refresh();
    }

    /**
     * call to AppointmentsDB to delete selected appointment
     * @throws SQLException SQL exception handler
     */
    public void removeAppointment() throws SQLException {
        if (Helpers.ConfirmationMessage("Are you sure you want to delete this Appointment?")) {
            if (AppointmentsDB.deleteAppointment(aptsTable.getSelectionModel().getSelectedItem().getAppointmentID())) {
                allAppointments.setAll(AppointmentsDB.getAllAppointments());
                clearFields();
                aptsTable.refresh();
            }
        }
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
        customerIDComboBox.getSelectionModel().clearSelection();
        userIDComboBox.getSelectionModel().clearSelection();
        contactComboBox.getSelectionModel().clearSelection();
        startTimeHour.getSelectionModel().clearSelection();
        startTimeMin.getSelectionModel().clearSelection();
        endTimeHour.getSelectionModel().clearSelection();
        endTimeMin.getSelectionModel().clearSelection();
        startTimeHour.setPromptText("Hour");
        startTimeMin.setPromptText("Minute");
        endTimeHour.setPromptText("Hour");
        endTimeMin.setPromptText("Minute");
        aptsTable.getSelectionModel().clearSelection();
    }

    private boolean validateFields(Object[] toValidate) {
        return true;
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
