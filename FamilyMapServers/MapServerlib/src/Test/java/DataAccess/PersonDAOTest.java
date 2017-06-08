package DataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import Models.Person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by brandonderbidge on 5/31/17.
 */
public class PersonDAOTest {

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
    public void postAndGet() throws Exception {

        Person person = new Person();
        person.setGender("m");
        person.setUsername("bderb");
        person.setFirstName("Brandon");
        person.setLastName("Derbidge");
        person.setId("1");

        Boolean success = personDAO.Post(person);
        List<Person> persons = new ArrayList<>();

        if(success == true)
           persons = personDAO.Get(person.getId() ,person.getUsername(), false);

        assertTrue(persons.get(0).getId().equals(person.getId()));
    }

    @Test
    public void addFatherAndMother() throws Exception {

        Person person = new Person();
        person.setGender("m");
        person.setUsername("bderb");
        person.setFirstName("Brandon");
        person.setLastName("Derbidge");
        person.setId("1");
        String father = "2";
        String mother = "3";

        Boolean success = personDAO.Post(person);
        List<Person> persons = new ArrayList<>();

        if(success == true) {
            personDAO.addFatherAndMother(person.getId(), father, mother);
            persons = personDAO.Get(person.getId() ,person.getUsername(), false);

            assertTrue(persons.get(0).getId().equals(person.getId()));
            assertTrue(persons.get(0).getFatherID().equals(person.getFatherID()));
            assertTrue(persons.get(0).getMotherID().equals(person.getMotherID()));
        }
    }

    @Test
    public void addSpouse() throws Exception {

        Person person = new Person();
        person.setGender("m");
        person.setUsername("bderb");
        person.setFirstName("Brandon");
        person.setLastName("Derbidge");
        person.setId("1");
        String spouse = "2";

        Boolean success = personDAO.Post(person);
        List<Person> persons = new ArrayList<>();

        if(success == true) {
            personDAO.addSpouse(person.getId(), spouse);
            persons = personDAO.Get(person.getId() ,person.getUsername(), false);

            assertTrue(person.getSpouseID().equals(persons.get(0).getSpouseID()));
        }
    }

    @Test
    public void delete() throws Exception {

        Person person = new Person();
        person.setGender("m");
        person.setUsername("bderb");
        person.setFirstName("Brandon");
        person.setLastName("Derbidge");
        person.setId("1");
        String spouse = "2";

        Boolean success = personDAO.Post(person);
        List<Person> persons = new ArrayList<>();

        if(success == true)
            personDAO.Delete(person.getUsername());

        persons = personDAO.Get(person.getId() ,person.getUsername(), false);

        assertFalse(person.getUsername().equals(persons.get(0).getUsername()));

    }

}