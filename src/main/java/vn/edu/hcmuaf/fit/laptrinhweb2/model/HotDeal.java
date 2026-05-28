package vn.edu.hcmuaf.fit.laptrinhweb2.model;

public class HotDeal {
    private int id;
    private String name;
    private String thumbnailUrl;
    private String mainImageUrl;
    private int displayOrder;
    private double discountPercentage;

    public HotDeal() {
    }

    public HotDeal(int id, String name, String thumbnailUrl, String mainImageUrl, int displayOrder, double discountPercentage) {
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.mainImageUrl = mainImageUrl;
        this.displayOrder = displayOrder;
        this.discountPercentage = discountPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
