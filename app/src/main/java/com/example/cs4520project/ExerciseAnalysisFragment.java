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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

    private Double upperTrapStrain, shoulderStrain, pecStrain, bicepsStrain, forearmStrain,
            quadStrain, calveStrain, trapStrain, triStrain, lowerBackStrain, glutStrain,
            hamsStrain, latsStrain, absStrain;

    private ImageView flipImage;

    private ImageView frontView, trapsFront, pecsFront, absFront, quadsFront, calvesFront,
            shouldersFront, bicepsFront, forearmsFront;

    private ArrayList<ImageView> frontMuscleImages;

    private ImageView backView, upperTrapsBack, trapsBack, shouldersBack, trisBack, forearmsBack,
            latsBack, lowerBackBack, glutsBack, hamsBack, calvesBack;

    private ProgressBar progressBar;
    private Spinner spinner;

    private ArrayList<ImageView> backMuscleImages;

    private CollectionReference workoutCollection;

    private ArrayList<Workout> listOfAnalyzedWorkouts;

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
            this.email = getArguments().getString(ARG_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercise_analysis, container, false);

        this.counter = 0;
        this.initializeStrain();
        this.timeFrames = new ArrayList<String>();
        this.timeFrames.add("Today's Status");
        this.timeFrames.add("Current Week");
        this.timeFrames.add("Current Month");
        this.timeFrames.add("Current Year");

        this.listOfAnalyzedWorkouts = new ArrayList<>();

        this.lookingFront = true;

        this.flipImage = rootView.findViewById(R.id.flipViewButton);
        this.flipImage.setOnClickListener(this);
        this.progressBar = rootView.findViewById(R.id.workoutAnalysisProgressBar);

        this.frontMuscleImages = new ArrayList<>();

        this.frontView = rootView.findViewById(R.id.frontViewImage);
        this.bicepsFront = rootView.findViewById(R.id.bicepsFrontImage);
        this.frontMuscleImages.add(bicepsFront);
        this.pecsFront = rootView.findViewById(R.id.pecsFrontImage);
        this.frontMuscleImages.add(pecsFront);
        this.calvesFront = rootView.findViewById(R.id.calvesFrontImage);
        this.frontMuscleImages.add(calvesFront);
        this.quadsFront = rootView.findViewById(R.id.quadsFrontImage);
        this.frontMuscleImages.add(quadsFront);
        this.forearmsFront = rootView.findViewById(R.id.forearmsFrontImage);
        this.frontMuscleImages.add(forearmsFront);
        this.absFront = rootView.findViewById(R.id.absFrontImage);
        this.frontMuscleImages.add(absFront);
        this.shouldersFront = rootView.findViewById(R.id.shouldersFrontImage);
        this.frontMuscleImages.add(shouldersFront);
        this.trapsFront = rootView.findViewById(R.id.trapsFrontImage);
        this.frontMuscleImages.add(trapsFront);

//        for (ImageView i : this.frontMuscleImages) {
//            i.setVisibility(View.INVISIBLE);
//        }

        this.backMuscleImages = new ArrayList<>();
        this.backView = rootView.findViewById(R.id.backViewImage);
        this.glutsBack = rootView.findViewById(R.id.glutesBackImage);
        this.backMuscleImages.add(this.glutsBack);
        this.calvesBack = rootView.findViewById(R.id.calvesBackImage);
        this.backMuscleImages.add(this.calvesBack);
        this.hamsBack = rootView.findViewById(R.id.hamsBackImage);
        this.backMuscleImages.add(this.hamsBack);
        this.lowerBackBack = rootView.findViewById(R.id.lowerBackImage);
        this.backMuscleImages.add(this.lowerBackBack);
        this.latsBack = rootView.findViewById(R.id.latsBackImage);
        this.backMuscleImages.add(this.latsBack);
        this.trapsBack = rootView.findViewById(R.id.trapsBackImage);
        this.backMuscleImages.add(this.trapsBack);
        this.upperTrapsBack = rootView.findViewById(R.id.upperTrapsBackImage);
        this.backMuscleImages.add(this.upperTrapsBack);
        this.forearmsBack = rootView.findViewById(R.id.forearmsBackImage);
        this.backMuscleImages.add(this.forearmsBack);
        this.trisBack = rootView.findViewById(R.id.trisBackImage);
        this.backMuscleImages.add(this.trisBack);
        this.shouldersBack = rootView.findViewById(R.id.shouldersBackImage);
        this.backMuscleImages.add(this.shouldersBack);

        for (ImageView i : this.backMuscleImages) {
            i.setVisibility(View.INVISIBLE);
        }
        this.backView.setVisibility(View.INVISIBLE);

        this.getWorkoutCollection();
        this.colorMusclesByStrain();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.flipImage.getId()) {
            if (this.lookingFront) {
                for (ImageView i : this.backMuscleImages) {
                    i.setVisibility(View.VISIBLE);
                }
                for (ImageView i : this.frontMuscleImages) {
                    i.setVisibility(View.INVISIBLE);
                }
                this.frontView.setVisibility(View.INVISIBLE);
                this.backView.setVisibility(View.VISIBLE);
            } else {
                for (ImageView i : this.backMuscleImages) {
                    i.setVisibility(View.INVISIBLE);
                }
                for (ImageView i : this.frontMuscleImages) {
                    i.setVisibility(View.VISIBLE);
                }
                this.frontView.setVisibility(View.VISIBLE);
                this.backView.setVisibility(View.INVISIBLE);
            }
            this.lookingFront = !this.lookingFront;
        }
    }

    private void getMuscleStrain() {
        this.flipImage.setEnabled(false);
        this.progressBar.setVisibility(View.VISIBLE);

        this.initializeStrain();
        this.counter = 0;
        Calendar cal = Calendar.getInstance();

        int queryDay = cal.get(Calendar.DAY_OF_MONTH);
        int queryMonth = cal.get(Calendar.MONTH) + 1;
        int queryYear = cal.get(Calendar.YEAR);

        AtomicInteger resultCounter = new AtomicInteger();

        while (this.counter < 4) {
            int factor;

            if (this.counter == 0 || this.counter == 1) {
                factor = 8;
            } else if (this.counter == 2) {
                factor = 4;
            } else {
                factor = 2;
            }

            this.workoutCollection
                    .whereEqualTo("day", queryDay)
                    .whereEqualTo("month", queryMonth)
                    .whereEqualTo("year", queryYear).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot d : task.getResult().getDocuments()) {
                                List<String> exerciseNames = (List<String>) d.get("exercises");
                                List<Exercise> exercises = exerciseNames
                                        .stream()
                                        .map(Exercise::valueOf).collect(Collectors.toList());
                                for (Exercise ex: exercises){
                                    this.updateStrain(ex, factor*1.0);
                                }
                            }
                            resultCounter.addAndGet(1);

                            // done getting the workout for the last day;
                            if (resultCounter.get() >= 4) {
                                Log.d("assf", "getting last");
                                this.colorMusclesByStrain();
                                this.flipImage.setEnabled(true);
                                this.progressBar.setVisibility(View.INVISIBLE);
                            }

                        }
                    });

            cal.setTimeInMillis(cal.getTimeInMillis() - 24 * 60 * 60 * 1000);

            queryDay = cal.get(Calendar.DAY_OF_MONTH);
            queryMonth = cal.get(Calendar.MONTH) + 1;
            queryYear = cal.get(Calendar.YEAR);

            this.counter++;
        }
    }

    private void updateStrain(Exercise exercise, Double factor){
        if (exercise.getMuscleGroup() == MuscleGroup.ABS){
            this.absStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.TRAPS) {
            this.trapStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.SHOULDERS) {
            this.shoulderStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.PECS) {
            this.pecStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.BICEPS) {
            this.bicepsStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.FOREARMS) {
            this.forearmStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.QUADS) {
            this.quadStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.CALVES) {
            this.calveStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.TRICEPS) {
            this.triStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.LATS) {
            this.latsStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.UPPER_TRAPS) {
            this.upperTrapStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.LOWER_BACK) {
            this.lowerBackStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.GLUTS) {
            this.glutStrain += factor;
        } else if (exercise.getMuscleGroup() == MuscleGroup.HAMSTRINGS) {
            this.hamsStrain += factor;
        }
    }

    private void getWorkoutCollection() {
        usersCollection.whereEqualTo("email", this.email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                this.workoutCollection =
                        task.getResult().getDocuments().get(0)
                                .getReference().collection("workouts");
                this.spinner = getActivity().findViewById(R.id.exerciseAnalysisSpinner);
                this.spinner.setOnItemSelectedListener(this);
                this.spinner.setAdapter(new ArrayAdapter<>(this.getContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        this.timeFrames));;

            }
        });
    }

    private void initializeStrain() {
        this.upperTrapStrain = 0.0;
        this.shoulderStrain = 0.0;
        this.pecStrain = 0.0;
        this.bicepsStrain = 0.0;
        this.forearmStrain = 0.0;
        this.quadStrain = 0.0;
        this.calveStrain = 0.0;
        this.trapStrain = 0.0;
        this.triStrain = 0.0;
        this.lowerBackStrain = 0.0;
        this.glutStrain = 0.0;
        this.hamsStrain = 0.0;
        this.latsStrain = 0.0;
        this.absStrain = 0.0;
    }

    private void colorMusclesByStrain() {
        this.trapsFront.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.upperTrapStrain) / 70.0 * 255)));
        this.upperTrapsBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.upperTrapStrain) / 70.0 * 255)));
        this.pecsFront.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.pecStrain) / 70.0 * 255)));
        this.shouldersFront.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.shoulderStrain) / 70.0 * 255)));
        this.shouldersBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.shoulderStrain) / 70.0 * 255)));
        this.absFront.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.absStrain) / 70.0 * 255)));
        this.bicepsFront.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.bicepsStrain) / 70.0 * 255)));
        this.quadsFront.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.quadStrain) / 70.0 * 255)));
        this.calvesFront.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.calveStrain) / 70.0 * 255)));
        this.calvesBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.calveStrain) / 70.0 * 255)));
        this.forearmsFront.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.forearmStrain) / 70.0 * 255)));
        this.forearmsBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.forearmStrain) / 70.0 * 255)));
        this.calvesFront.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.calveStrain) / 70.0 * 255)));
        this.trapsBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.trapStrain) / 70.0 * 255)));
        this.latsBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.latsStrain) / 70.0 * 255)));
        this.trisBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.triStrain) / 70.0 * 255)));
        this.lowerBackBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.lowerBackStrain) / 70.0 * 255)));
        this.glutsBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.glutStrain) / 70.0 * 255)));
        this.hamsBack.getDrawable().setAlpha(Math.min(255,
                (int) Math.round(((double) this.hamsStrain) / 70.0 * 255)));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
            this.getMuscleStrain();
        } else if(position == 1) {
            this.getCurrentWeekStrain();
        } else if (position == 2) {
            this.getCurrentMonthStrain();
        } else if (position == 3){
            this.getCurrentYearStrain();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.spinner.setSelection(0);
    }


    private void getCurrentMonthStrain(){
        this.progressBar.setVisibility(View.VISIBLE);
        this.spinner.setEnabled(false);
        this.flipImage.setEnabled(false);
        this.initializeStrain();

        workoutCollection
                .whereEqualTo("month",Calendar.getInstance().get(Calendar.MONTH)+1 )
                .whereEqualTo("year",Calendar.getInstance().get(Calendar.YEAR) )
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot d: task.getResult().getDocuments()){
                            List<String> exerciseNames = (List<String>) d.get("exercises");
                            List<Exercise> exercises = exerciseNames
                                    .stream()
                                    .map(Exercise::valueOf).collect(Collectors.toList());
                            for (Exercise ex: exercises){
                                this.updateStrain(ex,1.628);
                            }
                        }
                        this.progressBar.setVisibility(View.INVISIBLE);
                        this.spinner.setEnabled(true);
                        this.flipImage.setEnabled(true);
                        this.colorMusclesByStrain();

                    }
                });
    }

    private void getCurrentYearStrain(){
        this.progressBar.setVisibility(View.VISIBLE);
        this.spinner.setEnabled(false);
        this.flipImage.setEnabled(false);
        this.initializeStrain();

        workoutCollection
                .whereEqualTo("year",Calendar.getInstance().get(Calendar.YEAR) )
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot d: task.getResult().getDocuments()){
                            List<String> exerciseNames = (List<String>) d.get("exercises");
                            List<Exercise> exercises = exerciseNames
                                    .stream()
                                    .map(Exercise::valueOf).collect(Collectors.toList());
                            for (Exercise ex: exercises){
                                this.updateStrain(ex,0.1338);
                            }
                        }
                        this.progressBar.setVisibility(View.INVISIBLE);
                        this.spinner.setEnabled(true);
                        this.flipImage.setEnabled(true);
                        this.colorMusclesByStrain();

                    }
                });
    }

    private void getCurrentWeekStrain(){
        this.progressBar.setVisibility(View.VISIBLE);
        this.spinner.setEnabled(false);
        this.flipImage.setEnabled(false);
        this.initializeStrain();

        workoutCollection
                .whereEqualTo("year",Calendar.getInstance().get(Calendar.YEAR) )
                .whereEqualTo("week",Calendar.getInstance().get(Calendar.WEEK_OF_YEAR))
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot d: task.getResult().getDocuments()){
                            List<String> exerciseNames = (List<String>) d.get("exercises");
                            List<Exercise> exercises = exerciseNames
                                    .stream()
                                    .map(Exercise::valueOf).collect(Collectors.toList());
                            for (Exercise ex: exercises){
                                this.updateStrain(ex,6.9);
                            }
                        }
                        this.progressBar.setVisibility(View.INVISIBLE);
                        this.spinner.setEnabled(true);
                        this.flipImage.setEnabled(true);
                        this.colorMusclesByStrain();

                    }
                });
    }

}
