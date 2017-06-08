package Request;

import java.util.List;

/**
 * Created by brandonderbidge on 5/20/17.
 */

public class RegisterRequest {


    /**
     * This object was converted from json into a format that will be used to access the database.
     */
    private String userName = "";
    private String password = "";
    private String email = "";
    private String firstName = "";
    private String lastName = "";
    private String gender = "";




    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean validRegisterInput(){

        if(userName.isEmpty()|| password.isEmpty() || email.isEmpty()
                || firstName.isEmpty() ||lastName.isEmpty() || gender.isEmpty())
            return false;
        else
            return true;
    }

}
