package com.example.cs4520project;

import java.io.Serializable;

public class Meal implements Serializable {
    private String name;
    private MealType mealType;

    private int calories;
    private int protein;
    private int carbs;
    private int sodium;
    private int totalFat;
    private String additionalNotes;
    private int day;
    private int month;

    public Meal(String name, MealType mealType, int calories, int protein, int carbs, int sodium, int totalFat, String additionalNotes) {
        this.name = name;
        this.mealType = mealType;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.sodium = sodium;
        this.totalFat = totalFat;
        this.additionalNotes = additionalNotes;
    }

    public Meal() {

    }

    public String getName() {
        return name;
    }

    public MealType getMealType() {
        return mealType;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getSodium() {
        return sodium;
    }

    public int getTotalFat() {
        return totalFat;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
