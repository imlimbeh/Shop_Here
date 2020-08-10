package com.example.shophere;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentComplete extends AppCompatActivity {
    String hID, pdID, pdName;
    double pdPrice, totalPrice;
    int q;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference, dr, dRef, myHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_complete);

        hID = getIntent().getStringExtra("history");
        pdID = getIntent().getStringExtra("productID");
        q = getIntent().getIntExtra("qt", 0);
        databaseReference = firebaseDatabase.getReference("users").child("history").child(hID);
        String checkProduct = pdID.substring(0, 2);
        switch (checkProduct) {
            case "PV":
                dRef = firebaseDatabase.getReference("product_videogames");
                break;

        }
        Toast.makeText(PaymentComplete.this, hID, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dRef.child(pdID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pdName = dataSnapshot.child("product_name").getValue(String.class);
                pdPrice = dataSnapshot.child("product_price").getValue(double.class);
                totalPrice = pdPrice * q;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}