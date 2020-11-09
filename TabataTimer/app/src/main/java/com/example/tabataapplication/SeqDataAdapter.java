package com.example.tabataapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeqDataAdapter extends RecyclerView.Adapter<SeqViewHolder> {

    private LayoutInflater inflater;
    private List<Sequence> sequences;

    SeqDataAdapter(Context context, List<Sequence> sequences) {
        this.inflater = LayoutInflater.from(context);
        this.sequences = sequences;
    }

    @NonNull
    @Override
    public SeqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.seq_item, parent, false);
        return new SeqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeqViewHolder holder, int position) {
        Sequence sequence = sequences.get(position);
        holder.seqTitle.setText(sequence.getTitle());
        holder.seqItemLayout.setBackgroundColor(sequence.getColour());
    }

    @Override
    public int getItemCount() {
        return sequences.size();
    }
}

