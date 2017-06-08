package Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import DataAccess.EventDAO;

import static org.junit.Assert.*;

/**
 * Created by brandonderbidge on 5/31/17.
 */
public class EventTest {


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
    public void testEvent(){
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

        try {

          success = eventDAO.Post(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        assertTrue(success);

    }


}