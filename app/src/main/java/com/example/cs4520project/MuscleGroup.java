package com.example.cs4520project;

import androidx.annotation.NonNull;

/**
 * An enumeration of possible muscle groups.
 */
public enum MuscleGroup {
    PECS,
    SHOULDERS,
    TRAPS,
    BICEPS,
    TRICEPS,
    FOREARMS,
    ABS,
    QUADS,
    HAMSTRINGS,
    CALVES,
    LATS,
    UPPER_TRAPS,
    LOWER_BACK,
    GLUTS;

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}