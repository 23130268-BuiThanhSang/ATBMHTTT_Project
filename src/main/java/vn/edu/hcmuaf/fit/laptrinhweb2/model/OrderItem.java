package vn.edu.hcmuaf.fit.laptrinhweb2.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private int id;
    private int orderId;
    private ProductVariant variant;
    private Product product;
    private int quantity;
    private double price;

    public OrderItem() {
    }

    public OrderItem(Product product, int quantity, double price, ProductVariant variant) {
        this.product = product;
        this.variant = variant;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product;}
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public ProductVariant getVariant() {
        return variant;
    }

    public void setVariant(ProductVariant variant) {
        this.variant = variant;
    }
}

