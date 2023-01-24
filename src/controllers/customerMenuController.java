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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class customerMenuController implements Initializable {
    private final ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
    private final ObservableList<Countries> allCountries = FXCollections.observableArrayList();
    private final ArrayList<String> allCountriesNames = new ArrayList<>();

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
    public TableColumn<Object, Object> customerTable_comboBox;
    @FXML
    public ComboBox<String> customerTable_comboBox_switch;
    @FXML
    public TextField divisionID_field;
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
    public Button modifyButton;
    @FXML
    public Button returnButton;
    @FXML
    public Button removeButton;
    @FXML
    public Button addButton;

    /**
     * Init table with customer data, country column initially unfiltered
     * Puts listener on table so text fields will fill with selected row
     * @param url unused
     * @param resourceBundle unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allCustomers.setAll(CustomersDB.getAllCustomers());
            allCountries.setAll(CountriesDB.getAllCountries());
            for (Countries c : allCountries) {
                allCountriesNames.add(c.getCountry());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerTable_comboBox_switch.getItems().add("Division ID");
        customerTable_comboBox_switch.getItems().addAll(allCountriesNames);

        customerTable.setItems(allCustomers);
        customerTable.refresh();

        customerTable_ID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerTable_Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable_Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerTable_PostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerTable_Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTable_comboBox.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        // Add listener to table
        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldC, newC) -> {
            if (newC != null) {
                id_field.setText(Integer.toString(newC.getCustomerID()));
                name_field.setText(newC.getCustomerName());
                address_field.setText(newC.getAddress());
                postalCode_field.setText(newC.getPostalCode());
                phone_field.setText(newC.getPhone());
                divisionID_field.setText(Integer.toString(newC.getDivisionID()));
            }
        });
    }

    /**
     * call to CustomersDB delete customer function, then refreshes table
     * @throws SQLException SQL exception handler
     */
    public void removeCustomer() throws SQLException {
        int customerID = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
        if (CustomersDB.deleteCustomer(customerID)) {
            allCustomers.setAll(CustomersDB.getAllCustomers());
            clearFields();
            customerTable.refresh();
        }
    }

    public void modifyCustomer() {

        clearFields();
        allCustomers.setAll(CustomersDB.getAllCustomers());
        customerTable.refresh();
    }

    /**
     * Receives Customer values from text fields, creates new customer object, then adds to database
     * @throws SQLException SQL exception handler
     */
    public void addCustomer() throws SQLException {
        if (id_field.getText().isEmpty()){
            Object[] toValidate = new Object[];
            int customerID = Customers.newCustomerID();
            String customerName = name_field.getText();
            String address = address_field.getText();
            String postalCode = postalCode_field.getText();
            String phone = phone_field.getText();
            int divisionID = Integer.parseInt(divisionID_field.getText());

            if (!validateFields(toValidate)){
                Customers c = new Customers(customerID, customerName, address, postalCode, phone, divisionID);
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
        id_field.setText("");
        name_field.setText("");
        address_field.setText("");
        postalCode_field.setText("");
        phone_field.setText("");
        divisionID_field.setText("");
    }

    public boolean validateFields(String[]) {

    }

    /**
     * Handles column switching on combo box selection
     */
    @FXML
    private void switchComboBox() throws SQLException {
        ObservableList<Customers> queryResult = FXCollections.observableArrayList();
        String searchString = customerTable_comboBox_switch.getSelectionModel().getSelectedItem();
        customerTable.setItems(null);
        for (Customers c : allCustomers) {
            if (searchString.equals(FirstLevelDivisionsDB.getCountryName(c.getDivisionID()))) {
                queryResult.add(c);
            }
        }
        if (searchString.equals("Division ID")) {
            customerTable.setItems(allCustomers);
            return;
        }
        customerTable.setItems(queryResult);
    }
}
