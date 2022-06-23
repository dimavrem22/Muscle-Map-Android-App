package com.example.cs4520project;

public class Meal {
    private String name;
    private int hour;
    private int minute;

    private int calories;
    private int protein;
    private int carbs;
    private int sodium;
    private int totalFat;
    private String additionalNotes;

    public Meal(String name, int hour, int min, int calories, int protein, int carbs, int sodium, int totalFat, String additionalNotes) {
        this.name = name;
        this.hour = hour;
        this.minute = min;
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

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
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
}
