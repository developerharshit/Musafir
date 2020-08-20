package com.nitjsr.musafir;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder>{

    Context context;
    ArrayList<Ticket> data;

    public TicketAdapter(Context context, ArrayList<Ticket> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.ViewHolder holder, int position) {
        holder.busid.setText(String.valueOf(data.get(position).getBusId()));
        holder.price.setText(String.valueOf(data.get(position).getTransactionAmount()));
        holder.src.setText(String.valueOf(data.get(position).getSrc()));
        holder.des.setText(String.valueOf(data.get(position).getDst()));
        String s = data.get(position).getTimeStamp().substring(0,10);
        holder.date.setText(s);

        holder.itemView.setOnClickListener(view -> {
            Gson gson = new Gson();
            String json = gson.toJson(data.get(position));
            Intent intent = new Intent(context,ShowQr.class);
            intent.putExtra("ticket", json);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView busid,src,des,date,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            busid = itemView.findViewById(R.id.busid);
            src = itemView.findViewById(R.id.src);
            des = itemView.findViewById(R.id.dst);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.fare);
        }
    }
}
