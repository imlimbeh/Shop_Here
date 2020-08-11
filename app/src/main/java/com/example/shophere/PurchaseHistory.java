package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PurchaseHistory extends AppCompatActivity {
    String currentUserID, historyID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dr, dRef,findUser;
    FirebaseAuth mFirebaseAuth;
    RecyclerView recyclerView;
    TextView userName, no;
    ScrollView list;
    String pn,pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUserID = mFirebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(currentUserID).child("history");
        findUser = firebaseDatabase.getReference("users").child(currentUserID);

        recyclerView = findViewById(R.id.HistoryCartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userName = (TextView)findViewById(R.id.customer_name);
        list = (ScrollView)findViewById(R.id.listCart);
        no = (TextView)findViewById(R.id.no);

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_shopping_cart);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.nav_home:
                    Intent choose = new Intent(PurchaseHistory.this, MainStore.class);
                    startActivity(choose);
                    break;
                case R.id.nav_restore:
                    break;
                case R.id.nav_shopping_cart:
                    Intent shopping = new Intent(PurchaseHistory.this, Shopping_cart.class);
                    startActivity(shopping);
                    break;
                case R.id.nav_menu:
                    Intent menu = new Intent(PurchaseHistory.this, MenuBar.class);
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
                if(dataSnapshot.exists()){
                    list.setVisibility(View.VISIBLE);
                    no.setVisibility(View.GONE);
                }else{
                    list.setVisibility(View.GONE);
                    no.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerAdapter<product_history, HistoryViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<product_history, HistoryViewHolder>(
                        product_history.class,
                        R.layout.list_history,
                        HistoryViewHolder.class,
                        databaseReference
                ) {
                    @Override
                    public void populateViewHolder(final HistoryViewHolder historyViewHolder, final product_history product, int i) {
                        String checkProduct = product.getProduct_id().substring(0, 2);
                        switch (checkProduct) {
                            case "PV":
                                dr = firebaseDatabase.getReference("product_videogames");
                        }
                        dRef = dr.child(product.getProduct_id());
                        dRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pn = dataSnapshot.child("product_name").getValue(String.class);
                                pi = dataSnapshot.child("product_image").getValue(String.class);
                                historyViewHolder.setHistory(getApplicationContext(), product.getProduct_id(), product.getHistory_id(), product.getQuantity(), pn, pi, product.getProduct_price());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public HistoryViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
                        final HistoryViewHolder viewHolder = super.onCreateViewHolder(parent,viewType);
                        viewHolder.setOnclickListener(new HistoryViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                            @Override
                            public void onItemLongClick(View view, int position) {
                                //historyID = getItem(position).getShoppingCart_id();
                                //delete(historyID);
                            }
                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        findUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("username").getValue(String.class);
                userName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void delete(String hID) {

    }
}