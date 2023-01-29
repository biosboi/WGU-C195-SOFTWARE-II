package controllers;

import DAO.ContactsDB;
import DAO.CountriesDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import main.Helpers;
import model.Appointments;
import model.Contacts;
import model.Countries;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class reportMenuController implements Initializable {
    private ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    private ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
    private ObservableList<Countries> allCountries = FXCollections.observableArrayList();
    private ArrayList<Integer> contactIDs = new ArrayList<>();
    private ArrayList<Integer> countryIDs = new ArrayList<>();
    @FXML
    public TextArea customerText;
    @FXML
    public TextArea contactsText;
    @FXML
    public TextArea countriesText;

    /**
     * Fills out text sections with relevant report data
     * @param url unused
     * @param resourceBundle unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // the total number of customer appointments by type and month
            appointmentReport();

            // a schedule for each contact in your organization that includes
            // appointment ID, title, type and description, start date and time, end date and time, and customer ID
            contactReport();

            // an additional report of your choice that is different from the two other required reports
            // in this prompt and from the user log-in date and time stamp that will be tracked in part C
            countryReport();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Queries and formats report for displaying appointments by month and type in text field
 * @throws SQLException SQL exception handler
     */
    public void appointmentReport() throws SQLException {
        ResultSet rs = Helpers.DBQuery("SELECT monthname(start) AS 'Month',type AS 'Type', count(*) as 'Number' FROM appointments GROUP BY start,type;");
        while (rs.next()) {
            String month = "Month: " + rs.getString("Month");
            String type = "Type: " + rs.getString("Type");
            String count = "Count:  " + rs.getString("Number");
            customerText.appendText(String.format("%-25s%-25s%-25s", month, type, count));
            customerText.appendText("\n");
        }
    }

    /**
     * Queries and formats report for displaying appointment information for each contact.
     * @throws SQLException SQL exception handler
     */
    public void contactReport() throws SQLException {
        allContacts = ContactsDB.getAllContacts();
        for (Contacts c : allContacts) {
            contactIDs.add(c.getContactID());
        }
        for (Integer i : contactIDs) {
            ResultSet rs = Helpers.DBQuery("SELECT Appointment_ID,Title,Type,Description,Start,End,Customer_ID FROM appointments WHERE Contact_ID = " + i);
            while (rs.next()) {
                String appointmentID = "Appointment_ID: " + rs.getString("Appointment_ID");
                String title = "Title: " + rs.getString("Title");
                String type = "Type:  " + rs.getString("Type");
                String description = "Description:  " + rs.getString("Description");
                String start = "Start:  " + rs.getString("Start");
                String end = "End:  " + rs.getString("End");
                String customerID = "Customer_ID:  " + rs.getString("Customer_ID");
                contactsText.appendText(String.format("%-25s%-25s%-30s%-30s%-30s%-30s%-30s", appointmentID, title, type,description, start, end, customerID));
                contactsText.appendText("\n");
            }
        }
    }

    /**
     * Queries and formats report for displaying all country information
     * @throws SQLException SQL exception handler
     */
    public void countryReport() throws SQLException {
        allCountries = CountriesDB.getAllCountries();
        for (Countries c : allCountries) {
            countryIDs.add(c.getCountryID());
        }
        for (Integer i : countryIDs) {
            ResultSet rs = Helpers.DBQuery("SELECT Country_ID,Country FROM countries WHERE Country_ID = " + i);
            while (rs.next()) {
                String countryID = "Country_ID: " + rs.getString("Country_ID");
                String country = "Country: " + rs.getString("Country");
                countriesText.appendText(String.format("%-25s%-25s", countryID, country));
                countriesText.appendText("\n");
            }
        }
    }

    /**
     * Return user to main menu
     * @param click execute on button click
     * @throws IOException File open exception handler
     */
    public void previousMenu(ActionEvent click) throws IOException {
        Helpers.openMenu(click, "../mainMenu.fxml");
    }
}
