package DBControllers;

import main.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Users Database Accessor
 * @author William Nathan
 */
public class UsersDB {
    private static final Connection db = JDBC.getConnection();

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
}
