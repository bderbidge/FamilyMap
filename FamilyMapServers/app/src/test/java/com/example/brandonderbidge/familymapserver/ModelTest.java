package com.example.brandonderbidge.familymapserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

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



}