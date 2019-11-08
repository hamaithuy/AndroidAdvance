package com.example.demoapp1.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp1.Common.Common;
import com.example.demoapp1.Model.Room;
import com.example.demoapp1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdminAdapter extends RecyclerView.Adapter<RecyclerAdminAdapter.RoomViewHolderAdmin>{

    private Context mContext;
    private List<Room> listRoom;
    private RecyclerAdminAdapter.IOnItemClickListener mListener;

    public RecyclerAdminAdapter(Context mContext, List<Room> listRoom) {
        this.mContext = mContext;
        this.listRoom = listRoom;
    }

    public void setOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
            mListener = iOnItemClickListener;
    }


    public class RoomViewHolderAdmin extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Button btnAccept;
        ImageView iv_RoomAvatar;
        TextView tv_price,tv_title,tv_address,tv_acreage,tv_time;
        public RoomViewHolderAdmin(@NonNull View itemView) {
            super(itemView);
            btnAccept = itemView.findViewById(R.id.btn_duyet);
            iv_RoomAvatar = itemView.findViewById(R.id.iv_RoomAvatar);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_acreage = itemView.findViewById(R.id.tv_acreage);
            tv_time = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);
            try {
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        mListener.onButtonAcceptClick(position);
                    }
                });
            }catch (Exception ex){

            }

        }

        @Override
        public void onClick(View view) {
            if(mListener!=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

    }

    // Chức năng này chỉ dùng ở danh sách phòng đã đăng của user
    public interface  IOnItemClickListener{
        void onItemClick(int position);
        void onButtonAcceptClick(int position);
    }


    public void setOnItemClickListener(RecyclerAdapter.IOnItemClickListener listener) {
        mListener = (IOnItemClickListener) listener;
    }


    @NonNull
    @Override
    public RoomViewHolderAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tham chiếu View đến list-item (layout)
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_admin,parent,false);
        // Trả về 1 view holder tham số truyền vào là 1 view(layout)
        return new RoomViewHolderAdmin(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolderAdmin holder, int position) {
        Room currentRoom = listRoom.get(position);
        // Format price
        String priceFormat = Common.FormatCurrentcy(currentRoom.getPrice());
        holder.tv_price.setText(priceFormat);
        holder.tv_title.setText(currentRoom.getTitle());
        holder.tv_acreage.setText(Float.toString(currentRoom.getAcreage())+" m2");
        holder.tv_address.setText(currentRoom.getAddress());

        // Set time
        String datePost = Common.getDateFormat(currentRoom.getTimePost());
        holder.tv_time.setText("Đăng ngày: "+datePost);
        Uri uri = Uri.parse(currentRoom.getImageUrl());
        Picasso.with(mContext)
                .load(uri)
                .placeholder(R.drawable.home)
                .fit()
                .centerCrop()
                .into(holder.iv_RoomAvatar);
    }

    @Override
    public int getItemCount() {
        return listRoom.size();
    }
}
