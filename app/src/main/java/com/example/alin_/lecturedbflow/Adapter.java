package com.example.alin_.lecturedbflow;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alin_.lecturedbflow.persistence.Worker;

import java.util.ArrayList;

/**
 * Created by alin- on 18.11.2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.WorkersHolder> {

    private ArrayList<Worker> workers;

    public Adapter(ArrayList<Worker> workers) {
        this.workers = workers;
    }

    @Override
    public WorkersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.worker_item,parent,false);
        return new WorkersHolder(root);
    }

    @Override
    public void onBindViewHolder(WorkersHolder holder, int position) {
        Worker worker = workers.get(position);
        holder.getBinding().setVariable(com.example.alin_.lecturedbflow.BR.worker,worker);
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

    class WorkersHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding binding;
        public WorkersHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
