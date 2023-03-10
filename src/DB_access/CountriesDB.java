package DB_access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Helpers;
import model.Countries;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Country Database Accessor
 * @author William Nathan
 */
public class CountriesDB {

    /**
     * Generate list of all Countries in database.
     * @return allCountriesList
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> allCountriesList = FXCollections.observableArrayList();
        ResultSet rs = Helpers.DBQuery("SELECT * FROM countries");
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            Countries countries = new Countries(countryID, country);
            allCountriesList.add(countries);
        }
        return allCountriesList;
    }

    public static int getCountryID(String countryName) throws SQLException {
        int countryID = 0;
        ResultSet rs = Helpers.DBQuery("SELECT Country_ID FROM countries WHERE Country = " + "'" + countryName + "'");
        while (rs.next()) {
            countryID = rs.getInt("Country_ID");
        }
        return countryID;
    }

}
