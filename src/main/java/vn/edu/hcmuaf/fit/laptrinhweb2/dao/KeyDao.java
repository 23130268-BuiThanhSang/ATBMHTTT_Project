package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

public class KeyDao extends BaseDao {

    /**
     * Thực hiện kiểm tra version, vô hiệu hóa khóa cũ và lưu khóa mới vào hệ thống
     * Chạy trong một Transaction để đảm bảo tính toàn vẹn dữ liệu
     */
    public void saveNewKey(int userId, String newKey) {
        get().useTransaction(handle -> {

            // 1. Tìm version lớn nhất hiện tại của User trong bảng key_info
            Integer maxVersion = handle
                    .createQuery("SELECT MAX(version) FROM key_info WHERE user_id = :userId")
                    .bind("userId", userId)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);

            // Mặc định import lần đầu là version 1, nếu đã có thì tự động tăng +1
            int nextVersion = (maxVersion != null && maxVersion > 0) ? maxVersion + 1 : 1;

            // 2. Chuyển trạng thái khóa cũ 'ACTIVE' thành 'EXPIRED'
            handle.createUpdate(
                            "UPDATE key_info " +
                                    "SET key_status = 'EXPIRED' " +
                                    "WHERE user_id = :userId AND key_status = 'ACTIVE'"
                    )
                    .bind("userId", userId)
                    .execute();

            // 3. Chèn dữ liệu khóa mới vào bảng key_info
            handle.createUpdate(
                            "INSERT INTO key_info (user_id, public_key, version, key_status, enter_date) " +
                                    "VALUES (:userId, :publicKey, :version, 'ACTIVE', NOW())"
                    )
                    .bind("userId", userId)
                    .bind("publicKey", newKey)
                    .bind("version", nextVersion)
                    .execute();
        });
    }

    /**
     * Cập nhật trạng thái báo mất khóa và ghi nhận thời gian khóa bị lộ
     */
    public void reportLostKey(int userId) {
        get().withHandle(handle ->
                handle.createUpdate(
                                "UPDATE key_info " +
                                        "SET key_status = 'COMPROMISED', enter_date = NOW() " + // Cập nhật lại enter_date
                                        "WHERE user_id = :userId AND key_status = 'ACTIVE'"
                        )
                        .bind("userId", userId)
                        .execute()
        );
    }

    public String getActiveKeyByUserId(int userId) {
        return get().withHandle(handle ->
                handle.createQuery("SELECT public_key FROM key_info WHERE user_id = :userId AND key_status = 'ACTIVE' LIMIT 1")
                        .bind("userId", userId)
                        .mapTo(String.class)
                        .findOne()
                        .orElse(null)
        );
    }
}
