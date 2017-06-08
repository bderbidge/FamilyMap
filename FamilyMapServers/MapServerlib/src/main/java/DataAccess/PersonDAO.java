package DataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Models.Person;
import Models.User;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class PersonDAO {

    private DatabaseDAO db;

    public PersonDAO(DatabaseDAO db) {
        this.db = db;
    }



    /**
     * This function gets both multiple persons or just one depending
     * on the parameter
     * @param multiplePeople if this is true then it will return multiple people
     *                       otherwise it will just return and array containing
     *                       one person
     */
    public List<Person> Get(String id, String username, boolean multiplePeople) throws SQLException {



        List<Person> people = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet keyRS = null;


        String sqlForPerson = "SELECT *" +
                "FROM [person] WHERE person.person_id = ? AND person.username = ?";


        String sqlForPeople = "SELECT *" +
                "FROM [person] WHERE person.username = ?";



        try
        {
            if(multiplePeople == false){

                stmt = db.getConnection().prepareStatement(sqlForPerson);
                stmt.setString(1, id);
                stmt.setString(2, username);
            }else {

                stmt = db.getConnection().prepareStatement(sqlForPeople);
                stmt.setString(1, username);

            }

            keyRS = stmt.executeQuery();


            while (keyRS.next()) {
                Person person = new Person();

                person.setId(keyRS.getString(1));
                person.setUsername(keyRS.getString(2));
                person.setFirstName(keyRS.getString(3));
                person.setLastName(keyRS.getString(4));
                person.setGender(keyRS.getString(5));
                person.setFatherID(keyRS.getString(6));
                person.setMotherID(keyRS.getString(7));
                person.setSpouseID(keyRS.getString(8));

                people.add(person);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Log in Failed");
        }
        finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }

        return people;
    }

    /**
     * Returns all Persons in the database
     * @return AuthToken objects
     */

    /**
     * Creates a new Person in the database
     * @param person object
     * @return boolean
     */
    public boolean Post(Person person) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String sql = "insert into person (person_id, username, firstname, lastname, gender, " +
                "fatherId, motherId, spouseId) " +
                "values ( ?, ?, ?, ?, ?, ?, ?, ?)";

        boolean success = false;
        try {
            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, person.getId());
            stmt.setString(2, person.getUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            if (stmt.executeUpdate() == 1) {
                success = true;

            } else {
                throw new SQLException();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }


        return success;
    }

    public void addFatherAndMother(String currentPersonID, String fatherID, String motherID) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String sql = "UPDATE person SET fatherId = ?, motherId = ? WHERE person_id = ?";

        try {
            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, fatherID);
            stmt.setString(2, motherID);
            stmt.setString(3, currentPersonID);


            if (stmt.executeUpdate() != 1) {
                throw new SQLException();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }


    }

    public void addSpouse(String spouse1, String spouse2) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String sql = "UPDATE person SET spouseId = ? WHERE person_id = ?";

        try {
            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, spouse1);
            stmt.setString(2, spouse2);


            if (stmt.executeUpdate() != 1) {
                throw new SQLException();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }

    }

    public void Delete(String username) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String sql = "DELETE FROM person WHERE username = ?";

        try {

            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }


    }



}
