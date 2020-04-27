package com.example.bd_employees;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bd_employees.model.Worker;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class WorkersRecyclerAdapter extends RealmRecyclerViewAdapter<Worker, WorkersRecyclerAdapter.MyViewHolder> {
    private final Context context;
    private Realm realm = Realm.getDefaultInstance();

    public WorkersRecyclerAdapter(Context context, RealmResults<Worker> data) {
        super(data, true);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Worker worker = getItem(position);
        if (worker != null) {
            String surnameSpaceName = worker.getSurname() + " " + worker.getName();
            holder.textView.setText(surnameSpaceName);
            holder.textView.setOnClickListener(v -> {
                Intent intent = new Intent(context, WorkerProfileActivity.class);
                intent.putExtra(WorkerProfileActivity.INTENT_EXTRA_WORKER_ID, worker.getId());
                context.startActivity(intent);
            });
        }
    }

    public void filterResults(String text) {
        text = text == null ? null : text.toLowerCase().trim();
        if (text == null || "".equals(text)) {
            updateData(realm.where(Worker.class).findAll().sort("id"));
        } else {
            Log.v("Search: ", text);
            updateData(realm.where(Worker.class)
                    .contains("fullName", text, Case.INSENSITIVE)
                    .findAll()
                    .sort("surname")
            );
        }
    }

    public Filter getFilter() {
        return new WorkersFilter(this);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    private class WorkersFilter extends Filter {
        private final WorkersRecyclerAdapter adapter;

        private WorkersFilter(WorkersRecyclerAdapter adapter) {
            super();
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return new FilterResults();
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filterResults(constraint.toString());
        }
    }
}