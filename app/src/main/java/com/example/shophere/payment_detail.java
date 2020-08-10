package com.example.shophere;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class payment_detail extends AppCompatActivity {

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayList2 = new ArrayList<>();
    Spinner spinner,spinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        spinner=(Spinner)findViewById(R.id.cardmonth);
        spinner2=(Spinner)findViewById(R.id.cardyear);

    }

    @Override
    protected void onStart() {
        super.onStart();
        arrayList.clear();
        for (int i = 1; i<=12; i++){
            arrayList.add(String.format("%02d",i));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(payment_detail.this, R.layout.style_spinner2, arrayList);
        spinner.setAdapter(arrayAdapter);

        arrayList2.clear();
        for (int i = 2020; i<=2030; i++){
            arrayList2.add(String.valueOf(i));
        }
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(payment_detail.this, R.layout.style_spinner2, arrayList2);
        spinner2.setAdapter(arrayAdapter1);

    }
}