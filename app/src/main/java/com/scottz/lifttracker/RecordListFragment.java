package com.scottz.lifttracker;


import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordListFragment extends ListFragment {


    public RecordListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set adapter, which will be how we access our data
        setListAdapter(new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, new ArrayList<String>()));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().setBackgroundColor(Color.RED);
    }
}
