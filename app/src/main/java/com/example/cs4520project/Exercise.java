package com.example.cs4520project;

import java.security.spec.ECField;

public enum Exercise {
    BICEP_CURLS("Bicep Curls", ExerciseDifficulty.EASY, MuscleGroup.BICEPS),
    BENCH_PRESS("Bench Press", ExerciseDifficulty.MEDIUM, MuscleGroup.PECS),
    SQUATS("Squats", ExerciseDifficulty.EASY, MuscleGroup.HAMSTRINGS),
    DEADLIFTS("DeadLifts", ExerciseDifficulty.HARD, MuscleGroup.HAMSTRINGS),
    DIPS("Dips", ExerciseDifficulty.MEDIUM, MuscleGroup.TRICEPS),
    JUMP_ROPE("Jump Rope", ExerciseDifficulty.EASY, MuscleGroup.CALVES),
    PULL_UPS("Pull-ups", ExerciseDifficulty.EASY, MuscleGroup.TRAPS),
    OVERHEAD_PRESS("Overhead Press", ExerciseDifficulty.MEDIUM, MuscleGroup.SHOULDERS),
    CLOSE_GRIP_BENCH_PRESS("Close Grip Bench Press", ExerciseDifficulty.HARD, MuscleGroup.TRICEPS),
    SIT_UPS("Sit-ups", ExerciseDifficulty.EASY, MuscleGroup.ABS),
    DUMBBELL_FLYES("Dumbbell Flyes", ExerciseDifficulty.HARD, MuscleGroup.PECS),
    CABLE_CROSSOVERS("Cable Crossovers", ExerciseDifficulty.MEDIUM, MuscleGroup.PECS),
    CHEST_PRESS("Chest Press", ExerciseDifficulty.EASY, MuscleGroup.PECS),
    LAT_PULLDOWNS("Lat Pull-Downs",ExerciseDifficulty.EASY, MuscleGroup.LATS),
    SEATED_CABLE_ROWS("Cable Rows", ExerciseDifficulty.EASY, MuscleGroup.TRAPS),
    TBAR_ROWS("T-Bar Rows", ExerciseDifficulty.MEDIUM, MuscleGroup.LATS),
    STRAIGHT_ARM_PULLDOWNS("Straight-arm Pull-downs", ExerciseDifficulty.MEDIUM, MuscleGroup.LATS),
    FACE_PULL("Face Pull", ExerciseDifficulty.EASY, MuscleGroup.TRAPS),
    TRAP_BAR_SHRUG("Trap Bar Shrug", ExerciseDifficulty.EASY, MuscleGroup.UPPER_TRAPS),
    KIRK_SHRUG("Kirk Shrug", ExerciseDifficulty.MEDIUM, MuscleGroup.UPPER_TRAPS),
    FARMERS_CARRY("Farmer's Carry", ExerciseDifficulty.MEDIUM, MuscleGroup.UPPER_TRAPS),
    CABLE_SHRUGS("Cable Shrugs", ExerciseDifficulty.EASY, MuscleGroup.UPPER_TRAPS),
    OVERHEAD_BARBELL_SHRUG("Overhead Barbell Shrug", ExerciseDifficulty.HARD, MuscleGroup.TRAPS),
    DUMBBELL_OVERHEAD_CARRY("Dumbbell Overhead Carry", ExerciseDifficulty.HARD, MuscleGroup.TRAPS),
    SCAPTION("Scaption", ExerciseDifficulty.MEDIUM, MuscleGroup.TRAPS),
    RACK_PULL("Rack Pull", ExerciseDifficulty.HARD, MuscleGroup.LOWER_BACK),
    SUPERMAN("Superman", ExerciseDifficulty.MEDIUM, MuscleGroup.LOWER_BACK),
    BARBELL_GOODMORNING("Barbell Good Morning", ExerciseDifficulty.HARD, MuscleGroup.LOWER_BACK),
    BARBELL_HIP_THRUST("Barbell Hip Thrust", ExerciseDifficulty.EASY, MuscleGroup.GLUTS),
    REVERSE_LUNGE("Reverse Lunge", ExerciseDifficulty.EASY, MuscleGroup.GLUTS),
    SUMO_GOBLET_SQUAT("Sumo Goblet Squat", ExerciseDifficulty.EASY, MuscleGroup.GLUTS),
    DONKEY_KICKS("Donkey Kicks", ExerciseDifficulty.MEDIUM, MuscleGroup.GLUTS),
    SPLIT_SQUATS("Split Squats", ExerciseDifficulty.EASY, MuscleGroup.GLUTS),
    LATERAL_LUNGES("Lateral Lunges", ExerciseDifficulty.EASY, MuscleGroup.GLUTS),
    MACHINE_LEG_CURL("Machine Leg Curl", ExerciseDifficulty.EASY, MuscleGroup.HAMSTRINGS),
    CABLE_PULL_THROUGH("Cable Pull-through", ExerciseDifficulty.MEDIUM, MuscleGroup.HAMSTRINGS),
    NORDIC_HAMSTRING_CURL("Nordic Hamstring Curls", ExerciseDifficulty.HARD, MuscleGroup.HAMSTRINGS),
    WALKING_LUNGES("Walking Lunges", ExerciseDifficulty.MEDIUM, MuscleGroup.HAMSTRINGS),
    INVERTED_HAMSTRING("Inverted Hamstring", ExerciseDifficulty.MEDIUM, MuscleGroup.HAMSTRINGS),
    KETTLEBELL_SKI_SWINGS("Kettlebell Ski Swing", ExerciseDifficulty.HARD, MuscleGroup.HAMSTRINGS),
    CALF_RAISES("Calf Raises", ExerciseDifficulty.EASY, MuscleGroup.CALVES),
    JUMPING_JAKCS("Jumping Jacks", ExerciseDifficulty.EASY, MuscleGroup.CALVES),
    FRONT_RAISE("Front Raise", ExerciseDifficulty.MEDIUM, MuscleGroup.SHOULDERS),
    SEATED_DUMBELL_PRESS("Seated Dumbbell Press", ExerciseDifficulty.MEDIUM, MuscleGroup.SHOULDERS),
    DUMBBELL_LATERAL_RAISES("Dumbbell Lateral Raises", ExerciseDifficulty.MEDIUM, MuscleGroup.SHOULDERS),
    PUSH_PRESS("Push Press", ExerciseDifficulty.MEDIUM, MuscleGroup.SHOULDERS),
    PLANK("Plank", ExerciseDifficulty.EASY, MuscleGroup.ABS),
    CRUNCHES("Crunches", ExerciseDifficulty.EASY, MuscleGroup.ABS),
    TORSO_TWISTS("Torso Twists", ExerciseDifficulty.EASY, MuscleGroup.ABS),
    FLUTTER_KICKS("Flutter Kicks", ExerciseDifficulty.MEDIUM, MuscleGroup.ABS),
    FLATBENCH_LEG_RAISE("Flat Bench Leg Raise", ExerciseDifficulty.MEDIUM, MuscleGroup.ABS),
    MOUNTAIN_CLIMBERS("Mountain Climbers", ExerciseDifficulty.EASY, MuscleGroup.ABS),
    SLIDE_PLANK("Side Plank", ExerciseDifficulty.MEDIUM, MuscleGroup.ABS),
    HEEL_TAPS("Heel Taps", ExerciseDifficulty.MEDIUM, MuscleGroup.ABS),
    HAMMER_CURL("Hammer Curl", ExerciseDifficulty.EASY, MuscleGroup.BICEPS),
    CHIN_UPS("Chin-Ups", ExerciseDifficulty.MEDIUM, MuscleGroup.BICEPS),
    EZ_BAR_CURL("EZ Bar Curl", ExerciseDifficulty.EASY, MuscleGroup.BICEPS),
    PUSHUPS("Push-Ups", ExerciseDifficulty.EASY, MuscleGroup.TRICEPS),
    ROPE_PUSHDOWN("Rope Pushdown", ExerciseDifficulty.EASY, MuscleGroup.TRICEPS),
    TRICEPT_EXTENSIONS("Tricept Extensions", ExerciseDifficulty.MEDIUM, MuscleGroup.TRICEPS),
    BARBELL_REVERSE_BICEPS_CURLS("Reverse Biceps Curls", ExerciseDifficulty.EASY, MuscleGroup.FOREARMS),
    WRIST_ROLLERS("Wrist Rollers", ExerciseDifficulty.MEDIUM, MuscleGroup.FOREARMS),
    PINCH_CARRIES("Pinch Carries", ExerciseDifficulty.MEDIUM, MuscleGroup.FOREARMS),
    LEG_EXTENSIONS("Leg Extensions",ExerciseDifficulty.MEDIUM, MuscleGroup.QUADS),
    LEG_PRESS("Leg Press", ExerciseDifficulty.MEDIUM, MuscleGroup.QUADS),
    STAIR_CLIMBERS("Stair Climbers", ExerciseDifficulty.EASY, MuscleGroup.QUADS);




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

    public static Exercise getExercise(String name) {
        for (Exercise e: Exercise.values()) {
            if (e.getDisplayName() == name) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Exercise: " + displayName + " \n"
                + "Difficulty: " + difficulty + " \n"
                + "Muscle Group: " + muscleGroup;
    }
}
