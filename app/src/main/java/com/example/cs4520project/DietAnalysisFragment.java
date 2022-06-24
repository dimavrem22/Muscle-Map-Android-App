package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DietAnalysisFragment extends Fragment {
    public static final String FRAGMENT_TAG = "DIET_ANALYSIS_FRAGMENT";
    private static final String ARG_MEALS = "MEALS";

    private List<Meal> meals;

    public DietAnalysisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DietAnalysisFragment.
     */
    public static DietAnalysisFragment newInstance(List<Meal> meals) {
        DietAnalysisFragment fragment = new DietAnalysisFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEALS, new ArrayList<>(meals));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            meals = (List<Meal>) getArguments().getSerializable(ARG_MEALS);
            meals.sort(Comparator.comparing(Meal::getDate));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diet_analysis, container, false);

        Spinner chartSelect = rootView.findViewById(R.id.dietChartSelect);
        LineChart chart = rootView.findViewById(R.id.dietAnalysisChart);

        return rootView;
    }

}