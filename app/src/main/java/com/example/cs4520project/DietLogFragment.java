package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DietLogFragment extends Fragment {
    public static final String FRAGMENT_TAG = "DIET_FRAGMENT";

    public DietLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DietLogFragment.
     */
    public static DietLogFragment newInstance() {
        DietLogFragment fragment = new DietLogFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_diet_log, container, false);

        Button enterMeal = rootView.findViewById(R.id.buttonEnterMeal);

        enterMeal.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, DietEnterMealFragment.newInstance(),
                            DietEnterMealFragment.FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}