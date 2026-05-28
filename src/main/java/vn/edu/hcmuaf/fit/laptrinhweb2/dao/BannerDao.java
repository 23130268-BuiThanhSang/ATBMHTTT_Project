package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Banner;

import java.util.List;

public class BannerDao extends BaseDao {

    public void addBanner(Banner banner) {
        get().useHandle(handle ->
                handle.createUpdate("""
                INSERT INTO main_banner (target_url, image_url, display_order, is_active)
                VALUES (:targetUrl, :imageUrl, :displayOrder, :isActive)
            """)
                        .bind("targetUrl", banner.getTargetUrl())
                        .bind("imageUrl", banner.getImageUrl())
                        .bind("displayOrder", banner.getDisplayOrder())
                        .bind("isActive", banner.isActive())
                        .execute()
        );
    }

    public List<Banner> getAll() {
        return get().withHandle(handle ->
                handle.createQuery("""
                SELECT id, target_url, image_url, display_order, is_active
                FROM main_banner
                ORDER BY display_order ASC
            """)
                        .map((rs, ctx) -> {
                            Banner b = new Banner();
                            b.setId(rs.getInt("id"));
                            b.setTargetUrl(rs.getString("target_url"));
                            b.setImageUrl(rs.getString("image_url"));
                            b.setDisplayOrder(rs.getInt("display_order"));
                            b.setActive(rs.getBoolean("is_active"));
                            return b;
                        })
                        .list()
        );
    }

    public Banner getById(int id) {
        return get().withHandle(handle ->
                handle.createQuery("""
                SELECT id, target_url, image_url, display_order, is_active
                FROM main_banner
                WHERE id = :id
            """)
                        .bind("id", id)
                        .map((rs, ctx) -> {
                            Banner b = new Banner();
                            b.setId(rs.getInt("id"));
                            b.setTargetUrl(rs.getString("target_url"));
                            b.setImageUrl(rs.getString("image_url"));
                            b.setDisplayOrder(rs.getInt("display_order"));
                            b.setActive(rs.getBoolean("is_active"));
                            return b;
                        })
                        .findOne()
                        .orElse(null)
        );
    }

    /**
     * Lấy về danh sách 5 banner đang hoạt động (is_active = 1) có thứ tự hiển thị cao nhất
     * phực hiện phục vụ cho trang chủ
     * @return
     */
    public List<Banner> getTop5ActiveBanners() {
        return get().withHandle(handle -> {
            String sql = "SELECT * FROM main_banner WHERE is_active = 1 ORDER BY display_order DESC LIMIT 5";

            return handle.createQuery(sql)
                    .map((rs, ctx) -> new Banner(
                            rs.getInt("id"),
                            rs.getString("target_url"),
                            rs.getString("image_url"),
                            rs.getInt("display_order"),
                            rs.getBoolean("is_active")
                    ))
                    .list();
        });
    }




    public boolean update(Banner banner) {
        return get().withHandle(handle ->
                handle.createUpdate("""
                UPDATE main_banner
                SET target_url   = :targetUrl,
                    image_url    = :imageUrl,
                    display_order = :displayOrder,
                    is_active    = :isActive
                WHERE id = :id
            """)
                        .bind("id", banner.getId())
                        .bind("targetUrl", banner.getTargetUrl())
                        .bind("imageUrl", banner.getImageUrl())
                        .bind("displayOrder", banner.getDisplayOrder())
                        .bind("isActive", banner.isActive())
                        .execute()
        ) > 0;
    }


    public boolean deleteById(int id) {
        return get().withHandle(handle ->
                handle.createUpdate("""
                DELETE FROM main_banner
                WHERE id = :id
            """)
                        .bind("id", id)
                        .execute()
        ) > 0;
    }

}
