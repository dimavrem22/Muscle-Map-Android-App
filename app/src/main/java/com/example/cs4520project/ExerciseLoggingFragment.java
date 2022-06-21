package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ExerciseLoggingFragment extends Fragment implements View.OnClickListener {

    public static final String FRAGMENT_KEY = "ExerciseLoggingFragment";

    private static final String ARG_PARAM1 = "email";

    private String email;

    Button newWorkoutButton;
    RecyclerView workoutRecycler;

    public ExerciseLoggingFragment() {
        // Required empty public constructor
    }

    public static ExerciseLoggingFragment newInstance(String email) {
        ExerciseLoggingFragment fragment = new ExerciseLoggingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.email = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_exercise_logging, container, false);

        this.newWorkoutButton = rootView.findViewById(R.id.new_workout_button);
        this.newWorkoutButton.setOnClickListener(this);

        this.workoutRecycler = rootView.findViewById(R.id.workout_recycler);


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
        // open edit workout fragment
    }
}