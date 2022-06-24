package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DietLogFragment extends Fragment {
    public static final String FRAGMENT_TAG = "DIET_LOG_FRAGMENT";
    private static final String ARG_EMAIL = "ARG_EMAIL";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection("users");
    private CollectionReference mealsCollection;

    private String email;
    private final List<Meal> meals = new ArrayList<>();
    private MealAdapter mealAdapter;

    public DietLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DietLogFragment.
     */
    public static DietLogFragment newInstance(String email) {
        DietLogFragment fragment = new DietLogFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diet_log, container, false);

        setMealCollection(email);

        RecyclerView recentMeals = rootView.findViewById(R.id.recyclerViewRecentMeals);
        mealAdapter = new MealAdapter(meals);
        recentMeals.setAdapter(mealAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recentMeals.setLayoutManager(layoutManager);
        mealAdapter.notifyDataSetChanged();

        Button enterMeal = rootView.findViewById(R.id.buttonEnterMeal);
        Button dietAnalysis = rootView.findViewById(R.id.buttonDietAnalysis);

        enterMeal.setOnClickListener(v -> getActivity()
                .getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, DietEnterMealFragment.newInstance(),
                        DietEnterMealFragment.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit());

        dietAnalysis.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, DietAnalysisFragment.newInstance(meals),
                        DietAnalysisFragment.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit());

        populateMeals();

        return rootView;
    }

    private void setMealCollection(String email) {
        usersCollection.whereEqualTo("email", email)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mealsCollection = task.getResult().getDocuments().get(0)
                                .getReference().collection("meals");
                        mealsCollection.addSnapshotListener((value, error) -> populateMeals());
                        populateMeals();
                    }
                });
    }

    private void populateMeals() {
        if (mealsCollection != null) {
            mealsCollection
                    .get().addOnCompleteListener(task -> {
                        meals.clear();
                        for (DocumentSnapshot d : task.getResult().getDocuments()) {
                            String name = d.getString("name");
                            MealType mealType = MealType.valueOf(d.getString("type"));
                            int day = Math.toIntExact(d.getLong("day"));
                            int month = Math.toIntExact(d.getLong("month"));
                            int year = Math.toIntExact(d.getLong("year"));
                            int calories = Math.toIntExact(d.getLong("calories"));
                            int protein = Math.toIntExact(d.getLong("protein"));
                            int carbs = Math.toIntExact(d.getLong("carbs"));
                            int sodium = Math.toIntExact(d.getLong("sodium"));
                            int totalFat = Math.toIntExact(d.getLong("totalFat"));
                            String additionalNotes = d.getString("additionalNotes");
                            Meal meal = new Meal(name, mealType, calories,
                                    protein, carbs, sodium, totalFat, additionalNotes);
                            meal.setDate(LocalDate.of(year, month, day));
                            meals.add(meal);
                        }
                        mealAdapter.notifyDataSetChanged();
                    });
        }
    }
}