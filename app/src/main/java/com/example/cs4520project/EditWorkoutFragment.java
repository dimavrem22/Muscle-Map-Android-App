package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public class EditWorkoutFragment extends Fragment {
    public static final String FRAGMENT_KEY = "EditWorkoutFragment";

    private Button buttonStartTime, buttonEndTime, buttonEditWorkoutSave, buttonEditWorkoutCancel, buttonSetWorkoutTime;
    private TextView textViewWorkout, textViewName, textViewStartTime, textViewEndTime;
    private EditText editTextWorkoutName;
    private ListView listViewExercises;

    private TimePicker timePickerWorkout;

    private int sleepHr, sleepMin, wakeHr, wakeMin;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SleepLogFragment.
     */
    public static EditWorkoutFragment newInstance() {
        EditWorkoutFragment fragment = new EditWorkoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_workout, container, false);

        buttonStartTime = rootView.findViewById(R.id.buttonStartTime);
        buttonEndTime = rootView.findViewById(R.id.buttonEndTime);
        buttonSetWorkoutTime = rootView.findViewById(R.id.buttonSetWorkoutTime);
        textViewWorkout = rootView.findViewById(R.id.textViewWorkout);
        textViewName = rootView.findViewById(R.id.textViewName);
        textViewStartTime = rootView.findViewById(R.id.textViewStartTime);
        textViewEndTime = rootView.findViewById(R.id.textViewEndTime);
        editTextWorkoutName = rootView.findViewById(R.id.editTextWorkoutName);
        buttonEditWorkoutSave = rootView.findViewById(R.id.buttonEditWorkoutSave);
        buttonEditWorkoutCancel = rootView.findViewById(R.id.buttonEditWorkoutCancel);
        listViewExercises = rootView.findViewById(R.id.listViewExercises);
        timePickerWorkout = rootView.findViewById(R.id.timePickerWorkout);
        ArrayAdapter<Exercise> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        Exercise.values());

        listViewExercises.setAdapter(adapter);

        buttonSetWorkoutTime.setVisibility(View.INVISIBLE);
        buttonEditWorkoutSave.setVisibility(View.VISIBLE);
        buttonEditWorkoutCancel.setVisibility(View.VISIBLE);

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
            listViewExercises.setVisibility(View.INVISIBLE);
            buttonEditWorkoutSave.setVisibility(View.INVISIBLE);
            buttonEditWorkoutCancel.setVisibility(View.INVISIBLE);

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
            listViewExercises.setVisibility(View.INVISIBLE);
            buttonEditWorkoutSave.setVisibility(View.INVISIBLE);
            buttonEditWorkoutCancel.setVisibility(View.INVISIBLE);

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
            listViewExercises.setVisibility(View.VISIBLE);

            timePickerWorkout.setVisibility(View.INVISIBLE);
            timePickerWorkout.setEnabled(false);

            buttonSetWorkoutTime.setVisibility(View.INVISIBLE);
            buttonEditWorkoutSave.setVisibility(View.VISIBLE);
            buttonEditWorkoutCancel.setVisibility(View.VISIBLE);
        });

        return rootView;
    }

    private final TimePicker.OnTimeChangedListener timeChangedListenerStartTime = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            sleepHr = hour;
            sleepMin = min;
            buttonStartTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };

    private final TimePicker.OnTimeChangedListener timeChangedListenerEndTime = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            wakeHr = hour;
            wakeMin = min;
            buttonEndTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };
}