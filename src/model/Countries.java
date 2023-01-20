package model;
/**
 * Country model
 * @author William Nathan
 */

public class Countries {
    public int countryID;
    public String country;

    /**
     * @param countryID Unique PK
     * @param country Unique
     */
    public Countries (int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * @return Country ID
     */
    public int getCountryID() { return countryID; }

    /**
     * @return Country Name
     */
    public String getCountry() { return country; }
}
