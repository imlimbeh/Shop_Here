package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Categories extends AppCompatActivity {

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

        Intent intent = getIntent();
        String message = intent.getStringExtra(MenuBar.EXTRA_MESSAGE);
        switch (message){
            case "videogames":
                databaseReference = firebaseDatabase.getReference("product_videogames");
                break;
        }
    }

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
                        viewHolder.setdetails(getApplicationContext(),product.getProduct_name(), product.getProduct_image(),product.getProduct_price());
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}