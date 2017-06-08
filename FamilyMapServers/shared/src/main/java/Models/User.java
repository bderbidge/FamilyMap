package Models;

import java.util.UUID;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class User {

    private String Username;
    private String PersonId;
    private String Password;
    private String Email;
    private String FirstName;
    private String LastName;
    private String Gender;
    private String authToken;


    public User() {
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPersonId() {
        return PersonId;
    }

    public void createPersonId() {PersonId = UUID.randomUUID().toString();}

    public void setPerson(String personId){PersonId = personId;}

    public String getToken() {
        return authToken;
    }

    public void createToken() {
        authToken = UUID.randomUUID().toString();
    }

    public void setToken(String authToken){
        this.authToken = authToken;
    }
}
