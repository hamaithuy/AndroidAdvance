package com.appbtl.appweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appbtl.appweather.model.Hours;
import com.bumptech.glide.Glide;

import java.util.HashMap;

public class ListInfoAdapter extends RecyclerView.Adapter<ListInfoAdapter.recylerHolder> {
    Hours listHours;
    Context context;
    private HashMap<String, String> Des = new HashMap<String, String>();

    public ListInfoAdapter(Hours listHours, Context context) {
        this.listHours = listHours;
        this.context = context;
    }

    @NonNull
    @Override
    public ListInfoAdapter.recylerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.timeitem, parent, false);
        return new ListInfoAdapter.recylerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListInfoAdapter.recylerHolder holder, int position) {
        double temp = listHours.getList().get(position).getMain().getTemp();
        double mi = listHours.getList().get(position).getWind().getSpeed();
        holder.tMax.setText((int) temp + "°C");
        holder.tMin.setText(mi + "km/h");
        String date = listHours.getList().get(position).getDt_txt();
        String time = date.replaceAll("\\s+", "\n");
        holder.tdate.setText(time);
        Glide.with(holder.itemView.getContext()).load("http://openweathermap.org/img/w/" + listHours.getList().get(position).getWeather().get(0).getIcon() + ".png")
                .into(holder.imgTT);
    }

    @Override
    public int getItemCount() {
        return listHours.getList().size();
    }

    protected class recylerHolder extends RecyclerView.ViewHolder {
        ImageView imgTT;
        TextView tdate, txtTT, tMax, tMin;

        public recylerHolder(@NonNull View itemView) {
            super(itemView);
            tdate = (TextView) itemView.findViewById(R.id.tDate);
            tMax = (TextView) itemView.findViewById(R.id.thMax);
            tMin = (TextView) itemView.findViewById(R.id.thMin);
            imgTT = (ImageView) itemView.findViewById(R.id.imgTT);
            Des.put("scattered clouds", "Mây rải rác");
            Des.put("moderate rain", "Mưa vừa");
            Des.put("heavy intensity rain", "Mưa lớn");
            Des.put("broken clouds", "Mây rải rác");
            Des.put("sky is clear", "Trời quang");
            Des.put("light rain", "Mưa nhỏ");
            Des.put("overcast clouds", "Trời u ám");

        }
    }
}
