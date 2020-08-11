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
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class Shopping_cart extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dr, dRef;
    TextView item, tolPrice, noItem;
    ConstraintLayout bil;
    ScrollView list;
    String currentUserID, shoppingID;
    String pn,pi;
    double totalPrice;
    int numStock, totalItem;
    FirebaseAuth mFirebaseAuth;

    public Shopping_cart() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.HistoryCartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUserID = mFirebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(currentUserID).child("shopping_cart");

        bil = findViewById(R.id.bill);
        noItem = (TextView) findViewById(R.id.no);
        list = (ScrollView) findViewById(R.id.listCart);
        item = (TextView) findViewById(R.id.numSubtotal);
        tolPrice = (TextView) findViewById(R.id.SubTotalPrice);

        totalItem = 0;
        totalPrice = 0.00;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    bil.setVisibility(View.VISIBLE);
                    list.setVisibility(View.VISIBLE);
                    noItem.setVisibility(View.GONE);
                }else{
                    bil.setVisibility(View.GONE);
                    list.setVisibility(View.GONE);
                    noItem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final FirebaseRecyclerAdapter<product_ShoppingCart, ShoppingViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<product_ShoppingCart, ShoppingViewHolder>(
                        product_ShoppingCart.class,
                        R.layout.list_shopping_cart,
                        ShoppingViewHolder.class,
                        databaseReference
                ) {
                    @Override
                    public void populateViewHolder(final ShoppingViewHolder shoppingViewHolder, final product_ShoppingCart product, int i) {
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
                                numStock = dataSnapshot.child("product_stock").getValue(int.class);
                                totalItem += product.getQuantity();
                                totalPrice += product.getQuantity() * product.getProduct_price();

                                String it;
                                if (totalItem > 1) {
                                    it = " item ) ";
                                } else {
                                    it = " items ) ";
                                }
                                item.setText("( " + String.valueOf(totalItem) + it);
                                tolPrice.setText(String.format("RM %.2f", totalPrice));
                                shoppingViewHolder.setShopping(getApplicationContext(), product.getProduct_id(), product.getShoppingCart_id(), product.getQuantity(), pn, pi, product.getProduct_price(), numStock);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public ShoppingViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
                        final ShoppingViewHolder viewHolder = super.onCreateViewHolder(parent,viewType);
                        viewHolder.setOnclickListener(new ShoppingViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                            @Override
                            public void onDeleteClick(View view, int position) {
                                shoppingID = getItem(position).getShoppingCart_id();
                                delete(shoppingID);
                            }
                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

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
                    Intent choose = new Intent(Shopping_cart.this, MainStore.class);
                    startActivity(choose);
                    break;
                case R.id.nav_restore:
                    Intent history = new Intent(Shopping_cart.this, PurchaseHistory.class);
                    startActivity(history);
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
    public void delete( String sID){
        databaseReference.child(sID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    double pri = dataSnapshot.child("product_price").getValue(double.class);
                    int quan = dataSnapshot.child("quantity").getValue(int.class);
                    totalItem -= quan;
                    totalPrice -= pri * quan;
                    String it;
                    if (totalItem > 1) {
                        it = " item ) ";
                    } else {
                        it = " items ) ";
                    }
                    item.setText("( " + String.valueOf(totalItem) + it);
                    tolPrice.setText(String.format("RM %.2f", totalPrice));
                }else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child(sID).removeValue();
    }
}