package DB_access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Helpers;
import model.FirstLevelDivisions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * First Level Divisions Database Accessor
 * @author William Nathan
 */
public class FirstLevelDivisionsDB {

    /**
     * Generate list of all First Level Divisions in database.
     * @return allFirstLevelDivisionsList
     */
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() throws SQLException {
        ObservableList<FirstLevelDivisions> allFirstLevelDivisionsList = FXCollections.observableArrayList();
        ResultSet rs = Helpers.DBQuery("SELECT * FROM first_level_divisions");
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivisions fld = new FirstLevelDivisions(divisionID, division, countryId);
            allFirstLevelDivisionsList.add(fld);
        }
        return allFirstLevelDivisionsList;
    }

    /**
     * Get division ID by country name
     * @param countryName Country to grab division ID from
     * @return Division ID of selected country
     * @throws SQLException SQL exception handler
     */
    public static int getDivisionID(String countryName) throws SQLException {
        int divisionID = 0;
        int countryID = CountriesDB.getCountryID(countryName);
        ResultSet rs = Helpers.DBQuery("SELECT Division_ID FROM first_level_divisions WHERE Country_ID = " + countryID);
        while (rs.next()) {
            divisionID = rs.getInt("Division_ID");
        }
        return divisionID;
    }

    /**
     * Get division ID by country ID
     * @param countryID Country ID to grab division ID from
     * @return Division ID of selected country
     * @throws SQLException SQL exception handler
     */
    public static int getDivisionID(int countryID) throws SQLException {
        int divisionID = 0;
        ResultSet rs = Helpers.DBQuery("SELECT Division_ID FROM first_level_divisions WHERE Country_ID = " + countryID);
        while (rs.next()) {
            divisionID = rs.getInt("Division_ID");
        }
        return divisionID;
    }

    /**
     * Get division ID by division name
     * @param divisionName Country ID to grab division ID from
     * @return Division ID of selected country
     * @throws SQLException SQL exception handler
     */
    public static int getDivisionIDbyName(String divisionName) throws SQLException {
        int divisionID = 0;
        ResultSet rs = Helpers.DBQuery("SELECT Division_ID FROM first_level_divisions WHERE Division = " + "'" + divisionName + "'");
        while (rs.next()) {
            divisionID = rs.getInt("Division_ID");
        }
        return divisionID;
    }

    /**
     * Get Division Name by Division ID
     * @param divisionID Division ID to grab name from
     * @return String name of Division
     * @throws SQLException SQL exception handler
     */
    public static String getDivisionName(int divisionID) throws SQLException {
        String divisionName = "";
        ResultSet rs = Helpers.DBQuery("SELECT Division FROM first_level_divisions WHERE Division_ID = " + divisionID);
        while (rs.next()) {
            divisionName = rs.getString("Division");
        }
        return divisionName;
    }

    /**
     * @param divisionID First Level Division ID to get country name from
     * @return Country name based on First Level Division ID
     * @throws SQLException SQL exception handler
     */
    public static String getCountryName(int divisionID) throws SQLException {
        String countryName = "";
        ResultSet rs = Helpers.DBQuery("SELECT Country FROM countries AS c, first_level_divisions AS f WHERE f.Country_ID = c.Country_ID AND Division_ID = " + divisionID);
        while (rs.next()) {
            countryName = rs.getString("Country");
        }
        return countryName;
    }

    /**
     * @param countryID Country ID to search for division names with
     * @return String array of all division names in selected country
     * @throws SQLException SQL exception handler
     */
    public static ArrayList<String> getDivisionsByCountry(int countryID) throws SQLException {
        ArrayList<String> allFirstLevelDivisionsList = new ArrayList<>();
        ResultSet rs = Helpers.DBQuery("SELECT Division FROM first_level_divisions WHERE Country_ID = " + countryID);
        while (rs.next()) {
            allFirstLevelDivisionsList.add(rs.getString("Division"));
        }
        return allFirstLevelDivisionsList;
    }
}
