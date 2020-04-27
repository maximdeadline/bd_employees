package com.example.bd_employees.fragments;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bd_employees.R;
import com.example.bd_employees.WorkerAdd;
import com.example.bd_employees.WorkersRecyclerAdapter;
import com.example.bd_employees.model.Worker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    Realm realm = Realm.getDefaultInstance();

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        FloatingActionButton buttonAdd = view.findViewById((R.id.floatingActionButton));

        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WorkerAdd.class);
            startActivity(intent);
        });

        RealmResults<Worker> workers = realm
                .where(Worker.class)
                .sort("surname", Sort.ASCENDING)
                .findAllAsync();

        final WorkersRecyclerAdapter itemsRecyclerAdapter = new WorkersRecyclerAdapter(getContext(), workers);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(itemsRecyclerAdapter);

        SearchView searchView = view.findViewById(R.id.searchView);
        //searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("ФИО");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemsRecyclerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchView.getQuery().length() == 0) {
                    itemsRecyclerAdapter.getFilter().filter("");
                } else {
                    itemsRecyclerAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}



