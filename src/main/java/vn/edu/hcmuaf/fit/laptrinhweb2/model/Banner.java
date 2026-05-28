package vn.edu.hcmuaf.fit.laptrinhweb2.model;

public class Banner {

    private int id;
    private String targetUrl;
    private String imageUrl;
    private int displayOrder;
    private boolean isActive;

    public Banner() {
    }

    public Banner(int id, String targetUrl, String imageUrl, int displayOrder, boolean isActive) {
        this.id = id;
        this.targetUrl = targetUrl;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
