package com.example.cs4520project;

import java.io.Serializable;
import java.time.LocalDate;

public class Meal implements Serializable {
    private String id;
    private LocalDate date;

    private String name;
    private MealType mealType;

    private int calories;
    private int protein;
    private int carbs;
    private int sodium;
    private int totalFat;
    private String additionalNotes;

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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}