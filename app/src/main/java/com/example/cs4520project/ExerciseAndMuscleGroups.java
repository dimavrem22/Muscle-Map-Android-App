package com.example.cs4520project;

import java.util.ArrayList;
import java.util.HashMap;

public class ExerciseAndMuscleGroups {

    private static HashMap<String, ArrayList<String>> muscleGroupToExercise;

    public ExerciseAndMuscleGroups() {
        muscleGroupToExercise = new HashMap<String, ArrayList<String>>();

        ArrayList<String> hamstringExercises = new ArrayList<>();
        hamstringExercises.add("Squats");
        hamstringExercises.add("DeadLifts");
        muscleGroupToExercise.put("HamString", hamstringExercises);

        ArrayList<String> pecsExercises = new ArrayList<>();
        pecsExercises.add("Bench Press");
        pecsExercises.add("Dips");
        muscleGroupToExercise.put("Pecs", pecsExercises);

        ArrayList<String> calvesExercises = new ArrayList<>();
        calvesExercises.add("Jump Rope");
        calvesExercises.add("Dumbbell Jump Squat");
        muscleGroupToExercise.put("Calves", calvesExercises);

        ArrayList<String> backExercises = new ArrayList<>();
        backExercises.add("DeadLifts");
        backExercises.add("Pull-ups");
        backExercises.add("Chin-ups");
        muscleGroupToExercise.put("Back", backExercises);

        ArrayList<String> shouldersExercises = new ArrayList<>();
        shouldersExercises.add("OverHead Press");
        muscleGroupToExercise.put("Shoulders", shouldersExercises);

        ArrayList<String> bicepsExercises = new ArrayList<>();
        bicepsExercises.add("Close Grip Pull Ups");
        bicepsExercises.add("Dumbbell Curls");
        muscleGroupToExercise.put("Biceps", bicepsExercises);

        ArrayList<String> tricepsExercises = new ArrayList<>();
        tricepsExercises.add("Reverse Grip Bench Press");
        tricepsExercises.add("Close Grip Bench Press");
        tricepsExercises.add("Dips");
        muscleGroupToExercise.put("Triceps", tricepsExercises);

        ArrayList<String> forearmsExercises = new ArrayList<>();
        forearmsExercises.add("Wrist Curls");
        muscleGroupToExercise.put("Forearms", forearmsExercises);

        ArrayList<String> trapsExercises = new ArrayList<>();
        trapsExercises.add("DeadLifts");
        muscleGroupToExercise.put("Traps", trapsExercises);

        ArrayList<String> absExercises = new ArrayList<>();
        absExercises.add("Sit Ups");
        absExercises.add("Pull Ups");
        absExercises.add("Squats");
        muscleGroupToExercise.put("ABS", absExercises);
    }
}
