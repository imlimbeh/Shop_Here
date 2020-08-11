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

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    View view;
    public HistoryViewHolder (@NonNull View itemView) {
        super(itemView);
        view = itemView;
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mClicklistener.onItemClick(view, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                mClicklistener.onItemLongClick(view, getAdapterPosition());
                return false;
            }
        });
    }
    public void setHistory(Context ct, String pID, String hID, int q, String pn, String pi, double pp){
        TextView text_productID = view.findViewById(R.id.productID);
        TextView text_productName = view.findViewById(R.id.productName);
        TextView text_productPrice = view.findViewById(R.id.productPrice);
        TextView text_productQuantity = view.findViewById(R.id.quanti);
        TextView text_totalPrice = view.findViewById(R.id.totaPri);
        ImageView image_product = view.findViewById(R.id.productImage);

        double tpp = pp * q;
        text_productID.setText(pID);
        text_productName.setText(pn);
        text_productPrice.setText(String.format("RM %.2f", pp));
        text_productQuantity.setText(String.valueOf(q));
        text_totalPrice.setText(String.format("RM %.2f", tpp));
        Picasso.get().load(pi).into(image_product);

        Animation animation = AnimationUtils.loadAnimation(ct, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
    }
    private HistoryViewHolder.ClickListener mClicklistener;
    public interface ClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnclickListener(HistoryViewHolder.ClickListener clickListener){
        mClicklistener = clickListener;
    }
}
