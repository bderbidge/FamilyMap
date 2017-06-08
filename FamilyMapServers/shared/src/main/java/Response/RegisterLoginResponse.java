package Response;

/**
 * Created by brandonderbidge on 5/20/17.
 */

public class RegisterLoginResponse {

    /**
     * This returns a Register and Login response that will be converted into json
     * This response will hold information indicating that the login or Register was
     * successful.
     */

    private String authToken;
    private String userName;
    private String personID;


    public String getauthToken() {
        return authToken;
    }

    public void setauthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getusername() {
        return userName;
    }

    public void setusername(String username) {userName = username ;
    }

    public String getpersonID() {
        return personID;
    }

    public void setpersonID(String personID) {
        this.personID = personID;
    }
}
