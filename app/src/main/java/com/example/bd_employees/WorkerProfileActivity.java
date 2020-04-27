package com.example.bd_employees;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;

import static io.realm.Realm.getDefaultInstance;

public class WorkerProfileActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_WORKER_ID = "WorkerProfileActivity.workerId";
    TextView surname;
    TextView name;
    TextView patronymic;
    TextView post;
    Realm realm = getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);
    }
}
