package com.example.shophere;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MenuBar extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bar);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Toast.makeText(MenuBar.this, mFirebaseUser.getEmail(),Toast.LENGTH_SHORT).show();

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull AttributeSet attrs) {
        return inflater.inflate(R.layout.activity_menu_bar,container,false);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.nav_home:
                    Intent choose = new Intent(MenuBar.this, MainStore.class);
                    startActivity(choose);
                    break;
                case R.id.nav_restore:
                    //Intent restore = new Intent(MainStore.this, <?>.class);
                    //startActivity(restore);
                    Toast.makeText(MenuBar.this, "Building!!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_shopping_cart:
                    Intent shopping = new Intent(MenuBar.this, Shopping_cart.class);
                    startActivity(shopping);
                    break;
                case R.id.nav_menu:
                    break;
            }
            return true;
        }
    };
    public void onClickChoose(View view){
        Intent page;
        switch (view.getId()){
            case R.id.AllCategories:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "All Categories !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ArtCraft:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Art Craft !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.books:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Books !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.computers:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Computers !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fashion:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Fashion !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.healthHousehold:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Health House Hold !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homeKitchen:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Home Kitchen !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.movieTelevision:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Movie Television !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.petSupplies:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Pet Supplies !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.software:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Software !!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.videogames:
                //page = new Intent(MenuBar.this, <?>.class);
                //startActivity(page);
                Toast.makeText(MenuBar.this, "Video Games !!!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void logout(View view){
        // Logout
        mFirebaseAuth.signOut();
        // For Stay Logged in
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("stayLogged","false");
        editor.apply();
        // Go Back
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finishAffinity();
    }
}