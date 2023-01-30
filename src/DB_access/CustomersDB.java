package DB_access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Helpers;
import model.Customers;
import controllers.loginController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Customers Database Accessor
 * @author William Nathan
 */
public class CustomersDB {
    /**
     * Generate list of all Customers in database.
     * @return allCustomersList
     */
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> allCustomersList = FXCollections.observableArrayList();
        ResultSet rs = Helpers.DBQuery("SELECT * FROM customers");
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
     * @param c Customers object to be added to DB
     * @throws SQLException SQL exception handler
     */
    public static void addCustomer(Customers c) throws SQLException {
        String queryBuild = c.getCustomerID() + ", '" + c.getCustomerName() + "', '" + c.getAddress() + "', '" + c.getPostalCode() + "', '" + c.getPhone() + "', NOW(),'" + loginController.getLoggedIn().getUserName() + "', NOW(),'" + loginController.getLoggedIn().getUserName() + "', " + c.getDivisionID();
        Helpers.DBExec("INSERT INTO customers VALUES (" + queryBuild + ");");
    }

    /**
     * @param c Customer object to be updated. Will retain old Customer ID
     * @throws SQLException SQL exception handler
     */
    public static void modifyCustomer(Customers c) throws SQLException {
        Helpers.DBExec(
                "UPDATE customers SET " +
                        "Customer_Name = '" + c.getCustomerName() +
                        "', Address = '" + c.getAddress() +
                        "', Postal_Code = '" + c.getPostalCode() +
                        "', Phone = '" + c.getPhone() +
                        "', Last_Update = NOW(), Last_Updated_By = '" + loginController.getLoggedIn().getUserName() +
                        "', Division_ID = " + c.getDivisionID() +
                        " WHERE Customer_ID = " + c.getCustomerID()
        );
    }

    /**
     * Returns list of appointments in form of appointment IDs based on given customer ID
     * @param customerID Customer ID to select appointments of
     * @return Returns Integer list of appointment IDs
     * @throws SQLException SQL exception handler
     */
    public static List<Integer> getCustomersAppointments(int customerID) throws SQLException {
        List<Integer> customerAppointmentList = new ArrayList<>();
        ResultSet rs = Helpers.DBQuery("SELECT Appointment_ID FROM appointments WHERE Customer_ID = " + customerID);
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
        List<Integer> customerApts = getCustomersAppointments(customerID);
        if (!customerApts.isEmpty()) {
            if (Helpers.ConfirmationMessage("This customer currently has existing appointments. \nWould you like to delete those appointments?")) {
                for (int a : customerApts) {
                    if (!AppointmentsDB.deleteAppointment(a)) {
                        Helpers.WarningMessage("Error deleting appointments.\nTry manual deletion.");
                        return false;
                    }
                }
            }
        }
        try {
            Helpers.DBExec("DELETE FROM customers WHERE Customer_ID = " + customerID);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
