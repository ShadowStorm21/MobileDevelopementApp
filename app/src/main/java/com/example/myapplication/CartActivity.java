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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapters.MyCartRecyclerViewAdapter;
import com.example.myapplication.Classes.Cart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    private TextView totalPrice;
    private Uri mUri;


    private static HashMap<Cart,Integer> products = new HashMap<>();
    public static Integer findProduct(Cart cart) {
        return products.get(cart);
    }
    public static void addProduct(Cart cart) {
        Integer quan = products.get(cart);
        if(quan == null)
            products.put(cart,1);
        else {
            ++quan;
            products.put(cart,quan);
        }
    }

    public static HashMap<Cart, Integer> getProducts() {
        return products;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        setTitle("My Cart");

        // user clicked on product and the new intent carried data
        if(intent.hasExtra("pid")){

            mPid = intent.getStringExtra("pid");
            mPname = intent.getStringExtra("pname");
            mPrice = intent.getDoubleExtra("price", 0);
            mUri = Uri.parse(intent.getStringExtra("uri"));
            Cart item = new Cart(id, username, mPid, mPrice, mPname,mUri);
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

        myCartRecyclerViewAdapter = new MyCartRecyclerViewAdapter( new ArrayList<Cart>(products.keySet()));
        myCartRecyclerViewAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(myCartRecyclerViewAdapter);

        totalPrice.setText( getPrice() + " $");
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CartActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public static double getPrice() {
        if(products.isEmpty())
            return 0.0;
        double total = 0.0;

        for (Map.Entry<Cart, Integer> entry : products.entrySet()) {
            Cart product = entry.getKey();
            Integer productQuantity = entry.getValue();
            total += product.getPrice() * productQuantity;
        }
        return Double.parseDouble(new DecimalFormat("#.##").format(total).toString());
    }

    public void removeItem(View view) {
        LinearLayout x = (LinearLayout) view.getParent().getParent();
        TextView id = x.findViewById(R.id.tvId);

        // creating Cart with product id is ok for searching from the hashmap only
        // since the hashmap hashes by product id
        products.remove(new Cart(id.getText().toString()) );
        myCartRecyclerViewAdapter.setItems(new ArrayList<Cart>(products.keySet()));
        myCartRecyclerViewAdapter.notifyDataSetChanged();
        totalPrice.setText(getPrice() + "$");
    }

    public void increaseQuantity(View view) {
        LinearLayout x = (LinearLayout) view.getParent().getParent().getParent();
        TextView id = x.findViewById(R.id.tvId);

        Cart key = new Cart(id.getText().toString() );
        Integer quan =  products.get(key);
        ++quan;
        products.put(key,quan);
        totalPrice.setText(getPrice() + "$");
        myCartRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void decreaseQuantity(View view) {
        LinearLayout x = (LinearLayout) view.getParent().getParent().getParent();
        TextView id = x.findViewById(R.id.tvId);

        Cart key = new Cart(id.getText().toString() );
        Integer quan =  products.get(key);
        --quan;
        if(quan == 0){
            // remove the item
            products.remove(key);
            myCartRecyclerViewAdapter.setItems(new ArrayList<Cart>(products.keySet()));
            myCartRecyclerViewAdapter.notifyDataSetChanged();
            totalPrice.setText(getPrice() + "$");
            return;
        }
        products.put(key,quan);
        totalPrice.setText(getPrice() + "$");
        myCartRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void buyNow(View view) {
        if(products.isEmpty())
            return;
        // get the price and move to payment activity
        buttonAnimation(view);
        Intent paymentActivity = new Intent(CartActivity.this, PaymentActivity.class);
        paymentActivity.putExtra("username",username);
        paymentActivity.putExtra("id",id);
        paymentActivity.putExtra("totalPrice", getPrice());
        startActivity(paymentActivity);
        finish();
    }

    private void buttonAnimation(View view) {
        final double mAmplitude = 0.2;
        final double mFrequency = 15;
        Interpolator interpolator = new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return (float) (-1 * Math.pow(Math.E, -input / mAmplitude) *
                        Math.cos(mFrequency * input) + 1);
            }
        };

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);
    }
}







