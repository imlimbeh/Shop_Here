package com.example.shophere;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuBar extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    Boolean bSC, bA, bS;
    LinearLayout lSBC, lA, lS;
    public static final String EXTRA_MESSAGE = "com.example.shophere.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bar);

        bSC = false;
        bA = false;
        bS = false;

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //Toast.makeText(MenuBar.this, mFirebaseUser.getEmail(),Toast.LENGTH_SHORT).show();

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
                    Intent history = new Intent(MenuBar.this, PurchaseHistory.class);
                    startActivity(history);
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
        Intent page = new Intent(MenuBar.this, Categories.class);
        switch (view.getId()){
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
                Toast.makeText(MenuBar.this, "home Kitchen !!!", Toast.LENGTH_SHORT).show();
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
                page.putExtra(EXTRA_MESSAGE, "videogames");
                startActivityForResult(page, TEXT_REQUEST);
                break;
        }
    }
    public void onClickShopCategories(View view){
        lSBC = findViewById(R.id.layout_shopbycategories);
        if (bSC){
            lSBC.setVisibility(View.GONE);
            bSC = false;
        }else{
            lSBC.setVisibility(View.VISIBLE);
            bSC = true;
        }
    }
    public void onClickAccount(View view){
        lA = findViewById(R.id.layout_account);
        if(bA){
            lA.setVisibility(View.GONE);
            bA = false;
        }else{
            lA.setVisibility(View.VISIBLE);
            bA = true;
        }
    }
    public void onClickSetting(View view){
        lS = findViewById(R.id.layout_setting);
        if (bS){
            lS.setVisibility(View.GONE);
            bS = false;
        }else{
            lS.setVisibility(View.VISIBLE);
            bS = true;
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