package com.example.brandonderbidge.familymapserver;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Models.Event;
import Models.Login;
import Models.Person;
import Request.RegisterRequest;
import Response.EventResponse;
import Response.EventsResponse;
import Response.PeopleResponse;
import Response.PersonResponse;

/**
 * Created by brandonderbidge on 6/5/17.
 */

public class Model {

    public static void init() {

        currentPerson = new Person();
        people = new HashMap<>();
        maternalAncestors = new HashSet<>();
        paternalAncestors = new HashSet<>();
        eventTypes = new HashSet<>();
        events = new HashMap<>();
        authToken = "";
        personToEvents = new HashMap<>();
        connections = new LinkedList<>();
        eventMarkerToEvents = new HashMap<>();
        eventMarkerToPerson = new HashMap<>();
        personToChildren = new HashMap<>();
        eventTypeToColor = new HashMap<>();
        eventTypeMap = new HashMap<>();
        sortedEvents = new HashMap<>();
        setting = new Settings();

    }


    private static Person currentPerson;

    private static String serverHost;

    private static int serverPort;

    private static String authToken;

    public static RegisterRequest register;

    private static Login login;

    private static Map<String, Person> people;

    private static Set<String> maternalAncestors;

    private static Set<String> paternalAncestors;

    private static Set<String> eventTypes;

    private static Map<String, Boolean> eventTypeMap;

    private static Map<String, Float> eventTypeToColor;

    private static Map<String, Event> events;

    private static Map<Marker, Event> eventMarkerToEvents;

    private static Map<Marker, Person> eventMarkerToPerson;

    private static Person focusedPerson;

    private static List<Polyline> connections;

    private static Map<Person, List<Event>> personToEvents;

    private static Map<String, List<Person>> personToChildren;

    private static boolean filters;

    private static Map<String, Event> sortedEvents;

    private static Settings setting;



    public static Set<String> getMaternalAncestors() {
        return maternalAncestors;
    }

    public static Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public static Settings getSetting() {
        return setting;
    }

    public static Map<String, Boolean> getEventTypeMap() {
        return eventTypeMap;
    }

    public static Map<String, Float> getEventTypeToColor() {
        return eventTypeToColor;
    }

    public static Map<String, List<Person>> getPersonToChildren() {
        return personToChildren;
    }

    public static Map<Marker, Person> getEventMarkerToPerson() {
        return eventMarkerToPerson;
    }

    public static List<Polyline> getConnections() {
        return connections;
    }

    public static Map<Person, List<Event>> getPersonToEvents() {
        return personToEvents;
    }

    public static Map<Marker, Event> getEventMarkerToEvents() {
        return eventMarkerToEvents;
    }

    public static RegisterRequest getRegister() {
        return register;
    }

    public static void setRegister(RegisterRequest register) {
        Model.register = register;
    }

    public static String  getServerHost() {
        return serverHost;
    }

    public static void setServerHost(String  serverHost) {
        Model.serverHost = serverHost;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        Model.serverPort = serverPort;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        Model.authToken = authToken;
    }

    public static Login getLogin() {
        return login;
    }

    public static void setLogin(Login login) {
        Model.login = login;
    }

    public static Map<String, Person> getPeople() {
        return people;
    }

    public static Person getCurrentPerson() {
        return currentPerson;
    }

    public static Set<String> getEventTypes() {
        return eventTypes;
    }

    public static Map<String, Event> getEvents() {
        return events;
    }

    public static Person getFocusedPerson() {
        return focusedPerson;
    }

    public static void setFocusedPerson(Person focusedPerson) {
        Model.focusedPerson = focusedPerson;
    }



    public static boolean isFilters() {

        filters = false;

        for( Map.Entry<String, Boolean> entry :eventTypeMap.entrySet()){

            if(entry.getValue() == false)
                filters = true;

        }

        return filters;
    }

    public static void clearPolyline(){

        for (Polyline p: connections) {

            p.remove();

        }

        connections.clear();

    }

    public static void clearModel(){


        people = new HashMap<>();
        maternalAncestors = new HashSet<>();
        paternalAncestors = new HashSet<>();
        eventTypes = new HashSet<>();
        events = new HashMap<>();
        personToEvents = new HashMap<>();
        connections = new LinkedList<>();
        eventMarkerToEvents = new HashMap<>();
        eventMarkerToPerson = new HashMap<>();
        personToChildren = new HashMap<>();
        eventTypeToColor = new HashMap<>();
        eventTypeMap = new HashMap<>();
        sortedEvents = new HashMap<>();
        setting = new Settings();

    }

    public static void logout(){


        authToken = "";
        currentPerson = null;

        clearModel();

    }

    public static Map<String, Event> getSortedList(){


        if(sortedEvents.size() > 1)
            sortedEvents.clear();


        // first check if maternal or paternal are turned on


        if(eventTypeMap.get("Father's Side") && eventTypeMap.get("Mother's Side")){

            checkEvents(events);

        }
        else if(eventTypeMap.get("Father's Side") || eventTypeMap.get("Mother's Side")) {

            Map<String, Event> tempEvents = new HashMap<>();
            if (!eventTypeMap.get("Father's Side")) {


                for (String person : maternalAncestors) {

                    for (Event event : personToEvents.get(people.get(person))) {

                        tempEvents.put(event.getID(), event);

                    }
                }

                checkEvents(tempEvents);

            }


            if (!eventTypeMap.get("Mother's Side")) {


                for (String person : paternalAncestors) {

                    for (Event event : personToEvents.get(people.get(person))){

                        tempEvents.put(event.getID(), event);

                    }

                }

                checkEvents(tempEvents);
            }

        }

        return sortedEvents;

    }

    private static void checkEvents( Map<String, Event> tempEvents ){


        for (Map.Entry<String, Event> entry : tempEvents.entrySet()) {

            if (eventTypeMap.get(entry.getValue().getEventType())) {

                checkGender(entry.getValue().getPersonId(), entry.getValue(), entry.getValue().getID());
            }

        }

    }

    private static void checkGender(String person, Event event, String eventID){


        if( eventTypeMap.get("By Male") || eventTypeMap.get("By Female")){


            //if the Male button is switched but the Female is not then it adds the females
            if (!eventTypeMap.get("By Male") && eventTypeMap.get("By Female") ) {

                if(people.get(person).getGender().equals("f"))
                    sortedEvents.put(eventID, event);

            }
            else if( eventTypeMap.get("By Male") && !eventTypeMap.get("By Female")) {

                if(people.get(person).getGender().equals("m"))
                    sortedEvents.put(eventID, event);

            }else {

                sortedEvents.put(eventID, event);

            }


        }



    }






    public static void setPeople(PeopleResponse people) {

        Map<String, Person> tempPeople = new HashMap<>();

        for (PersonResponse person: people.getPeople()) {

            String id = person.getpersonID();
            Person tempPerson = new Person();

            tempPerson.setUsername(person.getdescendant());
            tempPerson.setSpouseID(person.getspouse());
            tempPerson.setMotherID(person.getmother());
            tempPerson.setFatherID(person.getfather());
            tempPerson.setGender(person.getgender());
            tempPerson.setFirstName(person.getfirstName());
            tempPerson.setLastName(person.getlastName());
            tempPerson.setId(person.getpersonID());


            tempPeople.put(id, tempPerson);

            addChildren(tempPerson);

        }

        Model.people = tempPeople;

        Model.setMaternalAncestors();
        Model.setPaternalAncestors();
        personToEvents();
    }




    private static void personToEvents(){

        for (Map.Entry<String, Person> person: people.entrySet()) {

            List<Event> tempEventList = new LinkedList<>();

            for (Map.Entry<String, Event> tempEvent :events.entrySet()) {

                String id = tempEvent.getValue().getPersonId();
                if(person.getKey().equals(id)){
                    tempEventList.add(tempEvent.getValue());
                }

            }

            Collections.sort(tempEventList);

            for(Event str: tempEventList){
                System.out.println(str);
            }

            personToEvents.put(person.getValue(), tempEventList);


        }


    }




    /**
    * This function recursively determines the ancestors for the side of the
     * family being passed in.
     * @param ancestor is the set of paternal or maternal ancestors being changed.
    * */

    private static Set<String> findAncestors(String id, Set<String> ancestor){



        if(!Model.people.get(id).getFatherID().equals(" ")){

            ancestor.add(id);

            ancestor.add(Model.people.get(id).getFatherID());
            ancestor = findAncestors(Model.people.get(id).getFatherID(), ancestor);
        }


        if(!Model.people.get(id).getMotherID().equals(" ")){

            ancestor.add(Model.people.get(id).getMotherID());
            ancestor = findAncestors(Model.people.get(id).getMotherID(), ancestor);

        }

        return ancestor;
    }

    /**
    * This function determines all the ancestors on the user's mother side
    */

    private static void setMaternalAncestors() {

        Set<String> maternalAncestors = new HashSet<>();
        boolean found = true;
        String motherSideID = Model.getCurrentPerson().getMotherID();

        maternalAncestors.add(Model.getCurrentPerson().getId());

        maternalAncestors = findAncestors(motherSideID,maternalAncestors);

        Model.maternalAncestors = maternalAncestors;
    }


    /**
    * This function determines all the ancestors on the user's father side
    */

    public static void setPaternalAncestors() {

        Set<String> paternalAncestors = new HashSet<>();

        String fatherSideID = Model.getCurrentPerson().getFatherID();
        paternalAncestors.add(Model.getCurrentPerson().getId());

        paternalAncestors = findAncestors(fatherSideID,paternalAncestors);


        Model.paternalAncestors = paternalAncestors;
    }


    /**
     * This function checks if the person's father and mother have already been loaded into the
     * people list. If they have then it assings the tempPerson as a child to the mother and
     * fahter
     *
     * @param tempPerson the child we are trying to find parents for.
     */
    public static void addChildren(Person tempPerson){

        if(!tempPerson.getFatherID().equals(" ")) {
            if (personToChildren.containsKey(tempPerson.getFatherID())){

                personToChildren.get(tempPerson.getFatherID()).add(tempPerson);

            }else{

                List<Person> personList = new LinkedList<>();

                personList.add(tempPerson);
                personToChildren.put(tempPerson.getFatherID(), personList);

            }
        }

        if(!tempPerson.getMotherID().equals(" ")) {

            if (personToChildren.containsKey(tempPerson.getMotherID())) {

                personToChildren.get(tempPerson.getMotherID()).add(tempPerson);

            } else {

                List<Person> personList = new LinkedList<>();
                personList.add(tempPerson);
                personToChildren.put(tempPerson.getMotherID(), personList);
            }
        }


    }


    /**
     * This function adds all the events to a map that will be used later for searching.
     * It also puts all the event types into a set.
     *
     * @param events a map of event ids to each event.
     */

    public static void setEvents(EventsResponse events) {

        Map<String, Event> tempEvents = new HashMap<>();

        for (EventResponse event: events.getevents()) {

            String id = event.geteventID();
            Event tempEvent = new Event();

            tempEvent.setUsername(event.getdescendant());
            tempEvent.setCity(event.getcity());
            tempEvent.setCountry(event.getcountry());
            tempEvent.setID(event.geteventID());
            tempEvent.setEventType(event.geteventType());
            tempEvent.setLatitude(event.getlatitude());
            tempEvent.setLongitude(event.getlongitude());
            tempEvent.setPersonId(event.getPersonID());
            tempEvent.setYear(event.getyear());

            tempEvents.put(id, tempEvent);

            eventTypes.add(event.geteventType());
        }


        if(Model.getEventTypes() != null){

            float num = 5;

            for (String eventType: Model.getEventTypes()) {

                Model.getEventTypeToColor().put(eventType, num);
                Model.getEventTypeMap().put(eventType, true);

                num += 75;
                num %= 360;

            }

            Model.getEventTypeMap().put("Father's Side", true);
            Model.getEventTypeMap().put("Mother's Side", true);
            Model.getEventTypeMap().put("By Male", true);
            Model.getEventTypeMap().put("By Female", true);
        }


        Model.events = tempEvents;


    }


}