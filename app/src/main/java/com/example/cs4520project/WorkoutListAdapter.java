package com.example.cs4520project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.ViewHolder> {

    private final ArrayList<Workout> workouts;
    private final ExerciseLoggingFragment fragment;

    public WorkoutListAdapter(ArrayList<Workout> workouts, ExerciseLoggingFragment fragment) {
        this.workouts = workouts;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_workout, parent, false);

        return new ViewHolder(view, fragment);
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

        String exercises = "";
        for (String e: this.workouts.get(position).getExerciseList()){
            exercises += e + "\n";
        }

        holder.getExercisesText().setText(exercises.substring(0, exercises.length() - 1));

       int duration =  (workouts.get(position).getEndHour() -
               workouts.get(position).getStartHour()) * 60 + workouts.get(position).getEndMinute() -
               workouts.get(position).getStartMinute();


       holder.getDurationText().setText(duration + " minutes");

    }

    @Override
    public int getItemCount() {
        return this.workouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ExerciseLoggingFragment fragment;
        private TextView nameText, exercisesText, timeText, durationText;
        private ImageView editImage;

        public ViewHolder(@NonNull View itemView, ExerciseLoggingFragment fragment) {
            super(itemView);
            this.fragment = fragment;
            this.nameText = itemView.findViewById(R.id.workoutNameText);
            this.exercisesText = itemView.findViewById(R.id.workoutExercisesText);
            this.timeText = itemView.findViewById(R.id.workoutTimeText);
            this.durationText = itemView.findViewById(R.id.workoutDurationtext);
            this.editImage = itemView.findViewById(R.id.workoutEditButton);
            this.editImage.setOnClickListener(this);
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
            if (this.editImage.getId() == v.getId()){
                fragment.clickedEditWorkout(this.getAdapterPosition());
            }
        }
    }

}
