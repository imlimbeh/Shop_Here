package com.example.shophere;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PaymentComplete extends AppCompatActivity {
    String hID, pdID, pdName, pdImage;
    double pdPrice, totalPrice;
    TextView n, qu, p, tp;
    ImageView i;
    Button c;
    int q;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_complete);

        n = (TextView)findViewById(R.id.nm);
        qu = (TextView)findViewById(R.id.qua);
        p = (TextView)findViewById(R.id.price_pd);
        tp = (TextView)findViewById(R.id.tolpric);
        i = (ImageView)findViewById(R.id.imageproduct);
        c = (Button)findViewById(R.id.close);

        hID = getIntent().getStringExtra("history");
        pdID = getIntent().getStringExtra("productID");
        q = getIntent().getIntExtra("qt", 0);

        String checkProduct = pdID.substring(0, 2);
        switch (checkProduct) {
            case "PV":
                dRef = firebaseDatabase.getReference("product_videogames");
                break;

        }
        Toast.makeText(PaymentComplete.this, hID, Toast.LENGTH_SHORT).show();
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dRef.child(pdID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pdName = dataSnapshot.child("product_name").getValue(String.class);
                pdPrice = dataSnapshot.child("product_price").getValue(double.class);
                pdImage = dataSnapshot.child("product_image").getValue(String.class);
                totalPrice = pdPrice * q;
                n.setText(pdName);
                p.setText(String.format("RM %.2f", pdPrice));
                tp.setText(String.format("RM %.2f", totalPrice));
                qu.setText(String.valueOf(q));
                Picasso.get().load(pdImage).into(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}