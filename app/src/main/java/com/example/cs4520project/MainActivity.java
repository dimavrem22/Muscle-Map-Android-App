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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener,
        View.OnClickListener, LogInFragment.LoginToMain, NewWorkoutFragment.INewWorkoutToMain,
        ExerciseLoggingFragment.ExerciseLogToMain, EditWorkoutFragment.IEditWorkoutToMain,
        ExerciseLoggingFragment.ISendDocFromExerciseLogToMain {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection("users");

    // authentication
    private FirebaseAuth mAuth;

    private final long DAY_IN_MS = 1000 * 60 * 60 * 24;

    private TextView dateText, monthText;

    private ConstraintLayout fragmentContainer;
    private ImageView calendarButton, nextArrow, prevArrow;
    private CalendarView calender;

    private Button exerciseButton, dietButton, sleepButton;

    boolean exerciseFragment, sleepFragment, dietFragment;

    private DocumentReference docToUpdate;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Welcome to Muscle Map");

        calender = findViewById(R.id.calendarView);
        calender.setMaxDate(System.currentTimeMillis());
        calender.setOnDateChangeListener(this);
        calender.setVisibility(View.INVISIBLE);
        calender.setEnabled(false);

        fragmentContainer = findViewById(R.id.fragment_container);

        dateText = findViewById(R.id.text_date);
        this.monthText = this.findViewById(R.id.text_month);

        java.util.Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        this.dateText.setText(cal.get(Calendar.DAY_OF_MONTH) + "");
        this.monthText.setText(new Utils().monthString(cal.get(Calendar.MONTH)) + "");

        this.exerciseButton = this.findViewById(R.id.exercise_button);
        this.exerciseButton.setOnClickListener(this);
        this.dietButton = this.findViewById(R.id.diet_button);
        this.dietButton.setOnClickListener(this);
        this.sleepButton = this.findViewById(R.id.sleep_button);
        this.sleepButton.setOnClickListener(this);


        this.nextArrow = this.findViewById(R.id.next_button);
        this.nextArrow.setOnClickListener(this);
        this.prevArrow = this.findViewById(R.id.prev_button);
        this.prevArrow.setOnClickListener(this);

        this.calendarButton = this.findViewById(R.id.cal_button);
        this.calendarButton.setOnClickListener(this);

        this.exerciseFragment = false;
        this.sleepFragment = false;
        this.dietFragment = false;

        // authentication
        this.mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            this.getSupportActionBar().hide();
            this.launchExerciseLogFragment();
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

        this.sendChangedDateToFragment();
    }


    @Override
    public void onClick(View v) {
        Log.d("ass", calender.getDate() + "");
        if (v.getId() == this.calendarButton.getId()) {
            this.calender.setVisibility(View.VISIBLE);
            this.calender.setEnabled(true);
        } else if (v.getId() == this.prevArrow.getId()) {
            this.calender.setDate(calender.getDate() - DAY_IN_MS);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(calender.getDate());
            this.dateText.setText(cal.get(Calendar.DAY_OF_MONTH) + "");
            this.monthText.setText(new Utils().monthString(cal.get(Calendar.MONTH)) + "");

        } else if (v.getId() == this.nextArrow.getId()) {
            this.calender.setDate(calender.getDate() + DAY_IN_MS);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(calender.getDate());
            this.dateText.setText(cal.get(Calendar.DAY_OF_MONTH) + "");
            this.monthText.setText(new Utils().monthString(cal.get(Calendar.MONTH)) + "");
        }
        this.sendChangedDateToFragment();
    }

    private void DateSelected(int year, int month, int dayOfMonth) {
        this.dateText.setText(dayOfMonth + "");
        this.monthText.setText(new Utils().monthString(month) + "");
    }


    private void launchLoginFragment() {

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
                this.launchExerciseLogFragment();

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


    private void checkDuplicateEmail(String name, String email, String pass) {
        usersCollection.whereEqualTo("email", email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getDocuments().size() > 0) {
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


    private void registerNewUser(String name, String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        addUserToDB(name, email);
                    }
                });
    }


    private void addUserToDB(String name, String email) {

        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);

        usersCollection.add(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                this.getSupportActionBar().hide();
                this.removeLoginFragment();
                this.launchExerciseLogFragment();
            }
        });
    }


    private void removeLoginFragment() {

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

    private void launchExerciseLogFragment() {
        this.exerciseFragment = true;
        this.dietFragment = false;
        this.sleepFragment = false;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.calender.getDate());

        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                ExerciseLoggingFragment.newInstance(mAuth.getCurrentUser().getEmail(),
                        cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.YEAR)),
                ExerciseLoggingFragment.FRAGMENT_KEY).commit();

    }


    private void sendChangedDateToFragment() {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.calender.getDate());

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        if (this.exerciseFragment) {
            ExerciseLoggingFragment f = (ExerciseLoggingFragment)
                    this.getSupportFragmentManager().findFragmentByTag(ExerciseLoggingFragment.FRAGMENT_KEY);
            f.changeDate(day, month, year);
        } else if (this.sleepFragment) {
            // change sleep frag date
        } else if (this.dietFragment) {
            // change diet frag date
        }

    }

    @Override
    public void addWorkoutToDB(Workout workout) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.calender.getDate());

        Map<String, Object> addWorkout = new HashMap<>();
        addWorkout.put("name", workout.getName());
        addWorkout.put("day", cal.get(Calendar.DAY_OF_MONTH));
        addWorkout.put("month", cal.get(Calendar.MONTH) + 1);
        addWorkout.put("startHour", workout.getStartHour());
        addWorkout.put("endHour", workout.getEndHour());
        addWorkout.put("startMinute", workout.getStartMinute());
        addWorkout.put("endMinute", workout.getEndMinute());
        addWorkout.put("exercises", workout.getExercises());
        addWorkout.put("year", cal.get(Calendar.YEAR));
        usersCollection.whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        CollectionReference workoutCollection = task.getResult().getDocuments().get(0)
                                .getReference().collection("workouts");
                        workoutCollection.add(addWorkout);
                    }
                });
    }

    @Override
    public void openMuscleMap() {
        this.getSupportFragmentManager().beginTransaction().add(R.id.outerFragmentContainer,
                ExerciseAnalysisFragment.newInstance(this.mAuth.getCurrentUser()
                        .getEmail())).addToBackStack(null).commit();
    }

    @Override
    public void updateWorkoutInDB(Workout newWorkout) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.calender.getDate());

        Map<String, Object> updateWorkout = new HashMap<>();
        updateWorkout.put("name", newWorkout.getName());
        updateWorkout.put("day", cal.get(Calendar.DAY_OF_MONTH));
        updateWorkout.put("month", cal.get(Calendar.MONTH) + 1);
        updateWorkout.put("startHour", newWorkout.getStartHour());
        updateWorkout.put("endHour", newWorkout.getEndHour());
        updateWorkout.put("startMinute", newWorkout.getStartMinute());
        updateWorkout.put("endMinute", newWorkout.getEndMinute());
        updateWorkout.put("exercises", newWorkout.getExercises());
        updateWorkout.put("year", cal.get(Calendar.YEAR));

        docToUpdate.update("exercises", "");
        docToUpdate.set(updateWorkout);
    }

    @Override
    public void sendDocFromExerciseLogToMain(DocumentReference doc) {
        docToUpdate = doc;
    }
}
