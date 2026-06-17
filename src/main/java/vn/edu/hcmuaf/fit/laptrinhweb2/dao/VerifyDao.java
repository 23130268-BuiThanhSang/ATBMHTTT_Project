package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.VerifyStatus;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.VerifyIfor;

public class VerifyDao extends BaseDao {

    /**
     * Thêm bản ghi mới vào bảng verify và trả về id của bản ghi vừa thêm phục vụ cho 1 số phương thức bên trang admin
     * @param v
     * @return
     */
    public int addVerify(VerifyIfor v) {
        return get().withHandle(handle ->
                handle.createUpdate(
                                "INSERT INTO verify_ifo (verify_status, key_id, order_id, date_verify) " +
                                        "VALUES (:verifyStatus, :keyId, :orderId, CURRENT_TIMESTAMP)"
                        )
                        .bind("verifyStatus", v.getVerifyStatus().name())
                        .bind("keyId", v.getKeyId())
                        .bind("orderId", v.getOrderId())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    /**
     * Lấy thông tin verify theo orderId, phục vụ cho trang admin để hiển thị trạng thái verify của đơn hàng
     * @param orderId
     * @return
     */
    public VerifyIfor getByOrderId(int orderId) {
        return get().withHandle(handle ->
                handle.createQuery("SELECT * FROM verify_ifo WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .mapToBean(VerifyIfor.class)
                        .findOne()
                        .orElse(null)
        );
    }

    /**
     * Cập nhật trạng thái verify của đơn hàng theo orderId, phục vụ cho trang admin để cập nhật trạng thái verify sau khi admin đã kiểm tra và xác nhận đơn hàng
     * @param orderId
     * @param status
     */
    public void updateVerifyStatusByOrderId(int orderId, VerifyStatus status, Integer keyId, String signature) {
        get().withHandle(handle ->
                handle.createUpdate(
                                "UPDATE verify_ifo " +
                                        "SET verify_status = :status, key_id = :keyId, signature = :signature, date_verify = CURRENT_TIMESTAMP " +
                                        "WHERE order_id = :orderId"
                        )
                        .bind("status", status.name()) // Use .name() to bind Enum securely as a String
                        .bind("orderId", orderId)
                        .bind("keyId", keyId)
                        .bind("signature", signature)
                        .execute()
        );
    }
}