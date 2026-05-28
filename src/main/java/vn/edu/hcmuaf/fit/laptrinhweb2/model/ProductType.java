package vn.edu.hcmuaf.fit.laptrinhweb2.model;

public class ProductType {
    private int id;
    private String name;
    public ProductType() {
        super();
    }
    public ProductType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
}
