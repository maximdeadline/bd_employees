package com.example.bd_employees.analytics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bd_employees.R;
import com.example.bd_employees.model.Worker;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class SalaryAnalyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_analytics);

        //realm data obtaining
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Worker> workers = realm.where(Worker.class)
                .sort("salary", Sort.DESCENDING)
                .limit(10)
                .findAll();

        //horizontal bar chart creation
        HorizontalBarChart barChart = findViewById(R.id.barChart);
        List<BarEntry> salaries = new ArrayList<>();
        ArrayList surnames = new ArrayList();

        for (int index = workers.size() - 1; index >= 0; index--) {
            Worker worker = workers.get(index);
            salaries.add(new BarEntry(workers.size() - index - 1, worker.getSalary()));
            surnames.add(worker.getSurname());
        }

        BarDataSet barDataSet = new BarDataSet(salaries, "Оклад работников");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        XAxis surnamesAxis = barChart.getXAxis();
        surnamesAxis.setLabelCount(10);
        surnamesAxis.setGranularity(1f);
        surnamesAxis.setValueFormatter(new IndexAxisValueFormatter(surnames));


        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.setFitBars(true);
        barChart.animateY(2500);
        barChart.invalidate();
    }
}
