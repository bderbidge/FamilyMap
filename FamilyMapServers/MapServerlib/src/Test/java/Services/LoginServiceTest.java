package Services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;

import Request.LoginRequest;
import Response.RegisterLoginResponse;


import static org.junit.Assert.*;

/**
 * Created by brandonderbidge on 5/31/17.
 */
public class LoginServiceTest {

    private DatabaseDAO db;

    @Before
    public void setUp() throws DatabaseException {
        db = new DatabaseDAO();



    }

    @After
    public void tearDown() throws DatabaseException {
        ClearService service = new ClearService();
        service.Delete();
        db = null;
    }

    @Test
    public void LoginUserDatabase(){

        LoginService loginService = new LoginService();
        LoginRequest request = new LoginRequest();
        RegisterServiceTest registerServiceTest = new RegisterServiceTest();
        RegisterLoginResponse login = new RegisterLoginResponse();
        try {

            registerServiceTest.RegisterUserDatabase();
            request.setUserName("bderbidg");
            request.setPassword("password");
            login = loginService.Post(request);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        assertTrue(login.getusername().equals("bderbidg"));

    }
}