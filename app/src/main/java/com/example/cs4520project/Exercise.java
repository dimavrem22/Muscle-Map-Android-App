package com.example.cs4520project;

public enum Exercise {
    BICEP_CURLS("Bicep Curls", ExerciseDifficulty.EASY, MuscleGroup.BICEPS),
    BENCH_PRESS("Bench Press", ExerciseDifficulty.MEDIUM, MuscleGroup.PECS),
    SQUATS("Squats", ExerciseDifficulty.EASY, MuscleGroup.HAMSTRINGS),
    DEADLIFTS("DeadLifts", ExerciseDifficulty.HARD, MuscleGroup.HAMSTRINGS),
    DIPS("Dips", ExerciseDifficulty.MEDIUM, MuscleGroup.PECS),
    JUMP_ROPE("Jump Rope", ExerciseDifficulty.EASY, MuscleGroup.CALVES),
    PULL_UPS("Pull-ups", ExerciseDifficulty.EASY, MuscleGroup.BACK),
    OVERHEAD_PRESS("Overhead Press", ExerciseDifficulty.MEDIUM, MuscleGroup.SHOULDERS),
    CLOSE_GRIP_BENCH_PRESS("Close Grip Bench Press", ExerciseDifficulty.HARD, MuscleGroup.TRICEPS),
    SIT_UPS("Sit-ups", ExerciseDifficulty.EASY, MuscleGroup.ABS);

    private final String displayName;
    private final ExerciseDifficulty difficulty;
    private final MuscleGroup muscleGroup;

    Exercise(String name, ExerciseDifficulty difficulty, MuscleGroup muscleGroup) {
        this.displayName = name;
        this.difficulty = difficulty;
        this.muscleGroup = muscleGroup;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ExerciseDifficulty getDifficulty() {
        return difficulty;
    }

    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }

    @Override
    public String toString() {
        return "Exercise: " + displayName + " \n"
                + "Difficulty: " + difficulty + " \n"
                + "Muscle Group: " + muscleGroup;
    }
}
