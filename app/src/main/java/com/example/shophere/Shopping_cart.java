package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Shopping_cart extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_shopping_cart);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull AttributeSet attrs) {
        return inflater.inflate(R.layout.activity_shopping_cart,container,false);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.nav_home:
                    Intent choose = new Intent(Shopping_cart.this, MainStore.class);
                    startActivity(choose);
                    break;
                case R.id.nav_restore:
                    //Intent restore = new Intent(MainStore.this, <?>.class);
                    //startActivity(restore);
                    Toast.makeText(Shopping_cart.this, "Building!!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_shopping_cart:
                    break;
                case R.id.nav_menu:
                    Intent menu = new Intent(Shopping_cart.this, MenuBar.class);
                    startActivity(menu);
                    break;
            }
            return true;
        }
    };
}