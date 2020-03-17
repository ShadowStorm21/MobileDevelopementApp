package com.example.myapplication.ui.Orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.OrdersAdapter;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Orders;
import com.example.myapplication.R;
import com.example.myapplication.RatingActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OrdersFragment extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Orders");
    private OrdersAdapter ordersAdapter;
    private ArrayList<Orders> ordersArrayList;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private String username,id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_orders, container, false);
        ordersArrayList = new ArrayList<>();
        ordersAdapter = new OrdersAdapter(ordersArrayList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = root.findViewById(R.id.ordersRecycler);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ordersAdapter);
        ordersAdapter.setmOnItemClickListener(onItemClickListener);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        username = LoginActivity.getUsername();
        id = LoginActivity.getId();
        getOrders();

        return root;
    }


    private void getOrders()
    {
        username = LoginActivity.getUsername();
        id = LoginActivity.getId();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot orders : dataSnapshot.getChildren())
                {
                   HashMap hashMap =  (HashMap) orders.getValue();

                   // order associated with the current user
                   if(id.equals(hashMap.get("user_id").toString())) {
                       Double price = (Double) hashMap.get("price");
                       Orders mOrder = new Orders(
                               hashMap.get("order_id").toString(),
                               id,username,
                               price.doubleValue(),
                               (ArrayList<String>) hashMap.get("productsId"),
                               hashMap.get("paymentOption").toString()
                       );
                       ordersArrayList.add(mOrder);
                       ordersAdapter.notifyDataSetChanged();
                   }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {  // When an item is clicked in recyclerView send its details into the next activity

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            Orders orders = ordersArrayList.get(position);
            Intent intent = new Intent(getActivity(), RatingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("orders",  ordersArrayList);
            intent.putExtras(bundle);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    };
}