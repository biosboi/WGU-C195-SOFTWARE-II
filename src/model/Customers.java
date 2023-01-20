package model;
/**
 * Customers model
 * @author William Nathan
 */

public class Customers {
    public int customerID;
    public String customerName;
    public String address;
    public int postalCode;
    public int phone;
    public int divisionID;

    /**
     * @param customerID PK
     * @param customerName Name of Customer
     * @param address Address of Customer
     * @param postalCode Postal Code of Address
     * @param phone Phone Number of Customer
     * @param divisionID Division ID assigned to Customer
     */
    public Customers (int customerID, String customerName, String address, int postalCode, int phone, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
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
    public int getPostalCode() { return postalCode; }

    /**
     * @return Phone Number of Customer
     */
    public int getPhone() { return phone; }

    /**
     * @return Division ID of Customer
     */
    public int getDivisionID() { return divisionID; }
}
