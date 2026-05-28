package vn.edu.hcmuaf.fit.laptrinhweb2.model;

public class ProductReview {
    private int id;
    private int userId;
    private int productId;
    private String reviewDate;
    private int rating;
    private String reviewText;
    private String userName;
    private String avatarUrl;
    public ProductReview() {
        super();
    }
    public ProductReview(int id, int userId, int productId, String reviewDate, int rating, String reviewText,
                         String userName, String avatarUrl) {
        super();
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.reviewText = reviewText;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getReviewText() {
        return reviewText;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


}

