package com.example.brandonderbidge.familymapserver;

import com.example.brandonderbidge.familymapserver.Activities.MainActivity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Models.Event;
import Models.Login;
import Models.Person;
import Request.RegisterRequest;

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

    }

    public static RegisterRequest register;

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

    private static String serverHost;

    private static int serverPort;

    private static String authToken;

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        Model.authToken = authToken;
    }

    private static MainActivity mainActivity;

    private static Person currentPerson;

    public static Login getLogin() {
        return login;
    }

    public static void setLogin(Login login) {
        Model.login = login;
    }

    private static Login login;

    private static Map<String, Person> people;

    private static Set<String> maternalAncestors;


    private static Set<String> paternalAncestors;


    private static Set<String> eventTypes;

    private static Map<String, Event> events;


    private static Person focusedPerson;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static Map<String, Person> getPeople() {
        return people;
    }

    public static void setPeople(Map<String, Person> people) {
        Model.people = people;
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

    public static void setMaternalAncestors(Set<String> maternalAncestors) {
        Model.maternalAncestors = maternalAncestors;
    }

    public static Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public static void setPaternalAncestors(Set<String> paternalAncestors) {
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

    public static void setEvents(Map<String, Event> events) {
        Model.events = events;
    }


    public static Person getFocusedPerson() {
        return focusedPerson;
    }

    public static void setFocusedPerson(Person focusedPerson) {
        Model.focusedPerson = focusedPerson;
    }

}