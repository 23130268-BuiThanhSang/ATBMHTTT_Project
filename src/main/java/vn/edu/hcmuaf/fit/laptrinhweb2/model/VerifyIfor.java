package vn.edu.hcmuaf.fit.laptrinhweb2.model;

import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.VerifyStatus;
import java.sql.Timestamp;

public class VerifyIfor {
    private int id;
    private VerifyStatus verifyStatus;
    private Integer keyId;
    private int orderId;
    private Timestamp dateVerify;

    public VerifyIfor() {
    }

    public VerifyIfor(int id, VerifyStatus verifyStatus, Integer keyId, int orderId, Timestamp dateVerify) {
        this.id = id;
        this.verifyStatus = verifyStatus;
        this.keyId = keyId;
        this.orderId = orderId;
        this.dateVerify = dateVerify;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public VerifyStatus getVerifyStatus() { return verifyStatus; }
    public void setVerifyStatus(VerifyStatus verifyStatus) { this.verifyStatus = verifyStatus; }

    public Integer getKeyId() { return keyId; }
    public void setKeyId(Integer keyId) { this.keyId = keyId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public Timestamp getDateVerify() { return dateVerify; }
    public void setDateVerify(Timestamp dateVerify) { this.dateVerify = dateVerify; }
}