package DBControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;
import model.FirstLevelDivisions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String query = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = db.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivisions fld = new FirstLevelDivisions(divisionID, division, countryId);
            allFirstLevelDivisionsList.add(fld);
        }
        return allFirstLevelDivisionsList;
    }
}
