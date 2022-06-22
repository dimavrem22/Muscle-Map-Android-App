package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ExerciseLoggingFragment extends Fragment implements View.OnClickListener {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection("users");

    public static final String FRAGMENT_KEY = "ExerciseLoggingFragment";

    private static final String ARG_EMAIL = "email";
    private static final String ARG_DAY = "day";
    private static final String ARG_MONTH = "month";
    private static final String ARG_YEAR = "year";

    private String email;
    private int day, month, year;

    private CollectionReference workoutCollection;

    private List<Workout> workoutList;

    private Button newWorkoutButton;
    private WorkoutListAdapter workoutListAdapter;

    public ExerciseLoggingFragment() {
        // Required empty public constructor
    }

    public static ExerciseLoggingFragment newInstance(String email, int day, int month, int year) {
        ExerciseLoggingFragment fragment = new ExerciseLoggingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        args.putInt(ARG_DAY, day);
        args.putInt(ARG_MONTH, month);
        args.putInt(ARG_YEAR, year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
            day = getArguments().getInt(ARG_DAY);
            month = getArguments().getInt(ARG_MONTH);
            year = getArguments().getInt(ARG_YEAR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exercise_logging, container, false);

        workoutList = new ArrayList<>();

        newWorkoutButton = rootView.findViewById(R.id.new_workout_button);
        newWorkoutButton.setOnClickListener(this);

        RecyclerView workoutRecycler = rootView.findViewById(R.id.workout_recycler);
        workoutListAdapter = new WorkoutListAdapter(workoutList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        workoutRecycler.setLayoutManager(layoutManager);
        workoutRecycler.setAdapter(workoutListAdapter);
        workoutListAdapter.notifyDataSetChanged();

        getWorkoutCollection();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == newWorkoutButton.getId()) {
            // code for making new fragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_layout, EditWorkoutFragment.newInstance(),
                            EditWorkoutFragment.FRAGMENT_KEY)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void clickedEditWorkout(int adapterPosition) {
        // take to edit workout
    }

    private void getWorkoutCollection() {
        usersCollection.whereEqualTo("email", email)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        workoutCollection = task.getResult().getDocuments().get(0)
                                .getReference().collection("workouts");
                        workoutCollection.addSnapshotListener((value, error) -> populateWorkouts());
                        populateWorkouts();
                    }
                });
    }

    private void populateWorkouts() {
        workoutCollection
                .whereEqualTo("day", day)
                .whereEqualTo("month", month)
                .whereEqualTo("year", year).get().addOnCompleteListener(task -> {
                    workoutList.clear();
                    for (DocumentSnapshot d : task.getResult().getDocuments()) {
                        Workout wo = new Workout();
                        wo.setName(d.getString("name"));
                        wo.setExercises((List<Exercise>) d.get("exercises"));
                        wo.setStartHour((int) Math.round(d.getDouble("startHour")));
                        wo.setStartMinute((int) Math.round(d.getDouble("startMinute")));
                        wo.setEndHour((int) Math.round(d.getDouble("endHour")));
                        wo.setEndMinute((int) Math.round(d.getDouble("endMinute")));
                        workoutList.add(wo);
                    }
                    workoutListAdapter.notifyDataSetChanged();
                });
    }

    public void changeDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        populateWorkouts();
    }
}