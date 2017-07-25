package com.scottz.lifttracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExerciseInputFragment exerciseInputFragment = (ExerciseInputFragment) getFragmentManager().findFragmentById(R.id.exercise_input_fragment);

    }
}
