package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.cardform.view.CardEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class payment_detail extends AppCompatActivity {
    String pid;
    int quantity, ProductQuantity;
    double ProductPrice, totalPrice;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayList2 = new ArrayList<>();
    Spinner spinner,spinner2;
    Button buy;
    TextView p, cancel;
    CardEditText cardnum;
    int num;

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference, dr, findNumHistory, myHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        buy = (Button)findViewById(R.id.continuepay);
        p = (TextView)findViewById(R.id.priID);
        cancel = (TextView)findViewById(R.id.cancel);
        cardnum = findViewById(R.id.bt_card_form_card_number);

        pid = getIntent().getStringExtra("productid");
        if(pid != null) {
            quantity = getIntent().getIntExtra("quan", 0);
        }

        String checkProduct = pid.substring(0, 2);
        switch (checkProduct) {
            case "PV":
                dr = firebaseDatabase.getReference("product_videogames");
        }
        databaseReference = dr.child(pid);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNum = String.valueOf(cardnum.getText());
                String cardType = cardnum.getCardType().name();
                if(cardType == null || cardType == "UNKNOWN"){
                    Toast.makeText(payment_detail.this, "Credit Card UNKNOWN !!!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(payment_detail.this, "We are under construction!!!", Toast.LENGTH_SHORT).show();
                    String history_id = "H" + (num);
                    String userID = mFirebaseAuth.getCurrentUser().getUid();
                    myHistory = firebaseDatabase.getReference("users").child(userID).child("history").child(history_id);
                    myHistory.child("history_id").setValue(history_id);
                    myHistory.child("product_id").setValue(pid);
                    myHistory.child("quantity").setValue(quantity);
                    myHistory.child("payment_price").setValue(totalPrice);
                    int leftStock = ProductQuantity - quantity;
                    databaseReference.child("product_stock").setValue(leftStock);
                    firebaseDatabase.getReference("IDNumStore").child("historyNum").setValue(num);
                    Intent intent = new Intent(payment_detail.this, PaymentComplete.class);
                    intent.putExtra("history", history_id);
                    intent.putExtra("productID", pid);
                    intent.putExtra("qt", quantity);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductPrice = dataSnapshot.child("product_price").getValue(double.class);
                ProductQuantity = dataSnapshot.child("product_stock").getValue(int.class);

                totalPrice = ProductPrice * quantity;
                p.setText(String.format("RM %.2f", totalPrice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findNumHistory = firebaseDatabase.getReference("IDNumStore").child("historyNum");
        findNumHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                num = dataSnapshot.getValue(int.class);
                num += 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}