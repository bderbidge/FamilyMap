package Services;

import java.util.LinkedList;
import java.util.List;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import Models.Person;
import Request.GeneralIDRequest;
import Response.PeopleResponse;
import Response.PersonResponse;

/**
 * Created by brandonderbidge on 5/19/17.
 */

public class PersonService {

    /**
     * Returns ALL family members of the current user. The current user is
     * determined from the provided auth token.
     * @param generalRequest an auth token used to access the database.
     * @return list of Person objects
     */
    public PeopleResponse GetPeople(GeneralIDRequest generalRequest) throws DatabaseException {

        DatabaseDAO db = new DatabaseDAO();


        String id = generalRequest.getRequestID();
        String authToken = generalRequest.getAuthToken();
        Person person = new Person();
        PersonResponse response = new PersonResponse();
        boolean multiplePeople = true;

        boolean success = false;
        List<Person> personList = new LinkedList<>();


            db.openConnection();


           personList = db.getAllPeople(id, authToken, multiplePeople, personList);

            if(personList != null)
                success = true;

            db.closeConnection(success);

        PeopleResponse people = new PeopleResponse();


        for (Person p:personList) {
            PersonResponse temPerson = new PersonResponse();
            temPerson.setdescendant(p.getUsername());
            temPerson.setspouse(p.getSpouseID());
            temPerson.setpersonID(p.getId());
            temPerson.setmother(p.getMotherID());
            temPerson.setfather(p.getFatherID());
            temPerson.setfirstName(p.getFirstName());
            temPerson.setlastName(p.getLastName());
            temPerson.setgender(p.getGender());

            people.getPeople().add(temPerson);
        }


        return people;
    }


    /**
     * Returns the single Person object with the specified ID
     * @param generalRequest is an authtoken that accesses a specific person and returns that person.
     * @return person object
     */
    public PersonResponse Get(GeneralIDRequest generalRequest) throws DatabaseException {

        DatabaseDAO db = new DatabaseDAO();

        String id = generalRequest.getRequestID();
        String authToken = generalRequest.getAuthToken();

        List<Person> person = new LinkedList<>();
        PersonResponse response = new PersonResponse();

        boolean success = true;





            db.openConnection();

            person = db.getPerson(id, authToken);

            if(person.size() != 0) {


                response.setpersonID(person.get(0).getId());
                response.setdescendant(person.get(0).getUsername());
                response.setfirstName(person.get(0).getFirstName());
                response.setlastName(person.get(0).getLastName());
                response.setgender(person.get(0).getGender());
                response.setfather(person.get(0).getFatherID());
                response.setmother(person.get(0).getMotherID());
                response.setspouse(person.get(0).getSpouseID());
            }
            else {
                response = null;
            }

                db.closeConnection(success);





        return response;

    }

}
