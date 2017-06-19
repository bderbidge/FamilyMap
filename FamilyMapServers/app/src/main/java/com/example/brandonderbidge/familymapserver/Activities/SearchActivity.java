package com.example.brandonderbidge.familymapserver.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.brandonderbidge.familymapserver.Model;
import com.example.brandonderbidge.familymapserver.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Models.Event;
import Models.Person;
import Models.PersonOrEvent;

public class SearchActivity extends AppCompatActivity {

    Button search_bttn;
    EditText search_text;
    List<PersonOrEvent> items;
    List<PersonOrEvent> tempItems;
    RecyclerView search_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_bttn = (Button) findViewById( R.id.search_the_things_btn);
        search_text = (EditText) findViewById(R.id.search_edit_text);
        search_recycler = (RecyclerView) findViewById(R.id.search_recycler);
        search_recycler.setLayoutManager(new LinearLayoutManager(this));
        items = new LinkedList<>();
        tempItems = new LinkedList<>();

        for(Map.Entry<String, Event> entry: Model.getEvents().entrySet()){

            PersonOrEvent personOrEvent = new PersonOrEvent(entry.getValue().getID(),
                    entry.getValue().toString(), "Event");

            items.add( personOrEvent);
        }

        for(Map.Entry<String, Person> entry: Model.getPeople().entrySet()){

            PersonOrEvent personOrEvent = new PersonOrEvent(entry.getValue().getId(),
                    entry.getValue().toString(), "Person");

            items.add( personOrEvent);

        }



        search_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPeopleAndEvents();
            }
        });

    }

    private void searchPeopleAndEvents(){

        tempItems.clear();
        for(PersonOrEvent p : items){

            String text = search_text.getText().toString();

            if(p.getText().contains(text)) {
                tempItems.add(p);
            }

        }

        SearchAdapt searchAdapt = new SearchAdapt(tempItems);
        search_recycler.setAdapter(searchAdapt);

    }


    private class SearchAdapt extends RecyclerView.Adapter<SearchHolder>
    {
        private List<PersonOrEvent> peopleAndEvents;


        public SearchAdapt(List<PersonOrEvent> people) { this.peopleAndEvents = people; }

        @Override
        public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_layout_item, parent, false);


            return new SearchHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SearchHolder holder, int position) {
            holder.bind(peopleAndEvents.get(position));
        }

        @Override
        public int getItemCount() {
            return peopleAndEvents.size();
        }


    }

    private class SearchHolder extends RecyclerView.ViewHolder{


        private RelativeLayout personOrEvent;
        private TextView eventOrPersonName;
        private String ID;
        private String type;

        public SearchHolder(View itemView) {

            super(itemView);

            eventOrPersonName = (TextView) itemView.findViewById(R.id.search_description);

            personOrEvent =(RelativeLayout) itemView.findViewById(R.id.search_item_lay);

            personOrEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    newPersonActivity(ID, v, type);
                }
            });
        }

        private void bind(PersonOrEvent personOrEvent){

            ID = personOrEvent.getID();
            type = personOrEvent.getType();
            eventOrPersonName.setText(personOrEvent.getText());

        }


        private void newPersonActivity(String ID, View v, String type){

            if(type.equals("Person")) {
                Intent intent = new Intent(v.getContext(), PersonActivity.class);
                intent.putExtra("PersonID", ID);
                startActivity(intent);

            }
            else{
                Intent intent = new Intent( v.getContext() ,MapActivity.class);
                intent.putExtra("eventID",ID);
                startActivity(intent);

            }

        }
    }

}
