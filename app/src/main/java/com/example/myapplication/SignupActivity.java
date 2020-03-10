package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mDay, mMonth, mYear, mUsername, mPassword, mConfirmPass, mPhoneNumber;
    private int Year, Month, Day;
    private Button buttonDate, buttonRegister;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Customers");
    private String mDate;
    private ArrayList<Customer> customers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        buttonDate = findViewById(R.id.buttonDate);
        buttonRegister = findViewById(R.id.buttonRegister);
        mUsername = findViewById(R.id.editText3);
        mPassword = findViewById(R.id.editText4);
        mConfirmPass = findViewById(R.id.editText5);
        mPhoneNumber = findViewById(R.id.editText6);
        customers = new ArrayList<>();
        buttonDate.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        customers.clear();
        getUsers();


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonDate:
                buttonAnimation(v);
                showCalenderDialog();
                hideKeyboard(v);
                break;

            case R.id.buttonRegister:

                if (checkUserDetails() && checkDate()) {


                   registerNewUser();
                   hideKeyboard(v);


                } else {

                    mUsername.requestFocus();
                    showKeyboard();

                }

                buttonAnimation(v);
                break;


        }
    }

    private void showCalenderDialog() {
        mDay = findViewById(R.id.editText7);
        mMonth = findViewById(R.id.editText8);
        mYear = findViewById(R.id.editText9);
        Calendar calendar;
        calendar = Calendar.getInstance();
        //Year = calendar.get(Calendar.YEAR);
       // Month = calendar.get(Calendar.MONTH);
       // Day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog;

        // default date of birth will be on 2000 / 1 Jan
        Year = 2000;
        Month = 0;
        Day = 1;

        datePickerDialog = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                mDate = year + "/" + (month + 1) + "/" + dayOfMonth;

                mDay.setText(dayOfMonth + "");
                mMonth.setText(month + 1 + "");
                mYear.setText(year + "");


            }

        }, Year, Month, Day);
        datePickerDialog.show();
    }

    private boolean checkDate() {
        mDay = findViewById(R.id.editText7);
        mMonth = findViewById(R.id.editText8);
        mYear = findViewById(R.id.editText9);

        if (mDay.getText().toString().isEmpty() || Integer.parseInt(mDay.getText().toString()) <= 0 || Integer.parseInt(mDay.getText().toString()) > 31) {
            Toast.makeText(SignupActivity.this, "Day can not be empty or less than zero or more than 31", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mMonth.getText().toString().isEmpty() || Integer.parseInt(mMonth.getText().toString()) <= 0 || Integer.parseInt(mMonth.getText().toString()) > 12) {

            Toast.makeText(SignupActivity.this, "Month can not be empty or less than zero or more than 12", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mYear.getText().toString().isEmpty() || Integer.parseInt(mYear.getText().toString()) > Year || Integer.parseInt(mYear.getText().toString()) <= 1900) {
            Toast.makeText(SignupActivity.this, "Year can not be empty or more than the current year or less than 1900", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkUserDetails() {


        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPass.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();

        if (mUsername.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty() || mConfirmPass.getText().toString().isEmpty() || mPhoneNumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (username.length() > 10 || password.length() < 8) {
            Toast.makeText(this, "Username length can not more than 10 characters / password length can not be less than 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "passwords are not matched", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!phoneNumber.matches("^[7-9]\\d{2}\\d{2}\\d{3}$")) { // starts with 7 or 9
            Toast.makeText(this, "Phone number must start with either 7 or 9 / phone length should not exceed 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


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


    private void getUsers() {

        try {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot usersInfo : dataSnapshot.getChildren()) {
                        Customer mCustomer = usersInfo.getValue(Customer.class);

                        customers.add(mCustomer);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    private boolean checkusersnames() {
        String username = mUsername.getText().toString();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getName().equalsIgnoreCase(username)) {

                return false;
            }
        }
        return true;


        }

        private void registerNewUser()
        {
            if (checkusersnames() == true) {

                String id = myRef.push().getKey();
                String password = mPassword.getText().toString();
                String username = mUsername.getText().toString();
                String phoneNumber = mPhoneNumber.getText().toString();
                Customer customer = new Customer(id, username, password, mDate, phoneNumber);
                myRef.child(id).setValue(customer);
                ProgressDialog progressDialog = ProgressDialog.show(SignupActivity.this, "Signing up", "Creating your Account..");
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
            else
            {

                Toast.makeText(this, "User Already Exists!", Toast.LENGTH_SHORT).show();
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














