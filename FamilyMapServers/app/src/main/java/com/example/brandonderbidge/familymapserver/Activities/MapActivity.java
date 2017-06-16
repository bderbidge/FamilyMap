package com.example.brandonderbidge.familymapserver.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.brandonderbidge.familymapserver.Fragments.MapFragment;
import com.example.brandonderbidge.familymapserver.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_event);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("eventID");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("eventID");
        }

        Bundle bundle = new Bundle();
        bundle.putBoolean("mapActivity", true);
        bundle.putString("eventID", newString);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapsActivity = new MapFragment();

        mapsActivity.setArguments(bundle);
        fm.beginTransaction().add(R.id.eventMapActivity, mapsActivity).commit();


    }

}
