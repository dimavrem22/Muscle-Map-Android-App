package com.example.cs4520project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.fragmentContainer = this.findViewById(R.id.fragment_container);

        this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                new LogInFragment(), "login fragment").addToBackStack(null).commit();


//

    }


}