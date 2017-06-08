package DataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;

import Models.Event;
import Models.Person;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class EventDAO {

    private DatabaseDAO db;

    public EventDAO(DatabaseDAO db) {
        this.db = db;
    }

    /**
     * Creates a new Event in the database
     * @param event object
     * @return boolean
     */
    public boolean Post(Event event) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String sql = "insert into event (id, username, person_id, latitude, longitude, country, " +
                "city, eventType, year) " +
                "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        boolean success = false;
        try {
            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, event.getID());
            stmt.setString(2, event.getUsername());
            stmt.setString(3, event.getPersonId());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            if (stmt.executeUpdate() == 1) {
                success = true;

            } else {
                throw new SQLException();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }


        return success;
    }


    /**
     * This function gets both multiple events or just one depending
     * on the parameter
     * @param multipleEvents if this is true then it will return multiple events
     *                       otherwise it will just return and array containing
     *                       one event
     */
    public List<Event> Get(String id, String username, boolean multipleEvents) throws SQLException, SQLFeatureNotSupportedException {

        List<Event> events = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet keyRS = null;

        // This string gets a single event
        String sqlForEvent = "SELECT event.id, event.username, event.person_id, " +
                "event.latitude, event.longitude, event.country," +
                " event.city, event.eventType, event.year " +
                "FROM [event] WHERE event.id = ? AND event.username = ?";

        //This gets multiple events
        String sqlForEvents = "SELECT event.id, event.username, event.person_id, " +
                "event.latitude, event.longitude, event.country," +
                " event.city, event.eventType, event.year " +
                "FROM [event] WHERE event.username = ?";




        try
        {


            if(multipleEvents == false){

                stmt = db.getConnection().prepareStatement(sqlForEvent);
                stmt.setString(1, id);
                stmt.setString(2, username);
            }else {

                stmt = db.getConnection().prepareStatement(sqlForEvents);
                stmt.setString(1, username);

            }


            keyRS = stmt.executeQuery();


            while (keyRS.next()){

                Event event = new Event();

                event.setID(keyRS.getString(1));
                event.setUsername(keyRS.getString(2));
                event.setPersonId(keyRS.getString(3));
                event.setLatitude(keyRS.getInt(4));
                event.setLongitude(keyRS.getInt(5));
                event.setCountry(keyRS.getString(6));
                event.setCity(keyRS.getString(7));
                event.setEventType(keyRS.getString(8));
                event.setYear(keyRS.getInt(9));

                events.add(event);

            }



        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Event");
        }
        finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }

        return events;

    }


    public void Delete(String username) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String sql = "DELETE FROM event WHERE username = ?";

        try {

            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }


    }

}
