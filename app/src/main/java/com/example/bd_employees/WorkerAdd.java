package com.example.bd_employees;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bd_employees.model.Worker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;


public class WorkerAdd extends AppCompatActivity implements View.OnClickListener {
    EditText surnameField;
    EditText nameField;
    EditText patronymicField;
    EditText birthDateField;
    EditText phoneField;
    EditText recruitDateField;
    EditText salaryField;
    Button addButton;

    String education;
    String post;

    Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_add);

        //setting spinners
        String[] educationArray = {"Среднее", "Среднее специальное", "Высшее"};
        Spinner spinnerEducation = findViewById(R.id.spinner);
        ArrayAdapter<String> adapterEducation = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, educationArray);
        adapterEducation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEducation.setAdapter(adapterEducation);

        String[] postArray = {"Менеджер", "Консультант", "Бухгалтер", "Кассир"};
        Spinner spinnerPost = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapterPost = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, postArray);
        adapterPost.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPost.setAdapter(adapterPost);

        AdapterView.OnItemSelectedListener educationSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                education = educationArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinnerEducation.setOnItemSelectedListener(educationSelectedListener);

        AdapterView.OnItemSelectedListener postSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                post = postArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinnerPost.setOnItemSelectedListener(postSelectedListener);

        //naming fields
        surnameField = findViewById(R.id.editText7);
        nameField = findViewById(R.id.editText10);
        patronymicField = findViewById(R.id.editText11);
        birthDateField = findViewById(R.id.editText9);
        phoneField = findViewById(R.id.editText13);
        recruitDateField = findViewById(R.id.editText8);
        salaryField = findViewById(R.id.editText14);

        addButton = findViewById(R.id.button11);

        addButton.setOnClickListener(this);


        //setting datepickers
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener birthDate = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String format = "yyyy.MM.dd";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

            birthDateField.setText(sdf.format(calendar.getTime()));
        };

        DatePickerDialog.OnDateSetListener recruitDate = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String format = "yyyy.MM.dd";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

            recruitDateField.setText(sdf.format(calendar.getTime()));
        };

        birthDateField.setOnClickListener(v -> new DatePickerDialog(WorkerAdd.this, birthDate, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());

        recruitDateField.setOnClickListener(v -> new DatePickerDialog(WorkerAdd.this, recruitDate, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    public void onClick(View view) {
        if (TextUtils.isEmpty(surnameField.getText().toString().trim()) |
                TextUtils.isEmpty(nameField.getText().toString().trim()) |
                TextUtils.isEmpty(patronymicField.getText().toString().trim()) |
                TextUtils.isEmpty(birthDateField.getText().toString().trim()) |
                TextUtils.isEmpty(phoneField.getText().toString().trim()) |
                TextUtils.isEmpty(recruitDateField.getText().toString().trim()) |
                TextUtils.isEmpty(salaryField.getText().toString().trim()) |
                TextUtils.isEmpty(education) |
                TextUtils.isEmpty(post)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WorkerAdd.this);
            builder.setTitle("Обнаружены пустые поля")
                    .setMessage("Заполните все данные работника")
                    .setCancelable(true)
                    .setNegativeButton("Ок",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            writeToDB(
                    surnameField.getText().toString().trim(),
                    nameField.getText().toString().trim(),
                    patronymicField.getText().toString().trim(),
                    birthDateField.getText().toString().trim(),
                    phoneField.getText().toString().trim(),
                    recruitDateField.getText().toString().trim(),
                    salaryField.getText().toString().trim(),
                    education,
                    post
            );
            surnameField.setText(null);
            nameField.setText(null);
            patronymicField.setText(null);
            birthDateField.setText(null);
            phoneField.setText(null);
            recruitDateField.setText(null);
            salaryField.setText(null);
        }
    }

    public void writeToDB(
            final String surname,
            final String name,
            final String patronymic,
            final String birthDate,
            final String phone,
            final String recruitDate,
            final String salary,
            final String education,
            final String post
    ) {
        realm.executeTransactionAsync(realm -> {
            Worker worker = new Worker();
            worker.setId(UUID.randomUUID().toString());
            worker.setSurname(surname);
            worker.setName(name);
            worker.setPatronymic(patronymic);
            worker.setFullName(surname.toLowerCase() + " " + name.toLowerCase() + " " + patronymic.toLowerCase());
            worker.setBirthDate(birthDate);
            worker.setPhone(phone);
            worker.setRecruitDate(recruitDate);
            worker.setSalary(Float.parseFloat(salary));
            worker.setEducation(education);
            worker.setPost(post);
            realm.insert(worker);
        }, () -> {
            // Transaction was a success.
            Log.v("Database", "Data added: " + name);
        }, error -> {
            // Transaction failed and was automatically canceled.
            Log.e("Database", error.getMessage());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
