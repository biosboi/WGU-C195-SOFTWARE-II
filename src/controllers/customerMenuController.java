package controllers;

import DAO.CountriesDB;
import DAO.CustomersDB;
import DAO.FirstLevelDivisionsDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Helpers;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class customerMenuController implements Initializable {
    private final ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
    private final ObservableList<Countries> allCountries = FXCollections.observableArrayList();
    private final ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();
    private final ArrayList<String> allCountriesNames = new ArrayList<>();
    private final ArrayList<String> allDivisionNames = new ArrayList<>();
    @FXML
    public TableView<Customers> customerTable;
    @FXML
    public TableColumn<Object, Object> customerTable_ID;
    @FXML
    public TableColumn<Object, Object> customerTable_Name;
    @FXML
    public TableColumn<Object, Object> customerTable_Address;
    @FXML
    public TableColumn<Object, Object> customerTable_PostalCode;
    @FXML
    public TableColumn<Object, Object> customerTable_Phone;
    @FXML
    public TableColumn<Object, Object> customerTable_division;
    @FXML
    public ComboBox<String> countryComboBox;
    @FXML
    public ComboBox<String> divisionComboBox;
    @FXML
    public TextField phone_field;
    @FXML
    public TextField postalCode_field;
    @FXML
    public TextField address_field;
    @FXML
    public TextField name_field;
    @FXML
    public TextField id_field;
    @FXML
    public Button returnButton;
    @FXML
    public Button removeButton;
    @FXML
    public Button addButton;
    @FXML
    public Button modifyButton;
    @FXML
    public Button clearButton;

    /**
     * Init table with customer data, country column initially unfiltered
     * Puts listener on table so text fields will fill with selected row
     * @param url unused
     * @param resourceBundle unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize Name and object lists for Customers / Countries / Divisions
        try {
            allCustomers.setAll(CustomersDB.getAllCustomers());
            allCountries.setAll(CountriesDB.getAllCountries());
            allDivisions.setAll(FirstLevelDivisionsDB.getAllFirstLevelDivisions());
            for (Countries c : allCountries) {
                allCountriesNames.add(c.getCountry());
            }
            countryComboBox.getItems().addAll(allCountriesNames);
            for (FirstLevelDivisions f : allDivisions) {
                allDivisionNames.add(f.getDivision());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customerTable.setItems(allCustomers);
        customerTable.refresh();

        customerTable_ID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerTable_Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable_Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerTable_PostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerTable_Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTable_division.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        // Add listener to table to update text fields on selection
        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldC, newC) -> {
            if (newC != null) {
                try {
                    id_field.setText(Integer.toString(newC.getCustomerID()));
                    name_field.setText(newC.getCustomerName());
                    address_field.setText(newC.getAddress());
                    postalCode_field.setText(newC.getPostalCode());
                    phone_field.setText(newC.getPhone());
                    countryComboBox.setValue(FirstLevelDivisionsDB.getCountryName(newC.getDivisionID()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // Add listener to country combo box to determine division list based on country selection
        countryComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldC, newC) -> {
            if (newC != null) {
                try {
                    // Filter new divisions
                    divisionComboBoxSwitch(newC);
                    // Set current value
                    divisionComboBox.setValue(FirstLevelDivisionsDB.getDivisionName(FirstLevelDivisionsDB.getDivisionID(newC)));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Receives Customer values from text fields, creates new customer object, then adds to database
     * @throws SQLException SQL exception handler
     */
    public void addCustomer() throws SQLException {
        if (id_field.getText().isEmpty()){
            Object[] toValidate = new Object[]{
                    name_field.getText(),
                    address_field.getText(),
                    postalCode_field.getText(),
                    phone_field.getText(),
                    divisionComboBox.getSelectionModel().getSelectedItem()
            };
            if (validateFields(toValidate)){
                Customers c = new Customers(
                        Customers.newCustomerID(),
                        (String) toValidate[0],
                        (String) toValidate[1],
                        (String) toValidate[2],
                        (String) toValidate[3],
                        (FirstLevelDivisionsDB.getDivisionIDbyName(divisionComboBox.getValue())));
                CustomersDB.addCustomer(c);
                clearFields();
                allCustomers.setAll(CustomersDB.getAllCustomers());
                customerTable.refresh();
            }

        } else {
            Helpers.WarningMessage("Please remove Customer ID if you\nwould like to create a new customer.");
        }
    }

    /**
     * Grabs selected customer from table and updates based on text fields
     * @throws SQLException SQL exception handler
     */
    public void modifyCustomer() throws SQLException {
        Customers oldC = customerTable.getSelectionModel().getSelectedItem();
        Customers modCustomer = new Customers(
                oldC.getCustomerID(),
                name_field.getText(),
                address_field.getText(),
                postalCode_field.getText(),
                phone_field.getText(),
                FirstLevelDivisionsDB.getDivisionIDbyName(divisionComboBox.getValue())
        );
        CustomersDB.modifyCustomer(modCustomer);

        clearFields();
        allCustomers.setAll(CustomersDB.getAllCustomers());
        customerTable.refresh();
    }

    /**
     * call to CustomersDB delete customer function, then refreshes table
     * @throws SQLException SQL exception handler
     */
    public void removeCustomer() throws SQLException {
        if (CustomersDB.deleteCustomer(customerTable.getSelectionModel().getSelectedItem().getCustomerID())) {
            allCustomers.setAll(CustomersDB.getAllCustomers());
            clearFields();
            customerTable.refresh();
        }
    }

    /**
     * Return to main menu
     * @param click Execute on button click
     * @throws IOException Handle menu open exception
     */
    public void previousMenu(ActionEvent click) throws IOException {
        Helpers.openMenu(click, "../mainMenu.fxml");
    }

    /**
     * Clears out text fields for cleanup after customer data manipulation
     */
    public void clearFields() {
        id_field.clear();
        name_field.clear();
        address_field.clear();
        postalCode_field.clear();
        phone_field.clear();
        divisionComboBox.getSelectionModel().clearSelection();
        countryComboBox.getSelectionModel().clearSelection();
        customerTable.getSelectionModel().clearSelection();
    }

    /**
     * name (String) No numbers
     * address (String)
     * postal code (String)
     * phone (String) No letters
     * division Name (String)
     * @param toValidate array of multiple data types to validate
     * @return boolean if all types are valid, returns message for each invalid
     */
    public boolean validateFields(Object[] toValidate) {
        Pattern onlyAlpha = Pattern.compile("\\d");
        Pattern onlyDigits = Pattern.compile("[a-zA-Z]");
        Matcher nameMatch = onlyAlpha.matcher((String) toValidate[0]);
        Matcher phoneMatch = onlyDigits.matcher((String) toValidate[3]);

        String errorMessage = "One or more errors have been found:\n";
        boolean valid = true;
        // Name
        if (((String) toValidate[0]).isEmpty() || nameMatch.find()) {
            errorMessage = errorMessage.concat("Name field is empty or invalid.\n");
            valid = false;
        }
        // Address
        if (((String) toValidate[1]).isEmpty()) {
            errorMessage = errorMessage.concat("Address field is empty or invalid.\n");
            valid = false;
        }
        // Postal Code
        if (((String) toValidate[2]).isEmpty()) {
            errorMessage = errorMessage.concat("Postal Code field is empty or invalid.\n");
            valid = false;
        }
        // Phone
        if (((String) toValidate[3]).isEmpty() || phoneMatch.find()) {
            errorMessage = errorMessage.concat("Phone Number field is empty or invalid.\n");
            valid = false;
        }
        // Division ID
        if (((String) toValidate[4]).isEmpty()) {
            errorMessage = errorMessage.concat("Division field is empty or invalid.");
            valid = false;
        }

        if (valid) {
            return true;
        } else {
            Helpers.WarningMessage(errorMessage);
            return false;
        }
    }

    /**
     * Handles filtering of country and division combo boxes. Reacts to listener in Initialization
     * @param newCountry string name of new country to be converted to division name list
     */
    @FXML
    private void divisionComboBoxSwitch(String newCountry) throws SQLException {
        divisionComboBox.getSelectionModel().clearSelection();
        divisionComboBox.getItems().removeAll();
        allDivisionNames.clear();

        allDivisionNames.addAll(FirstLevelDivisionsDB.getDivisionsByCountry(CountriesDB.getCountryID(newCountry)));
        divisionComboBox.getItems().setAll(allDivisionNames);
    }
}
