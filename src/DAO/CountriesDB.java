package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;
import model.Countries;

import java.sql.*;

/**
 * Country Database Accessor
 * @author William Nathan
 */
public class CountriesDB {
    private static final Connection db = JDBC.getConnection();

    /**
     * Generate list of all Countries in database.
     * @return allCountriesList
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> allCountriesList = FXCollections.observableArrayList();
        String query = "SELECT * FROM countries";
        PreparedStatement ps = db.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            Countries countries = new Countries(countryID, country);
            allCountriesList.add(countries);
        }
        return allCountriesList;
    }

    // takes First and Second level country data and turn it into a Division ID & Vice Versa.
    public static int getCountryID(String countryName) throws SQLException {
        int countryID = 0;
        Statement stmt = db.createStatement();
        String query = "SELECT Country_ID FROM countries WHERE Country = " + countryName;
        JDBC.makePreparedStatement(query, db);
        stmt.executeQuery(query);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            countryID = rs.getInt("Country_ID");
        }
        return countryID;
    }

    /**
     * Get division ID by country name
     * @param countryName Country to grab division ID from
     * @return Division ID of selected country
     * @throws SQLException SQL exception handler
     */
    public static int getDivisionID(String countryName) throws SQLException {
        int divisionID = 0;
        int countryID = getCountryID(countryName);
        Statement stmt = db.createStatement();
        String query = "SELECT Division_ID FROM first_level_divisions WHERE Country_ID = " + countryID;
        JDBC.makePreparedStatement(query, db);
        stmt.executeQuery(query);
        ResultSet rs = stmt.executeQuery(query);
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
        Statement stmt = db.createStatement();
        String query = "SELECT Division_ID FROM first_level_divisions WHERE Country_ID = " + countryID;
        JDBC.makePreparedStatement(query, db);
        stmt.executeQuery(query);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            divisionID = rs.getInt("Division_ID");
        }
        return divisionID;
    }
}
