package com.example.myapplication.Classes;

public class Orders {

    private String order_id;
    private String user_id;
    private String user_name;
    private double price;

    public Orders(String order_id, String user_id, String user_name, double price) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.price = price;
    }

    public Orders() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
