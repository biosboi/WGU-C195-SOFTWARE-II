package model;
/**
 * Contacts model
 * @author William Nathan
 */

public class Contacts {
    public int contactID;
    public String contactName;
    public String contactEmail;

    /**
     * @param contactID PK
     * @param contactName Contact's Name
     * @param contactEmail Contact's Email
     */
    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @return Contact ID
     */
    public int getContactID() { return contactID; }

    /**
     * @return Contact Name
     */
    public String getContactName() { return contactName; }

    /**
     * @return Contact Email
     */
    public String getContactEmail() { return contactEmail; }
}
