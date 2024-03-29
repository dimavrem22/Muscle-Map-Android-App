package com.example.cs4520project;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DietEnterMealFragment extends Fragment implements View.OnClickListener {
    public static final String FRAGMENT_TAG = "DIET_ENTER_MEAL_FRAGMENT";
    private ISaveMeal saveMeal;

    private Button cancelButton;

    @Override
    public void onClick(View v) {
        if (v.getId() == this.cancelButton.getId()) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction().remove(this).commit();
            onDetach();
        }
    }

    public interface ISaveMeal {
        void addMealToDB(Meal meal);
    }

    public DietEnterMealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DietEnterMealFragment.
     */
    public static DietEnterMealFragment newInstance() {
        DietEnterMealFragment fragment = new DietEnterMealFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diet_enter_meal, container, false);
        getActivity().setTitle(R.string.enter_meal_title);

        EditText editName = rootView.findViewById(R.id.editTextMealName);
        EditText editCalories = rootView.findViewById(R.id.editTextMealCalories);
        EditText editProtein = rootView.findViewById(R.id.editTextMealProtein);
        EditText editCarbs = rootView.findViewById(R.id.editTextMealCarbs);
        EditText editSodium = rootView.findViewById(R.id.editTextMealSodium);
        EditText editTotalFat = rootView.findViewById(R.id.editTextMealTotalFat);
        EditText editAdditionalNotes = rootView.findViewById(R.id.editTextMealAdditionalNotes);
        cancelButton = rootView.findViewById(R.id.new_meal_cancel_button);
        cancelButton.setOnClickListener(this);

        Spinner spinnerMealType = rootView.findViewById(R.id.spinnerMealType);
        spinnerMealType.setAdapter(new ArrayAdapter<>(rootView.getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                MealType.values()));

        Button buttonFinish = rootView.findViewById(R.id.buttonFinishEnterMeal);

        buttonFinish.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(getActivity(), "Must provide meal name", Toast.LENGTH_SHORT).show();
                return;
            }

            MealType mealType = (MealType) spinnerMealType.getSelectedItem();
            if (mealType == null) {
                Toast.makeText(getActivity(), "Must provide meal type", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer calories = handleParseInt("calories", editCalories);
            if (calories == null) {
                Toast.makeText(getActivity(), "Invalid calories", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer protein = handleParseInt("protein", editProtein);
            if (protein == null) {
                Toast.makeText(getActivity(), "Invalid protein", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer carbs = handleParseInt("carbs", editCarbs);
            if (carbs == null) {
                Toast.makeText(getActivity(), "Invalid carbs", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer sodium = handleParseInt("sodium", editSodium);
            if (sodium == null) {
                Toast.makeText(getActivity(), "Invalid sodium", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer totalFat = handleParseInt("total fat", editTotalFat);
            if (totalFat == null) {
                Toast.makeText(getActivity(), "Invalid total fat", Toast.LENGTH_SHORT).show();
                return;
            }

            String additionalNotes = editAdditionalNotes.getText().toString().trim();

            Meal meal = new Meal(name, mealType, calories, protein,
                    carbs, sodium, totalFat, additionalNotes);
            saveMeal.addMealToDB(meal);
            getActivity().getSupportFragmentManager().popBackStack();
        });

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ISaveMeal) {
            saveMeal = (ISaveMeal) context;
        } else {
            throw new RuntimeException(context + " must implement IFromFragment");
        }
    }

    // EditText integer parsing convenience method.
    Integer handleParseInt(String name, EditText editText) {
        String s = editText.getText().toString().trim();
        if (s.isEmpty()) {
            Toast.makeText(getActivity(), "Must provide " + name, Toast.LENGTH_SHORT).show();
            return null;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).EnableUI(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).EnableUI(false);
    }
}