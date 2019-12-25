package com.example.thstore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thstore.Activities.Cart;
import com.example.thstore.Activities.MainActivity;
import com.example.thstore.Model.Data_Offer;
import com.example.thstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.thstore.Activities.MainActivity.arrayData_Offers;

public class Adapter_Cart extends RecyclerView.Adapter<Adapter_Cart.ViewHolder>{

    Context context_offers;
    ArrayList<Data_Offer> data_offer;


    public Adapter_Cart(Context context_offers, ArrayList<Data_Offer> data_offer  ) {
        this.context_offers = context_offers;
        this.data_offer = data_offer;
    }


    @NonNull
    @Override
    public Adapter_Cart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context_offers).inflate(R.layout.item_cart_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Picasso.get().load(data_offer.get(position).getImage_Cart()).fit().into(holder.imgCart_Product);
        holder.txtName.setText(data_offer.get(position).getName());
        holder.txtSize.setText(data_offer.get(position).getSize());
        holder.txtPrice.setText(data_offer.get(position).getPrice());
        holder.btnValue.setText(data_offer.get(position).getAccount());
        holder.txtID_Cart_Products.setText(data_offer.get(position).getID());

        String ac = holder.btnValue.getText().toString();
        if (ac.equals("10"))
        {
            holder.btnIncrease.setVisibility(View.INVISIBLE);
            holder.btnDecrease.setVisibility(View.VISIBLE);
        }
        else if (ac.equals("1"))
        {
            holder.btnIncrease.setVisibility(View.VISIBLE);
            holder.btnDecrease.setVisibility(View.INVISIBLE);
        }

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newAc = Integer.parseInt(holder.btnValue.getText().toString()) + 1;
                int Price = Integer.parseInt(arrayData_Offers.get(position).getPrice());
                int Account = Integer.parseInt(arrayData_Offers.get(position).getAccount());
                int oldPrice = (Price * Account);
                arrayData_Offers.get(position).setAcount(String.valueOf(newAc));
                holder.btnValue.setText(data_offer.get(position).getAccount());
                int newPrice = (newAc * Price);
                int change = newPrice - oldPrice;
                int newTotal = Cart.total + change;
                Cart.total = newTotal;
                Cart.txtTotallyMoney.setText(String.valueOf(newTotal));

                if (newAc > 9){
                    holder.btnIncrease.setVisibility(View.INVISIBLE);
                    holder.btnDecrease.setVisibility(View.VISIBLE);
                }
                else{
                    holder.btnIncrease.setVisibility(View.VISIBLE);
                    holder.btnDecrease.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newAc = Integer.parseInt(holder.btnValue.getText().toString()) - 1;
                int Price = Integer.parseInt(arrayData_Offers.get(position).getPrice());
                int Account = Integer.parseInt(arrayData_Offers.get(position).getAccount());
                int oldPrice = (Price * Account);
                arrayData_Offers.get(position).setAcount(String.valueOf(newAc));
                holder.btnValue.setText(data_offer.get(position).getAccount());
                int newPrice = (newAc * Price);
                int change = oldPrice - newPrice;
                int newTotal = Cart.total - change;
                Cart.total = newTotal;
                Cart.txtTotallyMoney.setText(String.valueOf(newTotal));
                if (newAc <= 1){
                    holder.btnIncrease.setVisibility(View.VISIBLE);
                    holder.btnDecrease.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.btnIncrease.setVisibility(View.VISIBLE);
                    holder.btnDecrease.setVisibility(View.VISIBLE);
                }
            }
        });


    }



    @Override
    public int getItemCount() {
        return data_offer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtID_Cart_Products;
        TextView txtColor_Cart;
        ImageView imgCart_Product;
        TextView txtPrice;
        TextView txtSize;
        Button btnIncrease, btnDecrease, btnValue, btnDelete;
        CardView cvItems;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName_Cart_Products);
            imgCart_Product = itemView.findViewById(R.id.img_Cart_Products);
            txtColor_Cart = itemView.findViewById(R.id.txtColor_Cart);
            txtPrice = itemView.findViewById(R.id.txtPrice_Cart);
            txtSize = itemView.findViewById(R.id.txtSize_Cart);
            txtID_Cart_Products = itemView.findViewById(R.id.txtID_Cart_Products);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnValue = itemView.findViewById(R.id.btnValue);
            //btnDelete = itemView.findViewById(R.id.btnDelete);
            cvItems = itemView.findViewById(R.id.cvItems);
        }


    }
}












/*{
holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //arrayData_Offers.notify();
                int Price = Integer.parseInt(arrayData_Offers.get(position).getPrice());
                int Account = Integer.parseInt(arrayData_Offers.get(position).getAccount());
                int oldPrice = (Price * Account);
                arrayData_Offers.get(position).setAcount(String.valueOf(0));
                int newTotal = Cart.total - oldPrice;
                Cart.total = newTotal;
                Cart.txtTotallyMoney.setText(String.valueOf(newTotal));
                holder.cvItems.setVisibility(View.INVISIBLE);
                arrayData_Offers.remove(position);
                arrayData_Offers.get(position - 1);

            }
        });}*/