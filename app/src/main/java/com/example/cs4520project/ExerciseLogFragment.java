package com.example.cs4520project;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExerciseLogFragment extends Fragment implements View.OnClickListener {
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
    private Button muscleMapButton;
    private WorkoutListAdapter workoutListAdapter;

    private ISendDocFromExerciseLogToMain sendDocFromExerciseLogToMain;

    public interface ISendDocFromExerciseLogToMain {
        void sendDocFromExerciseLogToMain(DocumentReference doc);
    }

    public ExerciseLogFragment() {
        // Required empty public constructor
    }

    public static ExerciseLogFragment newInstance(String email, int day, int month, int year) {
        ExerciseLogFragment fragment = new ExerciseLogFragment();
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

        this.muscleMapButton = rootView.findViewById(R.id.muscleMapButton);
        this.muscleMapButton.setOnClickListener(this);

        getWorkoutCollection();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == newWorkoutButton.getId()) {
            // code for making new fragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_layout, NewWorkoutFragment.newInstance(),
                            NewWorkoutFragment.FRAGMENT_KEY)
                    .addToBackStack(null)
                    .commit();
        } else if (v.getId() == this.muscleMapButton.getId()) {
            ExerciseLogToMain context = (ExerciseLogToMain) this.getContext();
            context.openMuscleMap();
        }
    }

    public void clickedEditWorkout(int adapterPosition, Workout workout) {
        // take to edit workout
        workoutCollection
                .whereEqualTo("startHour", workout.getStartHour())
                .whereEqualTo("startMinute", workout.getStartMinute()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentReference doc = task.getResult().getDocuments().get(0).getReference();
                        Log.d("FP", doc.toString());
                        sendDocFromExerciseLogToMain.sendDocFromExerciseLogToMain(doc);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .add(R.id.outerFragmentContainer, EditWorkoutFragment.newInstance(workout), EditWorkoutFragment.FRAGMENT_KEY)
                                .addToBackStack(null)
                                .commit();
                    }
                });
    }

    private void getWorkoutCollection() {
        usersCollection.whereEqualTo("email", email)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("AYY", email);
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
                        List<String> exerciseNames = (List<String>) d.get("exercises");
                        List<Exercise> exercises = exerciseNames
                                .stream()
                                .map(Exercise::valueOf).collect(Collectors.toList());
                        wo.setExercises(exercises);
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

    public interface ExerciseLogToMain {
        void openMuscleMap();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ISendDocFromExerciseLogToMain) {
            sendDocFromExerciseLogToMain = (ISendDocFromExerciseLogToMain) context;
        } else {
            throw new RuntimeException(context.toString()+ " must implement ISendDocFromExerciseLogToMain");
        }
    }
}
