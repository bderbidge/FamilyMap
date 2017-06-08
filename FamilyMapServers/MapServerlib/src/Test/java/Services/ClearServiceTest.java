package Services;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import Response.GeneralPurposeMessage;

import static org.junit.Assert.*;

/**
 * Created by brandonderbidge on 5/31/17.
 */
public class ClearServiceTest {

    private DatabaseDAO db;

    @Before
    public void setUp() throws DatabaseException {
        db = new DatabaseDAO();

    }

    @After
    public void tearDown() throws DatabaseException {

        db = null;
    }



    @Test
    public void delete() throws Exception {

        ClearService service = new ClearService();
        GeneralPurposeMessage message = new GeneralPurposeMessage();

        message = service.Delete();

        assertTrue(message.getMessage() == "Clear succeeded");


    }

}