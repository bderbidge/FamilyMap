package com.example.brandonderbidge.familymapserver;

import com.example.brandonderbidge.familymapserver.Activities.MainActivity;
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

    }

    private static MainActivity mainActivity;

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

    private static Map<String, Event> events;

    private static Map<Marker, Event> eventMarkerToEvents;

    private static Map<Marker, Person> eventMarkerToPerson;

    private static Person focusedPerson;

    private static List<Polyline> connections;

    private static Map<Person, List<Event>> personToEvents;



    public static Map<Marker, Person> getEventMarkerToPerson() {
        return eventMarkerToPerson;
    }

    public static void setEventMarkerToPerson(Map<Marker, Person> eventMarkerToPerson) {
        Model.eventMarkerToPerson = eventMarkerToPerson;
    }

    public static void clearPolyline(){

        for (Polyline p: connections) {

            p.remove();

        }

        connections.clear();

    }

    public static List<Polyline> getConnections() {
        return connections;
    }

    public static void setConnections(List<Polyline> connections) {
        Model.connections = connections;
    }

    public static Map<Person, List<Event>> getPersonToEvents() {
        return personToEvents;
    }

    public static Map<Marker, Event> getEventMarkerToEvents() {
        return eventMarkerToEvents;
    }

    public static void setEventMarkerToEvents(Map<Marker, Event> eventMarkerToEvents) {
        Model.eventMarkerToEvents = eventMarkerToEvents;
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

        }

        Model.people = tempPeople;

        Model.setMaternalAncestors();
        Model.setPaternalAncestors();
        personToEvents();
    }

    public static Person getCurrentPerson() {
        return currentPerson;
    }

    public static void setCurrentPerson(Person currentPerson) {
        Model.currentPerson = currentPerson;
    }

    public static Set<String> getMaternalAncestors() {
        return maternalAncestors;
    }


    private static void setMaternalAncestors() {

        Set<String> maternalAncestors = new HashSet<>();
        boolean found = true;
        String motherSideID = Model.getCurrentPerson().getMotherID();

       maternalAncestors = findAncestors(motherSideID,maternalAncestors);

        Model.maternalAncestors = maternalAncestors;
    }


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



    public static Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }


    public static void setPaternalAncestors() {

        Set<String> paternalAncestors = new HashSet<>();

        String fatherSideID = Model.getCurrentPerson().getFatherID();

       paternalAncestors = findAncestors(fatherSideID,paternalAncestors);


        Model.paternalAncestors = paternalAncestors;
    }

    public static void setEventTypes(Set<String> eventTypes) {
        Model.eventTypes = eventTypes;
    }

    public static Set<String> getEventTypes() {
        return eventTypes;
    }

    public static Map<String, Event> getEvents() {
        return events;
    }

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

        Model.events = tempEvents;


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



    public static Person getFocusedPerson() {
        return focusedPerson;
    }

    public static void setFocusedPerson(Person focusedPerson) {
        Model.focusedPerson = focusedPerson;
    }

}