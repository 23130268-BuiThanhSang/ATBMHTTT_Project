package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductVariant;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductVariantImage;

import java.util.List;

public class ProductVariantDao extends BaseDao {

    public List<ProductVariant> getAll() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, product_id, color, size, price, stock FROM product_variant")
                        .mapToBean(ProductVariant.class)
                        .list()
        );
    }

    public ProductVariant getById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, product_id, color, size, price, stock FROM product_variant WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(ProductVariant.class)
                        .findFirst()
                        .orElse(null)
        );
    }

    public int addProductVariant(ProductVariant variant) {
        String sql = """
        INSERT INTO product_variant (
            product_id,
            color,
            size,
            price,
            stock
        ) VALUES (
            :productId,
            :color,
            :size,
            :price,
            :stock
        )
    """;

        return get().withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("productId", variant.getProduct_id())
                        .bind("color", variant.getColor())
                        .bind("size", variant.getSize())
                        .bind("price", variant.getPrice())
                        .bind("stock", variant.getStock())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }


    // Helper method to attach images
    public void loadImages(ProductVariant variant) {
        if (variant == null) return;

        List<ProductVariantImage> images =
                get().withHandle(h ->
                        h.createQuery(
                                        "SELECT id, product_id, variant_id AS variantId, image_url AS imageUrl " +
                                                "FROM product_variant_image WHERE variant_id = :variantId"
                                )
                                .bind("variantId", variant.getId())
                                .mapToBean(ProductVariantImage.class)
                                .list()
                );

        variant.setImages(images);
    }

    // Update existing variant
    public void updateVariant(ProductVariant v) {
        String sql = "UPDATE product_variant SET color = :color, size = :size, price = :price, stock = :stock WHERE id = :id";
        get().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("color", v.getColor())
                        .bind("size", v.getSize())
                        .bind("price", v.getPrice())
                        .bind("stock", v.getStock())
                        .bind("id", v.getId())
                        .execute()
        );
    }

    // Delete list of variants (bulk delete)
    public void deleteVariants(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;
        get().useHandle(handle ->
                handle.createUpdate("DELETE FROM product_variant WHERE id IN (<ids>)")
                        .bindList("ids", ids)
                        .execute()
        );
    }
}
