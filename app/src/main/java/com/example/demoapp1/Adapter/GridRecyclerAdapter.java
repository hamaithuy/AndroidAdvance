package com.example.demoapp1.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp1.Common.Common;
import com.example.demoapp1.Model.Room;
import com.example.demoapp1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridRecyclerAdapter extends RecyclerView.Adapter<GridRecyclerAdapter.GridRoomViewHolder> {
    private Context mContext;
    private List<Room> listRoom;
    private GridRecyclerAdapter.IOnItemClickListener mListener;
    private int kindAdapter; // 1 - Adapter cho phòng cho thuê, 2- Adapter cho phòng ở ghép

    public GridRecyclerAdapter(Context mContext, List<Room> listRoom,int kindAdapter) {
        this.mContext = mContext;
        this.listRoom = listRoom;
        this.kindAdapter = kindAdapter;
    }

    public class GridRoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView iv_GridRoomAvatar;
        TextView tv_price,tv_address,tv_acreage;
        public GridRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_GridRoomAvatar = itemView.findViewById(R.id.iv_GridRoomAvatar);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_acreage = itemView.findViewById(R.id.tv_acreage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position,kindAdapter);
                }
            }
        }
    }

    public interface  IOnItemClickListener{
        void onItemClick(int position,int kindAdapter);
    }

    public void setOnItemClickListener(GridRecyclerAdapter.IOnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public GridRecyclerAdapter.GridRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,parent,false);
        return new GridRecyclerAdapter.GridRoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GridRecyclerAdapter.GridRoomViewHolder holder, int position) {

            Room currentRoom = listRoom.get(position);
            // Format price
            String priceFormat = Common.FormatCurrentcy(currentRoom.getPrice());
            holder.tv_price.setText(priceFormat);
            holder.tv_acreage.setText(Float.toString(currentRoom.getAcreage())+" m2");
            holder.tv_address.setText(currentRoom.getAddress());
            // Set image
            Uri uri = Uri.parse(currentRoom.getImageUrl());
            Picasso.with(mContext)
                    .load(uri)
                    .placeholder(R.drawable.home)
                    .fit()
                    .centerCrop()
                    .into(holder.iv_GridRoomAvatar);

    }

    @Override
    public int getItemCount() {
        return listRoom.size();
    }
}
