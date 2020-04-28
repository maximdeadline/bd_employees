package com.example.bd_employees.analytics;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bd_employees.R;
import com.example.bd_employees.model.Worker;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class EducationAnalyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //realm data obtaining
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Worker> education;

        education = realm.where(Worker.class).equalTo("education", "Среднее").findAll();
        int middle = education.size();
        education = realm.where(Worker.class).equalTo("education", "Среднее специальное").findAll();
        int middleProf = education.size();
        education = realm.where(Worker.class).equalTo("education", "Высшее").findAll();
        int high = education.size();

        //pie chart creation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_analytics);

        PieChart pieChart = findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setEntryLabelColor(Color.BLACK);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(middle, "Среднее"));
        yValues.add(new PieEntry(middleProf, "Среднее специальное"));
        yValues.add(new PieEntry(high, "Высшее"));

        PieDataSet dataSet = new PieDataSet(yValues, "Образование");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(30f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
    }
}
