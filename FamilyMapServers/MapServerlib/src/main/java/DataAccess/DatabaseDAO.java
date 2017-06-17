package DataAccess;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import FillData.FemaleNames;
import FillData.LastNames;
import FillData.Location;
import FillData.LocationData;
import FillData.MaleNames;
import Models.Event;
import Models.Person;
import Models.User;

/**
 * Created by brandonderbidge on 5/19/17.
 */

public class DatabaseDAO {


    private UserDAO userTable = new UserDAO(this);
    private EventDAO eventTable = new EventDAO(this);
    private PersonDAO personTable = new PersonDAO(this);
    private LocationData locationData = new LocationData();
    private FemaleNames femaleNames = new FemaleNames();
    private MaleNames maleNames = new MaleNames();
    private LastNames lastNames = new LastNames();

    public static void SetAuthTokenExpire(long authTokenExpire){
        UserDAO.SetAuthTokenExpire(authTokenExpire);
    }

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private Connection connection;

    public DatabaseDAO() {
    }

    /**
     * Creates a database connection and then returns if
     * the connection was successful
     *
     */

    public void openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyMapServer.sqlite";

            // Open a database connection
            connection = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            connection.setAutoCommit(false);

        }
        catch (SQLException e) {
            throw new DatabaseException("openConnection failed", e);
        }
    }

    /**
     * Closes the database connection and returns a boolean
     * if the connection was successfully closed.
     *
     */
    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                connection.commit();
            }
            else {
                connection.rollback();
            }

            connection.close();
            connection = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed", e);
        }
    }

    public boolean createTables() throws DatabaseException {

        boolean success = false;

        try {
            Statement stmt = null;


            try {
                stmt = connection.createStatement();

                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user " +
                        "( person_id text NOT NULL, authtoken text UNIQUE ," +
                        " username text NOT NULL UNIQUE" +
                        ", password text NOT NULL, email text NOT NULL," +
                        " firstname text NOT NULL, lastname text NOT NULL, gender text NOT NULL," +
                        " date datetime, PRIMARY KEY(person_id)," +
                        " constraint ck_gender check ( gender in ( 'm' , 'f') ))");

                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS person " +
                        "( person_id text NOT NULL, " +
                        " username text NOT NULL," +
                        "firstname text NOT NULL," +
                        "lastname text NOT NULL, " +
                        "gender text NOT NULL," +
                        "fatherId text, " +
                        "motherId text, " +
                        "spouseId text, " +
                        "FOREIGN KEY (person_id) REFERENCES USER(person_id)) "

                );

                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS event (" +
                        "id text not null primary key, username text not null," +
                        " person_id text not null, latitude int," +
                        " longitude int, country text not null," +
                        " city text not null, eventType text not null," +
                        " year int, FOREIGN KEY (person_id) REFERENCES person(id))");

                success = true;
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("createTables failed", e);
        }

        return success;
    }

    public boolean register(User user)  {

        Person newPerson = new Person();
        user.createPersonId();
        newPerson.setId(user.getPersonId());
        newPerson.setUsername(user.getUsername());
        newPerson.setGender(user.getGender());
        newPerson.setFirstName(user.getFirstName());
        newPerson.setLastName(user.getLastName());


        boolean success = false;


        try {

            success = userTable.registerUser(user);

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }


        if(success == true) {

            try {

                success = personTable.Post(newPerson);
                setFill();

                int generation = 4;
                int year = 2017;
                createEvents(year, user.getPersonId(), user.getUsername());
                fill(generation, user.getUsername(), year, user.getPersonId());

            } catch (SQLException e) {

                e.printStackTrace();
                return false;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return success;
    }

    public User login(String username, String password){

        User user = new User();

        try {

            user = userTable.loginUser(username,password);

        } catch (SQLException e) {
            e.printStackTrace();
            user = null;
        }

        return user;
    }


    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and
     * generated person and event data
     * @return
     */
    public String clear() throws DatabaseException {

        try {
            Statement stmt = null;
            try {

                stmt = connection.createStatement();

                stmt.executeUpdate("drop table if exists user");
                stmt.executeUpdate("drop table if exists person");
                stmt.executeUpdate("drop table if exists event");

                }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unable to clear database.", e);
        }
        return "Clear succeeded";
    }


    /**
     * Clears all data from the database (just like the /clear API), and then loads the
     * posted user, person, and event data into the database.
     * @param users list
     * @param persons list
     * @param events list
     * @return a message stating if the post was successful.
     */
    public boolean load(List<User> users, List<Person>persons, List<Event>events) throws SQLException {

        boolean success = false;
        for (User u :users) {
           success = userTable.registerUser(u);
            if(success == false)
                return success;

        }


        for (Person p: persons){
            try {
              success = personTable.Post(p);
                if(success == false)
                    return success;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        for(Event e: events){
            try {
               success = eventTable.Post(e);
                if(success == false)
                    return success;

                success = true;
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
        }


        return success;
    }


    /**
     * Returns the single Event object with the specified ID
     * @param id this is the id of the event being accessed.
     * @param authToken to get unique token to
     * @return the event object being accessed
     */
    public List<Event> getEvent(String id, String authToken ) throws DatabaseException {

        boolean success = false;
        boolean multipleEvents = false;

        List<Event> events = new LinkedList<>();
        try {

           success = userTable.checkToken(authToken);
            if(success == true) {
                closeConnection(success);
                throw new DatabaseException("AuthToken is not valid");
            }

            String username = userTable.getUsername(authToken);
            events = eventTable.Get(id, username, multipleEvents);

        } catch (SQLException e) {
            e.printStackTrace();
            events = null;
        }

        return events;
    }

    /**
     *
     * Returns the single Person object with the specified ID
     * @param id
     * @return person object
     */
    public List<Person> getPerson(String id, String authToken ) throws DatabaseException {

        boolean multiplePeople = false;
        List<Person> people = new LinkedList<>();
        try {
            boolean success = false;
            success = userTable.checkToken(authToken);

            if(success == true) {
                closeConnection(success);
                throw new DatabaseException("AuthToken is not valid");
            }

            String username = userTable.getUsername(authToken);
           people = personTable.Get(id, username, multiplePeople);

        } catch (SQLException e) {
            e.printStackTrace();
            people = null;
        }

        return people;
    }

    /**
     * Returns ALL family members of the current user. The current user is
     * determined from the provided auth token.
     * @param id
     * @param authToken
     * @return list of Person objects
     */
    public List<Person> getAllPeople(String id, String authToken, boolean multiplePeople,
                                     List<Person> personsList) throws DatabaseException {

        Person person = new Person();
        boolean success = false;

        try {

            success = userTable.checkToken(authToken);

            if(success == true) {
                closeConnection(success);
                throw new DatabaseException("AuthToken is not valid");
            }

            String username = userTable.getUsername(authToken);
            personsList =  personTable.Get(id, username, multiplePeople);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personsList;
    }

    /**
     * Returns ALL events for ALL family members of the current user. The current
     * user is determined from the provided auth token.
     * @return List of Events
     */
    public List<Event> getAllEvents(String id, String authToken, boolean multipleEvents,
                                    List<Event> eventList) throws DatabaseException {
        boolean success = false;
        try {

            success = userTable.checkToken(authToken);

            if(success == true) {
                closeConnection(success);
                throw new DatabaseException("AuthToken is not valid");
            }

            String username = userTable.getUsername(authToken);
            eventList = eventTable.Get(id, username, multipleEvents);


        } catch (SQLException e) {
            e.printStackTrace();
        }



        return eventList;
    }

    /**
     *  Populates the server's database with generated data for the specified user name.
     * The required "username" parameter must be a user already registered with the server. If there is
     * any data in the database already associated with the given user name, it is deleted. The
     * optional “generations” parameter lets the caller specify the number of generations of ancestors
     * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
     * persons each with associated events).
     *
     * @return authToken object
     */
    public void fill( int generation, String username, int year, String currentPersonID) throws SQLException {

        int femaleRnd = new Random().nextInt(femaleNames.getData().size());
        int maleRnd = new Random().nextInt(maleNames.getData().size());
        int lastRnd = new Random().nextInt(lastNames.getData().size());



        String fatherID = " ";
        String motherID = " ";
        generation--;

        year -= 20;

        if(generation < 0)
            return ;

        //create father
        fatherID = createFather(maleNames.getData().get(maleRnd), lastNames.getData().get(lastRnd),
                                username, year);


        //then call this function recursively
        fill(generation, username, year, fatherID);



        lastRnd = new Random().nextInt(lastNames.getData().size());
        //create mother
        motherID = createMother(femaleNames.getData().get(femaleRnd), lastNames.getData().get(lastRnd),
                     username, year);



        //then call this function recursively
        fill(generation, username, year, motherID);


        //create spouse


        //find user/person and add his parents person_id's to his person

        try {

            personTable.addSpouse(fatherID,motherID);
            personTable.addSpouse(motherID,fatherID);
            personTable.addFatherAndMother(currentPersonID, fatherID, motherID);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public String createFather(String maleName, String lastName, String username, int year) throws SQLException {

        Person father = new Person();
        User tempuser = new User();
        tempuser.createPersonId();

        father.setGender("m");
        father.setUsername(username);
        father.setFirstName(maleName);
        father.setLastName(lastName);
        father.setId(tempuser.getPersonId());

        try {
            personTable.Post(father);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createEvents(year, father.getId(), username);

        return father.getId();
    }

    public String createMother(String femaleName, String lastName, String username, int year) throws SQLException {

        Person mother = new Person();
        User tempuser = new User();
        tempuser.createPersonId();

        mother.setGender("f");
        mother.setUsername(username);
        mother.setFirstName(femaleName);
        mother.setLastName(lastName);
        mother.setId(tempuser.getPersonId());

        try {
            personTable.Post(mother);
        } catch (SQLException e) {
            e.printStackTrace();
        }


          createEvents(year, mother.getId(), username);



        return mother.getId();

    }

    public void createEvents(int year, String personID, String username) throws SQLException {




        for(int i = 0; i < 5; i++){

            int locationRnd = new Random().nextInt(locationData.getData().size());
            Location location = new Location();
            location = locationData.getData().get(locationRnd);

            Event newEvent = new Event();
            year += 2;

            //birth
            if(i == 0){
                newEvent.setEventType("Birth");

            }//baptism
            else if(i == 1){
                newEvent.setEventType("Baptism");

            //mission
            }
            else if (i == 2){
                newEvent.setEventType("Mission");


            }//Marriage
            else if(i == 3){
                newEvent.setEventType("Marriage");


            }//death
            else if(i == 4){
                newEvent.setEventType("Death");
            }

            newEvent.setYear(year);
            newEvent.setUsername(username);
            newEvent.setCountry(location.getCountry());
            newEvent.setCity(location.getCity());
            newEvent.setPersonId(personID);
            newEvent.setLatitude(Double.parseDouble(location.getLatitude()));
            newEvent.setLongitude(Double.parseDouble(location.getLongitude()));
            newEvent.generateID();


                eventTable.Post(newEvent);



        }

    }

    public void setFill() throws FileNotFoundException {
        Gson gson = new Gson();



        Reader reader = new FileReader("/Users/brandonderbidge/Documents/CS240/FamilyMap" +
                "/FamilyMapServers/MapServerlib/familymapserver/data/json/locations.json");
        LocationData locData = gson.fromJson(reader, LocationData.class);

        Reader reader2 = new FileReader("/Users/brandonderbidge/Documents/CS240/FamilyMap" +
                "/FamilyMapServers/MapServerlib/familymapserver/data/json/fnames.json");
        FemaleNames fnames = gson.fromJson(reader2, FemaleNames.class);

        Reader reader3 = new FileReader("/Users/brandonderbidge/Documents/CS240/FamilyMap" +
                "/FamilyMapServers/MapServerlib/familymapserver/data/json/snames.json");
        LastNames snames = gson.fromJson(reader3, LastNames.class);

        Reader reader4 = new FileReader("/Users/brandonderbidge/Documents/CS240/FamilyMap" +
                "/FamilyMapServers/MapServerlib/familymapserver/data/json/mnames.json");
        MaleNames mnames = gson.fromJson(reader4, MaleNames.class);

        this.locationData = locData;
        this.femaleNames = fnames;
        this.maleNames = mnames;
        this.lastNames = snames;

    }

    public String getPersonID(String username) throws SQLException {

        return userTable.getPersonID(username);
    }

    public void deleteEventAndPerson(String username) throws SQLException {

        personTable.Delete(username);
        eventTable.Delete(username);

    }

    public User getUser(String username){

        User user = new User();

        try {
             user = userTable.Get(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void postPerson(Person person){

        try {
            personTable.Post(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
