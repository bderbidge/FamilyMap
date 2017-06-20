package com.example.brandonderbidge.familymapserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import Response.EventResponse;
import Response.EventsResponse;
import Response.PeopleResponse;
import Response.PersonResponse;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by brandonderbidge on 6/19/17.
 */
public class ModelTest {

    Model model;
    @Before
    public void setUp() {

        Model.init();



        EventResponse event = new EventResponse();

        EventsResponse events = new EventsResponse();

        List<EventResponse> eventList = new LinkedList<>();

        event.setlongitude(22);
        event.setlatitude(33);
        event.setcountry("USA");
        event.seteventType("Birth");
        event.seteventID("1");
        event.setdescendant("derb");
        event.setPersonID("1");
        event.setcity("China");
        event.setyear(1993);

        eventList.add(event);

        EventResponse event1 = new EventResponse();

        event1.setlongitude(22);
        event1.setlatitude(33);
        event1.setcountry("USA");
        event1.seteventType("Baptism");
        event1.seteventID("2");
        event1.setdescendant("derb");
        event1.setPersonID("1");
        event1.setcity("China");
        event1.setyear(1995);

        eventList.add(event1);

        EventResponse event2 = new EventResponse();

        event2.setlongitude(22);
        event2.setlatitude(33);
        event2.setcountry("USA");
        event2.seteventType("Mission");
        event2.seteventID("3");
        event2.setdescendant("derb");
        event2.setPersonID("1");
        event2.setcity("China");
        event2.setyear(1997);

        eventList.add(event2);

        EventResponse event3 = new EventResponse();

        event3.setlongitude(22);
        event3.setlatitude(33);
        event3.setcountry("USA");
        event3.seteventType("Marriage");
        event3.seteventID("4");
        event3.setdescendant("derb");
        event3.setPersonID("1");
        event3.setcity("China");
        event3.setyear(1999);

        eventList.add(event3);

        EventResponse event4 = new EventResponse();

        event4.setlongitude(22);
        event4.setlatitude(33);
        event4.setcountry("USA");
        event4.seteventType("job");
        event4.seteventID("5");
        event4.setdescendant("derb");
        event4.setPersonID("1");
        event4.setcity("China");
        event4.setyear(2001);

        eventList.add(event4);

        EventResponse event5 = new EventResponse();

        event5.setlongitude(22);
        event5.setlatitude(33);
        event5.setcountry("USA");
        event5.seteventType("death");
        event5.seteventID("6");
        event5.setdescendant("derb");
        event5.setPersonID("1");
        event5.setcity("China");
        event5.setyear(2003);

        eventList.add(event5);

        events.setevents(eventList);

        Model.setEvents(events);



        List<PersonResponse> personList = new LinkedList<>();

        PersonResponse person = new PersonResponse();
        person.setfirstName("Brandon");
        person.setlastName("Derbidge");
        person.setspouse("2");
        person.setpersonID("1");
        person.setgender("m");
        person.setfather("3");
        person.setmother("4");

        personList.add(person);


        Model.getCurrentPerson().setId(person.getpersonID());
        Model.getCurrentPerson().setFirstName(person.getfirstName());
        Model.getCurrentPerson().setLastName(person.getlastName());
        Model.getCurrentPerson().setGender(person.getgender());
        Model.getCurrentPerson().setFatherID(person.getfather());
        Model.getCurrentPerson().setMotherID(person.getmother());
        Model.getCurrentPerson().setSpouseID(person.getspouse());
        Model.getCurrentPerson().setUsername(person.getdescendant());


        PersonResponse person2 = new PersonResponse();
        person2.setfirstName("d");
        person2.setlastName("Derbidge");
        person2.setspouse("4");
        person2.setpersonID("3");
        person2.setgender("m");
        person2.setfather("7");
        person2.setmother("8");

        personList.add(person2);

        PersonResponse person4 = new PersonResponse();
        person4.setfirstName("lilly");
        person4.setlastName("Doe");
        person4.setspouse("5");
        person4.setpersonID("4");
        person4.setgender("f");
        person4.setfather("9");
        person4.setmother("10");

        personList.add(person4);

        PersonResponse person5 = new PersonResponse();
        person5.setfirstName("a");
        person5.setlastName("Derbidge");
        person5.setspouse("8");
        person5.setpersonID("7");
        person5.setgender("m");
        person5.setfather(" ");
        person5.setmother(" ");


        personList.add(person5);

        PersonResponse person6 = new PersonResponse();
        person6.setfirstName("c");
        person6.setlastName("Doe");
        person6.setspouse("7");
        person6.setpersonID("8");
        person6.setgender("f");
        person6.setfather(" ");
        person6.setmother(" ");


        personList.add(person6);

        PersonResponse person7 = new PersonResponse();
        person7.setfirstName("h");
        person7.setlastName("Derbidge");
        person7.setspouse("10");
        person7.setpersonID("9");
        person7.setgender("m");
        person7.setfather(" ");
        person7.setmother(" ");


        personList.add(person7);

        PersonResponse person8 = new PersonResponse();
        person8.setfirstName("x");
        person8.setlastName("Doe");
        person8.setspouse("9");
        person8.setpersonID("10");
        person8.setgender("f");
        person8.setfather(" ");
        person8.setmother(" ");


        personList.add(person8);



        PeopleResponse people = new PeopleResponse();
        people.setPeople(personList);

        Model.setPeople(people);


    }

    @After
    public void tearDown() {

    }


    //If the Model contains these ids then it is determining the paternal side correctly
    //This Test also shows that I can test
    @Test
    public void checkPaternalChildrenAndMaternal(){


        assertTrue(Model.getPaternalAncestors().contains("3"));
        assertTrue(Model.getPaternalAncestors().contains("7"));
        assertTrue(Model.getPaternalAncestors().contains("8"));


        assertTrue(Model.getMaternalAncestors().contains("4"));
        assertTrue(Model.getMaternalAncestors().contains("9"));
        assertTrue(Model.getMaternalAncestors().contains("10"));


        assertFalse(Model.getPaternalAncestors().contains("4"));
        assertFalse(Model.getPaternalAncestors().contains("9"));
        assertFalse(Model.getPaternalAncestors().contains("10"));


        assertFalse(Model.getMaternalAncestors().contains("3"));
        assertFalse(Model.getMaternalAncestors().contains("7"));
        assertFalse(Model.getMaternalAncestors().contains("8"));

        assertTrue(Model.getPersonToChildren().get("7").get(0).getId().equals("3"));
        assertFalse(Model.getPersonToChildren().get("7").get(0).getId().equals("8"));

        assertTrue(Model.getPeople().get("4").getSpouseID().equals("5"));

    }



    //This tests event searching and the filter settings.
    //It also shows that my set events function called in the setup function
    //orders the event based on date.
    @Test
    public void filterAndSortEvents(){


        assertTrue(Model.getPersonToEvents().get(Model.getPeople().get("1")).get(0)
                .getEventType().equals("Birth"));
        assertTrue(Model.getPersonToEvents().get(Model.getPeople().get("1")).get(5)
                .getEventType().equals("death"));
        assertTrue(Model.getPersonToEvents().get(Model.getPeople().get("1")).get(3)
                .getEventType().equals("Marriage"));
        assertFalse(Model.getPersonToEvents().get(Model.getPeople().get("1")).get(2)
                .getEventType().equals("Birth"));
        assertFalse(Model.getPersonToEvents().get(Model.getPeople().get("1")).get(4)
                .getEventType().equals("death"));

        Model.getEventTypeMap().put("Baptism", false);
        Model.getEventTypeMap().put("Birth", false);
        Model.getEventTypeMap().put("death", false);

        Model.getSortedList();

        assertFalse(Model.getSortedList().containsKey("1"));
        assertFalse(Model.getSortedList().containsKey("2"));
        assertFalse(Model.getSortedList().containsKey("6"));

    }


}