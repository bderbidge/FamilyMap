package Request;

/**
 * Created by brandonderbidge on 5/20/17.
 */

public class GeneralIDRequest {

    /**
     * JSON will be converted into this object adn the IDs will be
     * used to access the database through multiple services.
     */

    private String requestID;
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
}
