package com.example.cs4520project;

import java.io.Serializable;

// TODO: Consider this as a temporary class for now, to test muscle group display.
public class ExerciseStats implements Serializable {
    MuscleGroupActivityMap activityMap;

    public ExerciseStats() {

    }

    public ExerciseStats(MuscleGroupActivityMap activityMap) {
        this.activityMap = activityMap;
    }

//    public MuscleActivity activityOnMuscleGroup(MuscleGroup group) {
//        return activityMap.get(group);
//    }

}
