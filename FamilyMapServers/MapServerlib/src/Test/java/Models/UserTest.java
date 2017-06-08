package Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;

import static org.junit.Assert.*;

/**
 * Created by brandonderbidge on 5/31/17.
 */
public class UserTest {

    private DatabaseDAO db;

    @Before
    public void setUp() throws DatabaseException {
        db = new DatabaseDAO();
        db.openConnection();
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
    public void testPerson(){
        User person = new User();

        person.setUsername("bderbidge");
        person.setGender("m");
        person.setEmail("a;lskdfj@gmail.com");
        person.setPassword("password");

        person.setFirstName("Brandon");
        person.setLastName("Derbidge");
        person.setToken("1");
        person.setPerson("2");
        String id = "";

        boolean success = false;
        success = db.register(person);
        person = db.getUser(person.getUsername());


        assertTrue(success);
        assertTrue(person.getFirstName().equals("Brandon"));





    }


}