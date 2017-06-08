package DataAccess;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Models.Event;
import Models.Person;
import Models.User;
import Request.LoadEventRequest;
import Request.LoadRequest;
import Request.LoadUserRequest;
import Response.PersonResponse;

import static org.junit.Assert.*;

/**
 * Created by brandonderbidge on 5/31/17.
 */
public class DatabaseDAOTest {


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
        db.closeConnection(true);
        db = null;
    }


    @Test
    public void register() throws Exception {


        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        boolean success = false;
        success = db.register(user);

        assertTrue(success);

    }

    @Test
    public void login() throws Exception {

        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        boolean success = false;
        success = db.register(user);


        String username = "bderbidg";
        String password = "password";
        user = db.login(username, password);

        String personId = user.getPersonId();
        String authToken = user.getToken();
        assertTrue(user.getUsername().equals(username));

    }

    @Test
    public void clear() throws Exception {

       String data = db.clear();

        assertTrue(data == "Clear succeeded");
    }

    @Test
    public void load() throws Exception {

        Gson gson = new Gson();

        Reader reader = new FileReader("/Users/brandonderbidge/Documents/AndroidStudioProjects/" +
                "FamilyMapServer/MapServerlib/familymapserver/data/json/example.json");
        LoadRequest load = gson.fromJson(reader, LoadRequest.class);

        List<User> users = new LinkedList<>();
        List<Person>persons = new LinkedList<>();
        List<Event>events = new LinkedList<>();

        for (LoadUserRequest user: load.getUsers()) {

            User u = new User();
            u.setLastName(user.getLastName());
            u.setUsername(user.getUserName());
            u.setGender(user.getGender());
            u.setEmail(user.getEmail());
            u.setFirstName(user.getFirstName());
            u.setPassword(user.getPassword());
            u.setPerson(user.getPersonID());

            users.add(u);
        }

        for (PersonResponse person: load.getPersons()) {

            Person p = new Person();
            p.setLastName(person.getlastName());
            p.setUsername(person.getdescendant());
            p.setGender(person.getgender());
            p.setId(person.getpersonID());
            p.setFirstName(person.getfirstName());
            p.setFatherID(person.getfather());
            p.setMotherID(person.getmother());
            p.setSpouseID(person.getspouse());

            persons.add(p);
        }

        for (LoadEventRequest event: load.getEvents()) {

            Event e = new Event();

            e.setID(event.getEventID());
            e.setYear(Integer.parseInt(event.getYear()));
            e.setUsername(event.getDescendant());
            e.setEventType(event.getEventType());
            e.setCity(event.getCity());
            e.setCountry(event.getCountry());
            e.setLatitude(Double.parseDouble(event.getLatitude()));
            e.setLongitude(Double.parseDouble(event.getLongitude()));
            e.setPersonId(event.getPersonID());

            events.add(e);
        }

        boolean success = false;
        success = db.load(users, persons, events);
        assertTrue(success);
    }



    @Test
    public void getEvent() throws DatabaseException {
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

        User user = new User();
        user.setUsername("derb");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");

        success = db.register(user);

        List<Event> events = new ArrayList<>();
        try {
            success = eventDAO.Post(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(success);

        if(success == true)
            events = db.getAllEvents(event.getID(), user.getToken(), false, events);

        assertTrue(events.get(0).getID().equals(event.getID()));


    }

    @Test
    public void getEvents() throws DatabaseException {

        Event event = new Event();

        event.setLongitude(22);
        event.setLatitude(33);
        event.setCountry("USA");
        event.setEventType("Birth");
        event.setID("1");
        event.setUsername("derb1");
        event.setPersonId("12345");
        event.setCity("China");
        event.setYear(1993);
        boolean success = false;

        User user = new User();
        user.setUsername("derb");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");

        success = db.register(user);

        List<Event> events = new ArrayList<>();
        try {
            success = eventDAO.Post(event);

            event.setLongitude(23);
            event.setLatitude(33);
            event.setCountry("USA");
            event.setEventType("Birth");
            event.setID("2");
            event.setUsername("derb");
            event.setPersonId("1345");
            event.setCity("China");
            event.setYear(1293);
            success = eventDAO.Post(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(success);

        if(success == true)
            events = db.getAllEvents(event.getID(), user.getToken(), false, events);

        assertTrue(events.size() == 2);


    }

    @Test
    public void DeleteEventAndPerson(){

        try {
            db.deleteEventAndPerson("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllPeople() throws Exception {

        Person person = new Person();
        person.setGender("m");
        person.setUsername("bderb");
        person.setFirstName("Brandon");
        person.setLastName("Derbidge");
        person.setId("1");

        db.postPerson(person);


        person.setGender("m");
        person.setUsername("bdderb");
        person.setFirstName("Bradndon");
        person.setLastName("Derbidge");
        person.setId("12");

        db.postPerson(person);
        List<Person> persons = new ArrayList<>();


        persons = db.getAllPeople(person.getId() ,person.getUsername(), false, persons);

        assertTrue(persons.size() == 2);

    }





    @Test
    public void getUser() throws Exception {

        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        user.createToken();
        boolean success = false;

        success = db.register(user);
        User newUser = new User();
        if(success == true)
            newUser = db.getUser(user.getUsername());

        assertTrue(newUser.getPersonId().equals(user.getPersonId()));

    }


}