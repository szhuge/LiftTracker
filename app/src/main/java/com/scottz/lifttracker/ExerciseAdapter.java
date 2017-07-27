package com.scottz.lifttracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.scottz.lifttracker.model.Exercise;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by scottz on 7/26/17.
 */

public class ExerciseAdapter extends RealmRecyclerViewAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder> {

    public ExerciseAdapter(@Nullable OrderedRealmCollection<Exercise> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.row_exercise, parent, false /* attach to parent immediately */);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        Exercise e = getItem(position);

        if (e == null) {
            holder.exerciseTextView.setText(null);
        } else {
            holder.exercise = getItem(position);
            holder.exerciseTextView.setText(holder.exercise.name);
        }
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        protected Exercise exercise;
        protected ImageButton deleteImageButton;
        protected TextView exerciseTextView;

        public ExerciseViewHolder(View itemView) {
            super(itemView);

            deleteImageButton = (ImageButton) itemView.findViewById(R.id.ib_delete);
            deleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ExerciseActivity) view.getContext()).removeExercise(exercise.name);
                }
            });

            exerciseTextView = (TextView) itemView.findViewById(R.id.tv_exercise);
            exerciseTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    context.startActivity(RecordActivity.newIntent(context
                            , exercise.name));
                }
            });
        }
    }
}
