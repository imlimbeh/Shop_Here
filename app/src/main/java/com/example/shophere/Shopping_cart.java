package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    String userID, currentUserID;
    String pn,pi;
    double pp, totalPrice;
    int numStock, totalItem;
    boolean b = false;
    int haveCartOrNot = 0, test = 0;
    FirebaseAuth mFirebaseAuth;

    public Shopping_cart() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUserID = mFirebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(currentUserID).child("shopping_cart");

        bil = findViewById(R.id.bill);
        noItem = (TextView) findViewById(R.id.no);
        list = (ScrollView) findViewById(R.id.listCart);


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
        FirebaseRecyclerAdapter<product_ShoppingCart, ShoppingViewHolder> firebaseRecyclerAdapter =
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
                                pp = dataSnapshot.child("product_price").getValue(double.class);
                                numStock = dataSnapshot.child("product_stock").getValue(int.class);
                                totalItem += product.getQuantity();
                                totalPrice += product.getQuantity() * pp;
                                item = (TextView) findViewById(R.id.numSubtotal);
                                tolPrice = (TextView) findViewById(R.id.SubTotalPrice);
                                String it;
                                if (totalItem > 1) {
                                    it = " item ) ";
                                } else {
                                    it = " items ) ";
                                }
                                item.setText("( " + String.valueOf(totalItem) + it);
                                tolPrice.setText(String.format("RM %.2f", totalPrice));
                                shoppingViewHolder.setShopping(getApplicationContext(), product.getProduct_id(), product.getShoppingCart_id(), product.getQuantity(), pn, pi, pp, numStock);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

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