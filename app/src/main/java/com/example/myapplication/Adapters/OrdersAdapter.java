package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Classes.Orders;
import com.example.myapplication.R;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private ArrayList<Orders> ordersArrayList;
    private Context context;

    public OrdersAdapter(ArrayList<Orders> ordersArrayList) {
        this.ordersArrayList = ordersArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView order_id,price,products,paymentOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.tvOrderID);
            price = itemView.findViewById(R.id.tvTotalPrice);
            products = itemView.findViewById(R.id.tvProducts);
            paymentOptions = itemView.findViewById(R.id.tvPaymentMethod);
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
            holder.price.setText("Price : "+ orders.getPrice() + " $ ");
            holder.products.setText( orders.getProductsId().toString());
            holder.paymentOptions.setText("Via: " + orders.getPaymentOption());
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
