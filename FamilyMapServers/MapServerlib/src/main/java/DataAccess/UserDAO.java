package DataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.User;

/**
 * Created by brandonderbidge on 5/18/17.
 */

public class UserDAO {


    private static long authTokenExpire;
    private DatabaseDAO db;

    public static void SetAuthTokenExpire(long authTokenTimeout){
        authTokenExpire = authTokenTimeout * 1000;
    }

    public UserDAO(DatabaseDAO db) {
        this.db = db;
    }


    /**
     * Returns a specific AuthToken
     * @param username
     * @return User object
     */
    public User Get(String username) throws SQLException {

        User user = new User();
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String sqlForPerson = "SELECT person_id, firstname, lastname, gender FROM [user] WHERE username = ?";

        try {
            stmt = db.getConnection().prepareStatement(sqlForPerson);

            stmt.setString(1, username);

            keyRS = stmt.executeQuery();

            while (keyRS.next()) {


                user.setPerson(keyRS.getString(1));
                user.setFirstName(keyRS.getString(2));
                user.setLastName(keyRS.getString(3));
                user.setGender(keyRS.getString(4));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }
        return user;

    }

    public User loginUser(String username, String password) throws SQLException {

        User logedInUser = new User();
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String sql = " SELECT authtoken, person_id FROM [user] Where username = ? AND password = ?";
        try
        {

            logedInUser.setUsername(username);
            updateAuthToken(logedInUser);

            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            keyRS = stmt.executeQuery();

            if(keyRS != null)
            {

                logedInUser.setToken(keyRS.getString(1));
                logedInUser.setPerson(keyRS.getString(2));

            }
            else
            {
                throw new SQLException();
            }

        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Log in Failed");
            logedInUser = null;
        }
        finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }


        return logedInUser;
    }

    /**
     * Creates a new User in the database
     * @param user object
     * @return boolean
     */

    public boolean registerUser(User user) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String sql = "insert into user (username, password, email, firstName, "
                + "lastName, person_id, gender) values (?, ?, ?, ?, ?, ?, ?)";

        boolean success = false;
        try
        {
            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getPersonId());
            stmt.setString(7, user.getGender());

            if(stmt.executeUpdate() == 1)
            {
                success = true;
               updateAuthToken(user);
            }
            else
            {
                throw new SQLException();
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            success = false;
        }
        finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }

        return success;


    }


    public void updateAuthToken(User user) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        long time = System.currentTimeMillis();
        user.createToken();

        try
        {
            String sql = " UPDATE user SET authtoken = ?, date = ? WHERE username = ?";
            stmt = db.getConnection().prepareStatement(sql);

            stmt.setString(1, user.getToken());
            stmt.setLong(2, time);
            stmt.setString(3, user.getUsername());
            stmt.executeUpdate();
        }
        finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }

    }

    public boolean checkToken(String authToken) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        long currentTime = System.currentTimeMillis();
        boolean success = false;

        String sql = "SELECT authtoken, date FROM [user] WHERE authtoken = ? ";
        String auth = " ";
        long previoustime = 0;

        try {

            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, authToken);
            keyRS = stmt.executeQuery();
            auth = keyRS.getString(1);
            previoustime = keyRS.getLong(2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }


        if(currentTime - previoustime > authTokenExpire)
           success = deleteAuthToken(auth);



        return success;
    }

    public boolean deleteAuthToken(String auth) throws SQLException {

        String sql = "UPDATE user SET authtoken = NULL, date = NULL  WHERE authtoken = ? ";
        PreparedStatement stmt = null;
        ResultSet keyRS = null;


        boolean success = false;

        try {

            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, auth);
            stmt.executeUpdate();
            success = true;

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }
        return success;


    }

    public String getUsername(String authToken) throws SQLException {

        String sqlForUserName = "Select user.username FROM [user] WHERE user.authToken = ?";

        PreparedStatement stmt = null;
        ResultSet keyRS = null;

        String username = "";

        try {

            stmt = db.getConnection().prepareStatement(sqlForUserName);
            stmt.setString(1, authToken);
            keyRS = stmt.executeQuery();
            username = keyRS.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }


        return username;

    }

    public String getPersonID(String username) throws SQLException {

        String sqlForUserName = "Select person_id FROM [user] WHERE username = ?";

        PreparedStatement stmt = null;
        ResultSet keyRS = null;

        String personID = "";

        try {

            stmt = db.getConnection().prepareStatement(sqlForUserName);
            stmt.setString(1, username);
            keyRS = stmt.executeQuery();
            personID = keyRS.getString(1);

        }
        finally
        {
            if(stmt != null)
                stmt.close();
            if (keyRS != null)
                keyRS.close();
        }


        return personID;

    }


}
