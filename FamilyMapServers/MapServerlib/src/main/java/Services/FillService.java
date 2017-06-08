package Services;

import java.io.IOException;
import java.sql.SQLException;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import Models.Person;
import Models.User;
import Request.FillRequest;
import Response.GeneralPurposeMessage;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class FillService {


    public FillService() {
    }

    /**
     *  Populates the server's database with generated data for the specified user name.
     * The required "username" parameter must be a user already registered with the server. If there is
     * any data in the database already associated with the given user name, it is deleted. The
     * optional “generations” parameter lets the caller specify the number of generations of ancestors
     * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
     * persons each with associated events).
     * @param fillRequest is the username and number of generations that the user wants to go back
     * @return GeneralPurposeMessage if the fill was successful.
     */
    public GeneralPurposeMessage Post(FillRequest fillRequest) throws IOException, DatabaseException {


        //first create 31 people then create 5 events for each person
        String personID = " ";

        GeneralPurposeMessage message = new GeneralPurposeMessage();

        DatabaseDAO db = new DatabaseDAO();

        int year = 2017;



        try {

            boolean success = true;

            db.openConnection();

            personID = db.getPersonID(fillRequest.getUsername());

            db.setFill();

            db.deleteEventAndPerson(fillRequest.getUsername());

            User user = db.getUser(fillRequest.getUsername()); //getuser;

            Person newPerson = new Person();
            newPerson.setUsername(fillRequest.getUsername());
            newPerson.setId(user.getPersonId());
            newPerson.setGender(user.getGender());
            newPerson.setFirstName(user.getFirstName());
            newPerson.setLastName(user.getLastName());

            db.postPerson(newPerson);


            db.createEvents(year,personID, fillRequest.getUsername());

            db.fill(fillRequest.getGenerations(), fillRequest.getUsername(), year, personID);

            db.closeConnection(success);

            int numGen = (int) Math.pow(fillRequest.getGenerations(), 2);

            int numPeople = numGen * 2 - 1;
            int numEvents = numPeople * 5;

            if(numGen != 1)
                message.setMessage("Successfully added "+ numPeople+ " persons and " + numEvents +
                        " events to the database");
            else
                message.setMessage("Successfully added 3 persons and 15 events to the database");
        }
         catch (SQLException e) {

            e.printStackTrace();
             db.closeConnection(false);
             message.setMessage("unable to fill user's generations");
        }


        return message;
    }

}
