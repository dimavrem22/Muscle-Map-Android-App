package com.example.cs4520project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>
        implements Filterable {
    private final List<Exercise> exercises;
    private List<Exercise> filteredExercises;
    private ICheckExercises checkExercises;
    private List<Exercise> checkedExercises;


    public ExerciseListAdapter(List<Exercise> exercises, ICheckExercises checkExercises, List<Exercise> checkedExercises) {
        this.exercises = exercises;
        this.filteredExercises = exercises;
        this.checkExercises = checkExercises;
        this.checkedExercises = checkedExercises;
    }

    interface ICheckExercises {
        public void checkExercise(Exercise exercise);
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_row, parent, false);
        return new ViewHolder(view, checkExercises);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListAdapter.ViewHolder holder, int position) {
        holder.getExerciseName().setText(filteredExercises.get(position).getDisplayName());
        boolean check = false;
        for (Exercise e: checkedExercises) {
                if (e.getDisplayName() == filteredExercises.get(position).getDisplayName()) {
                    check = true;
                }
            }
        holder.setChecked(check);
    }

    @Override
    public int getItemCount() {
        return filteredExercises.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String seq = String.valueOf(constraint);
                List<Exercise> filteredList;
                if (seq.isEmpty()) {
                    filteredList = exercises;
                } else {
                    filteredList = new ArrayList<>();
                    for (Exercise exercise : exercises) {
                        if (exercise.getDisplayName().toLowerCase().contains(seq.toLowerCase())) {
                            filteredList.add(exercise);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredExercises = (List<Exercise>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView exerciseName;
        private final ImageView checkImage;
        private boolean checked;
        private final ICheckExercises checkExercises;

        public ViewHolder(@NonNull View itemView, ICheckExercises checkExercises) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.textViewExerciseName);
            checkImage = itemView.findViewById(R.id.imageViewCheckBox);
            checkImage.setImageDrawable(itemView.getResources().getDrawable(R.drawable.unchecked));
            itemView.setOnClickListener(this);
            this.checkExercises = checkExercises;
        }

        @Override
        public void onClick(View v) {
            checked = !checked;
            if (checked) {
                checkImage.setImageDrawable(itemView.getResources().getDrawable(R.drawable.checked));
            } else {
                checkImage.setImageDrawable(itemView.getResources().getDrawable(R.drawable.unchecked));
            }
            Log.d("FP", exerciseName.getText().toString());
            checkExercises.checkExercise(Exercise.getExercise(exerciseName.getText().toString()));
        }

        public TextView getExerciseName() {
            return exerciseName;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
            if (checked) {
                checkImage.setImageDrawable(itemView.getResources().getDrawable(R.drawable.checked));
            } else {
                checkImage.setImageDrawable(itemView.getResources().getDrawable(R.drawable.unchecked));
            }
        }
    }
}
