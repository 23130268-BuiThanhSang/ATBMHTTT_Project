package vn.edu.hcmuaf.fit.laptrinhweb2.model;

public class Policy {
    private int id;
    private String name;
    private String content;
    private int displayOrder;
    public Policy() {
    }
    public Policy(int id, String name, String content, int displayOrder) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.displayOrder = displayOrder;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
