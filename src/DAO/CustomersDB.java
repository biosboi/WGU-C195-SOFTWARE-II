package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;
import model.Customers;

import java.sql.*;

/**
 * Customers Database Accessor
 * @author William Nathan
 */
public class CustomersDB {
    private static final Connection db = JDBC.getConnection();

    /**
     * Generate list of all Customers in database.
     * @return allCustomersList
     */
    public static ObservableList<Customers> getAllAppointments() throws SQLException {
        ObservableList<Customers> allCustomersList = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointments";
        PreparedStatement ps = db.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");
            Customers customer = new Customers(customerID, customerName, address, postalCode, phone, divisionID);
            allCustomersList.add(customer);
        }
        return allCustomersList;
    }
}
