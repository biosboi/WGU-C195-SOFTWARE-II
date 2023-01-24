package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * First Level Divisions model
 * @author William Nathan
 */

public class FirstLevelDivisions {
    private static ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FXCollections.observableArrayList();
    public int divisionID;
    public String division;
    public int countryID;

    /**
     * @param divisionID PK
     * @param division Division Name
     * @param countryID FK
     */
    public FirstLevelDivisions(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * @return Division ID
     */
    public int getDivisionID() { return divisionID; }

    /**
     * @return Division Name
     */
    public String getDivision() { return division; }

    /**
     * @return Country ID of Division
     */
    public int getCountryID() { return countryID; }
}
