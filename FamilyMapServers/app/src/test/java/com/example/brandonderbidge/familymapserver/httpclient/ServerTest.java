package com.example.brandonderbidge.familymapserver.httpclient;

import com.example.brandonderbidge.familymapserver.Model;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import Models.Login;
import Response.PersonResponse;
import Response.RegisterLoginResponse;

import static org.junit.Assert.assertTrue;

/**
 * Created by brandonderbidge on 6/19/17.
 */
public class ServerTest {


    @Before
    public void setUp() {

        Model.init();

        try {
            Model.setServerHost(InetAddress.getLocalHost().getHostAddress());
            Model.setServerPort(8080);
            Login login = new Login();
            login.setusername("brander44");
            login.setPassword("password");
            Model.setLogin(login);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void register() throws Exception {

        try {
            URL url = new URL(
                    "http",
                    Model.getServerHost(),
                    Model.getServerPort(),
                    "/user/register"
            );

            Server server = new Server();

            String response = server.register(url);

            Gson gson = new Gson();
            RegisterLoginResponse loginResponse;
            loginResponse = gson.fromJson(response, RegisterLoginResponse.class);

            Model.getCurrentPerson().setId(loginResponse.getpersonID());
            Model.setAuthToken(loginResponse.getauthToken());
            assertTrue(Model.getLogin().getusername().equals(loginResponse.getusername()));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




    }

    @Test
    public void login() throws Exception {


        try {
            URL url = new URL(
                    "http",
                    Model.getServerHost(),
                    Model.getServerPort(),
                    "/user/login"
            );

            Server server = new Server();

            String response = server.login(url);

            Gson gson = new Gson();
            RegisterLoginResponse loginResponse;
            loginResponse = gson.fromJson(response, RegisterLoginResponse.class);

            assertTrue(loginResponse.getusername().equals("brander"));



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPerson(){


        try {
            URL url = new URL(
                    "http",
                    Model.getServerHost(),
                    Model.getServerPort(),
                    "/person/" + Model.getCurrentPerson().getId()



            );

            Server server = new Server();

            String response = server.getPerson(url);

            if (!response.equals("Unable to get PersonActivity") && !response.equals("Invalid AuthToken")) {

                Gson gson = new Gson();
                PersonResponse personResponse;
                personResponse = gson.fromJson(response, PersonResponse.class);

                Model.getCurrentPerson().setId(personResponse.getpersonID());
                Model.getCurrentPerson().setFirstName(personResponse.getfirstName());
                Model.getCurrentPerson().setLastName(personResponse.getlastName());
                Model.getCurrentPerson().setGender(personResponse.getgender());
                Model.getCurrentPerson().setFatherID(personResponse.getfather());
                Model.getCurrentPerson().setMotherID(personResponse.getmother());
                Model.getCurrentPerson().setSpouseID(personResponse.getspouse());
                Model.getCurrentPerson().setUsername(personResponse.getdescendant());
            }

            } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getPeople() throws Exception {

    }

    @Test
    public void getEvents() throws Exception {

    }

}