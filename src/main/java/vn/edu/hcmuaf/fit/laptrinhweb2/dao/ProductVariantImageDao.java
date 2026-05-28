package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductVariantImage;

import java.util.List;

public class ProductVariantImageDao extends BaseDao {

    public List<ProductVariantImage> getAll() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, variant_id AS variantId, image_url AS imageUrl FROM product_variant_image")
                        .mapToBean(ProductVariantImage.class)
                        .list()
        );
    }
    public ProductVariantImage getById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, variant_id AS variantId, image_url AS imageUrl FROM product_variant_image WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(ProductVariantImage.class)
                        .stream()
                        .findFirst()
                        .orElse(null)
        );
    }
    public List<ProductVariantImage> getByVariantId(int variantId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, variant_id AS variantId, image_url AS imageUrl FROM product_variant_image WHERE variant_id = :variantId")
                        .bind("variantId", variantId)
                        .mapToBean(ProductVariantImage.class)
                        .list()
        );
    }

    public int insert(ProductVariantImage image) {
        String sql = """
        INSERT INTO product_variant_image (variant_id, image_url)
        VALUES (:variantId, :imageUrl)
    """;

        return get().withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("variantId", image.getVariantId())
                        .bind("imageUrl", image.getImageUrl())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }
    public void deleteImages(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;
        get().useHandle(handle ->
                handle.createUpdate("DELETE FROM product_variant_image WHERE id IN (<ids>)")
                        .bindList("ids", ids)
                        .execute()
        );
    }
}