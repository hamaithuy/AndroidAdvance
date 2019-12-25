package com.example.thstore.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thstore.Model.Data;
import com.example.thstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Products extends RecyclerView.Adapter<Adapter_Products.ViewHolder> {
    Context context;
    ArrayList<Data> data;
    OnItemClickListener vlistener;

    public Adapter_Products(Context context, ArrayList<Data> data)
    {
        this.context = context;
        this.data = data;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_rows, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data_FP = data.get(position);
        holder.txtName.setText(data_FP.getName());
        //holder.imgPicture.setImageResource(Integer.parseInt(String.valueOf(data_FP)));
        Picasso.get().load(data_FP.getImage()).fit().into(holder.imgPicture);
        holder.txtPrice.setText(data_FP.getPrice());
        holder.txtID.setText(data_FP.getID());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtName;
        ImageView imgPicture;
        TextView txtPrice;
        TextView txtID;


        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtNameProducts);
            imgPicture = (ImageView) itemView.findViewById(R.id.imgPicture);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtID = itemView.findViewById(R.id.txtID);


            //EVENT SET CONLICKLISTENER
            itemView.setOnClickListener(this);
            //itemView.setOnCreateContextMenuListener(this);
        }


// Sự kiện onclick
        @Override
        public void onClick(View v) {
            if (vlistener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    vlistener.OnItemClick(position);
                }
            }
        }

    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public  void setOnItemClickListener(OnItemClickListener listener){
        vlistener = listener;
    }
}