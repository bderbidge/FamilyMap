package Services;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import Response.GeneralPurposeMessage;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class ClearService {

    public ClearService() {
    }

    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and
     * generated person and event data
     * @return GeneralPurposeMessage that states if the clear was successful.
     */
    public GeneralPurposeMessage Delete(){

        DatabaseDAO db = new DatabaseDAO();
        GeneralPurposeMessage message = new GeneralPurposeMessage();

        boolean success = false;
        try {

            db.openConnection();
            message.setMessage( db.clear());
            success = db.createTables();

            db.closeConnection(success);


        } catch (DatabaseException e) {
            e.printStackTrace();
            message.setMessage("Unable to clear Database");

        }


        return message;
    }

}
