package com.example.shophere;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShoppingViewHolder extends RecyclerView.ViewHolder{
    View view;
    private ArrayList<String> arrayList = new ArrayList<>();
    public ShoppingViewHolder (@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setShopping(Context ct, String pID, String sID, int quantity, String pn, String pi, double pp, int numStock){
        CardView have = view.findViewById(R.id.card);
        have.setVisibility(View.VISIBLE);
        TextView text_shoppingID = view.findViewById(R.id.shoppingID);
        TextView text_productID = view.findViewById(R.id.productID);
        TextView text_productName = view.findViewById(R.id.productName);
        TextView text_productPrice = view.findViewById(R.id.productPrice);
        ImageView image_product = view.findViewById(R.id.productImage);
        TextView text_quantity = view.findViewById(R.id.textQuantity);
        TextView num_quantity = view.findViewById(R.id.quan);
        Spinner spinner = view.findViewById(R.id.id_quantity);

        text_productID.setText(pID);
        text_shoppingID.setText(sID);
        num_quantity.setText(String.valueOf(quantity));
        text_productName.setText(pn);
        text_productPrice.setText("RM "+String.valueOf(pp));
        Picasso.get().load(pi).into(image_product);
        if(numStock == 0){
            text_quantity.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            arrayList.clear();
            arrayList.add(String.valueOf(0));
        }else{
            text_quantity.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            arrayList.clear();
            for (int i = 1; i<=numStock; i++){
                arrayList.add(String.valueOf(i));
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ct, R.layout.style_spinner, arrayList);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(quantity-1);

        Animation animation = AnimationUtils.loadAnimation(ct, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
    }
}
