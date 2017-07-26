package com.scottz.lifttracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecordAdapter.RecordClickListener {

    private static final int NUM_LIST_ITEMS = 11;

    private TextView mWeightInput;
    private TextView mRepsInput;
    private Button mSubmitButton;

    private RecordAdapter mRecordAdapter;
    private RecyclerView mRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeightInput = (TextView) findViewById(R.id.et_weight);
        mRepsInput = (TextView) findViewById(R.id.et_reps);

        mSubmitButton = (Button) findViewById(R.id.button_submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecordAdapter.addRecord(mWeightInput.getText() + " lb - " + mRepsInput.getText() + " reps");
            }
        });

        mRecordList = (RecyclerView) findViewById(R.id.rv_records);
        // Set LayoutManager (vertical list by default)
        mRecordList.setLayoutManager(new LinearLayoutManager(this));
        // Improves performance - child layout size doesn't change in RecyclerView
        mRecordList.setHasFixedSize(true);
        mRecordAdapter = new RecordAdapter(new ArrayList<String>(), this);
        mRecordList.setAdapter(mRecordAdapter);
    }

    @Override
    public void onRecordClick(int index) {
        Toast.makeText(this, Integer.toString(index), Toast.LENGTH_LONG).show();
    }
}
