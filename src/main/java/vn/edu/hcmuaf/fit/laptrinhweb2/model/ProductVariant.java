package vn.edu.hcmuaf.fit.laptrinhweb2.model;

import java.util.ArrayList;
import java.util.List;

public class ProductVariant{
    private int id;

    private int product_id;
    private String color;
    //Tím , Nâu, Đen, Trắng, Bạc, Hồng, Xanh Navy, Xanh Dương, Xanh Lục, Vàng, Đỏ, Cam
    private int size;
    private double price;
    private int stock;
    private List<ProductVariantImage> images;
    public ProductVariant() {
        super();
    }
    public ProductVariant(int id, int product_id, String color, int size, double price, int stock) {
        super();
        this.id = id;
        this.product_id = product_id;
        this.color = color;
        this.size = size;
        this.price = price;
        this.stock = stock;
        this.images = new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public List<ProductVariantImage> getImages() {
        return images;
    }
    public void setImages(List<ProductVariantImage> images) {
        this.images = images;
    }

    public String getSizeString() {
        int fake_size = size;
        String real_size = String.valueOf(fake_size);
        switch (fake_size) {
            case -1: real_size = "Free Size"; break;
            case 1: real_size = "S"; break;
            case 2: real_size = "M"; break;
            case 3: real_size = "L"; break;
            case 4: real_size = "XL"; break;
            case 5: real_size = "XXL"; break;
            case 100: real_size = "Nhỏ"; break;
            case 200: real_size = "Vừa"; break;
            case 300: real_size = "Lớn"; break;
        }
        return real_size;
    }
}
