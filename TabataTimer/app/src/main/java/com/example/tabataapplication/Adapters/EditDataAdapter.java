package com.example.tabataapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabataapplication.Phase;
import com.example.tabataapplication.R;

import java.util.List;

public class EditDataAdapter extends RecyclerView.Adapter<EditViewHolder> {
    private final LayoutInflater inflater;
    private final List<Phase> phases;

    public EditDataAdapter(Context context, List<Phase> phases) {
        this.inflater = LayoutInflater.from(context);
        this.phases = phases;
    }

    @NonNull
    @Override
    public EditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.edit_item, parent, false);
        return new EditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditViewHolder holder, int position) {
        Phase phase = phases.get(position);
        holder.editImage.setImageDrawable(phase.getActionImage());
        holder.editDescription.setText(phase.getDescription());
        holder.editTime.setText(phase.getTime());
        holder.editActionName.setText(phase.getActionName().toString());
    }

    @Override
    public int getItemCount() {
        return phases.size();
    }
}
