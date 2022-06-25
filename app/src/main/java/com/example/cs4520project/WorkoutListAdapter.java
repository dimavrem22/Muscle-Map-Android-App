package com.example.cs4520project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.ViewHolder> {
    private final List<Workout> workouts;
    private final ExerciseLogFragment fragment;

    public WorkoutListAdapter(List<Workout> workouts, ExerciseLogFragment fragment) {
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
        String startTime = String.format(Locale.getDefault(), "%02d:%02d",
                workouts.get(position).getStartHour(), workouts.get(position).getStartMinute());
        String endTime = String.format(Locale.getDefault(), "%02d:%02d",
                workouts.get(position).getEndHour(), workouts.get(position).getEndMinute());
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
        holder.getDurationText().setText(String.format(Locale.getDefault(), "%d minutes", duration));
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ExerciseLogFragment fragment;
        private final TextView nameText;
        private final TextView exercisesText;
        private final TextView timeText;
        private final TextView durationText;
        private final ImageView editImage;
        private final List<Workout> workouts;

        public ViewHolder(@NonNull View itemView, ExerciseLogFragment fragment, List<Workout> workouts) {
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
