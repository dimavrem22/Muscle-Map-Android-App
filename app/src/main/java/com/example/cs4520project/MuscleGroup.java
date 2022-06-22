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
    BACK,
    QUADS,
    HAMSTRINGS,
    CALVES;

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}