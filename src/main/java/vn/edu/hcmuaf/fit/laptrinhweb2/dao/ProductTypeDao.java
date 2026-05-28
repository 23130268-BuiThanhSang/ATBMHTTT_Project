package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Policy;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductType;

import java.util.List;

public class ProductTypeDao extends  BaseDao  {
    public List<ProductType> getAll() {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM product_type")
                        .mapToBean(ProductType.class)
                        .list()
        );
    }
    public ProductType getById(int id) {
        return get().withHandle(h -> h.createQuery("select * from product_type where id = :id")
                .bind("id", id)
                .mapToBean(ProductType.class).stream().findFirst().orElse(null));
    }
}
