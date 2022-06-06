package com.example.cs4520project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener,
        View.OnClickListener {

    private final long DAY_IN_MS = 1000 * 60 * 60 * 24;

    int day, month, year;

    private TextView dateText, monthText;

    private ConstraintLayout fragmentContainer;
    private ImageView calendarButton, nextArrow, prevArrow;
    private CalendarView calender;

    Button toSleepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.calender = this.findViewById(R.id.calendarView);
        this.calender.setMaxDate(System.currentTimeMillis());
        this.calender.setOnDateChangeListener(this);
        this.calender.setVisibility(View.INVISIBLE);
        this.calender.setEnabled(false);



        this.fragmentContainer = this.findViewById(R.id.fragment_container);

        this.dateText = this.findViewById(R.id.text_date);
        this.monthText = this.findViewById(R.id.text_month);

        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        this.dateText.setText(cal.get(Calendar.DAY_OF_MONTH) + "");
        this.monthText.setText(new Utils().monthString(cal.get(Calendar.MONTH))+ "");


        this.nextArrow = this.findViewById(R.id.next_button);
        this.nextArrow.setOnClickListener(this);
        this.prevArrow = this.findViewById(R.id.prev_button);
        this.prevArrow.setOnClickListener(this);

        this.calendarButton = this.findViewById(R.id.cal_button);
        this.calendarButton.setOnClickListener(this);



        this.getSupportFragmentManager().beginTransaction().add(R.id.main_layout,
                new LogInFragment(), "login fragment").addToBackStack(null).commit();

        setTitle("CS4520 Project");

        toSleepFragment = findViewById(R.id.buttonToSleepFragment);

        toSleepFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSleepFragment.setVisibility(View.INVISIBLE);
                MainActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                        new SleepAnalysisFragment(), "sleep main fragment").addToBackStack(null).commit();
            }
        });

//

    }


    @Override
    public void onSelectedDayChange(@NonNull CalendarView view,
                                    int year, int month, int dayOfMonth) {
        this.calender.setVisibility(View.INVISIBLE);
        this.calender.setEnabled(false);

        Calendar cal = Calendar.getInstance();

        cal.set(year, month, dayOfMonth);
        this.calender.setDate(cal.getTimeInMillis());

            this.DateSelected(year, month, dayOfMonth);

    }

    @Override
    public void onClick(View v) {
        Log.d("ass", calender.getDate()+"");
        if(v.getId() == this.calendarButton.getId()){
            this.calender.setVisibility(View.VISIBLE);
            this.calender.setEnabled(true);
        }
        else if (v.getId() == this.prevArrow.getId()){
            this.calender.setDate(calender.getDate() - DAY_IN_MS);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(calender.getDate());
            this.dateText.setText(cal.get(Calendar.DAY_OF_MONTH) + "");
            this.monthText.setText(new Utils().monthString(cal.get(Calendar.MONTH))+ "");
            Log.d("ass", calender.getDate()+"");

        }
        else if (v.getId() == this.nextArrow.getId()) {
            this.calender.setDate(calender.getDate() + DAY_IN_MS);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(calender.getDate());
            this.dateText.setText(cal.get(Calendar.DAY_OF_MONTH) + "");
            this.monthText.setText(new Utils().monthString(cal.get(Calendar.MONTH))+ "");
            Log.d("ass", calender.getDate()+"");
        }
    }

    private void DateSelected(int year, int month, int dayOfMonth){
        this.dateText.setText(dayOfMonth + "");
        this.monthText.setText(new Utils().monthString(month) + "");

    }
}