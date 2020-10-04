package com.example.converter;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

public class KeyboardFragment extends Fragment {

    private ConversionViewModel conversionViewModel;
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonDot, buttonC, buttonConv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversionViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ConversionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_keyboard, container, false);

        button0 = view.findViewById(R.id.button0);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);
        button9 = view.findViewById(R.id.button9);
        buttonDot = view.findViewById(R.id.buttonDot);
        buttonConv = view.findViewById(R.id.buttonConv);
        buttonC = view.findViewById(R.id.buttonC);

        button0.setOnClickListener(item -> conversionViewModel.setNumber("0"));
        button1.setOnClickListener(item -> conversionViewModel.setNumber("1"));
        button2.setOnClickListener(item -> conversionViewModel.setNumber("2"));
        button3.setOnClickListener(item -> conversionViewModel.setNumber("3"));
        button4.setOnClickListener(item -> conversionViewModel.setNumber("4"));
        button5.setOnClickListener(item -> conversionViewModel.setNumber("5"));
        button6.setOnClickListener(item -> conversionViewModel.setNumber("6"));
        button7.setOnClickListener(item -> conversionViewModel.setNumber("7"));
        button8.setOnClickListener(item -> conversionViewModel.setNumber("8"));
        button9.setOnClickListener(item -> conversionViewModel.setNumber("9"));
        buttonDot.setOnClickListener(item -> conversionViewModel.setDot());
        buttonConv.setOnClickListener(item -> conversionViewModel.convert());
        buttonC.setOnClickListener(item -> conversionViewModel.setC());

        return view;
    }
}
