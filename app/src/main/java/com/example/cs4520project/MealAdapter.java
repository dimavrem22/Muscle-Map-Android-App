package com.example.cs4520project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private final List<Meal> meals;

    public MealAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.getMealName().setText(meal.getName());
        holder.getMealType().setText(meal.getMealType().name());

        holder.getMealCalories().setText(R.string.meal_calories);
        holder.getMealCalories().append(": " + meal.getCalories());

        holder.getMealCarbs().setText(R.string.meal_carbs);
        holder.getMealCarbs().append(": " + meal.getCarbs());

        holder.getMealProtein().setText(R.string.meal_protein);
        holder.getMealProtein().append(": " + meal.getProtein());

        holder.getMealSodium().setText(R.string.meal_sodium);
        holder.getMealSodium().append(": " + meal.getSodium());

        holder.getMealTotalFat().setText(R.string.meal_total_fat);
        holder.getMealTotalFat().append(": " + meal.getTotalFat());

        holder.getMealNotes().setText(R.string.meal_notes);
        holder.getMealNotes().append(": " + meal.getAdditionalNotes());

        holder.getDeleteButton().setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mealName;
        private final TextView mealType;
        private final TextView mealCalories;
        private final TextView mealCarbs;
        private final TextView mealProtein;
        private final TextView mealSodium;
        private final TextView mealTotalFat;
        private final TextView mealNotes;
        private final ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.textViewMealName);
            mealType = itemView.findViewById(R.id.textViewMealType);
            mealCalories = itemView.findViewById(R.id.textViewMealCalories);
            mealCarbs = itemView.findViewById(R.id.textViewMealCarbs);
            mealProtein = itemView.findViewById(R.id.textViewMealProtein);
            mealSodium = itemView.findViewById(R.id.textViewMealSodium);
            mealTotalFat = itemView.findViewById(R.id.textViewMealTotalFat);
            mealNotes = itemView.findViewById(R.id.textViewMealNotes);
            deleteButton = itemView.findViewById(R.id.deleteMeal);
        }

        public TextView getMealName() {
            return mealName;
        }

        public TextView getMealType() {
            return mealType;
        }

        public TextView getMealCalories() {
            return mealCalories;
        }

        public TextView getMealCarbs() {
            return mealCarbs;
        }

        public TextView getMealProtein() {
            return mealProtein;
        }

        public TextView getMealSodium() {
            return mealSodium;
        }

        public TextView getMealTotalFat() {
            return mealTotalFat;
        }

        public TextView getMealNotes() {
            return mealNotes;
        }

        public ImageButton getDeleteButton() {
            return deleteButton;
        }
    }
}