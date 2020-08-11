package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Categories extends AppCompatActivity {

    String message;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("product_videogames");

        Intent intent = getIntent();
        message = intent.getStringExtra(MenuBar.EXTRA_MESSAGE);
        switch (message){
            case "videogames":
                databaseReference = firebaseDatabase.getReference("product_videogames");
                break;
        }
        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.nav_home:
                    Intent choose = new Intent(Categories.this, MainStore.class);
                    startActivity(choose);
                    break;
                case R.id.nav_restore:
                    Intent restore = new Intent(Categories.this, PurchaseHistory.class);
                    startActivity(restore);
                    break;
                case R.id.nav_shopping_cart:
                    Intent shopping = new Intent(Categories.this, Shopping_cart.class);
                    startActivity(shopping);
                    break;
                case R.id.nav_menu:
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<product, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<product, ViewHolder>(
                        product.class,
                        R.layout.list_categories,
                        ViewHolder.class,
                        databaseReference
                ){
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, product product, int i) {
                        viewHolder.setdetails(getApplicationContext(), product.getProduct_id(), product.getProduct_name(), product.getProduct_image(),product.getProduct_price());
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        ViewHolder viewHolder = super.onCreateViewHolder(parent,viewType);
                        viewHolder.setOnclickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView id = view.findViewById(R.id.productID);
                                String mid = id.getText().toString();

                                Intent intent = new Intent(view.getContext(), ProductOverview.class);
                                intent.putExtra("id", mid);
                                intent.putExtra("type", message);
                                startActivity(intent);;
                            }
                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}