package vn.edu.hcmuaf.fit.laptrinhweb2.model;

public class Image {
    private int id;
    private String image_url;
    public Image() {
    }
    public Image(int id, String image_url) {
        this.id = id;
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
