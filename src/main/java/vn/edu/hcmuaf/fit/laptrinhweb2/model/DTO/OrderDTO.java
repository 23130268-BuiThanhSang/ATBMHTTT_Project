package vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.VerifyStatus;
import java.sql.Timestamp;

public class OrderDTO {
    private Order order;
    private VerifyStatus verifyStatus;
    private Integer keyId;
    private String signature;
    private Timestamp dateVerify;

    public OrderDTO(Order order, VerifyStatus verifyStatus, Integer keyId, String signature, Timestamp dateVerify) {
        this.order = order;
        this.verifyStatus = verifyStatus;
        this.keyId = keyId;
        this.signature = signature;
        this.dateVerify = dateVerify;
    }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public VerifyStatus getVerifyStatus() { return verifyStatus; }
    public void setVerifyStatus(VerifyStatus verifyStatus) { this.verifyStatus = verifyStatus; }

    public Integer getKeyId() { return keyId; }
    public void setKeyId(Integer keyId) { this.keyId = keyId; }

    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }

    public Timestamp getDateVerify() { return dateVerify; }
    public void setDateVerify(Timestamp dateVerify) { this.dateVerify = dateVerify; }
}