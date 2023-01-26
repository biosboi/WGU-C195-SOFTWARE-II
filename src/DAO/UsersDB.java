package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Helpers;
import model.Users;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Users Database Accessor
 * @author William Nathan
 */
public class UsersDB {
    /**
     * Generate list of all Users in database.
     * @return allUsersList
     */
    public static ObservableList<Users> getAllUsers() throws SQLException {
        ObservableList<Users> allUsersList = FXCollections.observableArrayList();
        ResultSet rs = Helpers.DBQuery("SELECT * FROM users");
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String name = rs.getString("Name");
            String password = rs.getString("Password");
            Users user = new Users(userId, name, password);
            allUsersList.add(user);
        }
        return allUsersList;
    }

    /**
     * Retrieve username based on user id
     * @param UserID find name with this id
     * @return associated username
     */
    public static String getUsername(int UserID) throws SQLException {
        String userName = "";
        ResultSet rs = Helpers.DBQuery("SELECT User_Name FROM users WHERE User_ID = " + UserID);
        while (rs.next()) {
            userName = rs.getString("User_Name");
        }
        return userName;
    }

    /**
     * Retrieve user id based on username
     * @param UserName find id with this name
     * @return associated user id
     */
    public static int getUserID(String UserName) throws SQLException {
        int userId = 0;
        ResultSet rs = Helpers.DBQuery("SELECT User_ID FROM users WHERE User_Name = " + UserName);
        while (rs.next()) {
            userId = rs.getInt("User_ID");
        }
        return userId;
    }

    /**
     * Handles login verification, returns -1 if credentials are bad, otherwise returns user ID
     * @param usr username to check against
     * @param pwd password to check against
     * @return returns userID if valid, -1 if invalid
     * @throws SQLException SQL exception handler
     */
    public static int loginVerification(String usr, String pwd) throws SQLException {
        ResultSet rs = Helpers.DBQuery("SELECT User_ID FROM users WHERE User_Name = '" + usr + "'AND Password = '" + pwd + "'");
        int userID = -1;
        while (rs.next()) {
            userID = rs.getInt("User_ID");
        }
        return Math.max(userID, -1);
    }
}
