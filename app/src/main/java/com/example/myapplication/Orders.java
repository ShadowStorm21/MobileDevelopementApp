package com.example.myapplication;

public class Orders {

    private String order_id;
    private String user_id;
    private String user_name;
    private Double price;

    public Orders(String order_id, String user_id, String user_name, Double price) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
