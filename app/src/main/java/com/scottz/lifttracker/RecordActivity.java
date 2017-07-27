package com.scottz.lifttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scottz.lifttracker.model.Exercise;
import com.scottz.lifttracker.model.Record;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.Sort;

public class RecordActivity extends AppCompatActivity implements RecordAdapter.RecordClickListener {

    public final static String EXTRA_EXERCISE = "com.scottz.lifttracker.exercise";

    // Exercise that corresponds to this activity
    private Exercise mExercise;

    private EditText mWeightInput;
    private EditText mRepsInput;
    private Button mSubmitButton;

    private RecordAdapter mRecordAdapter;
    private RecyclerView mRecordList;

    private Realm realm;

    // Called by other Activities to start this activity.
    // Hides implementation details for passing through exercise name.
    public static Intent newIntent(Context packageContext, String exerciseName) {
        return new Intent(packageContext, RecordActivity.class)
                .putExtra(EXTRA_EXERCISE, exerciseName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        realm = Realm.getDefaultInstance();
        mExercise = realm.where(Exercise.class).contains("name", getIntent().getStringExtra(EXTRA_EXERCISE)).findFirst();

        initializeWidgets();
        initializeRecordList();
    }

    private void initializeWidgets() {
        mWeightInput = (EditText) findViewById(R.id.et_weight);
        mRepsInput = (EditText) findViewById(R.id.et_reps);

        mSubmitButton = (Button) findViewById(R.id.button_submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mRepsInput.getText()) || TextUtils.isEmpty(mWeightInput.getText())) {
                    return;
                }

                addRecord(Double.parseDouble(mWeightInput.getText().toString())
                        , Integer.parseInt(mRepsInput.getText().toString()));
                mRecordAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addRecord(final double weight, final int reps) {
        if (realm == null || realm.isClosed()) {
            return;
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Record record = realm.createObject(Record.class);
                record.date = Calendar.getInstance().getTime();
                record.exercise = mExercise;
                record.weight = weight;
                record.reps = reps;
            }
        });
    }

    private void initializeRecordList() {
        mRecordList = (RecyclerView) findViewById(R.id.rv_records);
        // Set LayoutManager (vertical list by default)
        mRecordList.setLayoutManager(new LinearLayoutManager(this));
        // Improves performance - child layout size doesn't change in RecyclerView
        mRecordList.setHasFixedSize(true);

        // Get data from realm
        mRecordAdapter = new RecordAdapter(realm.where(Record.class)
                .contains("exercise.name", mExercise.name)
                .findAll().sort("date", Sort.DESCENDING)
                , this);
        mRecordList.setAdapter(mRecordAdapter);
    }

    // This happens when a record is clicked
    @Override
    public void onRecordClick(int index) {
        Toast.makeText(this, Integer.toString(index), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }
}
