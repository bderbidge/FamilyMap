package com.example.brandonderbidge.familymapserver.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.brandonderbidge.familymapserver.Activities.FilterActivity;
import com.example.brandonderbidge.familymapserver.Activities.MainActivity;
import com.example.brandonderbidge.familymapserver.Activities.PersonActivity;
import com.example.brandonderbidge.familymapserver.Activities.SearchActivity;
import com.example.brandonderbidge.familymapserver.Activities.SettingsActivity;
import com.example.brandonderbidge.familymapserver.Model;
import com.example.brandonderbidge.familymapserver.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Map;

import Models.Event;
import Models.Person;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView textName;

    private TextView textEvent;

    private ImageView genderPic;

    private LinearLayout personLayout;

    private boolean eventSet;

    private boolean mapType;

    private String eventFocus;

    @Override
    public void onResume() {
        super.onResume();

        if(mMap != null) {

            onMapReady(mMap);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_maps, container, false);
        // xObtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setHasOptionsMenu(true);

        if(getArguments() != null) {
            mapType = getArguments().getBoolean("mapActivity");
            eventFocus = getArguments().getString("eventID");
        }

        textEvent = (TextView) v.findViewById(R.id.map_event);
        textName = (TextView) v.findViewById(R.id.mapName);
        genderPic = (ImageView) v.findViewById(R.id.icon_gender);

        personLayout = (LinearLayout) v.findViewById(R.id.person_linear);

        eventSet = false;




        return v;
    }


    private void setPersonActivity() {

       if(eventSet == true) {
           Intent intent = new Intent(getActivity(), PersonActivity.class);
           intent.putExtra("PersonID", Model.getFocusedPerson().getId());
           startActivity(intent);
       }
    }

    private boolean drawAllMarkerLines(Marker marker) {

        Model.clearPolyline();


        List<Event> tempEvents;

        Event event = Model.getEventMarkerToEvents().get(marker);

        Person person = Model.getPeople().get(event.getPersonId());

        tempEvents = Model.getPersonToEvents().get(person);



        for (int i = 0; i < tempEvents.size() - 1 ; i++) {

            if(Model.getEventTypeMap().get(tempEvents.get(i).getEventType())) {

                LatLng templatlang = new LatLng(tempEvents.get(i).getLatitude(),
                        tempEvents.get(i).getLongitude());

                if(Model.getSetting().isLifeStoryBool()) {
                    Polyline poly = drawLine(templatlang, tempEvents.get(i + 1));


                    for (Map.Entry<Integer, Boolean> entry : Model.getSetting().getLifeStory().entrySet()) {

                        if (entry.getValue())
                            poly.setColor(entry.getKey());

                    }


                    Model.getConnections().add(poly);
                }

            }
        }

        LatLng position = marker.getPosition();

        float width = 5;

        if (!person.getFatherID().equals(" ") && Model.getEventTypeMap().get("By Male")
                && Model.getEventTypeMap().get("Father's Side")) {

            ancestorDraw(Model.getPeople().get(person.getFatherID()), position, width);

        }

        if (!person.getMotherID().equals(" ")&& Model.getEventTypeMap().get("By Female")
                && Model.getEventTypeMap().get("Mother's Side")) {

            ancestorDraw(Model.getPeople().get(person.getMotherID()), position, width);

        }

        List<Event> tempEvent = Model.getPersonToEvents().get(Model.getPeople().get(person.getSpouseID()));


        if (!person.getSpouseID().equals(" ") ) {

            int index = 0;
            for(Event e: tempEvent){
                if(Model.getEventTypeMap().get(e.getEventType()))
                {
                    break;
                }
                index++;
            }

            if(index < tempEvent.size()) {

                if (person.getGender().equals("m") && Model.getEventTypeMap().get("By Female")) {

                    if(Model.getSetting().isSpouseBool() == true) {
                        Polyline poly = drawLine(marker.getPosition(), tempEvent.get(index));

                        for (Map.Entry<Integer, Boolean> entry : Model.getSetting().getSpouse().entrySet()) {

                            if (entry.getValue() == true)
                                poly.setColor(entry.getKey());

                        }

                        Model.getConnections().add(poly);
                    }

                } else if (person.getGender().equals("f") && Model.getEventTypeMap().get("By Male")) {

                    if(Model.getSetting().isSpouseBool() == true) {
                        Polyline poly = drawLine(marker.getPosition(), tempEvent.get(index));

                        for (Map.Entry<Integer, Boolean> entry : Model.getSetting().getSpouse().entrySet()) {

                            if (entry.getValue() == true)
                                poly.setColor(entry.getKey());

                        }

                        Model.getConnections().add(poly);
                    }
                }
            }
        }

        Model.setFocusedPerson(Model.getEventMarkerToPerson().get(marker));



        return true;

    }



    private Polyline drawLine(LatLng position, Event tempEvent) {

        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(false)
                .add(
                        position,
                        new LatLng(tempEvent.getLatitude(), tempEvent.getLongitude())
                ));


        return polyline1;

    }

    private void ancestorDraw(Person person, LatLng position, Float width) {


        List<Event> tempEvent = Model.getPersonToEvents().get(person);

        int index = 0;
        for(Event e: tempEvent){
            if(Model.getEventTypeMap().get(e.getEventType()))
            {
                break;
            }
            index++;
        }

        LatLng tempos = null;

        if(index < tempEvent.size()) {

            if(Model.getSetting().isFamilyTreeBool() == true) {

                Polyline polyline = drawLine(position, tempEvent.get(index));

                for (Map.Entry<Integer, Boolean> entry : Model.getSetting().getFamilyTree().entrySet()) {

                    if (entry.getValue() == true)
                        polyline.setColor(entry.getKey());

                }
                polyline.setWidth(width);
                Model.getConnections().add(polyline);

            }

            tempos = new LatLng(tempEvent.get(index).getLatitude(), tempEvent.get(index).getLongitude());
        }

        width--;
        if (!person.getFatherID().equals(" ") && Model.getEventTypeMap().get("By Male")) {

            ancestorDraw(Model.getPeople().get(person.getFatherID()), tempos, width);

        }

        if (!person.getMotherID().equals(" ") && Model.getEventTypeMap().get("By Female")) {

            ancestorDraw(Model.getPeople().get(person.getMotherID()), tempos, width);
        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        if(mMap != null)
            mMap.clear();

        if (Model.getEvents() != null) {

            if(Model.isFilters())
            {
                setMarkers(Model.getSortedList());
            }
            else {
                setMarkers(Model.getEvents());
            }

            if(mapType == true) {

                double latitude =  Model.getEvents().get(eventFocus).getLatitude();
                double longitude=   Model.getEvents().get(eventFocus).getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    getEvent(marker);
                    return drawAllMarkerLines(marker);
                }
            });
        }


        personLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setPersonActivity();
            }
        });

        if(Model.getSetting().getGoogleMapType().get("Hybrid"))
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        else if(Model.getSetting().getGoogleMapType().get("Satellite"))
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }



    private void setMarkers(Map<String, Event> events){

        Model.getEventMarkerToEvents().clear();
        Model.getEventMarkerToPerson().clear();

        for (Map.Entry<String, Event> entry : events.entrySet()) {

            LatLng location = new LatLng(entry.getValue().getLatitude(),
                    entry.getValue().getLongitude());

            float color = Model.getEventTypeToColor().get(entry.getValue().getEventType());


            Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(
                    entry.getValue().getEventType() + " in: " + entry.getValue().getCity())
                    .icon(BitmapDescriptorFactory.defaultMarker(color)));


            Model.getEventMarkerToEvents().put(marker, entry.getValue());

            Model.getEventMarkerToPerson().put(marker,
                    Model.getPeople().get(entry.getValue().getPersonId()));

        }


    }


    private void getEvent(Marker marker) {

        eventSet = true;
        textName.setText(Model.getEventMarkerToPerson().get(marker).getFirstName() + ", "
                + Model.getEventMarkerToPerson().get(marker).getLastName());

        textEvent.setText(Model.getEventMarkerToEvents().get(marker).toString());

        if (Model.getEventMarkerToPerson().get(marker).getGender().equals("m"))
            genderPic.setImageResource(R.mipmap.male_icon);
        else
            genderPic.setImageResource(R.mipmap.gender_female);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        if(mapType == true){

            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.menu_main, menu);

        }else {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.menu_main_items, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


      boolean bool = super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_filter:    Intent intent = new Intent( getContext() ,FilterActivity.class);
                                        startActivity(intent);
                                        break;
            case R.id.up_bttn:          intent = new Intent(getContext(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        break;
            case R.id.action_settings:  intent = new Intent(getContext(), SettingsActivity.class);
                                        startActivity(intent);
                                        break;

            case R.id.search_bttn:       intent = new Intent(getContext(), SearchActivity.class);
                                         startActivity(intent);
                                         break;
        }

        return bool;
    }
}


