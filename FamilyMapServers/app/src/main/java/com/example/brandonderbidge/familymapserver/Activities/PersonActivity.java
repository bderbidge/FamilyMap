package com.example.brandonderbidge.familymapserver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.brandonderbidge.familymapserver.Model;
import com.example.brandonderbidge.familymapserver.R;

import java.util.LinkedList;
import java.util.List;

import Models.Event;
import Models.Person;

public class PersonActivity extends AppCompatActivity {


    RecyclerView eventList;
    RecyclerView personList;
    String currentID;
    TextView fname;
    TextView lname;
    TextView gender;
    ImageButton eventsSpinner;
    ImageButton familySpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        currentID = getIntent().getExtras().getString("PersonID");

        eventList = (RecyclerView) findViewById(R.id.recycle_event);
        personList = (RecyclerView) findViewById(R.id.recycle_person);

        eventsSpinner = (ImageButton) findViewById(R.id.eventButtonDrop);
        familySpinner = (ImageButton) findViewById(R.id.familyButtonDrop);



        Person person = Model.getPeople().get(currentID);

        fname = (TextView) findViewById(R.id.person_act_fname);
        lname = (TextView) findViewById(R.id.person_act_lname);
        gender = (TextView) findViewById(R.id.person_act_gender);

        fname.setText(person.getFirstName());
        lname.setText(person.getLastName());

        if(person.getGender().equals("m"))
            gender.setText("Male");
        else
            gender.setText("Female");



        eventsSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SetEventRecycler();

            }
        });


        familySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFamilyRecycler();
            }
        });
    }

    private void setFamilyRecycler(){


        if(personList.getAdapter() != null && personList.getAdapter().getItemCount() > 0 ){

            List<Person> tempPeople = new LinkedList<>();
            tempPeople.clear();

            personList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            PersonAdapt personAdapt = new PersonAdapt(tempPeople);

            personList.setAdapter(personAdapt);


        }else {

            Person person = Model.getPeople().get(currentID);

            List<Person> tempPeople = new LinkedList<>();

            if (Model.getPersonToChildren().get(currentID) != null) {
                for (Person tempPerson : Model.getPersonToChildren().get(currentID)) {

                    tempPeople.add(tempPerson);

                }
            }


            Person father = Model.getPeople().get(person.getFatherID());
            Person mother = Model.getPeople().get(person.getMotherID());

            if (mother != null)
                tempPeople.add(mother);

            if (father != null)
                tempPeople.add(father);


            personList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            PersonAdapt personAdapt = new PersonAdapt(tempPeople);

            personList.setAdapter(personAdapt);

        }
    }

    private void SetEventRecycler(){

        if(eventList.getAdapter() != null && eventList.getAdapter().getItemCount() > 0 ){


            List<Event> eventList1 = new LinkedList<>();
            eventList1.clear();
            eventList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            EventAdapt eventAdapt = new EventAdapt(eventList1);
            eventList.setAdapter(eventAdapt);


        }else {


            Person person = Model.getPeople().get(currentID);

            List<Event> eventList1 = Model.getPersonToEvents().get(person);
            eventList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            EventAdapt eventAdapt = new EventAdapt(eventList1);
            eventList.setAdapter(eventAdapt);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       boolean bool = super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.up_bttn:      Intent intent = new Intent( this , MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    break;

        }

        return bool;


    }

    private class EventAdapt extends RecyclerView.Adapter<EventHolder>
    {
        private List<Event> mevents;

        public EventAdapt(List<Event> events) {

            mevents = events;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_layout_item, parent, false);
            return new EventHolder(itemView);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {


            holder.bind(mevents.get(position));
        }

        @Override
        public int getItemCount() {
            return mevents.size();
        }



    }

    private class EventHolder extends RecyclerView.ViewHolder{

        private TextView eventText;
        private RelativeLayout event;
        private Event eventFocus;

        public EventHolder(View itemView) {
            super(itemView);

            eventText = (TextView) itemView.findViewById(R.id.item_description);
            event = (RelativeLayout) itemView.findViewById(R.id.event_item);

            event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    newMapActivity(v);
                }
            });
        }


        public void bind(Event e){

            eventFocus = e;
            String text = e.getEventType() + ": " + e.getCity() + ", " + e.getCountry()
                    + "(" + e.getYear() + ")";

            eventText.setText(text);

        }

        private void newMapActivity( View v){


            Intent intent = new Intent( v.getContext() ,MapActivity.class);
            intent.putExtra("eventID", eventFocus.getID());
            startActivity(intent);


        }
    }

    private class PersonAdapt extends RecyclerView.Adapter<PersonHolder>
    {
        private List<Person> people;


        public PersonAdapt(List<Person> people) {
            this.people = people;
        }

        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.person_layout_item, parent, false);


            return new PersonHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {

            holder.bind(people.get(position));

        }
        @Override
        public int getItemCount() {
            return people.size();
        }


    }

    private class PersonHolder extends RecyclerView.ViewHolder{

        private TextView personName;
        private ImageView gender;
        private TextView family;
        private RelativeLayout person;
        private String ID;

        public PersonHolder(View itemView) {

            super(itemView);

            personName = (TextView) itemView.findViewById(R.id.person_frag_name);
            gender = (ImageView) itemView.findViewById(R.id.event_frag_loc);
            family = (TextView) itemView.findViewById(R.id.person_frag_family);
            person =(RelativeLayout) itemView.findViewById(R.id.frag_person);

            person.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    newPersonActivity(ID, v);
                }
            });
        }

        private void bind(Person p){



            ID = p.getId();

            String name = p.getFirstName() + " " +p.getLastName();

            personName.setText(name);
            if(p.getGender().equals("m"))
                gender.setImageResource(R.mipmap.male_icon);
            else
                gender.setImageResource(R.mipmap.gender_female);


            if(Model.getPeople().get(currentID).getFatherID().equals(p.getId()))
                family.setText("Father");
            else if(Model.getPeople().get(currentID).getMotherID().equals(p.getId()))
                family.setText("Mother");
            else
                family.setText("Child");
        }


        private void newPersonActivity(String ID, View v){

            Intent intent = new Intent( v.getContext() ,PersonActivity.class);
            intent.putExtra("PersonID", ID);
            startActivity(intent);


        }
    }

}
