package vn.edu.hcmuaf.fit.laptrinhweb2.Cart;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int productId;
    private int variantId;
    private String productName;
    private String color;
    private String size;
    private String image;
    private double price;
    private int quantity;
    private boolean isSelected;
    public CartItem(int productId, int variantId, String productName, String color, String size, String image,
                    double price, int quantity) {
        super();
        this.productId = productId;
        this.variantId = variantId;
        this.productName = productName;
        this.color = color;
        this.size = size;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.isSelected = false;
    }
    public CartItem() {
        super();
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getVariantId() {
        return variantId;
    }
    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotalPrice() {
        return price * quantity;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
