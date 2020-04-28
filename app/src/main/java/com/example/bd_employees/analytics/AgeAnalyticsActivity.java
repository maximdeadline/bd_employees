package com.example.bd_employees.analytics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bd_employees.R;
import com.example.bd_employees.model.Worker;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class AgeAnalyticsActivity extends AppCompatActivity {

    public int getAge(String birthDate) {
        String[] subStr = birthDate.split("\\.");
        int year = Integer.parseInt(subStr[0]);
        int month = Integer.parseInt(subStr[1]);
        int day = Integer.parseInt(subStr[2]);

        Calendar birthDateCalendar = Calendar.getInstance();
        Calendar todayCalendar = Calendar.getInstance();

        birthDateCalendar.set(year, month, day);

        int age = todayCalendar.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);

        if (todayCalendar.get(Calendar.DAY_OF_YEAR) < birthDateCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_analytics);

        //realm data obtaining
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Worker> workers = realm.where(Worker.class)
                .sort("birthDate", Sort.ASCENDING)
                .limit(10)
                .findAll();

        //data processing and bar chart creation
        BarChart barChart = findViewById(R.id.barChart);
        List<BarEntry> ages = new ArrayList<>();
        ArrayList surnames = new ArrayList();

        for (int index = 0; index < workers.size(); index++) {
            Worker worker = workers.get(index);
            ages.add(new BarEntry(index, getAge(worker.getBirthDate())));
            surnames.add(worker.getSurname());
        }

        BarDataSet barDataSet = new BarDataSet(ages, "Возраст работников");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        XAxis surnamesAxis = barChart.getXAxis();
        surnamesAxis.setLabelRotationAngle(30f);
        surnamesAxis.setLabelCount(10);
        surnamesAxis.setGranularity(1f);
        surnamesAxis.setValueFormatter(new IndexAxisValueFormatter(surnames));

        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.animateY(2500);
        barChart.invalidate();
    }
}
