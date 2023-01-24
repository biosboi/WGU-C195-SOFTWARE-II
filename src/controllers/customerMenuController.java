package controllers;

import DAO.CountriesDB;
import DAO.CustomersDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Helpers;
import model.Countries;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class customerMenuController implements Initializable {
    private ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Countries> allCountries = FXCollections.observableArrayList();
    public TableView<Customers> customerTable;
    public TableColumn<Object, Object> customerTable_ID;
    public TableColumn<Object, Object> customerTable_Name;
    public TableColumn<Object, Object> customerTable_Address;
    public TableColumn<Object, Object> customerTable_PostalCode;
    public TableColumn<Object, Object> customerTable_Phone;
    public TableColumn<Object, Object> customerTable_comboBox;
    public ComboBox<String> customerTable_comboBox_switch;
    public Button returnButton;
    public Button removeButton;
    public Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Display data to tables
        try {
            allCustomers.setAll(CustomersDB.getAllCustomers());
            allCountries.setAll(CountriesDB.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerTable_comboBox_switch.getItems().addAll("Division ID","Country");

        customerTable.setItems(allCustomers);
        customerTable.refresh();

        customerTable_ID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerTable_Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable_Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerTable_PostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerTable_Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTable_comboBox.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }



    public void removeCustomer(ActionEvent click) {
        customerTable.refresh();
    }

    public void addCustomer(ActionEvent click) {
        customerTable.refresh();
    }

    /**
     * Return to main menu
     * @param click Execute on button click
     * @throws IOException Handle menu open exception
     */
    public void previousMenu(ActionEvent click) throws IOException {
        Helpers.openMenu(click, "../mainMenu.fxml");
    }


    /*private void switchComboBox(String forCountry) {
        customerTable_comboBox_switch.getItems().clear();
        db.divisionCache.getAll().stream()
                .filter(div -> div.country.name.equals(forCountry))
                .forEach(div -> divisionComboBox.getItems().add(div.name));
        divisionComboBox.setValue("");
    }*/
    public void switchComboBox(ActionEvent actionEvent) throws SQLException {
        String choice = customerTable_comboBox_switch.getSelectionModel().getSelectedItem();
        if(choice.equals("Division ID")) {
            customerTable_comboBox.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
            customerTable.refresh();
        } else {
            customerTable.setItems(allCountries);
                //FirstLevelDivisionsDB.getCountryName((Integer) customerTable_comboBox.getCellObservableValue(i).getValue())
            //customerTable_comboBox.setCellValueFactory(new PropertyValueFactory<>("country"));
            customerTable.refresh();
        }
    }
}
