package com.scottz.lifttracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scottz.lifttracker.model.Exercise;

import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by scottz on 7/26/17.
 */

public class ExerciseActivity extends AppCompatActivity {

    private ExerciseAdapter mExerciseAdapter;
    private RecyclerView mExerciseList;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        realm = Realm.getDefaultInstance();
        initializeExerciseList();
    }

    private void initializeExerciseList() {
        mExerciseList = (RecyclerView) findViewById(R.id.rv_exercise);
        mExerciseList.setLayoutManager(new LinearLayoutManager(this));
        mExerciseList.setHasFixedSize(true);

        RealmResults<Exercise> results = realm.where(Exercise.class).findAll();

        // add in a few exercises if empty
        if (results.size() <= 1) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Exercise exercise = realm.createObject(Exercise.class);
                    exercise.name = "Curls";
                }
            });
        }

        mExerciseAdapter = new ExerciseAdapter(realm.where(Exercise.class).findAll(), true /* autoUpdate */);
        mExerciseList.setAdapter(mExerciseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }
}