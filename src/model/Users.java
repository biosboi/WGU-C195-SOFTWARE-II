package model;
/**
 * Users model
 * @author William Nathan
 */

public class Users {
    public int userID;
    public String userName;
    public String userPassword;

    /**
     * @param userID Unique PK
     * @param userName Unique
     * @param userPassword user's password
     */
    public Users (int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * @return User ID
     */
    public int getUserID() { return userID; }

    /**
     * @return User Name
     */
    public String getUserName() { return userName; }

    /**
     * @return User Password
     */
    public String getUserPassword() { return userPassword; }
}
