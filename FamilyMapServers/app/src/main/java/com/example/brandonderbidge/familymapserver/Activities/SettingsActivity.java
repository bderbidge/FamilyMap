package com.example.brandonderbidge.familymapserver.Activities;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.brandonderbidge.familymapserver.Model;
import com.example.brandonderbidge.familymapserver.R;
import com.example.brandonderbidge.familymapserver.httpclient.Server;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import Response.EventsResponse;
import Response.PeopleResponse;
import Response.PersonResponse;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerGoogle;
    Spinner spinnerlife;
    Spinner spinnerfamily;
    Spinner spinnerspouse;
    Switch lifeSwitch;
    Switch familySwitch;
    Switch spouseSwitch;
    TextView reSync;
    TextView logout;
    Callback callback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        lifeSwitch = (Switch) findViewById( R.id.life_story_switch);
        familySwitch = (Switch) findViewById(R.id.family_switch);
        spouseSwitch = (Switch) findViewById(R.id.spouse_switch);
        reSync = (TextView) findViewById(R.id.ReSync);
        logout = (TextView) findViewById(R.id.logout);

        lifeSwitch.setChecked(Model.getSetting().isLifeStoryBool());
        familySwitch.setChecked(Model.getSetting().isFamilyTreeBool());
        spouseSwitch.setChecked(Model.getSetting().isSpouseBool());

         spinnerGoogle = (Spinner) findViewById(R.id.google_spinner);
         spinnerGoogle.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> googleAdapter = ArrayAdapter.createFromResource(this,
                R.array.google_map_items, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        googleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerGoogle.setAdapter(googleAdapter);


        spinnerGoogle.setSelection(Model.getSetting().getMapPosition());


        spinnerlife = (Spinner) findViewById(R.id.life_story_spinner);
        spinnerlife.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> lifeAdapter = ArrayAdapter.createFromResource(this,
                R.array.life_items, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        lifeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerlife.setAdapter(lifeAdapter);


        spinnerlife.setSelection(Model.getSetting().getLifeStoryPosition());


        spinnerfamily = (Spinner) findViewById(R.id.family_spinner);
        spinnerfamily.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> familyAdapter = ArrayAdapter.createFromResource(this,
                R.array.family_items, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        familyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerfamily.setAdapter(familyAdapter);


        spinnerfamily.setSelection(Model.getSetting().getFamilyPosition());


        spinnerspouse = (Spinner) findViewById(R.id.spouse_spinner);
        spinnerspouse.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spouseAdapter = ArrayAdapter.createFromResource(this,
                R.array.spouse_items, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        spouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerspouse.setAdapter(spouseAdapter);


        spinnerspouse.setSelection(Model.getSetting().getSpousePosition());


        lifeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Model.getSetting().setLifeStoryBool(isChecked);
            }
        });

        spouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Model.getSetting().setSpouseBool(isChecked);
            }
        });

        familySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Model.getSetting().setFamilyTreeBool(isChecked);
            }
        });

        reSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Model.clearModel();
            try{

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

        }
        });




    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()){

            case R.id.google_spinner:

               for(Map.Entry<String, Boolean> entry: Model.getSetting().getGoogleMapType()
                       .entrySet()){

                   entry.setValue(false);
               }

                Model.getSetting().getGoogleMapType()
                        .put(parent.getSelectedItem().toString(), true);
                Model.getSetting().setMapPosition(position);

            break;

            case R.id.family_spinner:

                for(Map.Entry<Integer, Boolean> entry: Model.getSetting().getFamilyTree()
                        .entrySet()){

                    entry.setValue(false);
                }

                if(parent.getSelectedItem().toString().equals("Green"))
                    Model.getSetting().getFamilyTree().put(Color.GREEN, true);
                else if(parent.getSelectedItem().toString().equals("Blue"))
                    Model.getSetting().getFamilyTree().put(Color.BLUE, true);
                else
                    Model.getSetting().getFamilyTree().put(Color.MAGENTA, true);

                Model.getSetting().setFamilyPosition(position);

                break;

            case R.id.life_story_spinner:

                for(Map.Entry<Integer, Boolean> entry: Model.getSetting().getLifeStory()
                        .entrySet()){

                    entry.setValue(false);
                }

                if(parent.getSelectedItem().toString().equals("Green"))
                    Model.getSetting().getLifeStory().put(Color.GREEN, true);
                else if(parent.getSelectedItem().toString().equals("Blue"))
                    Model.getSetting().getLifeStory().put(Color.BLUE, true);
                else
                    Model.getSetting().getLifeStory().put(Color.MAGENTA, true);

                Model.getSetting().setLifeStoryPosition(position);

                break;

            case R.id.spouse_spinner:

                for(Map.Entry<Integer, Boolean> entry: Model.getSetting().getSpouse()
                        .entrySet()){

                    entry.setValue(false);
                }

                if(parent.getSelectedItem().toString().equals("Green"))
                    Model.getSetting().getSpouse().put(Color.GREEN, true);
                else if(parent.getSelectedItem().toString().equals("Blue"))
                    Model.getSetting().getSpouse().put(Color.BLUE, true);
                else
                    Model.getSetting().getSpouse().put(Color.MAGENTA, true);

                Model.getSetting().setSpousePosition(position);

                break;

        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface Callback{

    }

    public class dataTask extends AsyncTask<URL, Void, String> {


        protected String doInBackground(URL... urls) {

            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            Server server = new Server();

            String response = server.getPerson(urls[0]);

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

                response = personResponse.getfirstName() + " " + personResponse.getlastName() +
                        " has logged in successfully.";

                String personEventResponse;

                personEventResponse =  server.getEvents(urls[1]);

                EventsResponse eventsResponse = gson.fromJson(personEventResponse,EventsResponse.class);

                Model.setEvents(eventsResponse);

                personEventResponse = server.getPeople(urls[2]);

                PeopleResponse peopleResponse = gson.fromJson(personEventResponse,PeopleResponse.class);

                Model.setPeople(peopleResponse);




            }
            return response;
        }


        protected void onPostExecute(String response){

        }

    }
}
