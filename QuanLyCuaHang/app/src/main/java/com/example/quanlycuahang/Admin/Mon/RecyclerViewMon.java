package com.example.quanlycuahang.Admin.Mon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlycuahang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


public class RecyclerViewMon {
    private Context context;
    private MonAdapter monAdapter;

    public void setConfig(RecyclerView recyclerView, Context mContext, List<Mon> mons, List<String> keys) {
        context = mContext;
        monAdapter = new MonAdapter(mons, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(monAdapter);

    }


    class MonItemView extends RecyclerView.ViewHolder {
        private TextView tvTenMon;
        private TextView tvGia;
        private TextView tvLoaiMon;
        private ImageView imgItem;
       // private TextView tvStt;
        private String key;

        public MonItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.mon_item_layout, parent, false));
            tvTenMon = itemView.findViewById(R.id.tv_ten_mon);
            tvGia = itemView.findViewById(R.id.tv_gia_mon);
            tvLoaiMon = itemView.findViewById(R.id.tv_loai_mon);
          //  tvStt = itemView.findViewById(R.id.tv_stt_mon);
            imgItem = itemView.findViewById(R.id.img_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MonUpdateActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("TenMon", tvTenMon.getText().toString());
                    intent.putExtra("Gia", tvGia.getText().toString());
                    intent.putExtra("LoaiMon", tvLoaiMon.getText().toString());
                    context.startActivity(intent);

                }
            });

        }

        public void bind(Mon mon, String key) {

            NumberFormat formatter = new DecimalFormat("#,###");
            int gia = (mon.getGia());
            String formattedNumber = formatter.format(gia);
            tvGia.setText(formattedNumber+" đ");
            tvTenMon.setText(mon.getTenMon());
            tvLoaiMon.setText(mon.getLoaiMon());
            imgItem.setImageResource(R.drawable.img_sinh_to);
            this.key = key;
        }
    }

    class MonAdapter extends RecyclerView.Adapter<MonItemView> {
        private List<Mon> monList;
        private List<String> mKeys;

        public MonAdapter(List<Mon> monList, List<String> mKeys) {
            this.monList = monList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public MonItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MonItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MonItemView holder, int position) {
            holder.bind(monList.get(position), mKeys.get(position));
//            position++;
//            holder.tvStt.setText(""+position);
        }

        @Override
        public int getItemCount() {
            return monList.size();
        }
    }
}
