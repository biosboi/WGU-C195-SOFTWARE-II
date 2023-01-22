package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Helpers;
import main.JDBC;
import model.Customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> allCustomersList = FXCollections.observableArrayList();
        String query = "SELECT * FROM customers";
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

    /**
     * Returns list of appointments in form of appointment IDs based on given customer ID
     * @param customerID Customer ID to select appointments of
     * @return Returns Integer list of appointment IDs
     * @throws SQLException SQL exception handler
     */
    public static List<Integer> getCustomersAppointments(int customerID) throws SQLException {
        List<Integer> customerAppointmentList = new ArrayList<>();
        String query = "SELECT Appointment_ID FROM appointments WHERE Customer_ID = " + customerID;
        PreparedStatement ps = db.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            customerAppointmentList.add(rs.getInt("Appointment_ID"));
        }
        return customerAppointmentList;
    }

    /**
     * Deletes customer based on customer ID. If customer has existing appointments, the user will be notified with a confirmation message.
     * @param customerID Customer to be deleted
     * @return Boolean true if successful, false if unsuccessful
     * @throws SQLException SQL exception handler
     */
    public static boolean deleteCustomer(int customerID) throws SQLException {
        if (Helpers.ConfirmationMessage("This customer currently has existing appointments. \nWould you like to delete those appointments?")) {
            List<Integer> customerApts = getCustomersAppointments(customerID);
            if (!customerApts.isEmpty()) {
                for (int a : customerApts) {
                    if (!AppointmentsDB.deleteAppointment(a)) {
                        return false;
                    }
                }
            }
            try {
                String query = "DELETE FROM customers WHERE Customer_ID =" + customerID;
                PreparedStatement ps = db.prepareStatement(query);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
