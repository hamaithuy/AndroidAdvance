package com.example.demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.time.LocalDateTime;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Room> {
    private final Context context;
    private final ArrayList<Room> value;

    public ListAdapter(@NonNull Context context, ArrayList<Room> value) {
        super(context, R.layout.grid_item,value);
        this.context = context;
        this.value = value;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView =  inflater.inflate(R.layout.list_item,null);
        }


        ImageView ivRoomAvatar = (ImageView) listItemView.findViewById(R.id.iv_list_roomAvatar);
        TextView tvPrice = (TextView) listItemView.findViewById(R.id.tv_price);
        TextView tvTitle = (TextView) listItemView.findViewById(R.id.tv_title);
        TextView tvAddress = (TextView) listItemView.findViewById(R.id.tv_address);
        TextView tvAcreage = (TextView) listItemView.findViewById(R.id.tv_acreage);
        TextView tvTime = (TextView) listItemView.findViewById(R.id.tv_time);

        ivRoomAvatar.setImageResource(value.get(position).image);
        tvPrice.setText(Float.toString(value.get(position).price));
        tvTitle.setText(value.get(position).title);
        tvAddress.setText(value.get(position).address);
        tvAcreage.setText(value.get(position).acreage);
        tvTime.setText(value.get(position).timePost.toString());
        return listItemView;
    }

}
