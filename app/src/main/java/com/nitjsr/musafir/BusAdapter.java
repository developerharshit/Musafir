package com.nitjsr.musafir;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {
    Context context;
    ArrayList<Bus> data;
    boolean isClickable;

    public BusAdapter(Context context, ArrayList<Bus> data,boolean isClickable) {
        this.context = context;
        this.data = data;
        this.isClickable = isClickable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_bus_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.id.setText("" + data.get(position).getBUS_ID());
        holder.name.setText(data.get(position).getBUS_NAME());
        holder.location.setText(data.get(position).getCURRENT_LOCATION());
        int p= (data.get(position).getSEATS_AVAILABLE()*100)/data.get(position).getTOTAL_SEATS();
        holder.avail.setText(p+"%");
        if(p==0){
            holder.avail.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }
        if(isClickable){
            OnClick onClick = (OnClick) context;
            if(p==0){
                holder.avail.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            }else{
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClick.click(position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, id, location,avail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.busName);
            id = itemView.findViewById(R.id.busId);
            location = itemView.findViewById(R.id.currLoc);
            avail = itemView.findViewById(R.id.avail);
        }
    }

    interface OnClick{
        void click(int position);
    }
}
