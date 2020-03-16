package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.Classes.Customer;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Customers");
    private EditText Password,PhoneNo,Date,Username;
    private Button buttonUpdate;
    private ArrayList<Customer> customerArrayList;
    private Intent mIntent;
    private static String userName,uid;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        Password = root.findViewById(R.id.editTextEmailProfile);
        PhoneNo = root.findViewById(R.id.editTextPhoenNoProfile);
        buttonUpdate = root.findViewById(R.id.buttonUpdateProfile);
        Date = root.findViewById(R.id.editTextDateProfile);
        Username = root.findViewById(R.id.editTextProfileUsername);
        customerArrayList = new ArrayList<>();
        mIntent = getActivity().getIntent();
        userName = LoginActivity.getUsername();
        buttonUpdate.setEnabled(true);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Password.setEnabled(true);
                PhoneNo.setEnabled(true);
                Date.setEnabled(true);

                if(Password.isFocused() || PhoneNo.isFocused() || Date.isFocused()) {

                    if (checkUserDetails()) {
                        updateUserDetails();

                    }


                }

                }



            });
        getUserInfo();

        return root;
    }

    private void getUserInfo()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot user : dataSnapshot.getChildren())
                {
                    Customer customer = user.getValue(Customer.class);
                    customerArrayList.add(customer);

                }

                for(int i = 0 ; i < customerArrayList.size(); i++)
                {
                    if(customerArrayList.get(i).getName().equals(userName))
                    {

                        Password.setText(customerArrayList.get(i).getPassword());
                        PhoneNo.setText(customerArrayList.get(i).getPhoneNumber());
                        Date.setText(customerArrayList.get(i).getDate());
                        Username.setText(customerArrayList.get(i).getName());
                        uid = customerArrayList.get(i).getId();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean checkUserDetails() {



        String password = Password.getText().toString();
        String phoneNumber = PhoneNo.getText().toString();

        if (Password.getText().toString().isEmpty() || PhoneNo.getText().toString().isEmpty() || Date.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();

            return false;
        }
         if (password.length() < 8) {
            Toast.makeText(getContext(), "password length can not be less than 8 characters", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (!phoneNumber.matches("^[7-9]\\d{2}\\d{2}\\d{3}$")) { // starts with 7 or 9
            Toast.makeText(getContext(), "Phone number must start with either 7 or 9 / phone length should not exceed 8 characters", Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;


    }
    private void updateUserDetails()
    {
        myRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> UserValues = new HashMap();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   UserValues.put(snapshot.getKey(),snapshot.getValue());
                }
                String password = Password.getText().toString();
                String phoneNumber = PhoneNo.getText().toString();
                String date = Date.getText().toString();

                UserValues.put("password",password );
                UserValues.put("phoneNumber", phoneNumber);
                UserValues.put("date",date);

                myRef.child(uid).updateChildren(UserValues).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                        PhoneNo.setEnabled(false);
                        Password.setEnabled(false);
                        Date.setEnabled(false);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}