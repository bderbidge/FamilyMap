package Request;

/**
 * Created by brandonderbidge on 5/30/17.
 */

public class LoadEventRequest {

    private String descendant;
    private String eventID;
    private String  latitude;
    private String  longitude;
    private String country;
    private String city;
    private String eventType;
    private String  year;
    private String personID;

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String  latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String  longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String  getYear() {
        return year;
    }

    public void setYear(String  year) {
        this.year = year;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
