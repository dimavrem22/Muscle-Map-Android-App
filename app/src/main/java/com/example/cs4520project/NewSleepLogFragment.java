package com.example.cs4520project;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class NewSleepLogFragment extends Fragment {
    public static final String FRAGMENT_TAG = "New Sleep Log Fragment";

    private Button buttonSelectSleepTime, buttonSelectWakeTime, buttonSetTime, buttonSaveSleepTime;
    private TextView textViewGetToSleep, textViewWakeUp;

    private TimePicker timePicker;

    private int sleepHr, sleepMin, wakeHr, wakeMin;

    private IAddNewSleepLogToDB addNewSleepLog;

    public interface IAddNewSleepLogToDB {
        void addNewSleepLogToDB(Sleep sleep);
    }

    public NewSleepLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SleepLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewSleepLogFragment newInstance() {
        NewSleepLogFragment fragment = new NewSleepLogFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_new_sleep_log, container, false);

        buttonSelectSleepTime = rootView.findViewById(R.id.buttonSelectSleepTime);
        buttonSelectWakeTime = rootView.findViewById(R.id.buttonSelectWakeTime);
        buttonSetTime = rootView.findViewById(R.id.buttonSetTime);
        buttonSaveSleepTime = rootView.findViewById(R.id.buttonSaveSleepTime);
        textViewGetToSleep = rootView.findViewById(R.id.textViewGetToSleep);
        textViewWakeUp = rootView.findViewById(R.id.textViewWakeUp);

        timePicker = rootView.findViewById(R.id.timePickerWorkout);

        buttonSetTime.setVisibility(View.INVISIBLE);

        timePicker.setVisibility(View.INVISIBLE);
        timePicker.setEnabled(true);

        buttonSelectSleepTime.setOnClickListener(v -> {
            buttonSelectSleepTime.setVisibility(View.INVISIBLE);
            buttonSelectWakeTime.setVisibility(View.INVISIBLE);
            buttonSaveSleepTime.setVisibility(View.INVISIBLE);
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
            buttonSaveSleepTime.setVisibility(View.INVISIBLE);

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
            buttonSaveSleepTime.setVisibility(View.VISIBLE);

            timePicker.setVisibility(View.INVISIBLE);
            timePicker.setEnabled(false);

            buttonSetTime.setVisibility(View.INVISIBLE);
        });

        buttonSaveSleepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sleepHr != 0 && sleepMin != 0 && wakeHr != 0 && wakeMin != 0) {
                    Log.d("sleepmin", sleepMin + "");
                    Log.d("sleepmin", wakeMin + "");
                    Sleep sleep = new Sleep(sleepHr, sleepMin, wakeHr, wakeMin);
                    Log.d("FP", sleep.toString());
                    addNewSleepLog.addNewSleepLogToDB(sleep);
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Empty Fields!", Toast.LENGTH_SHORT).show();
                }
            }
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

    private boolean checkValidTime() {
//        boolean valid = false;
//        if (sleepHr < wakeHr) {
//            valid = true;
//        }
//        else if (sleepHr == wakeHr) {
//            if (sleepMin < wakeMin) {
//                valid = true;
//            }
//        }
//        return valid;

        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IAddNewSleepLogToDB) {
            addNewSleepLog = (IAddNewSleepLogToDB) context;
        } else {
            throw new RuntimeException(context.toString()+ " must implement IAddNewSleepLogToDB");
        }
    }
}