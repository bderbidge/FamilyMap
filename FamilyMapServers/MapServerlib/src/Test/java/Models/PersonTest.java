package Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;

import DataAccess.PersonDAO;

import static org.junit.Assert.*;

/**
 * Created by brandonderbidge on 5/31/17.
 */
public class PersonTest {

    private DatabaseDAO db;
    private PersonDAO personDAO;


    @Before
    public void setUp() throws DatabaseException {
        db = new DatabaseDAO();
        db.openConnection();
        personDAO = new PersonDAO(db);
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
        Person person = new Person();
        List<Person> newPerson = new ArrayList<>();
        person.setUsername("bderbidge");
        person.setGender("m");
        person.setId("1");
        person.setFatherID("2");
        person.setMotherID("3");
        person.setFirstName("Brandon");
        person.setLastName("Derbidge");
        person.setSpouseID("4");
        String id = "";

        boolean success = false;
        try {
            db.postPerson(person);
            db.closeConnection(true);
            db.openConnection();
            boolean multiplePeople = false;
            newPerson = personDAO.Get(person.getId(), person.getUsername(), multiplePeople);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        id = newPerson.get(0).getId();
        assertTrue(id.equals(person.getId()));

    }



}