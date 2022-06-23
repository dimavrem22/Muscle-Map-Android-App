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
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EditWorkoutFragment extends Fragment implements ExerciseListAdapter.ICheckExercises {
    public static final String FRAGMENT_KEY = "EditWorkoutFragment";

    private static final String ARG_PARAM1 = "param1";

    private Workout originalWorkout;

    private Button buttonStartTime, buttonEndTime, buttonNewWorkoutSave, buttonNewWorkoutCancel, buttonSetWorkoutTime;
    private TextView textViewWorkout, textViewName, textViewStartTime, textViewEndTime;
    private EditText editTextWorkoutName;

    private ExerciseListAdapter exerciseListAdapter;
    private RecyclerView exercisesRecycler;
    private SearchView searchViewExercise;

    private TimePicker timePickerWorkout;

    private int startHr, startMin, endHr, endMin;
    private List<Exercise> clickedExercises;

    private IEditWorkoutToMain editWorkoutToMain;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    public interface IEditWorkoutToMain {

        public void updateWorkoutInDB(Workout newWorkout);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SleepLogFragment.
     */
    public static EditWorkoutFragment newInstance(Workout originalWorkout) {
        EditWorkoutFragment fragment = new EditWorkoutFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, originalWorkout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            originalWorkout = (Workout) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_workout, container, false);

        clickedExercises = originalWorkout.getExercises();

        buttonStartTime = rootView.findViewById(R.id.buttonStartTimeEF);
        buttonEndTime = rootView.findViewById(R.id.buttonEndTimeEF);
        buttonSetWorkoutTime = rootView.findViewById(R.id.buttonSetWorkoutTimeEF);
        textViewWorkout = rootView.findViewById(R.id.textViewWorkoutEF);
        textViewName = rootView.findViewById(R.id.textViewNameEF);
        textViewStartTime = rootView.findViewById(R.id.textViewStartTimeEF);
        textViewEndTime = rootView.findViewById(R.id.textViewEndTimeEF);
        editTextWorkoutName = rootView.findViewById(R.id.editTextWorkoutNameEF);
        buttonNewWorkoutSave = rootView.findViewById(R.id.buttonEditWorkoutSave);
        buttonNewWorkoutCancel = rootView.findViewById(R.id.buttonEditWorkoutCancel);
        timePickerWorkout = rootView.findViewById(R.id.timePickerWorkoutEF);
        exercisesRecycler = rootView.findViewById(R.id.recyclerViewExercisesEF);

        textViewName.setText(getString(R.string.EditWorkout));


        startHr = originalWorkout.getStartHour();
        startMin = originalWorkout.getStartMinute();
        endHr = originalWorkout.getEndHour();
        endMin = originalWorkout.getEndMinute();


        editTextWorkoutName.setText(originalWorkout.getName());
        buttonStartTime.setText(originalWorkout.getStartHour()+":"+ originalWorkout.getStartMinute());
        buttonEndTime.setText(originalWorkout.getEndHour()+":"+ originalWorkout.getEndMinute());


        RecyclerView.LayoutManager exerciseManager = new LinearLayoutManager(getContext());
        exercisesRecycler.setLayoutManager(exerciseManager);
        exerciseListAdapter = new ExerciseListAdapter(new ArrayList<Exercise>(Arrays.asList(Exercise.values())), this, clickedExercises);
        exercisesRecycler.setAdapter(exerciseListAdapter);

        searchViewExercise = rootView.findViewById(R.id.searchViewExerciseEF);
        searchViewExercise.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                exerciseListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        buttonSetWorkoutTime.setVisibility(View.INVISIBLE);
        buttonNewWorkoutSave.setVisibility(View.VISIBLE);
        buttonNewWorkoutCancel.setVisibility(View.VISIBLE);

        timePickerWorkout.setVisibility(View.INVISIBLE);
        timePickerWorkout.setEnabled(true);

        buttonStartTime.setOnClickListener(v -> {
            buttonStartTime.setVisibility(View.INVISIBLE);
            buttonEndTime.setVisibility(View.INVISIBLE);
            textViewWorkout.setVisibility(View.INVISIBLE);
            textViewName.setVisibility(View.INVISIBLE);
            textViewStartTime.setVisibility(View.INVISIBLE);
            textViewEndTime.setVisibility(View.INVISIBLE);
            editTextWorkoutName.setVisibility(View.INVISIBLE);
            exercisesRecycler.setVisibility(View.INVISIBLE);
            searchViewExercise.setVisibility(View.INVISIBLE);

            buttonNewWorkoutSave.setVisibility(View.INVISIBLE);
            buttonNewWorkoutCancel.setVisibility(View.INVISIBLE);

            buttonSetWorkoutTime.setVisibility(View.VISIBLE);

            timePickerWorkout.setVisibility(View.VISIBLE);
            timePickerWorkout.setEnabled(true);

            timePickerWorkout.setOnTimeChangedListener(timeChangedListenerStartTime);
        });

        buttonEndTime.setOnClickListener(v -> {
            buttonStartTime.setVisibility(View.INVISIBLE);
            buttonEndTime.setVisibility(View.INVISIBLE);
            textViewWorkout.setVisibility(View.INVISIBLE);
            textViewName.setVisibility(View.INVISIBLE);
            textViewStartTime.setVisibility(View.INVISIBLE);
            textViewEndTime.setVisibility(View.INVISIBLE);
            editTextWorkoutName.setVisibility(View.INVISIBLE);
            exercisesRecycler.setVisibility(View.INVISIBLE);
            searchViewExercise.setVisibility(View.INVISIBLE);

            buttonNewWorkoutSave.setVisibility(View.INVISIBLE);
            buttonNewWorkoutCancel.setVisibility(View.INVISIBLE);

            buttonSetWorkoutTime.setVisibility(View.VISIBLE);

            timePickerWorkout.setVisibility(View.VISIBLE);
            timePickerWorkout.setEnabled(true);

            timePickerWorkout.setOnTimeChangedListener(timeChangedListenerEndTime);
        });

        buttonSetWorkoutTime.setOnClickListener(v -> {
            buttonStartTime.setVisibility(View.VISIBLE);
            buttonEndTime.setVisibility(View.VISIBLE);
            textViewWorkout.setVisibility(View.VISIBLE);
            textViewName.setVisibility(View.VISIBLE);
            textViewStartTime.setVisibility(View.VISIBLE);
            textViewEndTime.setVisibility(View.VISIBLE);
            editTextWorkoutName.setVisibility(View.VISIBLE);
            exercisesRecycler.setVisibility(View.VISIBLE);
            searchViewExercise.setVisibility(View.VISIBLE);


            timePickerWorkout.setVisibility(View.INVISIBLE);
            timePickerWorkout.setEnabled(false);

            buttonSetWorkoutTime.setVisibility(View.INVISIBLE);
            buttonNewWorkoutSave.setVisibility(View.VISIBLE);
            buttonNewWorkoutCancel.setVisibility(View.VISIBLE);
        });

        buttonNewWorkoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickedExercises.size() > 0 && editTextWorkoutName.getText().toString().length() > 0
                        && startHr != 0 && startMin != 0 && endHr != 0 && endMin != 0) {
                    if (checkValidTime()) {
                        Workout newWorkout = new Workout(editTextWorkoutName.getText().toString(),
                                startHr, startMin, endHr, endMin, clickedExercises);
                        Log.d("FP", newWorkout.toString());
                        editWorkoutToMain.updateWorkoutInDB(newWorkout);
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    else {
                        Toast.makeText(getContext(), "Enter A Valid Time!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Enter A Valid Time!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getContext(), "Empty Fields!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonNewWorkoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

    private final TimePicker.OnTimeChangedListener timeChangedListenerStartTime = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            startHr = hour;
            startMin = min;
            buttonStartTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };

    private final TimePicker.OnTimeChangedListener timeChangedListenerEndTime = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            endHr = hour;
            endMin = min;
            buttonEndTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };

    public void checkExercise(Exercise exercise) {
        if (clickedExercises.contains(exercise)) {
            clickedExercises.remove(exercise);
        }
        else {
            clickedExercises.add(exercise);
        }
    }

    private boolean checkValidTime() {
        boolean valid = false;
        if (startHr < endHr) {
            valid = true;
        }
        else if (startHr == endHr) {
            if (startMin < endMin) {
                valid = true;
            }
        }
        return valid;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IEditWorkoutToMain) {
            editWorkoutToMain = (IEditWorkoutToMain) context;
        } else {
            throw new RuntimeException(context.toString()+ " must implement IEditWorkoutToMain");
        }
    }
}