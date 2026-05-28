package vn.edu.hcmuaf.fit.laptrinhweb2.model;

import java.util.List;

public class ProductVariantImage {
    private int id;
    private int variantId;
    private String imageUrl;
    public ProductVariantImage() {
        super();
    }
    public ProductVariantImage(int id, int variantId, String imageUrl) {
        this.id = id;
        this.variantId = variantId;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}