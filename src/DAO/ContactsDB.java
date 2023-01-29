package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Helpers;
import model.Contacts;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contacts Database Accessor
 * @author William Nathan
 */
public class ContactsDB {

    /**
     * Generate list of all contacts in database.
     * @return allContactsList
     */
    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> allContactsList = FXCollections.observableArrayList();
        ResultSet rs = Helpers.DBQuery("SELECT * FROM contacts");
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
        ResultSet rs = Helpers.DBQuery("SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contactID);
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
        ResultSet rs = Helpers.DBQuery("SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + contactName + "'");
        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }
}
