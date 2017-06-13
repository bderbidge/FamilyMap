package Models;

import java.util.UUID;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class Event implements Comparable {

    private String ID;
    private String Username;
    private String PersonId;
    private double Latitude;
    private double Longitude;
    private String Country;
    private String City;
    private String EventType;
    private int Year;

    public Event() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void generateID(){ID = UUID.randomUUID().toString();}

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPersonId() {
        return PersonId;
    }

    public void setPersonId(String personId) {
        PersonId = personId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    @Override
    public int compareTo(Object compareEvent) {

        int compareyear=((Event) compareEvent).getYear();
        /* For Ascending order*/
        return this.getYear()-compareyear;
    }

    @Override
    public String toString() {
        return getEventType() + ": "+ getCity() + ", " + getCountry() + " ( " + getYear() + " )";
    }
}
