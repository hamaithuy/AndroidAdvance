package com.example.quanlycuahang.Kitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlycuahang.Order.Oder;
import com.example.quanlycuahang.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewKitchenChild {
    private Context context;
    private KitchenChildAdapter kitchenChildAdapter;

    public void setConfig(RecyclerView recyclerView, Context mContext, List<Oder> oders) {
        context = mContext;
        kitchenChildAdapter = new KitchenChildAdapter(oders);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(kitchenChildAdapter);

    }

    class KitchenChildItemView extends RecyclerView.ViewHolder {
        private ImageView mImgAnhsanpham;
        private TextView mtxtTenmon,mtxtSoluong;
        public KitchenChildItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.kitchen_item_child_layout, parent, false));
            init();
        }

        public void bind(Oder oder) {
            if (oder.getLoaiMon().equals("Sinh tố")) {
                mImgAnhsanpham.setImageResource(R.drawable.img_sinh_to);
            } else if (oder.getLoaiMon().equals("Nước uống")) {
                mImgAnhsanpham.setImageResource(R.drawable.img_nuoc_uong);
            } else if (oder.getLoaiMon().equals("Đồ ăn")) {
                mImgAnhsanpham.setImageResource(R.drawable.img_do_an);
            }
            mtxtTenmon.setText(oder.getTenMon());
            mtxtSoluong.setText(Integer.toString(oder.getSoluong()));

        }
        private void init(){
            mtxtTenmon =(TextView) itemView.findViewById(R.id.txtTenmon_Nhabep);
            mtxtSoluong = (TextView) itemView.findViewById(R.id.txtSoluong_Nhabep);
            mImgAnhsanpham =(ImageView) itemView.findViewById(R.id.img_Anhsanpham_Nhabep);
        }
    }

    class KitchenChildAdapter extends RecyclerView.Adapter<KitchenChildItemView> {
        private List<Oder> oders;

        public KitchenChildAdapter(List<Oder> oders) {
            this.oders = oders;
        }

        @NonNull
        @Override
        public KitchenChildItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new KitchenChildItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull KitchenChildItemView holder, int position) {
            holder.bind(oders.get(position));
        }

        @Override
        public int getItemCount() {
            return oders.size();
        }
    }
}
