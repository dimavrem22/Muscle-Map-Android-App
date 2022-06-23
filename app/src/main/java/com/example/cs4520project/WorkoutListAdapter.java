package com.example.cs4520project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.ViewHolder> {
    private final List<Workout> workouts;
    private final ExerciseLoggingFragment fragment;

    public WorkoutListAdapter(List<Workout> workouts, ExerciseLoggingFragment fragment) {
        this.workouts = workouts;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_workout, parent, false);
        return new ViewHolder(view, fragment, workouts);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNameText().setText(workouts.get(position).getName());
        String startTime = workouts.get(position).getStartHour() + ":" +
                workouts.get(position).getStartMinute();
        String endTime = workouts.get(position).getEndHour() + ":" +
                workouts.get(position).getEndMinute();
        String time = startTime + " — " + endTime;

        holder.getTimeText().setText(time);

        StringBuilder exercises = new StringBuilder();
        for (Exercise e : workouts.get(position).getExercises()) {
            exercises.append(e.getDisplayName()).append("\n");
        }

        holder.getExercisesText().setText(exercises.substring(0, exercises.length() - 1));

        int duration = (workouts.get(position).getEndHour() -
                workouts.get(position).getStartHour()) * 60 + workouts.get(position).getEndMinute() -
                workouts.get(position).getStartMinute();
        holder.getDurationText().setText(duration + " minutes");
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ExerciseLoggingFragment fragment;
        private final TextView nameText;
        private final TextView exercisesText;
        private final TextView timeText;
        private final TextView durationText;
        private final ImageView editImage;
        private final List<Workout> workouts;

        public ViewHolder(@NonNull View itemView, ExerciseLoggingFragment fragment, List<Workout> workouts) {
            super(itemView);
            this.fragment = fragment;
            this.nameText = itemView.findViewById(R.id.workoutNameText);
            this.exercisesText = itemView.findViewById(R.id.workoutExercisesText);
            this.timeText = itemView.findViewById(R.id.workoutTimeText);
            this.durationText = itemView.findViewById(R.id.workoutDurationtext);
            this.editImage = itemView.findViewById(R.id.workoutEditButton);
            this.editImage.setOnClickListener(this);
            this.workouts = workouts;
        }

        public TextView getNameText() {
            return nameText;
        }

        public TextView getExercisesText() {
            return exercisesText;
        }

        public TextView getTimeText() {
            return timeText;
        }

        public TextView getDurationText() {
            return durationText;
        }

        @Override
        public void onClick(View v) {
            if (editImage.getId() == v.getId()) {
                fragment.clickedEditWorkout(getAdapterPosition(), workouts.get(getAdapterPosition()));
            }
        }
    }

}
