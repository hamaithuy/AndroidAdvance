package com.example.demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<Room> {
    private final Context context;
    private final ArrayList<Room> value;

    public GridAdapter(@NonNull Context context, ArrayList<Room> value) {
        super(context, R.layout.grid_item,value);
        this.context = context;
        this.value = value;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View gridItemView = convertView;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridItemView =  inflater.inflate(R.layout.grid_item,null);
        }


        ImageView ivRoomAvatar = (ImageView) gridItemView.findViewById(R.id.ivRoomAvatar);
        TextView tvPrice = (TextView) gridItemView.findViewById(R.id.tv_price);
        TextView tvAddress = (TextView) gridItemView.findViewById(R.id.tvAddress);
        TextView tvAcreage = (TextView) gridItemView.findViewById(R.id.tvAcreage);

        ivRoomAvatar.setImageResource(value.get(position).image);
        tvAddress.setText(value.get(position).address);
        tvPrice.setText(Float.toString(value.get(position).price));
        tvAcreage.setText(value.get(position).acreage);

        return gridItemView;
    }
}
