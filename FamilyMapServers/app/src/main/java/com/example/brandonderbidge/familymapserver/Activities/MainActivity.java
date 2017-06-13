package com.example.brandonderbidge.familymapserver.Activities;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.brandonderbidge.familymapserver.Fragments.LoginFragment;
import com.example.brandonderbidge.familymapserver.Fragments.MapFragment;
import com.example.brandonderbidge.familymapserver.Model;
import com.example.brandonderbidge.familymapserver.R;

public class MainActivity extends AppCompatActivity implements LoginFragment.Callback {



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FragmentManager fm = getSupportFragmentManager();


        if (Model.getCurrentPerson() == null) {


            LoginFragment login = LoginFragment.newInstance(MainActivity.this);;
            Model.init();

            fm.beginTransaction().add(R.id.fragContainer, login).commit();

        }
        else {

            MapFragment mapsActivity = new MapFragment();
            fm.beginTransaction().add(R.id.fragContainer, mapsActivity).commit();

        }


    }

    public void onLogin(){

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapsActivity = new MapFragment();
        fm.beginTransaction().replace(R.id.fragContainer,mapsActivity).commit();

    }


}


