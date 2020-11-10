package com.example.tabataapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.tabataapplication.Adapters.SeqDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Sequence> sequenceList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.seqList);
        setInitialData();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SeqDataAdapter seqDataAdapter = new SeqDataAdapter(this, sequenceList);
        recyclerView.setAdapter(seqDataAdapter);
    }

    private void setInitialData() {
        sequenceList.add(new Sequence("Training 1", Color.RED));
        sequenceList.add(new Sequence("Training 2", Color.BLUE));
        sequenceList.add(new Sequence("Training 3", Color.YELLOW));
    }
}
