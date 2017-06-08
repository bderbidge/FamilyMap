package Request;

/**
 * Created by brandonderbidge on 5/23/17.
 */

public class LoginRequest {

    String userName = "";
    String password = "";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validLoginInput(){

        if(userName.isEmpty() || password.isEmpty())
            return false;
        else
            return true;

    }
}
