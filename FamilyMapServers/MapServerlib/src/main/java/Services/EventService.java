package Services;

import java.util.LinkedList;
import java.util.List;

import DataAccess.DatabaseDAO;
import DataAccess.DatabaseException;
import Models.Event;
import Request.GeneralIDRequest;
import Response.EventResponse;
import Response.EventsResponse;

/**
 * Created by brandonderbidge on 5/19/17.
 */

public class EventService {

    /**
     * Returns ALL events for ALL family members of the current user. The current
     * user is determined from the provided auth token.
     * @param request unique token that accesses and returns events
     * @return List of Events
     */
    public EventsResponse GetEvents(GeneralIDRequest request) throws DatabaseException {
        DatabaseDAO db = new DatabaseDAO();


        String id = request.getRequestID();
        String authToken = request.getAuthToken();
        Event event = new Event();
        EventResponse response = new EventResponse();
        boolean multiplePeople = true;

        boolean success = false;
        List<Event> eventsList = new LinkedList<>();


            db.openConnection();


           eventsList = db.getAllEvents(id, authToken, multiplePeople, eventsList);

            if(eventsList != null)
                success = true;

            db.closeConnection(success);


        EventsResponse eventsResponse = new EventsResponse();


        for (Event e:eventsList) {
            EventResponse temEvent = new EventResponse();
            temEvent.setdescendant(e.getUsername());
            temEvent.setcity(e.getCity());
            temEvent.seteventID(e.getID());
            temEvent.setcountry(e.getCountry());
            temEvent.seteventType(e.getEventType());
            temEvent.setlatitude(e.getLatitude());
            temEvent.setlongitude(e.getLongitude());
            temEvent.setyear(e.getYear());
            temEvent.setPersonID(e.getPersonId());


            eventsResponse.getevents().add(temEvent);
        }


        return eventsResponse;
    }

    /**
     * Returns the single Event object with the specified ID
     * @param  request is the id of the event being accessed.
     * @return the event object being accessed
     */
    public EventResponse Get(GeneralIDRequest request) throws DatabaseException {




        DatabaseDAO db = new DatabaseDAO();

        String id = request.getRequestID();
        String authToken = request.getAuthToken();

        List<Event> events = new LinkedList<>();
        EventResponse response = new EventResponse();

        boolean success = true;



            db.openConnection();

            events = db.getEvent(id, authToken);

            if(events.size() != 0) {


                response.seteventID(events.get(0).getID());
                response.setdescendant(events.get(0).getUsername());
                response.setyear(events.get(0).getYear());
                response.setcountry(events.get(0).getCountry());
                response.setcity(events.get(0).getCity());
                response.seteventType(events.get(0).getEventType());
                response.setlatitude(events.get(0).getLatitude());
                response.setlongitude(events.get(0).getLongitude());
                response.setPersonID(events.get(0).getPersonId());
            }
            else {
                response = null;
            }

            db.closeConnection(success);





        return response;
    }


}
