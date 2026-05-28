package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.HotDeal;

import java.util.List;

public class HotDealDao extends BaseDao {
    /**
     * thực hiện phương thức lấy HotDeal có display_order cao nhất nhằm phục vụ main Hotdeal ơn trang chủ
     * phương thức quan trọng
     * @return
     */
    public HotDeal getHotDealWithHighestDisplayOrder() {
        String query = "SELECT id, name, thumbnail_image_url, main_image_url, display_order, discount_per " +
                "FROM hot_deal " +
                "ORDER BY display_order DESC LIMIT 1";

        return get().withHandle(handle -> {
            return handle.createQuery(query)
                    .map((rs, ctx) -> {
                        return new HotDeal(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("thumbnail_image_url"),
                                rs.getString("main_image_url"),
                                rs.getInt("display_order"),
                                rs.getDouble("discount_per")
                        );
                    })
                    .findFirst()
                    .orElse(null);
        });
    }

    /**
     * thực hiện phương thức lấy về 2 hotdeal phụ có display_order cao thứ 2 và 3 nhằm phục vụ cho trang chủ
     * phương thức quan trọng
     * @return
     */
    public List<HotDeal> getSubHotDeal() {
        String query = "SELECT id, name, thumbnail_image_url, main_image_url, display_order, discount_per " +
                "FROM hot_deal " +
                "ORDER BY display_order DESC LIMIT 2 OFFSET 1";

        return get().withHandle(handle -> {
            return handle.createQuery(query)
                    .map((rs, ctx) -> {
                        return new HotDeal(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("thumbnail_image_url"),
                                rs.getString("main_image_url"),
                                rs.getInt("display_order"),
                                rs.getDouble("discount_per")
                        );
                    })
                    .list();
        });
    }
    public List<HotDeal> getAllHotDeals() {
        String query = """
        SELECT id, name, thumbnail_image_url, main_image_url, display_order, discount_per
        FROM hot_deal
        ORDER BY display_order DESC
    """;

        return get().withHandle(handle ->
                handle.createQuery(query)
                        .map((rs, ctx) -> new HotDeal(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("thumbnail_image_url"),
                                rs.getString("main_image_url"),
                                rs.getInt("display_order"),
                                rs.getDouble("discount_per")
                        ))
                        .list()
        );
    }
    public boolean updateHotDeal(HotDeal hotDeal) {
        String query = """
        UPDATE hot_deal
        SET name = :name,
            thumbnail_image_url = COALESCE(:thumbnailUrl, thumbnail_image_url),
            main_image_url = COALESCE(:mainImageUrl, main_image_url),
            display_order = :displayOrder,
            discount_per = :discountPer
        WHERE id = :id
    """;

        return get().withHandle(handle ->
                handle.createUpdate(query)
                        .bind("id", hotDeal.getId())
                        .bind("name", hotDeal.getName())
                        .bind("thumbnailUrl", hotDeal.getThumbnailUrl())
                        .bind("mainImageUrl", hotDeal.getMainImageUrl())
                        .bind("displayOrder", hotDeal.getDisplayOrder())
                        .bind("discountPer", hotDeal.getDiscountPercentage())
                        .execute() > 0
        );
    }
    public boolean deleteHotDeal(int id) {
        String query = "DELETE FROM hot_deal WHERE id = :id";

        return get().withHandle(handle ->
                handle.createUpdate(query)
                        .bind("id", id)
                        .execute() > 0
        );
    }

    public HotDeal getHotDealById(int id) {
        String query = """
        SELECT id, name, thumbnail_image_url, main_image_url, display_order, discount_per
        FROM hot_deal
        WHERE id = :id
    """;

        return get().withHandle(handle ->
                handle.createQuery(query)
                        .bind("id", id)
                        .map((rs, ctx) -> new HotDeal(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("thumbnail_image_url"),
                                rs.getString("main_image_url"),
                                rs.getInt("display_order"),
                                rs.getDouble("discount_per")
                        ))
                        .findOne()
                        .orElse(null)
        );
    }

    public int addHotDeal(HotDeal hotDeal) {
        String query = """
        INSERT INTO hot_deal (name, thumbnail_image_url, main_image_url, display_order, discount_per)
        VALUES (:name, :thumbnailUrl, :mainImageUrl, :displayOrder, :discountPer)
    """;

        return get().withHandle(handle ->
                handle.createUpdate(query)
                        .bind("name", hotDeal.getName())
                        .bind("thumbnailUrl", hotDeal.getThumbnailUrl())
                        .bind("mainImageUrl", hotDeal.getMainImageUrl())
                        .bind("displayOrder", hotDeal.getDisplayOrder())
                        .bind("discountPer", hotDeal.getDiscountPercentage())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }

}
