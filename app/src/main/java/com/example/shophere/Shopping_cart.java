package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;

public class Shopping_cart extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dr, dRef;
    TextView text_productName, text_productPrice, text_quantity;
    ImageView image_product;
    Spinner spinner;
    private ArrayList<String> arrayList = new ArrayList<>();
    String userID, currentUserID;
    String pn,pi;
    double pp;
    int numStock, q;

    FirebaseAuth mFirebaseAuth;

    public Shopping_cart() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("shopping_cart");

        mFirebaseAuth = FirebaseAuth.getInstance();

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

    @Override
    protected void onStart() {
        super.onStart();
        if(databaseReference == null) {

        }else {
            FirebaseRecyclerAdapter<product_ShoppingCart, ShoppingViewHolder> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<product_ShoppingCart, ShoppingViewHolder>(
                            product_ShoppingCart.class,
                            R.layout.list_shopping_cart,
                            ShoppingViewHolder.class,
                            databaseReference
                    ) {
                        @Override
                        protected void populateViewHolder(ShoppingViewHolder shoppingViewHolder, product_ShoppingCart product, int i) {
                            currentUserID = mFirebaseAuth.getCurrentUser().getUid();
                            userID = product.getUserID();
                            if(currentUserID.equals(userID)) {
                                //Toast.makeText(Shopping_cart.this, product.getProduct_id(),Toast.LENGTH_SHORT).show();
                                String checkProduct = product.getProduct_id().substring(0,2);
                                switch (checkProduct){
                                    case "PV":
                                        dr = firebaseDatabase.getReference("product_videogames");
                                }
                                dRef = dr.child(product.getProduct_id());
                                dRef.child("product_name").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        //text_productName = (TextView)findViewById(R.id.productName);
                                        pn = dataSnapshot.getValue(String.class);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                dRef.child("product_image").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        //image_product = (ImageView)findViewById(R.id.productImage);
                                        pi = dataSnapshot.getValue(String.class);
                                        //Picasso.get().load(pi).into(image_product);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                dRef.child("product_price").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        //text_productPrice = (TextView)findViewById(R.id.productPrice);
                                        pp = dataSnapshot.getValue(double.class);
                                        //text_productPrice.setText("RM " + String.valueOf(pp));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                spinner = (Spinner)findViewById(R.id.id_quantity);
                                text_quantity = (TextView)findViewById(R.id.textQuantity);
                                dRef.child("product_stock").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        numStock = dataSnapshot.getValue(int.class);
                                        if (numStock == 0){
                                            text_quantity.setVisibility(View.GONE);
                                            spinner.setVisibility(View.GONE);
                                            arrayList.clear();
                                            arrayList.add(String.valueOf(0));
                                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Shopping_cart.this, R.layout.style_spinner, arrayList);
                                            spinner.setAdapter(arrayAdapter);
                                        } else{
                                            text_quantity.setVisibility(View.VISIBLE);
                                            spinner.setVisibility(View.VISIBLE);
                                            arrayList.clear();
                                            for (int i = 1; i<=numStock; i++){
                                                arrayList.add(String.valueOf(i));
                                            }
                                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Shopping_cart.this, R.layout.style_spinner, arrayList);
                                            spinner.setAdapter(arrayAdapter);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                shoppingViewHolder.setShopping(getApplicationContext(), product.getProduct_id(), product.getUserID(), product.getShoppingCart_id(), product.getQuantity(), pn, pi, pp);
                            }
                        }
                    };
            recyclerView.setAdapter(firebaseRecyclerAdapter);
        }
    }
}