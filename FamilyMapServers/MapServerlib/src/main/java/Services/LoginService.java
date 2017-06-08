package Services;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import Models.User;
import Request.LoginRequest;
import Response.RegisterLoginResponse;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class LoginService {

    public LoginService() {
    }

    /**
     *
     * @param loginRequest object that contains registration info.
     * @return an RegisterLoginResponse object
     * Logs in the user and returns an auth token.
     */

    public RegisterLoginResponse Post(LoginRequest loginRequest){

        DatabaseDAO db = new DatabaseDAO();
        User user = new User();
        RegisterLoginResponse response = new RegisterLoginResponse();
        try {

            boolean success = false;
            db.openConnection();

            user = db.login(loginRequest.getUserName(),loginRequest.getPassword());


            if(user != null) {
                success = true;
                response.setauthToken(user.getToken());
                response.setusername(user.getUsername());
                response.setpersonID(user.getPersonId());
                db.closeConnection(success);
            }
            else {
                db.closeConnection(success);
                response = null;
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        return response;
    }

}
