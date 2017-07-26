package com.scottz.lifttracker;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by scottz on 7/26/17.
 */

public class LiftTrackerApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
    }
}
