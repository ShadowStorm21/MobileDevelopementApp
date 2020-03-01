package com.example.myapplication;

public class Customer {

    public String id;
    public String name;
    public String date;
    public String password;
    public String phoneNumber;

    public String getPassword() {
        return password;
    }

    public Customer() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer(String id, String name, String password, String date, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
