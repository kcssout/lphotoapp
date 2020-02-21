package com.example.myphotoapp.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoapp.R;
import com.example.myphotoapp.RecyclerView.RcViewHolder;

public class RecyclerAdapter extends RecyclerView.Adapter<RcViewHolder> {

    RcViewHolder viewHolder;

    @NonNull
    @Override
    public RcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_layout, parent, false);
        viewHolder = new RcViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RcViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
