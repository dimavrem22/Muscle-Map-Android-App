package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseStatsDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseStatsDisplay extends Fragment {
    private static final String ARG_EXERCISE_STATS = "EXERCISE_STATS";

    private ExerciseStats exerciseStats;

    public ExerciseStatsDisplay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExerciseStats.
     */
    public static ExerciseStatsDisplay newInstance(ExerciseStats exerciseStats) {
        ExerciseStatsDisplay fragment = new ExerciseStatsDisplay();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXERCISE_STATS, exerciseStats);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseStats = (ExerciseStats) getArguments().getSerializable(ARG_EXERCISE_STATS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exercise_stats, container, false);

        ImageView muscleGroupsFront = rootView.findViewById(R.id.imageViewMuscleGroupsFront);

        if (exerciseStats != null) {
            String activitySvg = exerciseStats.activityMap.generateSvg();
            InputStream inputStream = new ByteArrayInputStream(activitySvg.getBytes(StandardCharsets.UTF_8));
            Glide.with(rootView).load(inputStream).into(muscleGroupsFront);
        }

        return rootView;
    }
}