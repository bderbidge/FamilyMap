package Services;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import Models.Person;
import Models.User;
import Request.RegisterRequest;
import Response.RegisterLoginResponse;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class RegisterService {

    public RegisterService() {

    }

    /**
     *
     * @param registerRequest object
     * Creates a new user account, generates 4 generations of ancestor data for the new
     *  user, logs the user in, and returns a register Response
     * @return RegisterLoginResponse
     */
    public RegisterLoginResponse Post(RegisterRequest registerRequest) throws DatabaseException {

        User newUser = new User();
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getlastName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setGender(registerRequest.getGender());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setUsername(registerRequest.getUserName());
        newUser.createPersonId();

        DatabaseDAO db = new DatabaseDAO();
        boolean success = false;

        RegisterLoginResponse response = new RegisterLoginResponse();
        try {

            db.openConnection();

            success = db.register(newUser);

            if(success == true) {
                response.setpersonID(newUser.getPersonId());
                response.setusername(newUser.getUsername());
                response.setauthToken(newUser.getToken());
                db.closeConnection(success);
            }else {
                response = null;
                db.closeConnection(success);
            }

        } catch (DatabaseException e) {
            db.closeConnection(success);
            e.printStackTrace();
            response = null;

        }

        return response;
    }
}
