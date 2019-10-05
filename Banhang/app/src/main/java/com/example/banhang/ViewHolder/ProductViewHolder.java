package com.example.banhang.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhang.Interface.ItemClickListner;
import com.example.banhang.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tenSP,motaSP,giaSP;
    public ImageView anhSP;
    public ItemClickListner listner;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        tenSP = (TextView)itemView.findViewById(R.id.ten);
        motaSP = (TextView)itemView.findViewById(R.id.MotaSP);
        anhSP = (ImageView)itemView.findViewById(R.id.anhSP);
        giaSP = (TextView)itemView.findViewById(R.id.product_gia);

    }
    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;

    }

    @Override
    public void onClick(View view) {
        listner.Onclick(view,getAdapterPosition(),false);

    }
}
