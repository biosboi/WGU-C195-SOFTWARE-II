package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;
import model.Users;

import java.sql.*;

/**
 * Users Database Accessor
 * @author William Nathan
 */
public class UsersDB {
    private static final Connection db = JDBC.getConnection();

    /**
     * Generate list of all Users in database.
     * @return allUsersList
     */
    public static ObservableList<Users> getAllUsers() throws SQLException {
        ObservableList<Users> allUsersList = FXCollections.observableArrayList();
        String query = "SELECT * FROM users";
        PreparedStatement ps = db.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
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
     * Retrieve user name based on user id
     * @param UserID find name with this id
     * @return associated user name
     */
    public static String getUsername(int UserID) throws SQLException {
        String userName = "";
        Statement stmt = db.createStatement();
        String query = "SELECT User_Name FROM users WHERE User_ID = " + UserID;
        JDBC.makePreparedStatement(query, db);
        stmt.executeQuery(query);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            userName = rs.getString("User_Name");
        }
        return userName;
    }

    /**
     * Retrieve user id based on user name
     * @param UserName find id with this name
     * @return associated user id
     */
    public static int getUserID(String UserName) throws SQLException {
        int userId = 0;
        Statement stmt = db.createStatement();
        String query = "SELECT User_ID FROM users WHERE User_Name = " + UserName;
        JDBC.makePreparedStatement(query, db);
        stmt.executeQuery(query);
        ResultSet rs = stmt.executeQuery(query);
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
        Statement stmt = db.createStatement();
        String query = "SELECT User_ID FROM users WHERE User_Name = '" + usr + "'AND Password = '" + pwd + "'";
        JDBC.makePreparedStatement(query, db);
        stmt.executeQuery(query);
        ResultSet rs = stmt.executeQuery(query);
        int userID = -1;
        while (rs.next()) {
            userID = rs.getInt("User_ID");
        }
        return Math.max(userID, -1);
    }
}
