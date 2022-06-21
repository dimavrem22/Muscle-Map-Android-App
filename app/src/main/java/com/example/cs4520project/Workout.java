package com.example.cs4520project;

import java.util.ArrayList;

public class Workout {

    private String name;
    private int  startHour, startMinute, endHour, endMinute;
    private ArrayList<String> exerciseList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public ArrayList<String> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<String> exerciseList) {
        this.exerciseList = exerciseList;
    }
}
