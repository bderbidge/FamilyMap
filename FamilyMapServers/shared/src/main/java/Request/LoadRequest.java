package Request;

import java.util.LinkedList;
import java.util.List;

import Response.EventResponse;
import Response.PersonResponse;

/**
 * Created by brandonderbidge on 5/20/17.
 */

public class LoadRequest {

    /**
     * This Request holds converted Json arrays that will be loaded into the database.
     */

    private List<LoadUserRequest> users = new LinkedList<>();
    private List<PersonResponse> persons = new LinkedList<>();
    private List<LoadEventRequest> events = new LinkedList<>();

    public List<LoadUserRequest> getUsers() {
        return users;
    }

    public void setUsers(List<LoadUserRequest> users) {
        this.users = users;
    }

    public List<PersonResponse> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonResponse> persons) {
        this.persons = persons;
    }

    public List<LoadEventRequest> getEvents() {
        return events;
    }

    public void setEvents(List<LoadEventRequest> events) {
        this.events = events;
    }
}
