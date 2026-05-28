package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductTypeDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductVariantDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductVariantImageDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    ProductDao productDao = new ProductDao();
    ProductVariantDao productVariantDao = new ProductVariantDao();
    ProductVariantImageDao productVariantImageDao = new ProductVariantImageDao();
    public Product getProduct(int id) {
        return productDao.getProduct(id);
    }

    public boolean addProduct(Product product) {
            int product_id = productDao.insertProduct(product);
            List<ProductVariant> variants = product.getVariants();
            for (ProductVariant variant : variants) {
                System.out.println("insert variant trigger");
                variant.setProduct_id(product_id);
                int variant_id = productVariantDao.addProductVariant(variant);
                System.out.println(variant_id);

                List<ProductVariantImage> images = variant.getImages();
                for (ProductVariantImage productVariantImage : images) {
                    System.out.println("insert image trigger");
                    productVariantImage.setVariantId(variant_id);
                    productVariantImageDao.insert(productVariantImage);
                }
            }
        return true;
    }

    public List<ProductReview> getReviews(int id, Integer rating) {
        return productDao.getReviewsByProduct(id, rating);
    }


    public int countReviews(int id) {
        return productDao.countReviewsByProduct(id);
    }

    public Map<Integer, String> buildSizeMap(Product product) {
        Map<Integer, String> sizeMap = new HashMap<>();
        product.getVariants().forEach(v -> {
            sizeMap.put(v.getSize(), getSizeText(v.getSize()));
        });
        return sizeMap;
    }

    public String getSizeText(int sizeID) {
        switch (sizeID) {
            case 1: return "S";
            case 2: return "M";
            case 3: return "L";
            case 4: return "XL";
            case 5: return "XXL";

            case 38: return "38";
            case 39: return "39";
            case 40: return "40";
            case 41: return "41";
            case 42: return "42";
            case 43: return "43";
        }

        if (sizeID >= 100) {
            if (sizeID % 100 == 0) {
                return (sizeID / 100) + "KG";
            } else {
                return (sizeID / 100.0) + "KG";
            }
        }
        return String.valueOf(sizeID);
    }

    public Map<String, Object> buildRatingInfo(int productId) {
        Map<String, Object> map = new HashMap<>();

        double avgRating = productDao.getAverageRatingByProduct(productId);
        int fullStars = (int) avgRating;
        boolean hasHalfStar = (avgRating - fullStars) >0;

        map.put("avgRating", String.format("%.1f", avgRating));
        map.put("fullStars", fullStars);
        map.put("hasHalfStar", hasHalfStar);

        return map;
    }
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }
    public ProductVariant getVariantById(int variantId) {
        return productVariantDao.getById(variantId);
    }
    public void insertVariant(ProductVariant variant) {
        int variant_id = productVariantDao.addProductVariant(variant);
        System.out.println(variant_id);

        List<ProductVariantImage> images = variant.getImages();
        for (ProductVariantImage productVariantImage : images) {
            System.out.println("insert image trigger");
            productVariantImage.setVariantId(variant_id);
            productVariantImageDao.insert(productVariantImage);
        }
    }
    public void insertImage(ProductVariantImage productVariantImage) {
        productVariantImageDao.insert(productVariantImage);
    }

    public ProductType getProductType(int id) {
        ProductTypeDao productTypeDao = new ProductTypeDao();
        return productTypeDao.getById(id);
    }
    public void updateVariant(ProductVariant variant) {
        productVariantDao.updateVariant(variant);
    }
    public void deleteVariantImage(List<Integer> ids) {
        productVariantImageDao.deleteImages(ids);
    }
    public void removeCollectionFromProducts(int id) {
        productDao.removeCollectionFromProducts(id);
    }
    public void removeHotDealFromProducts(int id) {
        productDao.removeHotDealFromProducts(id);
    }
}
