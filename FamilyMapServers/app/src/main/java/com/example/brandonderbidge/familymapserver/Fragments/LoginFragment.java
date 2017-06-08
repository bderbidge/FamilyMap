package com.example.brandonderbidge.familymapserver.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.brandonderbidge.familymapserver.Model;
import com.example.brandonderbidge.familymapserver.R;
import com.example.brandonderbidge.familymapserver.httpclient.Server;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;

import Models.Login;
import Request.RegisterRequest;
import Response.PersonResponse;
import Response.RegisterLoginResponse;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {


    private static String username;

    private static String password;

    private static String serverHost;

    private static int serverPort;

    private Login login;

    private EditText usernameEditText;

    private EditText passwordEditText;

    private EditText serverHostEditText;

    private EditText serverPortEditText;

    private EditText firstNameEditText;

    private EditText lastNameEditText;

    private EditText emailEditText;

    private RadioGroup genderEditButton;

    private Button loginButton;

    private Button registerButton;

    private RegisterRequest register;

    public LoginFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static LoginFragment newInstance(Login login) {

        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(username, login.getusername());
        args.putString(password, login.getPassword());
        args.putString(serverHost, Model.getServerHost());
        args.putInt(String.valueOf(serverPort), Model.getServerPort());
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        login = new Login();
        register = new RegisterRequest();
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (Button) v.findViewById(R.id.btn_login);
        registerButton = (Button) v.findViewById(R.id.btn_register);

       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                loginButtonClick();
           }
       });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                 registerButtonClick();
            }
        });

        usernameEditText = (EditText) v.findViewById(R.id.edit_username);
        passwordEditText = (EditText) v.findViewById(R.id.edit_password);
        serverHostEditText = (EditText) v.findViewById(R.id.edit_host);
        serverPortEditText = (EditText) v.findViewById(R.id.edit_port);
        firstNameEditText = (EditText) v.findViewById(R.id.edit_first_name);
        lastNameEditText = (EditText) v.findViewById(R.id.edit_last_name);
        emailEditText = (EditText) v.findViewById(R.id.edit_email);
        genderEditButton = (RadioGroup) v.findViewById(R.id.radio_gender);



        return v;
    }

    private void loginButtonClick() {

        Model.setLogin(getLogin());

        try {
            URL url = new URL(
                    "http",
                    Model.getServerHost(),
                    Model.getServerPort(),
                    "/user/login"
            );



            LoginTask task = new LoginTask();
            task.execute(url);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    private void registerButtonClick() {

        Model.setLogin(getRegister());


        try {
            URL url = new URL(
                    "http",
                    Model.getServerHost(),
                    Model.getServerPort(),
                    "/user/Register"
            );



            LoginTask task = new LoginTask();
            task.execute(url);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    private Login getLogin(){

        login.setusername(usernameEditText.getText().toString());
        login.setPassword(passwordEditText.getText().toString());
        Model.setServerHost(serverHostEditText.getText().toString());
        Model.setServerPort(Integer.parseInt(serverPortEditText.getText().toString()));

        return login;
    }

    private Login getRegister(){

        login.setusername(usernameEditText.getText().toString());
        login.setPassword(passwordEditText.getText().toString());
        register.setUserName(usernameEditText.getText().toString());
        register.setPassword(passwordEditText.getText().toString());
        register.setFirstName(firstNameEditText.getText().toString());
        register.setlastName(lastNameEditText.getText().toString());
        register.setEmail(emailEditText.getText().toString());

        if(genderEditButton.toString().equals("Male"))
            register.setGender("m");
        else
            register.setGender("f");

        Model.setServerHost(serverHostEditText.getText().toString());
        Model.setServerPort(Integer.parseInt(serverPortEditText.getText().toString()));
        Model.setRegister(register);

        return login;
    }

    public class LoginTask extends AsyncTask<URL, Void, String> {


        protected String doInBackground(URL... urls) {

            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            String response;
            Server server = new Server();

            if(urls[0].equals("http"+ Model.getServerHost() + Model.getServerPort() +
                    "/user/Register")){


                response = server.login(urls[0]);

                if (!response.equals("Unable to Register the User")) {
                    Gson gson = new Gson();
                    RegisterLoginResponse loginResponse;
                    loginResponse = gson.fromJson(response, RegisterLoginResponse.class);

                    Model.getCurrentPerson().setId(loginResponse.getpersonID());
                    Model.setAuthToken(loginResponse.getauthToken());

                }


            }else {



                 response = server.login(urls[0]);

                if (!response.equals("Unable to login the User")) {
                    Gson gson = new Gson();
                    RegisterLoginResponse loginResponse;
                    loginResponse = gson.fromJson(response, RegisterLoginResponse.class);

                    Model.getCurrentPerson().setId(loginResponse.getpersonID());
                    Model.setAuthToken(loginResponse.getauthToken());


                }
            }
            return response;
        }


        protected void onPostExecute(String response) {

            if (!response.equals("Unable to login the User") && !response.equals("Unable to Register the User")) {
                try {
                    URL url = new URL(
                            "http",
                            Model.getServerHost(),
                            Model.getServerPort(),
                            "/person/" + Model.getCurrentPerson().getId()

                    );

                    URL url2 = new URL(
                            "http",
                            Model.getServerHost(),
                            Model.getServerPort(),
                            "/event/"

                    );


                    URL url3 = new URL(
                            "http",
                            Model.getServerHost(),
                            Model.getServerPort(),
                            "/person/"

                    );

                    dataTask task = new dataTask();
                    task.execute(url, url2, url3);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                //display in short period of time
            }else {

                Toast.makeText(getContext(),response,
                        Toast.LENGTH_SHORT).show();
            }


        }
    }

    public class dataTask extends AsyncTask<URL, Void, String> {


        protected String doInBackground(URL... urls) {

            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            Server server = new Server();

            String response = server.getPerson(urls[0]);

            if (!response.equals("Unable to get Person") && !response.equals("Invalid AuthToken")) {
                Gson gson = new Gson();
                PersonResponse personResponse;
                personResponse = gson.fromJson(response, PersonResponse.class);

                Model.getCurrentPerson().setId(personResponse.getpersonID());
                Model.getCurrentPerson().setFirstName(personResponse.getpersonID());
                Model.getCurrentPerson().setLastName(personResponse.getpersonID());
                Model.getCurrentPerson().setGender(personResponse.getpersonID());
                Model.getCurrentPerson().setFatherID(personResponse.getpersonID());
                Model.getCurrentPerson().setMotherID(personResponse.getpersonID());
                Model.getCurrentPerson().setSpouseID(personResponse.getpersonID());
                Model.getCurrentPerson().setUsername(personResponse.getdescendant());

                response = personResponse.getfirstName() + " " + personResponse.getlastName() +
                        " has logged in successfully.";

                String personEventResponse;

                 personEventResponse =  server.getEvents(urls[1]);





                 personEventResponse = server.getPeople(urls[2]);





            }
            return response;
        }


        protected void onPostExecute(String response){

            Toast.makeText(getContext(),response,
                    Toast.LENGTH_SHORT).show();
        }

    }

}
