package Services;

/**
 * Created by brandonderbidge on 5/30/17.
 */
import org.junit.* ;


import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;

import Request.FillRequest;
import Request.RegisterRequest;
import Response.GeneralPurposeMessage;

import static org.junit.Assert.* ;



public class TestFillService {




    @Before
    public void setUp() throws DatabaseException {



    }

    @After
    public void tearDown() throws DatabaseException {

        ClearService service = new ClearService();
        service.Delete();

    }

    @Test
    public void FillTest() throws Exception {
        GeneralPurposeMessage message = new GeneralPurposeMessage();

        RegisterService registerservice = new RegisterService();
        RegisterRequest user = new RegisterRequest();
        user.setUserName("bderbidg");
        user.setPassword("password");
        user.setGender("m");
        user.setFirstName("Brandon");
        user.setlastName("Derbidge");
        user.setEmail("brander81@gmail.com");

        boolean success = false;
        try {
            registerservice.Post(user);


        FillService service = new FillService();
        FillRequest request = new FillRequest();


        request.setUsername("bderbidg");
        request.setGenerations(2);


           message = service.Post(request);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        assertTrue(message.getMessage().equals("Successfully added 7 persons and 35 events to the database"));


    }

}
