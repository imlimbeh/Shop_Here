package com.example.shophere;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Shopping_cart extends AppCompatActivity {

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull AttributeSet attrs) {
        return inflater.inflate(R.layout.activity_shopping_cart,container,false);
    }
}