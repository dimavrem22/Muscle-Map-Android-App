package com.example.cs4520project;

/**
 * Describes the intensity of activity on a particular muscle group.
 */
public enum MuscleActivity {
    NONE,
    LOW,
    MEDIUM,
    HIGH,
    EXTREME;

    // TODO.
    public String hexColor() {
        switch (this) {
            case NONE:
                return "#0000FF";
            case LOW:
                return "#00FF00";
            case MEDIUM:
                return "#FFFF00";
            case HIGH:
                return "#FFAA00";
            case EXTREME:
                return "#FF0000";
            default:
                return "#000000";
        }
    }
}
