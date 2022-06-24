package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.firestore.CollectionReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DietAnalysisFragment extends Fragment {
    public static final String FRAGMENT_TAG = "DIET_ANALYSIS_FRAGMENT";
    private static final String ARG_MEALS = "MEALS";
    private static final Calendar calendar = Calendar.getInstance();

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

        chartSelect.setAdapter(new ArrayAdapter<>(rootView.getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                ChartType.values()));
        chartSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChartType chartType = (ChartType) parent.getItemAtPosition(position);
                switch (chartType) {
                    case CALORIES_PAST_WEEK:
                        List<Integer> calories = caloriesPastWeek(LocalDate.now());
                        List<Entry> entries = new ArrayList<>();
                        for (int i = 0; i < calories.size(); i++) {
                            entries.add(new Entry(i, calories.get(i)));
                        }
                        LineDataSet dataSet = new LineDataSet(entries, "Total calories per day");
                        LineData data = new LineData(dataSet);
                        chart.setData(data);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView averageBreakfastCalories = rootView.findViewById(R.id.textViewAvgBreakfastCalories);
        averageBreakfastCalories.setText(Double.toString(averageCalories(MealType.BREAKFAST)));

        TextView averageLunchCalories = rootView.findViewById(R.id.textViewAvgLunchCalories);
        averageLunchCalories.setText(Double.toString(averageCalories(MealType.LUNCH)));

        TextView averageDinnerCalories = rootView.findViewById(R.id.textViewAvgDinnerCalories);
        averageDinnerCalories.setText(Double.toString(averageCalories(MealType.DINNER)));

        TextView averageSnackCalories = rootView.findViewById(R.id.textViewAvgSnackCalories);
        averageSnackCalories.setText(Double.toString(averageCalories(MealType.SNACK)));

        TextView averageCalories = rootView.findViewById(R.id.textViewAvgCalories);
        averageCalories.setText(Double.toString(averageCalories()));

        return rootView;
    }

    private List<Integer> caloriesPastWeek(LocalDate date) {
        List<Integer> week = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            week.add(caloriesOnDate(date));
            date = date.minusDays(1);
        }
        Collections.reverse(week);
        return week;
    }

    private int caloriesOnDate(LocalDate date) {
        return meals.stream()
                .filter(meal -> date.equals(meal.getDate()))
                .mapToInt(Meal::getCalories)
                .sum();
    }

    private double averageCalories(MealType mealType) {
        return meals.stream()
                .filter(meal -> meal.getMealType() == mealType)
                .mapToInt(Meal::getCalories)
                .average()
                .orElse(0.0);
    }

    private double averageCalories() {
        return meals.stream().mapToInt(Meal::getCalories).average().orElse(0.0);
    }

    enum ChartType {
        CALORIES_PAST_WEEK,
    }
}