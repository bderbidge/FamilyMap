package Models;

import java.io.Serializable;

/**
 * Created by brandonderbidge on 6/5/17.
 */

public class Login implements Serializable {

    public Login(){
    }

    public Login(String username, String password) {
        this.userName = username;
        this.password = password;

    }


    private String userName;

    private String password;

    public String getusername() {
        return userName;
    }

    public void setusername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
