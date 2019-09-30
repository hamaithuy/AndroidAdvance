package com.example.jamlos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jamlos.Model.Category;
import com.example.jamlos.Model.Order;
import com.example.jamlos.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends BaseAdapter {
    ArrayList<Order> lstCart;
    Context inflate;

    public CartAdapter() {
    }

    public CartAdapter(ArrayList<Order> lstCart, Context myContext) {
        this.lstCart = lstCart;
        inflate = myContext;
    }

    @Override
    public int getCount() {
        return lstCart.size();
    }

    @Override
    public Object getItem(int i) {
        return lstCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        CartAdapter.RecyclerViewHolder holder;
        if(convertView == null){
            holder = new CartAdapter.RecyclerViewHolder();
            LayoutInflater inflate = LayoutInflater.from(viewGroup.getContext());
            convertView = inflate.inflate(R.layout.item_cart, null);

            holder.txtTen = (TextView)convertView.findViewById(R.id.txtCartNameProduct);
            holder.txtPrice = (TextView)convertView.findViewById(R.id.txtCartPriceProduct);
            holder.txtNumber = (TextView)convertView.findViewById(R.id.txtCartNumberProduct);
            holder.txtTotal = (TextView)convertView.findViewById(R.id.txtTotalProduct);
            convertView.setTag(holder);
        }else {
            holder = (CartAdapter.RecyclerViewHolder) convertView.getTag();
        }
        holder.txtTen.setText(lstCart.get(i).getProductName());
        holder.txtPrice.setText(lstCart.get(i).getPrice() + " VND");
        holder.txtNumber.setText(lstCart.get(i).getQuantity());
        String price = lstCart.get(i).getPrice();
        int price1 = Integer.parseInt(price.replace(".",""));
        String number = lstCart.get(i).getQuantity();
        int number1 = Integer.parseInt(number);
        int totalprice = price1*number1;
        String totalprice1 = NumberFormat.getInstance(Locale.GERMAN).format(totalprice);
        holder.txtTotal.setText(totalprice1 + " VND");
        return convertView;
    }

    public class RecyclerViewHolder {
        TextView txtTen, txtPrice, txtNumber, txtTotal;
    }
}