package com.example.brandonderbidge.familymapserver.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.brandonderbidge.familymapserver.Fragments.LoginFragment;
import com.example.brandonderbidge.familymapserver.Model;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);




        FragmentManager fm = getFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (Model.getCurrentPerson() == null) {

            LoginFragment login = new LoginFragment();
            Model.init();
            fragmentTransaction.replace(android.R.id.content, login);

        }
        fragmentTransaction.commit();
    }


}


