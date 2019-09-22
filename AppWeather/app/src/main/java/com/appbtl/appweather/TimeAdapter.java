package com.appbtl.appweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appbtl.appweather.model.item;
import com.appbtl.appweather.model.time;

import java.util.ArrayList;
import java.util.List;

public class TimeAdapter extends BaseAdapter {
    private ArrayList<time> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public TimeAdapter(ArrayList<time> listData, Context context) {
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
            view = layoutInflater.inflate(R.layout.timeitem, null);
            holder = new ViewHolder();
            holder.imgWeather = (ImageView) view.findViewById(R.id.imageView2);
            holder.temp = (TextView) view.findViewById(R.id.textView9);
            holder.time = (TextView) view.findViewById(R.id.textView7);
            holder.wind = (TextView) view.findViewById(R.id.textView10);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        time daily = this.listData.get(i);
        holder.temp.setText(daily.getTemp());
        holder.wind.setText(daily.getWind());
        holder.time.setText(daily.getTime());
        holder.imgWeather.setImageResource(R.drawable.shine);
        return view;
    }
    static class ViewHolder {
        ImageView imgWeather;
        TextView temp;
        TextView time;
        TextView wind;
    }
}

