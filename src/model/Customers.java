package model;

import DB_access.CustomersDB;
import DB_access.FirstLevelDivisionsDB;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Customers model
 * @author William Nathan
 */

public class Customers {
    public int customerID;
    public String customerName;
    public String address;
    public String postalCode;
    public String phone;
    public int divisionID;
    public String divisionName;

    /**
     * @param customerID PK
     * @param customerName Name of Customer
     * @param address Address of Customer
     * @param postalCode Postal Code of Address
     * @param phone Phone Number of Customer
     * @param divisionID Division ID assigned to Customer
     */
    public Customers (int customerID, String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.divisionName = getDivisionName();
    }

    /**
     * @return Customer ID
     */
    public int getCustomerID() { return customerID; }

    /**
     * @return Customer Name
     */
    public String getCustomerName() { return customerName; }

    /**
     * @return Address of Customer
     */
    public String getAddress() { return address; }

    /**
     * @return Postal Code of Address
     */
    public String getPostalCode() { return postalCode; }

    /**
     * @return Phone Number of Customer
     */
    public String getPhone() { return phone; }

    /**
     * @return Division ID of Customer
     */
    public int getDivisionID() { return divisionID; }

    public String getDivisionName() throws SQLException {
        return FirstLevelDivisionsDB.getDivisionName(this.divisionID);
    }

    /**
     * Generates a new ID which is incremented from previous known ID's
     * @return lastMaxId
     */
    public static int newCustomerID() throws SQLException {
        ObservableList<Customers> customersList = CustomersDB.getAllCustomers();
        int lastMaxId = 0;
        for(Customers c : customersList) {
            if (c.getCustomerID() > lastMaxId) {
                lastMaxId = c.getCustomerID();
            }
        }
        return ++lastMaxId;
    }
}
