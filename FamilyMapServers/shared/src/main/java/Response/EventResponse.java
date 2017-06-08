package Response;

/**
 * Created by brandonderbidge on 5/20/17.
 */

public class EventResponse {

    /**
     * This returns an Event object to be converted into JSON.
     */

    private String descendant;
    private String eventID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;


    public String getdescendant() {
        return descendant;
    }

    public void setdescendant(String descendant) {
        this.descendant = descendant;
    }

    public String geteventID() {
        return eventID;
    }

    public void seteventID(String eventID) {
        this.eventID = eventID;
    }

    public double getlatitude() {
        return latitude;
    }

    public void setlatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getlongitude() {
        return longitude;
    }

    public void setlongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }

    public String geteventType() {
        return eventType;
    }

    public void seteventType(String eventType) {
        this.eventType = eventType;
    }

    public int getyear() {
        return year;
    }

    public void setyear(int year) {
        this.year = year;
    }

}
