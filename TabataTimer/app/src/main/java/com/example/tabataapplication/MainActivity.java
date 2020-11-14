package com.example.tabataapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tabataapplication.Adapters.SeqDataAdapter;
import com.example.tabataapplication.ItemTouchHelper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Sequence> sequenceList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Button btnSeqAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.seqList);
        btnSeqAdd = findViewById(R.id.btnSeqAdd);
        setInitialData();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SeqDataAdapter seqDataAdapter = new SeqDataAdapter(this, sequenceList);
        recyclerView.setAdapter(seqDataAdapter);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(seqDataAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        btnSeqAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setInitialData() {
        sequenceList.add(new Sequence("Training 1", Color.RED));
        sequenceList.add(new Sequence("Training 2", Color.BLUE));
        sequenceList.add(new Sequence("Training 3", Color.YELLOW));
    }
}
