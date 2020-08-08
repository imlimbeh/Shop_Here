package com.example.shophere;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductOverview extends AppCompatActivity {
    TextView name, price, id, stock, aboutUs, description, detail, featuresNDetails;
    ImageView image;
    int stockQ;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private DatabaseReference findName, findImage, findPrice, findStock, findAboutUs, findDescription, findDetail, findFeaturesNDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_overview);

        id = (TextView)findViewById(R.id.productOverviewID);
        name = (TextView)findViewById(R.id.productOverviewName);
        price = (TextView)findViewById(R.id.productOverviewPrice);
        image = (ImageView)findViewById(R.id.productOverviewImage);
        stock = (TextView)findViewById(R.id.quanlityStock);
        aboutUs = (TextView)findViewById(R.id.id_aboutUs);
        description = (TextView)findViewById(R.id.id_description);
        detail = (TextView)findViewById(R.id.id_detail);
        featuresNDetails = (TextView)findViewById(R.id.id_featuresndetail);
        String productid = getIntent().getStringExtra("id");
        String message = getIntent().getStringExtra("type");
        switch (message){
            case "videogames":
                databaseReference = firebaseDatabase.getReference("product_videogames");
                break;
        }
        id.setText(productid);
        findName = databaseReference.child(productid).child("product_name");
        findImage = databaseReference.child(productid).child("product_image");
        findPrice = databaseReference.child(productid).child("product_price");
        findStock = databaseReference.child(productid).child("product_stock");
        findAboutUs = databaseReference.child(productid).child("product_aboutus");
        findDescription = databaseReference.child(productid).child("product_description");
        findDetail = databaseReference.child(productid).child("product_detail");
        findFeaturesNDetail = databaseReference.child(productid).child("product_featuresNdetails");
    }

    @Override
    protected void onStart() {
        super.onStart();
        findName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                name.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Picasso.get().load(value).into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findPrice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double value = dataSnapshot.getValue(double.class);
                price.setText("RM "+String.valueOf(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findStock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stockQ = dataSnapshot.getValue(int.class);
                stock.setText(String.valueOf(stockQ));
                TextView textStock = (TextView) findViewById(R.id.id_gotStock);
                LinearLayout hs1 = findViewById(R.id.haveShow1), hs2 = findViewById(R.id.haveShow2);
                if (stockQ == 0){
                    textStock.setText(R.string.outstock_text);
                    hs1.setVisibility(View.GONE);
                    hs2.setVisibility(View.GONE);;
                } else{
                    textStock.setText(R.string.instock_text);
                    hs1.setVisibility(View.VISIBLE);
                    hs2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findAboutUs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                aboutUs.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findDescription.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                description.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                detail.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findFeaturesNDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                featuresNDetails.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}