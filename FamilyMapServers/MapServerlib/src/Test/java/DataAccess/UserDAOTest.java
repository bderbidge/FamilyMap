package DataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Models.User;

import static org.junit.Assert.assertTrue;


/**
 * Created by brandonderbidge on 5/31/17.
 */
public class UserDAOTest {

    private DatabaseDAO db;
    private UserDAO userDAO;

    @Before
    public void setUp() throws DatabaseException {
        db = new DatabaseDAO();
        db.openConnection();
        userDAO = new UserDAO(db);
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
    public void Get() throws Exception {

        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        boolean success = false;

        success = userDAO.registerUser(user);
        User newuser = new User();
        newuser = userDAO.Get(user.getUsername());

        assertTrue(success);
        assertTrue(user.getEmail().equals(newuser.getEmail()));

    }

    @Test
    public void loginUser() throws Exception {

        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        boolean success = false;
        success = userDAO.registerUser(user);


        String username = "bderbidg";
        String password = "password";
        user = userDAO.loginUser(username, password);

        String personId = user.getPersonId();
        String authToken = user.getToken();
        assertTrue(user.getUsername().equals(username));
    }

    @Test
    public void registerUser() throws Exception {

        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        boolean success = false;

        success = userDAO.registerUser(user);

        assertTrue(success);
    }

    @Test
    public void updateAuthToken() throws Exception {

        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        user.createToken();
        boolean success = false;

        success = userDAO.registerUser(user);
        if(success == true)
         userDAO.updateAuthToken(user);

        User newuser = new User();
        newuser = userDAO.Get(user.getUsername());

        assertTrue(newuser.getToken() != user.getToken());
    }

    @Test
    public void checkToken() throws Exception {
        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        user.createToken();
        boolean success = false;

        success = userDAO.registerUser(user);

    }

    @Test
    public void deleteAuthToken() throws Exception {

        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        user.createToken();
        boolean success = false;

        success = userDAO.registerUser(user);

        if(success == true)
            success = userDAO.checkToken(user.getToken());

        User newuser = new User();
        newuser = userDAO.Get(user.getUsername());

        assertTrue(newuser.getToken().equals(null));

    }

    @Test
    public void getUsername() throws Exception {

        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        user.createToken();
        boolean success = false;

        success = userDAO.registerUser(user);

        String username = " ";
        if(success == true)
            username = userDAO.getUsername(user.getToken());

        assertTrue(username.equals(user.getUsername()));
    }

    @Test
    public void getPersonID() throws Exception {

        User user = new User();
        user.setUsername("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setLastName("Derbidge");
        user.setEmail("brander81@gmail.com");
        user.createToken();
        boolean success = false;

        success = userDAO.registerUser(user);
        String PersonID = " ";
        if(success == true)
            PersonID = userDAO.getPersonID(user.getToken());

        assertTrue(PersonID.equals(user.getPersonId()));

    }

}