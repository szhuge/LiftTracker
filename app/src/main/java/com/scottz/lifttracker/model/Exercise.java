package com.scottz.lifttracker.model;

import io.realm.RealmObject;

/**
 * Created by scottz on 7/26/17.
 */

public class Exercise extends RealmObject {
    public String name;

    @Override
    public String toString() {
        return name;
    }
}
