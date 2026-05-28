package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.AccountStatus;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Announcement;

import java.sql.Timestamp;
import java.util.List;

public class AnnouncementDao extends BaseDao {

    public void insert(Announcement announcement) {

        String sql = """
            INSERT INTO announcements
            (customer_type, content, target_url, is_active, expires_at)
            VALUES (:customerType, :content, :targetUrl, :isActive, :expiresAt)
        """;

        get().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("customerType", announcement.getAccountStatus().toString())
                        .bind("content", announcement.getContent())
                        .bind("targetUrl", announcement.getTargetUrl())
                        .bind("isActive", announcement.isActive())
                        .bind(
                                "expiresAt",
                                announcement.getExpiresAt() == null
                                        ? null
                                        : Timestamp.valueOf(announcement.getExpiresAt())
                        )
                        .execute()
        );
    }

    public List<Announcement> getAll() {

        String sql = """
            SELECT *
            FROM announcements
            ORDER BY created_at DESC
        """;

        return get().withHandle(handle ->
                handle.createQuery(sql)
                        .map((rs, ctx) -> {
                            Announcement a = new Announcement();
                            a.setId(rs.getInt("id"));
                            a.setAccountStatus(
                                    AccountStatus.valueOf(rs.getString("customer_type"))
                            );
                            a.setContent(rs.getString("content"));
                            a.setTargetUrl(rs.getString("target_url"));
                            a.setActive(rs.getBoolean("is_active"));
                            a.setCreatedAt(
                                    rs.getTimestamp("created_at").toLocalDateTime()
                            );

                            Timestamp exp = rs.getTimestamp("expires_at");
                            if (exp != null) {
                                a.setExpiresAt(exp.toLocalDateTime());
                            }
                            return a;
                        })
                        .list()
        );
    }

    public void update(Announcement announcement) {

        String sql = """
            UPDATE announcements
            SET customer_type = :customerType,
                content = :content,
                target_url = :targetUrl,
                is_active = :isActive,
                expires_at = :expiresAt
            WHERE id = :id
        """;

        get().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", announcement.getId())
                        .bind("customerType", announcement.getAccountStatus().toString())
                        .bind("content", announcement.getContent())
                        .bind("targetUrl", announcement.getTargetUrl())
                        .bind("isActive", announcement.isActive())
                        .bind(
                                "expiresAt",
                                announcement.getExpiresAt() == null
                                        ? null
                                        : Timestamp.valueOf(announcement.getExpiresAt())
                        )
                        .execute()
        );
    }

    public void delete(int id) {
        String sql = """
            DELETE FROM announcements
            WHERE id = :id
        """;

        get().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", id)
                        .execute()
        );
    }
}
