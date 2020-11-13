package com.example.tabataapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.tabataapplication.Adapters.EditDataAdapter;
import com.example.tabataapplication.Adapters.SeqDataAdapter;
import com.example.tabataapplication.databinding.ActivityEditBinding;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    boolean isRotate = false;
    List<Phase> list = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);

        layoutManager = new LinearLayoutManager(this);
        binding.editList.setLayoutManager(layoutManager);
        final EditDataAdapter editDataAdapter = new EditDataAdapter(this, list);
        binding.editList.setAdapter(editDataAdapter);

        binding.fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation(v);
            }
        });
        binding.fabPrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new Phase(Action.PREPARATION, 10, getResources().getDrawable(R.drawable.ic_preparation_color)));
                editDataAdapter.notifyDataSetChanged();
                animation(v);
            }
        });
        binding.fabWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new Phase(Action.WORK, 20, getResources().getDrawable(R.drawable.ic_work_color)));
                editDataAdapter.notifyDataSetChanged();
                animation(v);
            }
        });
        binding.fabRelax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new Phase(Action.RELAX, 5, getResources().getDrawable(R.drawable.ic_relax_color)));
                editDataAdapter.notifyDataSetChanged();
                animation(v);
            }
        });
        binding.fabRelaxBetweenSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new Phase(Action.RELAX_BETWEEN_SETS, 2, getResources().getDrawable(R.drawable.ic_relax_between_sets_color)));
                editDataAdapter.notifyDataSetChanged();
                animation(v);
            }
        });

        ViewAnimation.init(binding.fabPrep);
        ViewAnimation.init(binding.fabWork);
        ViewAnimation.init(binding.fabRelax);
        ViewAnimation.init(binding.fabRelaxBetweenSets);
    }

    private void animation(View v) {
        isRotate = ViewAnimation.rotateFab(v, !isRotate);
        if (isRotate) {
            showIn();
        } else {
            showOut();
        }
    }

    private void showIn() {
        ViewAnimation.showIn(binding.fabPrep);
        ViewAnimation.showIn(binding.fabWork);
        ViewAnimation.showIn(binding.fabRelax);
        ViewAnimation.showIn(binding.fabRelaxBetweenSets);
    }

    private void showOut() {
        ViewAnimation.showOut(binding.fabPrep);
        ViewAnimation.showOut(binding.fabWork);
        ViewAnimation.showOut(binding.fabRelax);
        ViewAnimation.showOut(binding.fabRelaxBetweenSets);
    }
}