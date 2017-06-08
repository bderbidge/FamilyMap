package Request;

/**
 * Created by brandonderbidge on 5/20/17.
 */



public class FillRequest {

    /**
     * JSON will be converted into this object
     */
    private String userName;
    private int generations;

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        userName = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
