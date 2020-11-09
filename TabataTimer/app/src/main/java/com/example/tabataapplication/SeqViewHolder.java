package com.example.tabataapplication;

import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class SeqViewHolder extends RecyclerView.ViewHolder {
    final TextView seqTitle;
    final LinearLayout seqItemLayout;

    SeqViewHolder(@NonNull View itemView) {
        super(itemView);
        seqTitle = itemView.findViewById(R.id.seqTitle);
        seqItemLayout = itemView.findViewById(R.id.seqItemLayout);
    }
}
