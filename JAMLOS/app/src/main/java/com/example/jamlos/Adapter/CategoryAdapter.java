package com.example.jamlos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jamlos.Model.Category;
import com.example.jamlos.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> lstCategory;
    Context inflate;

    public CategoryAdapter() {
    }

    public CategoryAdapter(ArrayList<Category> lstCategory, Context myContext) {
        this.lstCategory = lstCategory;
        inflate = myContext;
    }

    @Override
    public int getCount() {
        return lstCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return lstCategory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        CategoryAdapter.RecyclerViewHolder holder;
        if(convertView == null){
            holder = new CategoryAdapter.RecyclerViewHolder();
            LayoutInflater inflate = LayoutInflater.from(viewGroup.getContext());
            convertView = inflate.inflate(R.layout.item_category, null);
            holder.txtTen = (TextView)convertView.findViewById(R.id.txtCategory);
            holder.imgSP = (ImageView)convertView.findViewById(R.id.imgCategory);
            convertView.setTag(holder);
        }else {
            holder = (CategoryAdapter.RecyclerViewHolder) convertView.getTag();
        }
            holder.txtTen.setText(lstCategory.get(i).getName());
            Picasso.with(inflate).load(lstCategory.get(i).getImage()).into(holder.imgSP);
        return convertView;
    }

    public class RecyclerViewHolder {
        TextView txtTen;
        ImageView imgSP;
    }
}