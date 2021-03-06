package com.scottz.lifttracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.scottz.lifttracker.model.Exercise;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by scottz on 7/26/17.
 */

public class ExerciseActivity extends AppCompatActivity {

    private ExerciseAdapter mExerciseAdapter;
    private RecyclerView mExerciseList;

    private Realm realm;

    private static final String[] initialExercises =
            {"Squat", "Deadlift", "Bench Press", "Overhead Press", "Curls"};

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

        // Initialize exercise list if empty
        if (realm.where(Exercise.class).findAll().size() == 0) {
            for (String exerciseName : initialExercises) {
                addExercise(exerciseName);
            }
        }

        mExerciseAdapter = new ExerciseAdapter(realm.where(Exercise.class).findAll(), true /* autoUpdate */);
        mExerciseList.setAdapter(mExerciseAdapter);
    }

    public void addExercise(final String inputName) {
        if (realm == null || realm.isClosed()) {
            return;
        }

        // Check for duplicates
        RealmResults<Exercise> results = realm.where(Exercise.class).contains("name", inputName).findAll();
        if (results.size() > 0) {
            return;
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Exercise exercise = realm.createObject(Exercise.class);
                exercise.name = inputName;
            }
        });
    }

    public void removeExercise(final String inputName) {
        if (realm == null || realm.isClosed()) {
            return;
        }

        // Check for duplicates
        final RealmResults<Exercise> results = realm.where(Exercise.class).contains("name", inputName).findAll();
        if (results.size() == 0) {
            return;
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // Exit delete mode unless we are toggling it
        if (itemId != R.id.action_toggle_delete) {
            mExerciseAdapter.toggleDeleteMode(false /*visible*/);
        }

        switch (itemId) {
            case R.id.action_toggle_delete:
                mExerciseAdapter.toggleDeleteMode();
                break;
            case R.id.action_add_exercise:
                final EditText exercise_input = new EditText(this);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder
                        .setTitle("Add exercise")
                        .setCancelable(true /* cancelable */)
                        .setView(exercise_input)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!TextUtils.isEmpty(exercise_input.getText())) {
                                    addExercise(exercise_input.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                alertDialogBuilder.create().show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }
}
