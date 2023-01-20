package DBControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;
import model.Contacts;

import java.sql.*;

/**
 * Contacts Database Accessor
 * @author William Nathan
 */
public class ContactsDB {
    private static final Connection db = JDBC.getConnection();

    /**
     * Generate list of all contacts in database.
     * @return allContactsList
     */
    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> allContactsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM contacts";
        PreparedStatement ps = db.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contacts contact = new Contacts(contactID, contactName, contactEmail);
            allContactsList.add(contact);
        }
        return allContactsList;
    }

    /**
     * Retrieve contact name based on contact ID
     * @param contactID find name with this id
     * @return associated contact name
     */
    public static String getContactName(int contactID) throws SQLException {
        String contactName = "";
        Statement stmt = db.createStatement();
        String query = "SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contactID;
        JDBC.makePreparedStatement(query, db);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
    }

    /**
     * Retrieve contact ID based on contact name
     * @param contactName find name with this id
     * @return associated contact ID
     */
    public static int getContactId(String contactName) throws SQLException  {
        int contactId = 0;
        Statement stmt = db.createStatement();
        String query = "SELECT Contact_ID FROM contacts WHERE Contact_Name = " + contactName;
        JDBC.makePreparedStatement(query, db);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }
}
