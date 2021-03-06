package com.scottz.lifttracker.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by scottz on 7/26/17.
 */

public class Record extends RealmObject {

    public Exercise exercise;
    public double weight;
    public int reps;
    public Date date;

    @Override
    public String toString() {
        return "[" + (new SimpleDateFormat("E M/dd")).format(date) + "] "
                + exercise.name + " "
                + weight + " lb - "
                + reps + " reps";
    }
}
