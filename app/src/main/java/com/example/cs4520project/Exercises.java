package com.example.cs4520project;

import java.util.ArrayList;

public class Exercises {
    public static ArrayList<Exercise> getExercises() {
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();

        Exercise e1 = new Exercise("Bicep Curls", "easy", "biceps");
        Exercise e2 = new Exercise("Bench Press", "medium", "pecs");
        Exercise e3 = new Exercise("Squats", "easy", "hamstring");
        Exercise e4 = new Exercise("DeadLifts", "hard", "hamstring");
        Exercise e5 = new Exercise("Dips", "medium", "pecs");
        Exercise e6 = new Exercise("Jump Rope", "easy", "calves");
        Exercise e7 = new Exercise("Pull-ips", "easy", "back");
        Exercise e8 = new Exercise("Overhead Press", "medium", "shoulders");
        Exercise e9 = new Exercise("Close Grip Bench Press", "hard", "triceps");
        Exercise e10 = new Exercise("Sit Ups", "easy", "core");

        exercises.add(e1);
        exercises.add(e2);
        exercises.add(e3);
        exercises.add(e4);
        exercises.add(e5);
        exercises.add(e6);
        exercises.add(e7);
        exercises.add(e8);
        exercises.add(e9);
        exercises.add(e10);

        return exercises;
    }
}
