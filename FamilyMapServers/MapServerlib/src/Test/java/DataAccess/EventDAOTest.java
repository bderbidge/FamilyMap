package DataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.Event;

import static org.junit.Assert.*;

/**
 * Created by brandonderbidge on 5/31/17.
 */
public class EventDAOTest {


    private DatabaseDAO db;
    private EventDAO eventDAO;


    @Before
    public void setUp() throws DatabaseException {
        db = new DatabaseDAO();
        db.openConnection();
        eventDAO = new EventDAO(db);
        db.createTables();

    }

    @After
    public void tearDown() throws DatabaseException {
        db.clear();
        db.createTables();
        db.closeConnection(true);
        db = null;
    }

    @Test
    public void postAndGet() throws Exception {

        Event event = new Event();

        event.setLongitude(22);
        event.setLatitude(33);
        event.setCountry("USA");
        event.setEventType("Birth");
        event.setID("1");
        event.setUsername("derb");
        event.setPersonId("12345");
        event.setCity("China");
        event.setYear(1993);
        boolean success = false;

        List<Event> events = new ArrayList<>();
        try {
            success = eventDAO.Post(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(success);

        if(success == true)
           events = eventDAO.Get(event.getID(), event.getUsername(), false);

        assertTrue(events.get(0).getID().equals(event.getID()));


    }


    @Test
    public void delete() throws Exception {

        Event event = new Event();

        event.setLongitude(22);
        event.setLatitude(33);
        event.setCountry("USA");
        event.setEventType("Birth");
        event.setID("1");
        event.setUsername("derb");
        event.setPersonId("12345");
        event.setCity("China");
        event.setYear(1993);
        boolean success = false;

        List<Event> events = new ArrayList<>();

        try {

            success = eventDAO.Post(event);
            eventDAO.Delete(event.getUsername());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(success == true)
            events = eventDAO.Get(event.getID(), event.getUsername(), false);

        assertFalse(events.get(0).getID().equals(event.getID()));


    }

}