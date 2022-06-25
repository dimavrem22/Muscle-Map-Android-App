package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DietAnalysisFragment extends Fragment implements View.OnClickListener {
    public static final String FRAGMENT_TAG = "DIET_ANALYSIS_FRAGMENT";
    private static final String ARG_MEALS = "MEALS";
    private static final Calendar calendar = Calendar.getInstance();

    private List<Meal> meals;

    private ImageView backButton;

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

        this.backButton = rootView.findViewById(R.id.diet_analysis_back_button);
        this.backButton.setOnClickListener(this);

        Spinner chartSelect = rootView.findViewById(R.id.dietChartSelect);
        RelativeLayout chartLayout = rootView.findViewById(R.id.dietAnalysisChart);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        chartSelect.setAdapter(new ArrayAdapter<>(rootView.getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                ChartType.values()));
        chartSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChartType chartType = (ChartType) parent.getItemAtPosition(position);
                switch (chartType) {
                    case CALORIES_PAST_WEEK: {
                        LocalDate date = LocalDate.now();
                        LineChart lineChart = new LineChart(getContext());

                        List<Entry> entries = new ArrayList<>();
                        List<String> labels = new ArrayList<>();
                        for (int i = 0; i < 7; i++) {
                            entries.add(new Entry(i, caloriesOnDate(date)));
                            labels.add(weekAbbr(date.getDayOfWeek().getValue()));
                            date = date.minusDays(1);
                        }

                        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

                        LineDataSet dataSet = new LineDataSet(entries, "Total calories per day");
                        LineData data = new LineData(dataSet);
                        lineChart.setData(data);
                        Description description = new Description();
                        description.setText("Calories over the past 7 days");
                        lineChart.setDescription(description);
                        chartLayout.removeAllViews();
                        chartLayout.addView(lineChart, params);
                    }
                    break;
                    case OVERALL_AMOUNTS: {
                        BarChart barChart = new BarChart(getContext());
                        int calories = meals.stream().mapToInt(Meal::getCalories).sum();
                        int carbs = meals.stream().mapToInt(Meal::getCarbs).sum();
                        int protein = meals.stream().mapToInt(Meal::getProtein).sum();
                        int sodium = meals.stream().mapToInt(Meal::getSodium).sum();
                        int totalFat = meals.stream().mapToInt(Meal::getTotalFat).sum();

                        List<BarEntry> entries = new ArrayList<>();
                        entries.add(new BarEntry(0, calories));
                        entries.add(new BarEntry(1, carbs));
                        entries.add(new BarEntry(2, protein));
                        entries.add(new BarEntry(3, sodium));
                        entries.add(new BarEntry(4, totalFat));

                        List<String> labels = new ArrayList<>();
                        labels.add("Calories");
                        labels.add("Carbs");
                        labels.add("Protein");
                        labels.add("Sodium");
                        labels.add("Total Fat");

                        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

                        BarDataSet dataSet = new BarDataSet(entries, "Summed amount");
                        BarData data = new BarData(dataSet);
                        barChart.setData(data);
                        Description description = new Description();
                        description.setText("Overall amounts");
                        barChart.setDescription(description);
                        chartLayout.removeAllViews();
                        chartLayout.addView(barChart, params);
                    }
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

    private String weekAbbr(int day) {
        switch (day) {
            case 1:
                return "MON";
            case 2:
                return "TUES";
            case 3:
                return "WED";
            case 4:
                return "THUR";
            case 5:
                return "FRI";
            case 6:
                return "SAT";
            case 7:
                return "SUN";
            default:
                return "";
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.backButton.getId()){
            this.getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            this.onDetach();
        }
    }

    enum ChartType {
        CALORIES_PAST_WEEK,
        OVERALL_AMOUNTS,
    }


    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity)this.getActivity()).EnableUI(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)this.getActivity()).EnableUI(false);
    }

}