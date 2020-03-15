package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private ArrayList<Orders> ordersArrayList = new ArrayList<>();
    private Context context;

    public OrdersAdapter(ArrayList<Orders> ordersArrayList) {
        this.ordersArrayList = ordersArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView order_id,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.tvOrderID);
            price = itemView.findViewById(R.id.tvTotalPrice);

        }
    }
    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        context = view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {

        Orders orders = ordersArrayList.get(position);
        if(orders.getOrder_id() != null)
        {
            holder.order_id.setText("OrderID : "+orders.getOrder_id());
            holder.price.setText("Price : "+ orders.getPrice());
        }
        else
        {
            Toast.makeText(context, "You didn't order yet!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }
}
