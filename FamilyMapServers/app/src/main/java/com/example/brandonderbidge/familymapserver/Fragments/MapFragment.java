package com.example.brandonderbidge.familymapserver.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brandonderbidge.familymapserver.Model;
import com.example.brandonderbidge.familymapserver.R;
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

    @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState ){

        super.onCreate(savedInstanceState);
        View v =inflater.inflate(R.layout.activity_maps, container, false);
        // xObtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        textEvent = (TextView) v.findViewById(R.id.map_event);
        textName = (TextView) v.findViewById(R.id.mapName);
        genderPic = (ImageView) v.findViewById(R.id.icon_gender);

        return v;
    }

    private boolean drawAllMarkerLines(Marker marker){

        Model.clearPolyline();


        List<Event> tempEvents;

        Event event = Model.getEventMarkerToEvents().get(marker);

        Person person = Model.getPeople().get(event.getPersonId());

        tempEvents = Model.getPersonToEvents().get(person);

        for (Event tempEvent: tempEvents) {

            Polyline poly = drawLine(marker.getPosition(), tempEvent);
            poly.setColor(Color.BLUE);

            Model.getConnections().add(poly);

        }

        LatLng position = marker.getPosition();

        if(!person.getFatherID().equals(" ")){

            ancestorDraw(Model.getPeople().get(person.getFatherID()), position );

        }

        if(!person.getMotherID().equals(" ")){

            ancestorDraw(Model.getPeople().get(person.getMotherID()), position);

        }

        if(!person.getSpouseID().equals(" ")){


            List<Event> tempEvent = Model.getPersonToEvents().get(Model.getPeople().get(person.getSpouseID()));

            Polyline poly = drawLine(marker.getPosition(), tempEvent.get(0));
            poly.setColor(Color.MAGENTA);

            Model.getConnections().add(poly);

        }

        return true;

    }

    private Polyline drawLine(LatLng position, Event tempEvent){

        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(false)
                .add(
                        position,
                        new LatLng(tempEvent.getLatitude(), tempEvent.getLongitude())
                ));


        return polyline1;

    }

    private void ancestorDraw(Person person, LatLng position ){


        List<Event> tempEvent = Model.getPersonToEvents().get(person);



        Polyline polyline = drawLine(position, tempEvent.get(0));
        polyline.setColor(Color.GREEN);

        Model.getConnections().add(polyline);

        LatLng tempos = new LatLng(tempEvent.get(0).getLatitude(), tempEvent.get(0).getLongitude());

        if(!person.getFatherID().equals(" ")){

            ancestorDraw(Model.getPeople().get(person.getFatherID()), tempos);

        }

        if(!person.getMotherID().equals(" ")){

            ancestorDraw(Model.getPeople().get(person.getMotherID()), tempos) ;
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

        if(Model.getEvents() != null) {


            for (Map.Entry<String, Event> entry : Model.getEvents().entrySet()) {

                LatLng location = new LatLng(entry.getValue().getLatitude(),
                        entry.getValue().getLongitude());


                if(entry.getValue().getEventType().equals("Birth")) {

                   Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(
                            entry.getValue().getEventType() + " in: " + entry.getValue().getCity())
                            .icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_AZURE)));

                    Model.getEventMarkerToEvents().put(marker, entry.getValue());

                    Model.getEventMarkerToPerson().put(marker,
                            Model.getPeople().get(entry.getValue().getPersonId()));

                }else if(entry.getValue().getEventType().equals("Baptism")) {

                    Marker marker =  mMap.addMarker(new MarkerOptions().position(location).title(
                            entry.getValue().getEventType() + " in: " + entry.getValue().getCity())
                            .icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_GREEN)));

                    Model.getEventMarkerToEvents().put(marker, entry.getValue());

                    Model.getEventMarkerToPerson().put(marker,
                            Model.getPeople().get(entry.getValue().getPersonId()));

                }else if(entry.getValue().getEventType().equals("Mission")) {

                    Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(
                            entry.getValue().getEventType() + " in: " + entry.getValue().getCity())
                            .icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_BLUE)));

                    Model.getEventMarkerToEvents().put(marker, entry.getValue());

                    Model.getEventMarkerToPerson().put(marker,
                            Model.getPeople().get(entry.getValue().getPersonId()));

                }else if(entry.getValue().getEventType().equals("Marriage")) {

                    Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(
                            entry.getValue().getEventType() + " in: " + entry.getValue().getCity())
                            .icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_MAGENTA)));

                    Model.getEventMarkerToEvents().put(marker, entry.getValue());

                    Model.getEventMarkerToPerson().put(marker,
                            Model.getPeople().get(entry.getValue().getPersonId()));

                }else{

                    Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(
                            entry.getValue().getEventType() + " in: " + entry.getValue().getCity())
                            .icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_RED)));

                    Model.getEventMarkerToEvents().put(marker, entry.getValue());

                    Model.getEventMarkerToPerson().put(marker,
                            Model.getPeople().get(entry.getValue().getPersonId()));

                }

            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    getEvent(marker);
                    return drawAllMarkerLines(marker);
                }
            });
        }

    }

    private void getEvent(Marker marker){

        textName.setText(Model.getEventMarkerToPerson().get(marker).getFirstName() + ", "
                + Model.getEventMarkerToPerson().get(marker).getLastName());

        textEvent.setText(Model.getEventMarkerToEvents().get(marker).toString());

        if(Model.getEventMarkerToPerson().get(marker).getGender().equals("m"))
            genderPic.setImageResource(R.mipmap.male_icon);
        else
            genderPic.setImageResource(R.mipmap.gender_female);

    }

}
