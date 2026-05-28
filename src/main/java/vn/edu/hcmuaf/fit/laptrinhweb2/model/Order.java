package vn.edu.hcmuaf.fit.laptrinhweb2.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private int id;
    private int user_id;
    private LocalDateTime order_date;
    private double price;
    private String order_status;

    private String address;
    private List<OrderItem> items = new ArrayList<>();


    public Order() {
    }

    public Order(List<OrderItem> items, String address, String order_status, double price, LocalDateTime order_date, int user_id, int id) {
        this.items = items;
        this.address = address;
        this.order_status = order_status;
        this.price = price;
        this.order_date = order_date;
        this.user_id = user_id;
        this.id = id;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }
    public double calculateTotal() {
        if (items == null || items.isEmpty()) {
            return 0;
        }

        double total = 0;
        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDateTime order_date) {
        this.order_date = order_date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}

