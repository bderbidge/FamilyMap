package Response;

/**
 * Created by brandonderbidge on 5/20/17.
 */

public class PersonResponse {

    /**
     * This returns a Person object that will be converted into json
     */

    private String descendant;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;


    public String getdescendant() {
        return descendant;
    }

    public void setdescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getpersonID() {
        return personID;
    }

    public void setpersonID(String personID) {
        this.personID = personID;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }

    public String getfather() {
        return father;
    }

    public void setfather(String father) {
        this.father = father;
    }

    public String getmother() {
        return mother;
    }

    public void setmother(String mother) {
        this.mother = mother;
    }

    public String getspouse() {
        return spouse;
    }

    public void setspouse(String spouse) {
        this.spouse = spouse;
    }
}
