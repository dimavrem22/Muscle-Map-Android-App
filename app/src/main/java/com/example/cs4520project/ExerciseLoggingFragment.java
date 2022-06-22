package com.example.cs4520project;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ExerciseLoggingFragment extends Fragment implements View.OnClickListener {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection("users");

    public static final String FRAGMENT_KEY = "ExerciseLoggingFragment";

    private static final String ARG_PARAM1 = "email";
    private static final String ARG_PARAM2 = "day";
    private static final String ARG_PARAM3 = "month";
    private static final String ARG_PARAM4 = "year";

    private String email;
    private int day, month, year;

    private CollectionReference workoutCollection;

    private ArrayList<Workout> workoutList;

    private Button newWorkoutButton;
    private RecyclerView workoutRecycler;
    private WorkoutListAdapter workoutListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ExerciseLoggingFragment() {
        // Required empty public constructor
    }

    public static ExerciseLoggingFragment newInstance(String email, int day, int month, int year) {
        ExerciseLoggingFragment fragment = new ExerciseLoggingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, email);
        args.putInt(ARG_PARAM2, day);
        args.putInt(ARG_PARAM3, month);
        args.putInt(ARG_PARAM4, year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.email = getArguments().getString(ARG_PARAM1);
            this.day = getArguments().getInt(ARG_PARAM2);
            this.month = getArguments().getInt(ARG_PARAM3);
            this.year = getArguments().getInt(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_exercise_logging, container, false);

        this.workoutList = new ArrayList<Workout>();

        this.newWorkoutButton = rootView.findViewById(R.id.new_workout_button);
        this.newWorkoutButton.setOnClickListener(this);

        this.workoutRecycler = rootView.findViewById(R.id.workout_recycler);
        this.workoutListAdapter = new WorkoutListAdapter(this.workoutList, this);
        this.layoutManager = new LinearLayoutManager(this.getContext());
        this.workoutRecycler.setLayoutManager(this.layoutManager);
        this.workoutRecycler.setAdapter(this.workoutListAdapter);
        this.workoutListAdapter.notifyDataSetChanged();

        this.getWorkoutCollection();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.newWorkoutButton.getId()){
            // code for making new fragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_layout, EditWorkoutFragment.newInstance(null, null),
                            EditWorkoutFragment.FRAGMENT_KEY)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void clickedEditWorkout(int adapterPosition) {
        // take to edit workout
    }

    private void getWorkoutCollection(){
        this.usersCollection.whereEqualTo("email", this.email)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                this.workoutCollection = task.getResult().getDocuments().get(0)
                        .getReference().collection("workouts");
                this.workoutCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        populateWorkouts();
                    }
                });
                this.populateWorkouts();
            }
        });
    }

    private void populateWorkouts(){
        workoutCollection
                .whereEqualTo("day", this.day)
                .whereEqualTo("month", this.month)
                .whereEqualTo("year", this.year).get().addOnCompleteListener(task -> {
                    this.workoutList.clear();

                    for (DocumentSnapshot d: task.getResult().getDocuments()){
                        Workout wo = new Workout();
                        wo.setName(d.getString("name"));
                        wo.setExerciseList((ArrayList<String>) d.get("exercises"));
                        wo.setStartHour((int)Math.round(d.getDouble("startHour")));
                        wo.setStartMinute((int)Math.round(d.getDouble("startMinute")));
                        wo.setEndHour((int)Math.round(d.getDouble("endHour")));
                        wo.setEndMinute((int)Math.round(d.getDouble("endMinute")));
                        this.workoutList.add(wo);
                    }
                    this.workoutListAdapter.notifyDataSetChanged();

                });
    }




}