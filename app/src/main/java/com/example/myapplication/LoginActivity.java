package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Classes.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsername,mPassword;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Customers");
    private String id,currentUser;
    private Button buttonLogin,buttonClear;
    private ArrayList<Customer> customers;
    private TextView textViewSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = findViewById(R.id.editTextUseraname);
        mPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonClear = findViewById(R.id.buttonClear);
        textViewSignup = findViewById(R.id.textViewSignup);
        Intent intent = getIntent();
        id = intent.getStringExtra("id"); // get the user_id from previous activity
        customers = new ArrayList<>();
        customers.clear();
        getCustomerDetails();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkUserDetails())
                {
                    login();
                    hideKeyboard(v);

                }
                else {
                    showKeyboard();
                    mUsername.requestFocus();
                }


            }
        });

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUsername.setText("");
                mPassword.setText("");
                mUsername.requestFocus();
                showKeyboard();
            }
        });

    }


    private boolean checkUserDetails()
    {
        if(mUsername.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty())
        {

            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            mUsername.requestFocus();
            return false;
        }

        else
        {
            return true;
        }

    }


    private void getCustomerDetails()
    {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot customer : dataSnapshot.getChildren())
                {
                    Customer myCustomer = customer.getValue(Customer.class);
                    customers.add(myCustomer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void login()
    {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        int flag = 0;
        for(int i = 0 ; i < customers.size() ; i++) {
            if (username.equalsIgnoreCase(customers.get(i).getName()) && password.equalsIgnoreCase(customers.get(i).getPassword())) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                currentUser = customers.get(i).getName();
                id = customers.get(i).getId();
                intent.putExtra("currentUser",currentUser);
                intent.putExtra("id",id);
                flag = 1;
                startActivity(intent);
                finish();
            }
        }

        if(flag == 0)
        {

            Toast.makeText(this, "Username / password does not match", Toast.LENGTH_SHORT).show();
            return;

        }
    }

    private void hideKeyboard(View view)
    {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
    private void showKeyboard()
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
}
