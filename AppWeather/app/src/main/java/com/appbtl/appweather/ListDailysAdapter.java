package com.appbtl.appweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appbtl.appweather.model.ListDailys;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListDailysAdapter extends RecyclerView.Adapter<ListDailysAdapter.recylerHolder> {
    ListDailys listDailys;
    Context context;
    public ListDailysAdapter(ListDailys listDailys, Context context) {
        this.listDailys = listDailys;
        this.context = context;
    }

    @NonNull
    @Override
    public recylerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item,parent,false);
        return new recylerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recylerHolder holder, int position) {
        double ma = listDailys.getList().get(position).getTemp().getMax();
        double mi = listDailys.getList().get(position).getTemp().getMin();
        int max=(int)ma;
        int min=(int)mi;
        Date date = new Date(listDailys.getList().get(position).getDt()*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE \ndd-MM-yyyy");
        String Day = simpleDateFormat.format(date);
        holder.tMax.setText(max+"°C");
        holder.tMin.setText(min+"°C");
        holder.txtTT.setText(listDailys.getList().get(position).getWeather().get(0).getDescription());
        holder.tdate.setText(Day);
        Glide.with(holder.itemView.getContext()).load("http://openweathermap.org/img/w/"+listDailys.getList().get(position).getWeather().get(0).getIcon()+".png")
                .into(holder.imgTT);
    }

    @Override
    public int getItemCount() {
        return listDailys.getList().size();
    }

    protected class recylerHolder extends RecyclerView.ViewHolder{
        ImageView imgTT;
        TextView tdate,txtTT,tMax,tMin;
        public recylerHolder(@NonNull View itemView) {
            super(itemView);
            tdate = (TextView)itemView.findViewById(R.id.tDate);
            txtTT=(TextView)itemView.findViewById(R.id.txtTT);
            tMax =(TextView)itemView.findViewById(R.id.tMax);
            tMin = (TextView)itemView.findViewById(R.id.tMin);
            imgTT = (ImageView)itemView.findViewById(R.id.imgTT);
        }
    }
}
