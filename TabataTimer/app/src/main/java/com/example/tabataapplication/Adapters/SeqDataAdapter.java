package com.example.tabataapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabataapplication.R;

import com.example.tabataapplication.Sequence;
import com.example.tabataapplication.TimerActivity;

import java.util.List;

public class SeqDataAdapter extends RecyclerView.Adapter<SeqViewHolder> {

    private final LayoutInflater inflater;
    private final List<Sequence> sequences;

    public SeqDataAdapter(Context context, List<Sequence> sequences) {
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
        holder.seqItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), TimerActivity.class);
                inflater.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sequences.size();
    }
}

