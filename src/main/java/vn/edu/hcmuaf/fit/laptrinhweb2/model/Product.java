package vn.edu.hcmuaf.fit.laptrinhweb2.model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private int id;
    private String name;
    private ProductType productType;
    private ProductGroup brand;
    private ProductGroup sport;
    private ProductGroup collection;
    private String productInfor;
    private String productCareInstruction;
    private String productReturnInfor;
    private String productGender;
    private List<ProductVariant> variants;

    public Product() {
        super();
    }

    public Product(int id, String name, ProductType productType, ProductGroup brand, ProductGroup sport, ProductGroup collection, String productInfor, String productCareInstruction, String productReturnInfor, String productGender, List<ProductVariant> variants) {
        this.id = id;
        this.name = name;
        this.productType = productType;
        this.brand = brand;
        this.sport = sport;
        this.collection = collection;
        this.productInfor = productInfor;
        this.productCareInstruction = productCareInstruction;
        this.productReturnInfor = productReturnInfor;
        this.productGender = productGender;
        this.variants = variants;
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

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ProductGroup getBrand() {
        return brand;
    }

    public void setBrand(ProductGroup brand) {
        this.brand = brand;
    }

    public ProductGroup getSport() {
        return sport;
    }

    public void setSport(ProductGroup sport) {
        this.sport = sport;
    }

    public ProductGroup getCollection() {
        return collection;
    }

    public void setCollection(ProductGroup collection) {
        this.collection = collection;
    }

    public String getProductInfor() {
        return productInfor;
    }

    public void setProductInfor(String productInfor) {
        this.productInfor = productInfor;
    }

    public String getProductCareInstruction() {
        return productCareInstruction;
    }

    public void setProductCareInstruction(String productCareInstruction) {
        this.productCareInstruction = productCareInstruction;
    }

    public String getProductReturnInfor() {
        return productReturnInfor;
    }

    public void setProductReturnInfor(String productReturnInfor) {
        this.productReturnInfor = productReturnInfor;
    }

    public String getProductGender() {
        return productGender;
    }

    public void setProductGender(String productGender) {
        this.productGender = productGender;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }
}