package com.example.cs4520project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener,
        View.OnClickListener, LogInFragment.LoginToMain {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection("users");

    // authentication
    private FirebaseAuth mAuth;

    private final long DAY_IN_MS = 1000 * 60 * 60 * 24;

    private TextView dateText, monthText;

    private ConstraintLayout fragmentContainer;
    private ImageView calendarButton, nextArrow, prevArrow;
    private CalendarView calender;

    Button exerciseButton, dietButton, sleepButton;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Welcome to Muscle Map");

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

        this.exerciseButton = this.findViewById(R.id.exercise_button);
        this.exerciseButton.setOnClickListener(this);
        this.dietButton = this.findViewById(R.id.diet_button);
        this.dietButton.setOnClickListener(this);
        this.sleepButton = this.findViewById(R.id.sleep_button);
        this.sleepButton.setOnClickListener(this);

        this.sleepButton.setBackgroundColor(R.color.pink);

        this.nextArrow = this.findViewById(R.id.next_button);
        this.nextArrow.setOnClickListener(this);
        this.prevArrow = this.findViewById(R.id.prev_button);
        this.prevArrow.setOnClickListener(this);

        this.calendarButton = this.findViewById(R.id.cal_button);
        this.calendarButton.setOnClickListener(this);

        // authentication
        this.mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            this.getSupportActionBar().hide();
            // take to main app
        } else {
            // take to login or register
            this.launchLoginFragment();
        }



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



    private void launchLoginFragment(){

        this.getSupportActionBar().show();

        // hiding calendar when the user is not logged in
        this.sleepButton.setEnabled(false);
        this.dietButton.setEnabled(false);
        this.exerciseButton.setEnabled(false);
        this.sleepButton.setVisibility(View.INVISIBLE);
        this.dietButton.setVisibility(View.INVISIBLE);
        this.exerciseButton.setVisibility(View.INVISIBLE);
        this.calendarButton.setVisibility(View.INVISIBLE);
        this.calendarButton.setEnabled(false);
        this.nextArrow.setEnabled(false);
        this.nextArrow.setVisibility(View.INVISIBLE);
        this.prevArrow.setEnabled(false);
        this.prevArrow.setVisibility(View.INVISIBLE);
        this.dateText.setVisibility(View.INVISIBLE);
        this.monthText.setVisibility(View.INVISIBLE);

        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                LogInFragment.newInstance(), LogInFragment.FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void loginRequest(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // TODO: 6/20/22 Take to main app!

                this.removeLoginFragment();

            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(this, R.string.invalid_credentials,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void registerRequest(String name, String email, String pass) {
        this.checkDuplicateEmail(name, email, pass);
    }


    private void checkDuplicateEmail(String name, String email, String pass){
        usersCollection.whereEqualTo("email", email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                if(task.getResult().getDocuments().size() > 0){
                    Toast.makeText(this, R.string.duplicate_email,
                            Toast.LENGTH_SHORT).show();
                } else {
                    registerNewUser(name, email, pass);
                }
            } else {
                Toast.makeText(this, "Could not register! Try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void registerNewUser(String name, String email, String pass){
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        addUserToDB(name, email);
                    }
                });
    }


    private void addUserToDB(String name, String email){

        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);

        usersCollection.add(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                this.removeLoginFragment();
            }
        });
    }


    private void removeLoginFragment(){

        this.getSupportActionBar().hide();

        this.sleepButton.setEnabled(true);
        this.dietButton.setEnabled(true);
        this.exerciseButton.setEnabled(true);
        this.sleepButton.setVisibility(View.VISIBLE);
        this.dietButton.setVisibility(View.VISIBLE);
        this.exerciseButton.setVisibility(View.VISIBLE);

        this.calendarButton.setVisibility(View.VISIBLE);
        this.calendarButton.setEnabled(true);
        this.nextArrow.setEnabled(true);
        this.nextArrow.setVisibility(View.VISIBLE);
        this.prevArrow.setEnabled(true);
        this.prevArrow.setVisibility(View.VISIBLE);
        this.dateText.setVisibility(View.VISIBLE);
        this.monthText.setVisibility(View.VISIBLE);


        // removing login fragment
        this.getSupportFragmentManager().beginTransaction()
                .remove(this.getSupportFragmentManager()
                        .findFragmentByTag(LogInFragment.FRAGMENT_TAG)).commit();
    }

}