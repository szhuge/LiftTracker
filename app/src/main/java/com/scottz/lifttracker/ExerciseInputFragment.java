package com.scottz.lifttracker;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExerciseInputFragment extends Fragment{

    private EditText mWeight;
    private EditText mReps;
    private Button mSubmit;

    public ExerciseInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_input, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        getView().setBackgroundColor(Color.BLUE);

        // initialized in onStart() because it was giving me problems in onCreateView()
        View parentView = getView();
        mWeight = (EditText) parentView.findViewById(R.id.et_weight);
        mReps = (EditText) parentView.findViewById(R.id.et_reps);
        mSubmit = (Button) parentView.findViewById(R.id.button_submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // hide keyboard after clicking "submit"
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                // make toast
                Toast.makeText(getActivity(), mWeight.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
