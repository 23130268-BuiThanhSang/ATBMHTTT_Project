package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductGroup;

import java.util.List;

public class ProductGroupDao extends BaseDao {

    public void addGroup(
            GroupType type,
            String name,
            String thumbnailUrl,
            String imageUrl,
            int displayOrder
    ) {
        String sql = getInsertSql(type);

        get().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("name", name)
                        .bind("thumbnail_image_url", thumbnailUrl)
                        .bind("main_image_url", imageUrl)
                        .bind("display_order", displayOrder)
                        .execute()
        );
    }

    public List<ProductGroup> getGroups(GroupType type) {
        String sql = getSelectSql(type);

        return get().withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(ProductGroup.class)
                        .list()
        );
    }

    public ProductGroup getProductGroup(GroupType type, int id) {
        String sql = getSelectByIdSql(type);

        return get().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(ProductGroup.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public void updateGroup(
            GroupType type,
            int id,
            String name,
            String thumbnailUrl,
            String imageUrl,
            int displayOrder
    ) {
        String sql = getUpdateSql(type);

        get().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", id)
                        .bind("name", name)
                        .bind("thumbnail_image_url", thumbnailUrl)
                        .bind("main_image_url", imageUrl)
                        .bind("display_order", displayOrder)
                        .execute()
        );
    }

    public void deleteGroup(GroupType type, int id) {
        String sql = getDeleteSql(type);

        get().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", id)
                        .execute()
        );
    }

    // Sup method

    private String getTableName(GroupType type) {
        switch (type) {
            case BRAND:
                return "brand";
            case COLLECTION:
                return "collection";
            case SPORT:
                return "sport";
            default:
                throw new IllegalArgumentException("Invalid group type");
        }
    }

    private String getInsertSql(GroupType type) {
        String table = getTableName(type);
        return "INSERT INTO " + table + " (name, thumbnail_image_url, main_image_url, display_order) " +
                "VALUES (:name, :thumbnail_image_url, :main_image_url, :display_order)";
    }

    private String getSelectSql(GroupType type) {
        String table = getTableName(type);
        // Use aliases to match model property names
        return "SELECT id, " +
                "name, " +
                "thumbnail_image_url AS thumbnailUrl, " +
                "main_image_url AS imageUrl, " +
                "display_order AS displayOrder " +
                "FROM " + table + " ORDER BY display_order";
    }

    private String getSelectByIdSql(GroupType type) {
        String table = getTableName(type);
        // Use aliases to match model property names
        return "SELECT id, " +
                "name, " +
                "thumbnail_image_url AS thumbnailUrl, " +
                "main_image_url AS imageUrl, " +
                "display_order AS displayOrder " +
                "FROM " + table + " WHERE id = :id";
    }

    private String getUpdateSql(GroupType type) {
        String table = getTableName(type);
        return "UPDATE " + table + " SET " +
                "name = :name, " +
                "thumbnail_image_url = :thumbnail_image_url, " +
                "main_image_url = :main_image_url, " +
                "display_order = :display_order " +
                "WHERE id = :id";
    }

    private String getDeleteSql(GroupType type) {
        String table = getTableName(type);
        return "DELETE FROM " + table + " WHERE id = :id";
    }
    /**
     * Lấy danh sách nhóm sản phẩm (thương hiệu hoặc bộ sưu tập) có display order cao nhất để hiển thị trên trang chủ
     * @param type
     * @return
     */
    public List<ProductGroup> getTopProductGroups(GroupType type) {
        switch (type) {
            case BRAND:
                return get().withHandle(h ->
                        h.createQuery("""
                        SELECT 
                            id, 
                            name, 
                            thumbnail_image_url AS thumbnailUrl,  
                            main_image_url      AS imageUrl,      
                            display_order       AS displayOrder  
                        FROM brand 
                        ORDER BY display_order DESC 
                        LIMIT 12
                    """)
                                .mapToBean(ProductGroup.class)
                                .list()
                );

            case COLLECTION:
                return get().withHandle(h ->
                        h.createQuery("""
                        SELECT 
                            id, 
                            name, 
                            thumbnail_image_url AS thumbnailUrl, 
                            main_image_url      AS imageUrl, 
                            display_order       AS displayOrder 
                        FROM collection 
                        ORDER BY display_order DESC 
                        LIMIT 10
                    """)
                                .mapToBean(ProductGroup.class)
                                .list()
                );

            default:
                throw new IllegalArgumentException("Invalid group type");
        }
    }
}
