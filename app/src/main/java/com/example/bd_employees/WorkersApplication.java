package com.example.bd_employees;

import android.app.Application;

import io.realm.Realm;

public class WorkersApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
