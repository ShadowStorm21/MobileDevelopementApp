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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private MyCartRecyclerViewAdapter myCartRecyclerViewAdapter;
    private String username, id,mPid,mPname;
    private Double mPrice;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Cart");
    private TextView totalPrice;
    private Uri mUri;


    private static HashMap<Cart,Integer> products = new HashMap<>();
    public static Integer findProduct(Cart cart) {
        return products.get(cart);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        setTitle("My Cart");

        // user clicked on product and the new intent carried data
        if(intent.hasExtra("username")){
            username = intent.getStringExtra("username");
            id = intent.getStringExtra("id");
            mPid = intent.getStringExtra("pid");
            mPname = intent.getStringExtra("pname");
            mPrice = intent.getDoubleExtra("price", 0);
            mUri = Uri.parse(intent.getStringExtra("uri"));
            //Toast.makeText(CartActivity.this, "userid"+username+id+mPid+mPname+mPrice+mUri, Toast.LENGTH_LONG).show();


            Cart item = new Cart(id, username, mPid, mPrice, mPname,mUri);
            cartArrayList.add(item);
            Integer quan =  products.get(item);
            if(quan == null)
                products.put(item,1);
            else {
                ++quan;
                products.put(item,quan);
            }

        }
        else { } // user clicked on cart icon , no data carried

        totalPrice = findViewById(R.id.tvPrice);
        recyclerView = findViewById(R.id.cartRecyclerView);
        layoutManager = new LinearLayoutManager(this);

        Log.e("Hashing " , products.toString());

        myCartRecyclerViewAdapter = new MyCartRecyclerViewAdapter( new ArrayList<Cart>(products.keySet()));
        myCartRecyclerViewAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(myCartRecyclerViewAdapter);

        totalPrice.setText( getPrice() + " $");
    }


    private void getItems() {
        /*
        ArrayList<Cart> oldItems = new ArrayList<>();

        // this will cache the current products

        SharedPreferences sharedPreferences = getSharedPreferences("CartFile", MODE_PRIVATE);
        String userid =  sharedPreferences.getString("userid","");
        Float price =  sharedPreferences.getFloat("price",0);
        String product_id =  sharedPreferences.getString("product_id","");
        String username =  sharedPreferences.getString("username","");
        Uri uri = Uri.parse( sharedPreferences.getString("uri",""));
        String mProductName =  sharedPreferences.getString("pname","");
        */


        //Toast.makeText(CartActivity.this, "userid"+userid+price+product_id+username+uri, Toast.LENGTH_SHORT).show();

        // this code will check duplicate products after restoring products from the cache
        /*
                Cart items = new Cart(userid,username,product_id,price,mProductName,uri);
                oldItems.add(items);


                for(Cart i : oldItems){
                    Integer quan = products.get(i);
                    if(quan == null)
                        products.put(i,1);
                    else
                        ++quan;
                    myCartRecyclerViewAdapter.notifyDataSetChanged();
                }
        */
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

        //saveCart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //saveCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getItems();
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

    private double getPrice() {
        if(products.isEmpty())
            return 0.0;
        double total = 0.0;

        for (Map.Entry<Cart, Integer> entry : products.entrySet()) {
            Cart product = entry.getKey();
            Integer productQuantity = entry.getValue();
            total += product.getPrice() * productQuantity;
        }
        return Math.round(total);
    }

    public void increaseQuantity(View view,int position) {

    }

    public void decreaseQuantity(View view) {
    }
}







