package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseAnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseAnalysisFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection("users");

    private ArrayList<String> timeFrames;

    private static final String ARG_EMAIL = "email";
    private String email;

    private boolean lookingFront;

    private int counter = 0;

    private final EnumMap<MuscleGroup, Double> strain = new EnumMap<>(MuscleGroup.class);

    private ImageView flipImage, backButton;

    private ImageView frontView, trapsFront, pecsFront, absFront, quadsFront, calvesFront,
            shouldersFront, bicepsFront, forearmsFront;

    private ArrayList<ImageView> frontMuscleImages;

    private ImageView backView, upperTrapsBack, trapsBack, shouldersBack, trisBack, forearmsBack,
            latsBack, lowerBackBack, glutsBack, hamsBack, calvesBack;

    private ProgressBar progressBar;
    private Spinner spinner;

    private TextView totalWO, totalEx, totalTime, avgTime;
    private int totalWorkouts, totalExercises, totalTimeWorkingOut;

    private ArrayList<ImageView> backMuscleImages;

    private CollectionReference workoutCollection;

    public ExerciseAnalysisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExerciseAnalysisFragment.
     */
    public static ExerciseAnalysisFragment newInstance(String email) {
        ExerciseAnalysisFragment fragment = new ExerciseAnalysisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercise_analysis, container, false);

        counter = 0;
        initializeStrain();
        timeFrames = new ArrayList<>();
        timeFrames.add("Now (4 days)");
        timeFrames.add("Current Week");
        timeFrames.add("Current Month");
        timeFrames.add("Current Year");

        ArrayList<Workout> listOfAnalyzedWorkouts = new ArrayList<>();

        lookingFront = true;

        flipImage = rootView.findViewById(R.id.flipViewButton);
        flipImage.setOnClickListener(this);
        progressBar = rootView.findViewById(R.id.workoutAnalysisProgressBar);
        backButton = rootView.findViewById(R.id.woa_prev_button);
        backButton.setOnClickListener(this);

        frontMuscleImages = new ArrayList<>();

        frontView = rootView.findViewById(R.id.frontViewImage);
        bicepsFront = rootView.findViewById(R.id.bicepsFrontImage);
        frontMuscleImages.add(bicepsFront);
        pecsFront = rootView.findViewById(R.id.pecsFrontImage);
        frontMuscleImages.add(pecsFront);
        calvesFront = rootView.findViewById(R.id.calvesFrontImage);
        frontMuscleImages.add(calvesFront);
        quadsFront = rootView.findViewById(R.id.quadsFrontImage);
        frontMuscleImages.add(quadsFront);
        forearmsFront = rootView.findViewById(R.id.forearmsFrontImage);
        frontMuscleImages.add(forearmsFront);
        absFront = rootView.findViewById(R.id.absFrontImage);
        frontMuscleImages.add(absFront);
        shouldersFront = rootView.findViewById(R.id.shouldersFrontImage);
        frontMuscleImages.add(shouldersFront);
        trapsFront = rootView.findViewById(R.id.trapsFrontImage);
        frontMuscleImages.add(trapsFront);

        backMuscleImages = new ArrayList<>();
        backView = rootView.findViewById(R.id.backViewImage);
        glutsBack = rootView.findViewById(R.id.glutesBackImage);
        backMuscleImages.add(glutsBack);
        calvesBack = rootView.findViewById(R.id.calvesBackImage);
        backMuscleImages.add(calvesBack);
        hamsBack = rootView.findViewById(R.id.hamsBackImage);
        backMuscleImages.add(hamsBack);
        lowerBackBack = rootView.findViewById(R.id.lowerBackImage);
        backMuscleImages.add(lowerBackBack);
        latsBack = rootView.findViewById(R.id.latsBackImage);
        backMuscleImages.add(latsBack);
        trapsBack = rootView.findViewById(R.id.trapsBackImage);
        backMuscleImages.add(trapsBack);
        upperTrapsBack = rootView.findViewById(R.id.upperTrapsBackImage);
        backMuscleImages.add(upperTrapsBack);
        forearmsBack = rootView.findViewById(R.id.forearmsBackImage);
        backMuscleImages.add(forearmsBack);
        trisBack = rootView.findViewById(R.id.trisBackImage);
        backMuscleImages.add(trisBack);
        shouldersBack = rootView.findViewById(R.id.shouldersBackImage);
        backMuscleImages.add(shouldersBack);

        for (ImageView i : backMuscleImages) {
            i.setVisibility(View.INVISIBLE);
        }
        backView.setVisibility(View.INVISIBLE);

        getWorkoutCollection();
        colorMusclesByStrain();

        totalTime = rootView.findViewById(R.id.woa_total_time_text);
        totalEx = rootView.findViewById(R.id.woa_total_exercises_text);
        totalWO = rootView.findViewById(R.id.woa_total_workouts_text);
        avgTime = rootView.findViewById(R.id.woa_avg_wo_time_text);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == flipImage.getId()) {
            if (lookingFront) {
                for (ImageView i : backMuscleImages) {
                    i.setVisibility(View.VISIBLE);
                }
                for (ImageView i : frontMuscleImages) {
                    i.setVisibility(View.INVISIBLE);
                }
                frontView.setVisibility(View.INVISIBLE);
                backView.setVisibility(View.VISIBLE);
            } else {
                for (ImageView i : backMuscleImages) {
                    i.setVisibility(View.INVISIBLE);
                }
                for (ImageView i : frontMuscleImages) {
                    i.setVisibility(View.VISIBLE);
                }
                frontView.setVisibility(View.VISIBLE);
                backView.setVisibility(View.INVISIBLE);
            }
            lookingFront = !lookingFront;
        } else if (v.getId() == backButton.getId()) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .commit();
        }
    }

    private void getMuscleStrain() {
        flipImage.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        initializeStrain();
        counter = 0;
        Calendar cal = Calendar.getInstance();

        int queryDay = cal.get(Calendar.DAY_OF_MONTH);
        int queryMonth = cal.get(Calendar.MONTH) + 1;
        int queryYear = cal.get(Calendar.YEAR);

        AtomicInteger resultCounter = new AtomicInteger();

        while (counter < 4) {
            int factor;
            if (counter == 0 || counter == 1) {
                factor = 8;
            } else if (counter == 2) {
                factor = 4;
            } else {
                factor = 2;
            }

            workoutCollection
                    .whereEqualTo("day", queryDay)
                    .whereEqualTo("month", queryMonth)
                    .whereEqualTo("year", queryYear).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot d : task.getResult().getDocuments()) {
                                totalWorkouts += 1;
                                List<String> exerciseNames = (List<String>) d.get("exercises");
                                List<Exercise> exercises = exerciseNames
                                        .stream()
                                        .map(Exercise::valueOf).collect(Collectors.toList());
                                for (Exercise ex : exercises) {
                                    updateStrain(ex, factor * 1.0);
                                    totalExercises += 1;
                                }
                                totalTimeWorkingOut +=
                                        (d.getLong("endHour") - d.getLong("startHour")) * 60
                                                + d.getLong("endMinute") - d.getLong("startMinute");
                            }
                            resultCounter.addAndGet(1);

                            // done getting the workout for the last day;
                            if (resultCounter.get() >= 4) {
                                Log.d("assf", "getting last");
                                colorMusclesByStrain();
                                flipImage.setEnabled(true);
                                progressBar.setVisibility(View.INVISIBLE);
                                setAnalysisText();
                            }
                        }
                    });

            cal.setTimeInMillis(cal.getTimeInMillis() - 24 * 60 * 60 * 1000);

            queryDay = cal.get(Calendar.DAY_OF_MONTH);
            queryMonth = cal.get(Calendar.MONTH) + 1;
            queryYear = cal.get(Calendar.YEAR);

            counter++;
        }
    }

    private void updateStrain(Exercise exercise, Double factor) {
        MuscleGroup muscleGroup = exercise.getMuscleGroup();
        strain.put(muscleGroup, strain.getOrDefault(muscleGroup, 0.0) + factor);
    }

    private void getWorkoutCollection() {
        usersCollection.whereEqualTo("email", email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                workoutCollection =
                        task.getResult().getDocuments().get(0)
                                .getReference().collection("workouts");
                spinner = getActivity().findViewById(R.id.exerciseAnalysisSpinner);
                spinner.setOnItemSelectedListener(this);
                spinner.setAdapter(new ArrayAdapter<>(getContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        timeFrames));
            }
        });
    }

    private void initializeStrain() {
        totalWorkouts = 0;
        totalExercises = 0;
        totalTimeWorkingOut = 0;

        for (MuscleGroup group : MuscleGroup.values()) {
            strain.put(group, 0.0);
        }
    }

    private int strainToAlpha(MuscleGroup muscleGroup) {
        return (int) Math.min(255L,
                Math.round(strain.getOrDefault(muscleGroup, 0.0) / 70.0 * 255));
    }

    private void colorMusclesByStrain() {
        trapsFront.getDrawable().setAlpha(strainToAlpha(MuscleGroup.UPPER_TRAPS));
        upperTrapsBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.UPPER_TRAPS));
        trapsBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.TRAPS));
        pecsFront.getDrawable().setAlpha(strainToAlpha(MuscleGroup.PECS));
        shouldersFront.getDrawable().setAlpha(strainToAlpha(MuscleGroup.SHOULDERS));
        shouldersBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.SHOULDERS));
        absFront.getDrawable().setAlpha(strainToAlpha(MuscleGroup.ABS));
        bicepsFront.getDrawable().setAlpha(strainToAlpha(MuscleGroup.BICEPS));
        quadsFront.getDrawable().setAlpha(strainToAlpha(MuscleGroup.QUADS));
        calvesFront.getDrawable().setAlpha(strainToAlpha(MuscleGroup.CALVES));
        calvesBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.CALVES));
        forearmsFront.getDrawable().setAlpha(strainToAlpha(MuscleGroup.FOREARMS));
        forearmsBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.FOREARMS));
        latsBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.LATS));
        trisBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.TRICEPS));
        lowerBackBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.LOWER_BACK));
        glutsBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.GLUTS));
        hamsBack.getDrawable().setAlpha(strainToAlpha(MuscleGroup.HAMSTRINGS));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            getMuscleStrain();
        } else if (position == 1) {
            getCurrentWeekStrain();
        } else if (position == 2) {
            getCurrentMonthStrain();
        } else if (position == 3) {
            getCurrentYearStrain();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinner.setSelection(0);
    }

    private void getCurrentMonthStrain() {
        progressBar.setVisibility(View.VISIBLE);
        spinner.setEnabled(false);
        flipImage.setEnabled(false);
        initializeStrain();

        workoutCollection
                .whereEqualTo("month", Calendar.getInstance().get(Calendar.MONTH) + 1)
                .whereEqualTo("year", Calendar.getInstance().get(Calendar.YEAR))
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot d : task.getResult().getDocuments()) {
                            totalWorkouts += 1;
                            List<String> exerciseNames = (List<String>) d.get("exercises");
                            List<Exercise> exercises = exerciseNames
                                    .stream()
                                    .map(Exercise::valueOf).collect(Collectors.toList());
                            for (Exercise ex : exercises) {
                                updateStrain(ex, 1.628);
                                totalExercises += 1;
                            }
                            totalTimeWorkingOut +=
                                    (d.getLong("endHour") - d.getLong("startHour")) * 60
                                            + d.getLong("endMinute") - d.getLong("startMinute");
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        spinner.setEnabled(true);
                        flipImage.setEnabled(true);
                        colorMusclesByStrain();
                        setAnalysisText();
                    }
                });
    }

    private void getCurrentYearStrain() {
        progressBar.setVisibility(View.VISIBLE);
        spinner.setEnabled(false);
        flipImage.setEnabled(false);
        initializeStrain();

        workoutCollection
                .whereEqualTo("year", Calendar.getInstance().get(Calendar.YEAR))
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot d : task.getResult().getDocuments()) {
                            totalWorkouts += 1;
                            List<String> exerciseNames = (List<String>) d.get("exercises");
                            List<Exercise> exercises = exerciseNames
                                    .stream()
                                    .map(Exercise::valueOf).collect(Collectors.toList());
                            for (Exercise ex : exercises) {
                                updateStrain(ex, 0.1338);
                                totalExercises += 1;
                            }
                            totalTimeWorkingOut +=
                                    (d.getLong("endHour") - d.getLong("startHour")) * 60
                                            + d.getLong("endMinute") - d.getLong("startMinute");
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        spinner.setEnabled(true);
                        flipImage.setEnabled(true);
                        colorMusclesByStrain();
                        setAnalysisText();
                    }
                });
    }

    private void getCurrentWeekStrain() {
        progressBar.setVisibility(View.VISIBLE);
        spinner.setEnabled(false);
        flipImage.setEnabled(false);
        initializeStrain();

        workoutCollection
                .whereEqualTo("year", Calendar.getInstance().get(Calendar.YEAR))
                .whereEqualTo("week", Calendar.getInstance().get(Calendar.WEEK_OF_YEAR))
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot d : task.getResult().getDocuments()) {
                            totalWorkouts += 1;
                            List<String> exerciseNames = (List<String>) d.get("exercises");
                            List<Exercise> exercises = exerciseNames
                                    .stream()
                                    .map(Exercise::valueOf).collect(Collectors.toList());
                            for (Exercise ex : exercises) {
                                updateStrain(ex, 6.9);
                                totalExercises += 1;
                            }
                            this.totalTimeWorkingOut +=
                                    (d.getLong("endHour") - d.getLong("startHour")) * 60
                                            + d.getLong("endMinute") - d.getLong("startMinute");
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        spinner.setEnabled(true);
                        flipImage.setEnabled(true);
                        colorMusclesByStrain();
                        setAnalysisText();
                    }
                });
    }

    private void setAnalysisText() {
        int hour = totalTimeWorkingOut / 60;
        int minutes = totalTimeWorkingOut % 60;

        totalWO.setText(String.valueOf(totalWorkouts));
        totalEx.setText(String.valueOf(totalExercises));
        totalTime.setText(String.format(Locale.getDefault(), "%d:%02d", hour, minutes));
        avgTime.setText(String.format(Locale.getDefault(), "%d min", (int) Math.round(
                totalTimeWorkingOut * 1.0 / totalWorkouts)));
    }
}