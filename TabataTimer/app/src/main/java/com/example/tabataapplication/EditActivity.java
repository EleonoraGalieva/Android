package com.example.tabataapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.tabataapplication.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    boolean isRotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        setContentView(R.layout.activity_edit);
        ViewAnimation.init(binding.fabPrep);
        ViewAnimation.init(binding.fabWork);
        ViewAnimation.init(binding.fabRelax);
        ViewAnimation.init(binding.fabRelaxBetweenSets);
        binding.fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRotate = ViewAnimation.rotateFab(v, !isRotate);
                if (isRotate) {
                    ViewAnimation.showIn(binding.fabPrep);
                    ViewAnimation.showIn(binding.fabWork);
                    ViewAnimation.showIn(binding.fabRelax);
                    ViewAnimation.showIn(binding.fabRelaxBetweenSets);
                } else {
                    ViewAnimation.showOut(binding.fabPrep);
                    ViewAnimation.showOut(binding.fabWork);
                    ViewAnimation.showOut(binding.fabRelax);
                    ViewAnimation.showOut(binding.fabRelaxBetweenSets);
                }

            }
        });
    }
}