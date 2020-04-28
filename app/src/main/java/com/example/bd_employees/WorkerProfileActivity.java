package com.example.bd_employees;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bd_employees.model.Worker;

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

        String workerId = getIntent().getStringExtra(INTENT_EXTRA_WORKER_ID);
        Worker worker = realm.where(Worker.class).equalTo("id", workerId).findFirst();

        surname = findViewById(R.id.textView12);
        name = findViewById(R.id.textView13);
        patronymic = findViewById(R.id.textView15);
        post = findViewById(R.id.textView17);

        surname.setText(worker.getSurname());
        name.setText(worker.getName());
        patronymic.setText(worker.getPatronymic());
        post.setText(worker.getPost());

        Button deleteButton = findViewById(R.id.button6);
        deleteButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(WorkerProfileActivity.this);
            builder.setTitle("Удалить запись?")
                    .setMessage("Данные о сотруднике будут полностью удалены без возможности отменить действие")
                    .setCancelable(true)
                    .setPositiveButton("Удалить",
                            (dialog, id) -> deleteWorker())
                    .setNegativeButton("Отмена",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });

        Button editButton = findViewById(R.id.button5);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(WorkerProfileActivity.this, WorkerEdit.class);
            intent.putExtra(WorkerEdit.INTENT_EXTRA_WORKER_ID, worker.getId());
            startActivity(intent);
        });
    }

    protected void deleteWorker() {
        String workerId = getIntent().getStringExtra(INTENT_EXTRA_WORKER_ID);
        realm.executeTransactionAsync(realm -> getDefaultInstance()
                .where(Worker.class)
                .equalTo("id", workerId)
                .findFirst()
                .deleteFromRealm());
        WorkerProfileActivity.this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String workerId = getIntent().getStringExtra(INTENT_EXTRA_WORKER_ID);
        Worker worker = realm.where(Worker.class).equalTo("id", workerId).findFirst();

        surname.setText(worker.getSurname());
        name.setText(worker.getName());
        patronymic.setText(worker.getPatronymic());
        post.setText(worker.getPost());
    }
}
