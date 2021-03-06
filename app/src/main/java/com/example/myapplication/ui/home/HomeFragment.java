package com.example.myapplication.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Classes.SmartPhones;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MyRecyclerViewAdapter;
import com.example.myapplication.OrderActivity;
import com.example.myapplication.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private ArrayList<SmartPhones> mSmartPhones = new ArrayList<>();
    private Uri mUri;
    private Intent mIntent;
    private String username, id, pid, pname;
    private Double price;




    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {  // When an item is clicked in recyclerView send its details into the next activity

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            SmartPhones mSmartPhone = mSmartPhones.get(position);
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("smartphones", mSmartPhones);
            intent.putExtras(bundle);
            intent.putExtra("position", position);
            intent.putExtra("pname", mSmartPhone.getmName());
            intent.putExtra("pid", mSmartPhone.getmProductId());
            intent.putExtra("price", mSmartPhone.getmPrice());
            startActivity(intent);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recycler);
        mIntent = getActivity().getIntent();
        username = LoginActivity.getUsername();
        id = LoginActivity.getId();

        myRecyclerViewAdapter = new MyRecyclerViewAdapter(mSmartPhones);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerViewAdapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(myRecyclerViewAdapter);
        getProducts();
        return root;
    }

    private void getProducts() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Products").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot products : dataSnapshot.getChildren()) {
                    HashMap<String, Object> hashMap = (HashMap) products.getValue();
                    pid = hashMap.get("id").toString();
                    pname = hashMap.get("name").toString();
                    price = (Double) hashMap.get("price");
                    String description = (String) hashMap.get("description");
                    SmartPhones smartPhones = new SmartPhones(pid, pname, price, description, mUri);
                    mSmartPhones.add(smartPhones);
                    myRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }

        });


    }

    private void setRating()
    {

    }

}