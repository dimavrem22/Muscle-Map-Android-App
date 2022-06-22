package com.example.cs4520project;

public class Exercise {
    private String name;
    private String difficulty;
    private String muscleGroup;

    public Exercise(String name, String difficulty, String muscleGroup) {
        this.name = name;
        this.difficulty = difficulty;
        this.muscleGroup = muscleGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    @Override
    public String toString() {
        return "Exercise: " + name + " \n"
                + "Difficulty: " + difficulty + " \n"
                + "Muscle Group: " + muscleGroup;
    }
}
