package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Helpers;
import main.JDBC;
import model.FirstLevelDivisions;

import java.sql.*;

/**
 * First Level Divisions Database Accessor
 * @author William Nathan
 */
public class FirstLevelDivisionsDB {
    private static final Connection db = JDBC.getConnection();

    /**
     * Generate list of all First Level Divisions in database.
     * @return allFirstLevelDivisionsList
     */
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() throws SQLException {
        ObservableList<FirstLevelDivisions> allFirstLevelDivisionsList = FXCollections.observableArrayList();
        ResultSet rs = Helpers.makeQuery("SELECT * FROM first_level_divisions");
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
        ResultSet rs = Helpers.makeQuery("SELECT Division_ID FROM first_level_divisions WHERE Country_ID = " + countryID);
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
        ResultSet rs = Helpers.makeQuery("SELECT Division_ID FROM first_level_divisions WHERE Country_ID = " + countryID);
        while (rs.next()) {
            divisionID = rs.getInt("Division_ID");
        }
        return divisionID;
    }

    /**
     * @param divisionID First Level Division ID to get country name from
     * @return Country name based on First Level Division ID
     * @throws SQLException SQL exception handler
     */
    public static String getCountryName(int divisionID) throws SQLException {
        String countryName = "";
        ResultSet rs = Helpers.makeQuery("SELECT Country FROM countries AS c, first_level_divisions AS f WHERE f.Country_ID = c.Country_ID AND Division_ID = " + divisionID);
        while (rs.next()) {
            countryName = rs.getString("Country");
        }
        return countryName;
    }
}
