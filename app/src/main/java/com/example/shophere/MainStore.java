package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainStore extends AppCompatActivity {
    Button bLOUT;
    ImageView i1,i2,i3;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("product_videogames");
    private DatabaseReference first = databaseReference.child("PV00001").child("product_image");
    private DatabaseReference second = databaseReference.child("PV00002").child("product_image");
    private DatabaseReference third = databaseReference.child("PV00003").child("product_image");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_store);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("product_videogames");

        i1 = (ImageView)findViewById(R.id.PI1);
        i2 = (ImageView)findViewById(R.id.PI2);
        i3 = (ImageView)findViewById(R.id.PI3);

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
        System.exit(0);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            BottomNavigationView btmNav = findViewById(R.id.bottom_navigation);
            switch (item.getItemId()){
                case R.id.nav_home:
                    break;
                case R.id.nav_restore:
                    //Intent restore = new Intent(MainStore.this, <?>.class);
                    //startActivity(restore);
                    Toast.makeText(MainStore.this, "Building!!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_shopping_cart:
                    Intent shopping = new Intent(MainStore.this, Shopping_cart.class);
                    startActivity(shopping);
                    break;
                case R.id.nav_menu:
                    Intent menu = new Intent(MainStore.this, MenuBar.class);
                    startActivity(menu);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        first.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(i1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        second.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(i2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        third.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(i3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}