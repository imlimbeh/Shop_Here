package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
    public static final String EXTRA_MESSAGE = "com.example.shophere.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;
    ImageView i1,i2,i3;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("product_videogames");

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
                    Intent history = new Intent(MainStore.this, PurchaseHistory.class);
                    startActivity(history);
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link1 = dataSnapshot.child("PV00001").child("product_image").getValue(String.class);
                String link2 = dataSnapshot.child("PV00002").child("product_image").getValue(String.class);
                String link3 = dataSnapshot.child("PV00003").child("product_image").getValue(String.class);
                Picasso.get().load(link1).into(i1);
                Picasso.get().load(link2).into(i2);
                Picasso.get().load(link3).into(i3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onClickShopNow(View view){
        Intent page = new Intent(MainStore.this, Categories.class);
        page.putExtra(EXTRA_MESSAGE, "videogames");
        startActivityForResult(page, TEXT_REQUEST);
    }
}