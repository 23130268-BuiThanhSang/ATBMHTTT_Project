package vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO;

public class ProductCard {
    private int id;
    private String name;
    private String brandName;
    private double price;
    private Double oldPrice; // null nếu không giảm
    private String imageUrl;
    private double discountPercent;
    private double rating;
    private int reviewCount;
    private int fullStars;
    private boolean hasHalfStar;
    private boolean isNewProduct;
    public ProductCard() {
    }

    public ProductCard(int id, String name, String brandName, double price, Double oldPrice, String imageUrl, double discountPercent, double rating, int reviewCount, int fullStars, boolean hasHalfStar, boolean isnew) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.price = price;
        this.oldPrice = oldPrice;
        this.imageUrl = imageUrl;
        this.discountPercent = discountPercent;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.fullStars = fullStars;
        this.hasHalfStar = hasHalfStar;
        this.isNewProduct = isnew;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getFullStars() {
        return fullStars;
    }

    public void setFullStars(int fullStars) {
        this.fullStars = fullStars;
    }

    public boolean isHasHalfStar() {
        return hasHalfStar;
    }

    public void setHasHalfStar(boolean hasHalfStar) {
        this.hasHalfStar = hasHalfStar;
    }

    public boolean getIsNewProduct() {
        return isNewProduct;
    }

    public void setIsNewProduct(boolean newProduct) {
        this.isNewProduct = newProduct;
    }
}

