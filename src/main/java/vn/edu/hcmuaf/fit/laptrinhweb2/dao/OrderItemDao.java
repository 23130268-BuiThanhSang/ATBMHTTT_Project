package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.OrderItem;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Product;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductVariant;

import java.util.List;

public class OrderItemDao extends BaseDao {

    private final ProductDao productDao = new ProductDao();
    private final ProductVariantDao productVariantDao = new ProductVariantDao();
    public List<OrderItem> getByOrderId(int orderId) {
        return get().withHandle(handle ->
                handle.createQuery("SELECT * FROM order_detail WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .map((rs, ctx) -> {
                            OrderItem item = new OrderItem();
                            item.setId(rs.getInt("id"));
                            item.setOrderId(rs.getInt("order_id"));

                            int variantId = rs.getInt("variant_id");
                            ProductVariant productVariant = productVariantDao.getById(variantId);
                            Product product = productDao.getProduct(productVariant.getProduct_id());
                            item.setProduct(product);
                            item.setVariant(productVariant);
                            item.setQuantity(rs.getInt("quantity"));
                            item.setPrice(rs.getDouble("price"));

                            return item;
                        })
                        .list()
        );
    }

    public int insert(OrderItem item) {
        return get().withHandle(h ->
                h.createUpdate("""
                INSERT INTO order_detail (order_id, variant_id, price, quantity)
                VALUES (:orderId, :variantId, :price, :quantity)
            """)
                        .bind("orderId", item.getOrderId())
                        .bind("variantId", item.getVariant().getId())
                        .bind("price", item.getPrice())
                        .bind("quantity", item.getQuantity())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }
}
