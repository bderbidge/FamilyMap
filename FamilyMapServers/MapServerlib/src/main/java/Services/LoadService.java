package Services;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import Models.Event;
import Models.Person;
import Models.User;
import Request.LoadEventRequest;
import Response.GeneralPurposeMessage;
import Request.LoadRequest;
import Request.LoadUserRequest;
import Response.PersonResponse;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class LoadService {

    public LoadService() {
    }

    /**
     * Clears all data from the database (just like the /clear API), and then loads the
     * posted user, person, and event data into the database.
     * @param loadRequest contains arrays of events persons and users.
     * @return a GeneralPuposeMessage object message stating if the post was successful.
     */
    public GeneralPurposeMessage Post(LoadRequest loadRequest){

        List<User> users = new LinkedList<>();
        List<Person >persons = new LinkedList<>();
        List< Event >events = new LinkedList<>();
        DatabaseDAO db = new DatabaseDAO();

        for (LoadUserRequest user: loadRequest.getUsers()) {

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

        for (PersonResponse person: loadRequest.getPersons()) {

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

        for (LoadEventRequest event: loadRequest.getEvents()) {

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
        try {

            db.openConnection();

            success = db.load(users,persons,events);

            db.closeConnection(success);

        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        GeneralPurposeMessage message = new GeneralPurposeMessage();

        if(success == false)
            message.setMessage("Unable to load file");
        else
            message.setMessage("Successfully added "+ users.size()+ " users, " + persons.size() +
                " persons, and " + events.size() + " events to the database");


        return message;


    }
}
