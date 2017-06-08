package Services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import Request.RegisterRequest;
import Response.RegisterLoginResponse;

import static org.junit.Assert.*;

/**
 * Created by brandonderbidge on 5/31/17.
 */
public class RegisterServiceTest {


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
        db.closeConnection(true);
        db = null;
    }



    @Test
    public void RegisterUserDatabase() throws DatabaseException {


        RegisterService registerservice = new RegisterService();
        RegisterRequest user = new RegisterRequest();
        RegisterLoginResponse response = new RegisterLoginResponse();
        user.setUserName("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setlastName("Derbidge");
        user.setEmail("brander81@gmail.com");

        boolean success = false;

        response = registerservice.Post(user);

        assertTrue(response.getusername().equals("bderbidg"));



    }
}