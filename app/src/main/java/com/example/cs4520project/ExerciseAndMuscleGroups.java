package com.example.cs4520project;

import java.util.ArrayList;
import java.util.HashMap;

public class ExerciseAndMuscleGroups {
    private static HashMap<MuscleGroup, ArrayList<String>> muscleGroupToExercise;

    public ExerciseAndMuscleGroups() {
        muscleGroupToExercise = new HashMap<>();

        ArrayList<String> hamstringExercises = new ArrayList<>();
        hamstringExercises.add("Squats");
        hamstringExercises.add("DeadLifts");
        muscleGroupToExercise.put(MuscleGroup.HAMSTRINGS, hamstringExercises);

        ArrayList<String> pecsExercises = new ArrayList<>();
        pecsExercises.add("Bench Press");
        pecsExercises.add("Dips");
        muscleGroupToExercise.put(MuscleGroup.CHEST, pecsExercises);

        ArrayList<String> calvesExercises = new ArrayList<>();
        calvesExercises.add("Jump Rope");
        calvesExercises.add("Dumbbell Jump Squat");
        muscleGroupToExercise.put(MuscleGroup.CALVES, calvesExercises);

        ArrayList<String> backExercises = new ArrayList<>();
        backExercises.add("DeadLifts");
        backExercises.add("Pull-ups");
        backExercises.add("Chin-ups");
        muscleGroupToExercise.put(MuscleGroup.BACK, backExercises);

        ArrayList<String> shouldersExercises = new ArrayList<>();
        shouldersExercises.add("OverHead Press");
        muscleGroupToExercise.put(MuscleGroup.SHOULDERS, shouldersExercises);

        ArrayList<String> bicepsExercises = new ArrayList<>();
        bicepsExercises.add("Close Grip Pull Ups");
        bicepsExercises.add("Dumbbell Curls");
        muscleGroupToExercise.put(MuscleGroup.BICEPS, bicepsExercises);

        ArrayList<String> tricepsExercises = new ArrayList<>();
        tricepsExercises.add("Reverse Grip Bench Press");
        tricepsExercises.add("Close Grip Bench Press");
        tricepsExercises.add("Dips");
        muscleGroupToExercise.put(MuscleGroup.TRICEPS, tricepsExercises);

        ArrayList<String> forearmsExercises = new ArrayList<>();
        forearmsExercises.add("Wrist Curls");
        muscleGroupToExercise.put(MuscleGroup.FOREARMS, forearmsExercises);

        ArrayList<String> trapsExercises = new ArrayList<>();
        trapsExercises.add("DeadLifts");
        muscleGroupToExercise.put(MuscleGroup.TRAPS, trapsExercises);

        ArrayList<String> absExercises = new ArrayList<>();
        absExercises.add("Sit Ups");
        absExercises.add("Pull Ups");
        absExercises.add("Squats");
        muscleGroupToExercise.put(MuscleGroup.ABS, absExercises);
    }
}
