package com.example.myapplication.Classes;

import android.net.Uri;

public class Cart  {
    private String user_id;
    private String username;
    private String product_id;
    private double price;
    private String product_name;
    private Uri uri;


    @Override  // this method for Hashmap , Key test will be base on product_id
    public boolean equals(Object cart){
        if (this == cart)
            return true;
        if (cart == null)
            return false;
        if (getClass() != cart.getClass())
            return false;
        return product_id.equals(  ((Cart) cart).product_id );
    }

    public Cart(String product_id) {
        this.product_id = product_id;
    }

    @Override // this method for Hashmap , hash test will be base on product_id
    public int hashCode(){
        return this.product_id.hashCode();
    }

    public Cart(String user_id, String username, String product_id, double price, String product_name,Uri uri) {
        this.user_id = user_id;
        this.username = username;
        this.product_id = product_id;
        this.price = price;
        this.product_name = product_name;
        this.uri = uri;
    }

    public Cart() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
