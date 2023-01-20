package DBControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;
import model.Countries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void getCountryID() {

    }

    public void getDivisionIds() {

    }
}
