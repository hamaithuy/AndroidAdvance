package com.appbtl.appweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appbtl.appweather.model.item;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private ArrayList<item> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(ArrayList<item> listData, Context context) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.imgWeather = (ImageView) view.findViewById(R.id.imgTT);
            holder.maxTemp = (TextView) view.findViewById(R.id.thMax);
            holder.minTemp = (TextView) view.findViewById(R.id.thMin);
            holder.date = (TextView) view.findViewById(R.id.tDate);
            holder.status = (TextView) view.findViewById(R.id.txtTT);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        item daily = this.listData.get(i);
        holder.maxTemp.setText(daily.getMaxTemp());
        holder.minTemp.setText(daily.getMinTemp());
        holder.date.setText(daily.getDate());
        holder.status.setText(daily.getStatus());
        holder.imgWeather.setImageResource(R.drawable.shine);
        return view;
    }
    static class ViewHolder {
        ImageView imgWeather;
        TextView maxTemp;
        TextView minTemp;
        TextView date;
        TextView status;
    }
}

