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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SleepLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SleepLogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SleepLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SleepLogFragment newInstance(String param1, String param2) {
        SleepLogFragment fragment = new SleepLogFragment();
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
        View view = inflater.inflate(R.layout.fragment_sleep_log, container, false);

        buttonSelectSleepTime = view.findViewById(R.id.buttonSelectSleepTime);
        buttonSelectWakeTime = view.findViewById(R.id.buttonSelectWakeTime);
        buttonSetTime = view.findViewById(R.id.buttonSetTime);
        textViewGetToSleep = view.findViewById(R.id.textViewGetToSleep);
        textViewWakeUp = view.findViewById(R.id.textViewWakeUp);

        timePicker = view.findViewById(R.id.timePickerWorkout);

        buttonSetTime.setVisibility(View.INVISIBLE);

        timePicker.setVisibility(View.INVISIBLE);
        timePicker.setEnabled(true);

        buttonSelectSleepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectSleepTime.setVisibility(View.INVISIBLE);
                buttonSelectWakeTime.setVisibility(View.INVISIBLE);
                textViewGetToSleep.setVisibility(View.INVISIBLE);
                textViewWakeUp.setVisibility(View.INVISIBLE);

                buttonSetTime.setVisibility(View.VISIBLE);

                timePicker.setVisibility(View.VISIBLE);
                timePicker.setEnabled(true);

                timePicker.setOnTimeChangedListener(timeChangedListenerSleep);
            }
        });

        buttonSelectWakeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectSleepTime.setVisibility(View.INVISIBLE);
                buttonSelectWakeTime.setVisibility(View.INVISIBLE);
                textViewGetToSleep.setVisibility(View.INVISIBLE);
                textViewWakeUp.setVisibility(View.INVISIBLE);

                buttonSetTime.setVisibility(View.VISIBLE);

                timePicker.setVisibility(View.VISIBLE);
                timePicker.setEnabled(true);

                timePicker.setOnTimeChangedListener(timeChangedListenerWake);
            }
        });

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectSleepTime.setVisibility(View.VISIBLE);
                buttonSelectWakeTime.setVisibility(View.VISIBLE);
                textViewGetToSleep.setVisibility(View.VISIBLE);
                textViewWakeUp.setVisibility(View.VISIBLE);

                timePicker.setVisibility(View.INVISIBLE);
                timePicker.setEnabled(false);

                buttonSetTime.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    private TimePicker.OnTimeChangedListener timeChangedListenerSleep = new TimePicker.OnTimeChangedListener() {

        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            sleepHr = hour;
            sleepMin = min;

            buttonSelectSleepTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };

    private TimePicker.OnTimeChangedListener timeChangedListenerWake = new TimePicker.OnTimeChangedListener() {

        @Override
        public void onTimeChanged(TimePicker tp, int hour, int min) {
            wakeHr = hour;
            wakeMin = min;

            buttonSelectWakeTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
        }
    };

}