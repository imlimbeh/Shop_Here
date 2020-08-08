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

public class ViewHolder extends RecyclerView.ViewHolder{
    View view;
    public ViewHolder (@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setdetails(Context ct, String pn, String pi, /*String pp*/ double pp){
        TextView text_productName = view.findViewById(R.id.productName);
        TextView text_productPrice = view.findViewById(R.id.productPrice);
        ImageView image_product = view.findViewById(R.id.productImage);

        text_productName.setText(pn);
        text_productPrice.setText("RM "+String.valueOf(pp));
        Picasso.get().load(pi).into(image_product);

        Animation animation = AnimationUtils.loadAnimation(ct, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
    }
}
