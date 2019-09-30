package com.example.jamlos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jamlos.Model.Product;
import com.example.jamlos.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    ArrayList<Product> lstProduct;
    Context inflate;

    public ProductAdapter() {
    }

    public ProductAdapter(ArrayList<Product> lstProduct, Context myContext) {
        this.lstProduct = lstProduct;
        inflate = myContext;
    }

    @Override
    public int getCount() {
        return lstProduct.size();
    }

    @Override
    public Object getItem(int i) {
        return lstProduct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        RecyclerViewHolder holder;
        if(convertView == null){
            holder = new RecyclerViewHolder();
            LayoutInflater inflate = LayoutInflater.from(viewGroup.getContext());
            convertView = inflate.inflate(R.layout.item_featured_product, null);
            holder.txtGiaSP = (TextView)convertView.findViewById(R.id.txtFeaturedPriceProduct);
            holder.txtTenSP = (TextView)convertView.findViewById(R.id.txtFeaturedNameProduct);
            holder.txtDiscountSP = (TextView)convertView.findViewById(R.id.txtDiscount);
            holder.imgSP = (ImageView)convertView.findViewById(R.id.imgFeaturedProduct);
            convertView.setTag(holder);
        }else {
            holder = (RecyclerViewHolder) convertView.getTag();
        }
        if(lstProduct.get(i).getDiscount().equals("0")){
            holder.txtTenSP.setText(lstProduct.get(i).getName());
            holder.txtGiaSP.setText(lstProduct.get(i).getPrice() + " VND");
            holder.txtDiscountSP.setVisibility(View.INVISIBLE);
            Picasso.with(inflate).load(lstProduct.get(i).getLstImage().get(0)).into(holder.imgSP);
        } else{
            holder.txtTenSP.setText(lstProduct.get(i).getName());
            holder.txtGiaSP.setText(lstProduct.get(i).getPrice() + " VND");
            holder.txtDiscountSP.setText(lstProduct.get(i).getDiscount() + "%");
            Picasso.with(inflate).load(lstProduct.get(i).getLstImage().get(0)).into(holder.imgSP);
        }
        return convertView;
    }

    public class RecyclerViewHolder {
        TextView txtTenSP, txtGiaSP, txtDiscountSP;
        ImageView imgSP;
    }
}

