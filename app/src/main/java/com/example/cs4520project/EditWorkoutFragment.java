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

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditWorkoutFragment extends Fragment {

    public static final String FRAGMENT_KEY = "EditWorkoutFragment";
    public static final ArrayList<Exercise> exercises = new ArrayList<Exercise>(Exercises.getExercises());

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button buttonStartTime, buttonEndTime, buttonEditWorkoutSave, buttonEditWorkoutCancel, buttonSetWorkoutTime;
    private TextView textViewWorkout, textViewName, textViewStartTime, textViewEndTime;
    private EditText editTextWorkoutName;
    private ListView listViewExercises;

    ArrayAdapter<Exercise> adapter;

    private TimePicker timePickerWorkout;

    private int sleepHr, sleepMin, wakeHr, wakeMin;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SleepLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditWorkoutFragment newInstance(String param1, String param2) {
        EditWorkoutFragment fragment = new EditWorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_workout, container, false);

        buttonStartTime = view.findViewById(R.id.buttonStartTime);
        buttonEndTime = view.findViewById(R.id.buttonEndTime);
        buttonSetWorkoutTime = view.findViewById(R.id.buttonSetWorkoutTime);
        textViewWorkout = view.findViewById(R.id.textViewWorkout);
        textViewName = view.findViewById(R.id.textViewName);
        textViewStartTime = view.findViewById(R.id.textViewStartTime);
        textViewEndTime = view.findViewById(R.id.textViewEndTime);
        editTextWorkoutName = view.findViewById(R.id.editTextWorkoutName);
        buttonEditWorkoutSave = view.findViewById(R.id.buttonEditWorkoutSave);
        buttonEditWorkoutCancel = view.findViewById(R.id.buttonEditWorkoutCancel);
        listViewExercises = view.findViewById(R.id.listViewExercises);
        timePickerWorkout = view.findViewById(R.id.timePickerWorkout);
        adapter = new ArrayAdapter<Exercise>(getContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1,exercises);

        listViewExercises.setAdapter(adapter);

        buttonSetWorkoutTime.setVisibility(View.INVISIBLE);
        buttonEditWorkoutSave.setVisibility(View.VISIBLE);
        buttonEditWorkoutCancel.setVisibility(View.VISIBLE);

        timePickerWorkout.setVisibility(View.INVISIBLE);
        timePickerWorkout.setEnabled(true);



        buttonStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        buttonEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        buttonSetWorkoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        return view;
    }

    private TimePicker.OnTimeChangedListener timeChangedListenerStartTime = new TimePicker.OnTimeChangedListener() {

        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            sleepHr = hour;
            sleepMin = min;

            buttonStartTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };

    private TimePicker.OnTimeChangedListener timeChangedListenerEndTime = new TimePicker.OnTimeChangedListener() {

        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            wakeHr = hour;
            wakeMin = min;

            buttonEndTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };
}