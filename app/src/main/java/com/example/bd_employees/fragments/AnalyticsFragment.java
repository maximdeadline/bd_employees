package com.example.bd_employees.fragments;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.bd_employees.R;
import com.example.bd_employees.analytics.AgeAnalyticsActivity;
import com.example.bd_employees.analytics.EducationAnalyticsActivity;
import com.example.bd_employees.analytics.SalaryAnalyticsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyticsFragment extends Fragment {


    public AnalyticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);

        CardView cardView1 = view.findViewById(R.id.cardView1);
        cardView1.setOnClickListener(v -> {
            Intent intent = new Intent(AnalyticsFragment.this.getActivity(), EducationAnalyticsActivity.class);
            startActivity(intent);
        });

        CardView cardView2 = view.findViewById(R.id.cardView2);
        cardView2.setOnClickListener(v -> {
            Intent intent = new Intent(AnalyticsFragment.this.getActivity(), SalaryAnalyticsActivity.class);
            startActivity(intent);
        });

        CardView cardView3 = view.findViewById(R.id.cardView3);
        cardView3.setOnClickListener(v -> {
            Intent intent = new Intent(AnalyticsFragment.this.getActivity(), AgeAnalyticsActivity.class);
            startActivity(intent);
        });

        return view;
    }

}
