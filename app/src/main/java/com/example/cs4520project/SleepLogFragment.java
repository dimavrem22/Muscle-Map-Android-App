package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SleepLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SleepLogFragment extends Fragment {
    public static final String FRAGMENT_TAG = "Sleep Log Fragment";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection("users");
    private CollectionReference sleepCollection;

    private static final String ARG_EMAIL = "email";
    private static final String ARG_DAY = "day";
    private static final String ARG_MONTH = "month";
    private static final String ARG_YEAR = "year";

    private String email;
    private int day, month, year;
    private Sleep sleep;

    private TextView textViewBedTimeShow, textViewWakeTimeShow, textViewHoursOfSleep,
            textViewBedTime, textViewSleepTime;

    private Button buttonSetSleepTime;

    public SleepLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SleepMainFragment.
     */
    public static SleepLogFragment newInstance(String email, int day, int month, int year) {
        SleepLogFragment fragment = new SleepLogFragment();
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
        View view = inflater.inflate(R.layout.fragment_sleep_log, container, false);

        textViewBedTimeShow = view.findViewById(R.id.textViewBedTimeShow);
        textViewWakeTimeShow = view.findViewById(R.id.textViewWakeTimeShow);
        textViewHoursOfSleep = view.findViewById(R.id.textViewHoursOfSleep);
        textViewBedTime = view.findViewById(R.id.textViewBedTime);
        textViewSleepTime = view.findViewById(R.id.textViewSleepTime);


        textViewBedTimeShow.setVisibility(View.INVISIBLE);
        textViewWakeTimeShow.setVisibility(View.INVISIBLE);
        textViewHoursOfSleep.setVisibility(View.INVISIBLE);
        textViewBedTime.setVisibility(View.INVISIBLE);
        textViewSleepTime.setVisibility(View.INVISIBLE);

        buttonSetSleepTime = view.findViewById(R.id.buttonSetSleepTime);
        buttonSetSleepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_layout, NewSleepLogFragment.newInstance(),
                                NewSleepLogFragment.FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });

        getSleepCollection();

        return view;
    }

    private void getSleepCollection() {
        usersCollection.whereEqualTo("email", email)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sleepCollection = task.getResult().getDocuments().get(0)
                                .getReference().collection("sleep");
                        sleepCollection.addSnapshotListener((value, error) -> setSleepLog());
                        setSleepLog();
                    }
                });
    }

    private void setSleepLog() {
        sleepCollection
                .whereEqualTo("day", day)
                .whereEqualTo("month", month)
                .whereEqualTo("year", year).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().getDocuments().size() > 0) {
                            DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                            Sleep sleep = new Sleep();
                            sleep.setSleepHr((int)Math.round(doc.getDouble("sleepHour")));
                            sleep.setSleepMin((int)Math.round(doc.getDouble("sleepMinute")));
                            sleep.setWakeHr((int)Math.round(doc.getDouble("wakeHour")));
                            sleep.setWakeMin((int)Math.round(doc.getDouble("wakeMinute")));
                            this.sleep = sleep;
                            textViewBedTimeShow.setText(sleep.getSleepTimeInTwelveHrFormat());
                            textViewWakeTimeShow.setText(sleep.getWakeTimeInTwelveHrFormat());
                            textViewHoursOfSleep.setText(getString(R.string.HoursOfSleep, sleep.getAmountOfSleep()));
                            textViewBedTimeShow.setVisibility(View.VISIBLE);
                            textViewWakeTimeShow.setVisibility(View.VISIBLE);
                            textViewHoursOfSleep.setVisibility(View.VISIBLE);
                            textViewBedTime.setVisibility(View.VISIBLE);
                            textViewSleepTime.setVisibility(View.VISIBLE);
                            Log.d("FP", this.sleep.toString());
                        }
                        else {
                            textViewBedTimeShow.setVisibility(View.INVISIBLE);
                            textViewWakeTimeShow.setVisibility(View.INVISIBLE);
                            textViewHoursOfSleep.setVisibility(View.INVISIBLE);
                            textViewBedTime.setVisibility(View.INVISIBLE);
                            textViewSleepTime.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    public void changeDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        setSleepLog();
    }
}