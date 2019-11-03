package com.appbtl.appweather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appbtl.appweather.model.ListDailys;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ListDailysAdapter extends RecyclerView.Adapter<ListDailysAdapter.recylerHolder> {
    ListDailys listDailys;
    Context context;
    private HashMap<String, String> Des = new HashMap<String, String>();
    private static final String TAG = "MyActivity";
    private int lastPosition = -1;

    public ListDailysAdapter(ListDailys listDailys, Context context) {
        this.listDailys = listDailys;
        this.context = context;
    }

    @NonNull
    @Override
    public recylerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new recylerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recylerHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: " + listDailys);
        double ma = listDailys.getList().get(position).getTemp().getMax();
        double mi = listDailys.getList().get(position).getTemp().getMin();
        int max = (int) ma;
        int min = (int) mi;
        Date date = new Date(listDailys.getList().get(position).getDt() * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE \ndd-MM-yyyy");
        String Day = simpleDateFormat.format(date);
        holder.tMax.setText(max + "°C");
        holder.tMin.setText(min + "°C");
        Log.i(TAG, "onBindViewHolder: " + listDailys.getList().get(position).getWeather().get(0).getDescription());
        holder.txtTT.setText(Des.get(listDailys.getList().get(position).getWeather().get(0).getDescription()));
        holder.tdate.setText(Day);
        Glide.with(holder.itemView.getContext()).load("http://openweathermap.org/img/w/" + listDailys.getList().get(position).getWeather().get(0).getIcon() + ".png")
                .into(holder.imgTT);
        setAnimation(holder.itemView,position);
    }



    private void setAnimation(View view,int position){
        if(position>lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_left_item);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return listDailys.getList().size();
    }

    protected class recylerHolder extends RecyclerView.ViewHolder {
        ImageView imgTT;
        TextView tdate, txtTT, tMax, tMin;

        public recylerHolder(@NonNull View itemView) {
            super(itemView);
            tdate = (TextView) itemView.findViewById(R.id.tDate);
            txtTT = (TextView) itemView.findViewById(R.id.txtTT);
            tMax = (TextView) itemView.findViewById(R.id.thMax);
            tMin = (TextView) itemView.findViewById(R.id.thMin);
            imgTT = (ImageView) itemView.findViewById(R.id.imgTT);
            Des.put("scattered clouds", "Mây rải rác");
            Des.put("moderate rain", "Mưa vừa");
            Des.put("heavy intensity rain", "Mưa lớn");
            Des.put("broken clouds", "Mây rải rác");
            Des.put("sky is clear", "Trời quang");
            Des.put("light rain", "Mưa nhỏ");
            Des.put("few clouds", "Ít mây");
            Des.put("rain", "Mưa");
            Des.put("thunderstorm", "Sấm chớp");
            Des.put("snow", "Tuyết");
            Des.put("shower rain", "Mưa to");
            Des.put("mist", "Sương mù");
            Des.put("clear sky", "Trời quang");
            Des.put("overcast clouds", "Trời u ám");
            Des.put("light shower snow", "Mưa tuyết nhẹ");
        }
    }
}
