package com.example.shophere;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ShoppingViewHolder extends RecyclerView.ViewHolder{
    View view;
    public ShoppingViewHolder (@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setShopping(Context ct, String pID, String uID, String sID, int quantity, String pn, String pi, double pp){
        TextView text_shoppingID = view.findViewById(R.id.shoppingID);
        TextView text_productID = view.findViewById(R.id.productID);
        TextView text_productName = view.findViewById(R.id.productName);
        TextView text_productPrice = view.findViewById(R.id.productPrice);
        ImageView image_product = view.findViewById(R.id.productImage);
        TextView text_quantity = view.findViewById(R.id.quan);

        text_productID.setText(pID);
        text_shoppingID.setText(sID);
        text_quantity.setText(String.valueOf(quantity));
        text_productName.setText(pn);
        text_productPrice.setText("RM "+String.valueOf(pp));
        Picasso.get().load(pi).into(image_product);

        Animation animation = AnimationUtils.loadAnimation(ct, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
    }
}
