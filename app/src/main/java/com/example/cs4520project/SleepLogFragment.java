package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public class SleepLogFragment extends Fragment {
    private Button buttonSelectSleepTime, buttonSelectWakeTime, buttonSetTime;
    private TextView textViewGetToSleep, textViewWakeUp;

    private TimePicker timePicker;

    private int sleepHr, sleepMin, wakeHr, wakeMin;

    public SleepLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SleepLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SleepLogFragment newInstance() {
        SleepLogFragment fragment = new SleepLogFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_sleep_log, container, false);

        buttonSelectSleepTime = rootView.findViewById(R.id.buttonSelectSleepTime);
        buttonSelectWakeTime = rootView.findViewById(R.id.buttonSelectWakeTime);
        buttonSetTime = rootView.findViewById(R.id.buttonSetTime);
        textViewGetToSleep = rootView.findViewById(R.id.textViewGetToSleep);
        textViewWakeUp = rootView.findViewById(R.id.textViewWakeUp);

        timePicker = rootView.findViewById(R.id.timePickerWorkout);

        buttonSetTime.setVisibility(View.INVISIBLE);

        timePicker.setVisibility(View.INVISIBLE);
        timePicker.setEnabled(true);

        buttonSelectSleepTime.setOnClickListener(v -> {
            buttonSelectSleepTime.setVisibility(View.INVISIBLE);
            buttonSelectWakeTime.setVisibility(View.INVISIBLE);
            textViewGetToSleep.setVisibility(View.INVISIBLE);
            textViewWakeUp.setVisibility(View.INVISIBLE);

            buttonSetTime.setVisibility(View.VISIBLE);

            timePicker.setVisibility(View.VISIBLE);
            timePicker.setEnabled(true);

            timePicker.setOnTimeChangedListener(timeChangedListenerSleep);
        });

        buttonSelectWakeTime.setOnClickListener(v -> {
            buttonSelectSleepTime.setVisibility(View.INVISIBLE);
            buttonSelectWakeTime.setVisibility(View.INVISIBLE);
            textViewGetToSleep.setVisibility(View.INVISIBLE);
            textViewWakeUp.setVisibility(View.INVISIBLE);

            buttonSetTime.setVisibility(View.VISIBLE);

            timePicker.setVisibility(View.VISIBLE);
            timePicker.setEnabled(true);

            timePicker.setOnTimeChangedListener(timeChangedListenerWake);
        });

        buttonSetTime.setOnClickListener(v -> {
            buttonSelectSleepTime.setVisibility(View.VISIBLE);
            buttonSelectWakeTime.setVisibility(View.VISIBLE);
            textViewGetToSleep.setVisibility(View.VISIBLE);
            textViewWakeUp.setVisibility(View.VISIBLE);

            timePicker.setVisibility(View.INVISIBLE);
            timePicker.setEnabled(false);

            buttonSetTime.setVisibility(View.INVISIBLE);
        });

        return rootView;
    }

    private final TimePicker.OnTimeChangedListener timeChangedListenerSleep = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            sleepHr = hour;
            sleepMin = min;
            buttonSelectSleepTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };

    private final TimePicker.OnTimeChangedListener timeChangedListenerWake = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            wakeHr = hour;
            wakeMin = min;
            buttonSelectWakeTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };
}