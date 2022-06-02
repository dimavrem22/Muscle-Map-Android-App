package com.example.cs4520project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout fragmentContainer;

    Button toSleepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.fragmentContainer = this.findViewById(R.id.fragment_container);

        this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                new LogInFragment(), "login fragment").addToBackStack(null).commit();

        setTitle("CS4520 Project");

        toSleepFragment = findViewById(R.id.buttonToSleepFragment);

        toSleepFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSleepFragment.setVisibility(View.INVISIBLE);
                MainActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                        new SleepMainFragment(), "sleep main fragment").addToBackStack(null).commit();
            }
        });

//

    }


}