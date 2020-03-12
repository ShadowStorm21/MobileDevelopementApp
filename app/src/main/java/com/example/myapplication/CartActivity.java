package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CartActivity extends AppCompatActivity {

    private MyCartRecyclerViewAdapter myCartRecyclerViewAdapter;
    private ArrayList<Cart> cartArrayList,uniqueList;
    private Set<Cart> cartSet;
    private String username, id,mPid,mPname;
    private Double mPrice;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Cart");
    private TextView textView;
    private Uri mUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        Intent intent = getIntent();
        setTitle("My Cart");

        cartSet = new HashSet<>();
        cartArrayList = new ArrayList<>();
        uniqueList = new ArrayList<>();

        try {


            username = intent.getStringExtra("username");
            id = intent.getStringExtra("id");
            mPid = intent.getStringExtra("pid");
            mPname = intent.getStringExtra("pname");
           mPrice = intent.getDoubleExtra("price", 0);
            mUri = Uri.parse(intent.getStringExtra("uri"));
            Toast.makeText(CartActivity.this, "userid"+username+id+mPid+mPname+mPrice+mUri, Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        textView = findViewById(R.id.textViewTotalPrice);

        myCartRecyclerViewAdapter = new MyCartRecyclerViewAdapter(cartArrayList);
        recyclerView = findViewById(R.id.cartRecyclerView);

        layoutManager = new LinearLayoutManager(this);



                Cart item = new Cart(id, username, mPid, mPrice, mPname, mUri);

                    cartArrayList.add(item);
                    myCartRecyclerViewAdapter.notifyDataSetChanged();


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(myCartRecyclerViewAdapter);






    }


    private void getItems() {


        ArrayList<Cart> oldItems = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("CartFile", MODE_PRIVATE);
        String userid =  sharedPreferences.getString("userid","");
       Float price =  sharedPreferences.getFloat("price",0);
        String product_id =  sharedPreferences.getString("product_id","");
        String username =  sharedPreferences.getString("username","");
        Uri uri = Uri.parse( sharedPreferences.getString("uri",""));
        String mProductName =  sharedPreferences.getString("pname","");

        Toast.makeText(CartActivity.this, "userid"+userid+price+product_id+username+uri, Toast.LENGTH_SHORT).show();

                Cart items = new Cart(userid,username,product_id,price,mProductName,uri);
                oldItems.add(items);

                for(int i = 0 ; i < oldItems.size(); i++)
                {
                    if(!oldItems.get(i).getProduct_id().equals(cartArrayList.get(i).getProduct_id()))
                    {
                        cartArrayList.add(oldItems.get(i));
                        myCartRecyclerViewAdapter.notifyDataSetChanged();
                        Log.e("cart","size"+cartArrayList.get(0).getProduct_name());
                    }
                }









    }

    private void saveCart()
    {
        SharedPreferences preferences = getSharedPreferences("CartFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Cart item = new Cart(id,username,mPid,mPrice,mPname,mUri);


                editor.putString("userid", item.getUser_id());
                editor.putString("product_id", item.getProduct_id());
                editor.putString("username", item.getUsername());
                editor.putString("pname", item.getProduct_name());
                editor.putFloat("price", (float) item.getPrice());
                editor.putString("uri", String.valueOf(mUri));

                editor.commit();

        Toast.makeText(this, "productname"+mPname, Toast.LENGTH_SHORT).show();



    }

    private void setViews()
    {

    }

    @Override
    protected void onPause() {
        super.onPause();

        saveCart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getItems();
    }

    @Override
    protected void onStart() {

        super.onStart();
        //getItems();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CartActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}







