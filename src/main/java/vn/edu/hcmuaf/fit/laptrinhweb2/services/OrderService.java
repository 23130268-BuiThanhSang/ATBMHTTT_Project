package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.OrderDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.VerifyDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.VerifyStatus;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.VerifyIfor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

public class OrderService {

    private OrderDao orderDao = new OrderDao();

    private VerifyDao verifyDao = new VerifyDao();
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    public Order getOrder(int id) {
        return orderDao.getById(id);
    }

    public List<Order> getToday() {
        return orderDao.getToday();
    }
    public List<Order> getByFilter(
            Integer day,
            Integer month,
            Integer year,
            String status
    ) {
        return orderDao.getByFilter(day, month, year, status);
    }

    public void updateStatus(int orderId, String status) {
        orderDao.updateStatus(orderId,status);
    }

    public int addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    /**
     * Phương thức này giúp developer tạo đơn hàng sau khi nhấn chọn sản phẩm và mua ở trang cart, để sau này phục cụ cho trang ký điện tử
     * chú ý : ban đầu status sẽ là UNVERIFIED, sau khi admin xác nhận đơn hàng và ký điện tử thành công thì mới update lại status thành VERIFIED, còn nếu ký thất bại thì update thành ERROR, phục vụ cho trang admin để hiển thị trạng thái verify của đơn hàng
     * chú ý: keyId không tạo sau khi verify mới update vào
     * @param order
     * @param verifyInfo
     * @return
     */
    public int  addOrderBeforeSignature(Order order, VerifyIfor verifyInfo) {
        int orderId = orderDao.addOrder(order);
        verifyInfo.setOrderId(orderId);
        verifyDao.addVerify(verifyInfo);
        return orderId;
    }

    public static boolean verifyFile(String algo, byte[] pubKeyBytes, String filePath, String sigFilePath) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(pubKeyBytes);
        KeyFactory kf = KeyFactory.getInstance(algo);
        PublicKey publicKey = kf.generatePublic(spec);

        String signAlgo = "RSA".equals(algo) ? "SHA256withRSA" : "SHA1withDSA";

        Signature verifier = Signature.getInstance(signAlgo);
        verifier.initVerify(publicKey);

        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                verifier.update(buffer, 0, len);
            }
        }

        byte[] signatureBytes = Files.readAllBytes(new File(sigFilePath).toPath());

        return verifier.verify(signatureBytes);
    }

    public static boolean verifyText(String algo, byte[] pubKeyBytes, String inputText, String base64Signature) throws Exception {

        X509EncodedKeySpec spec = new X509EncodedKeySpec(pubKeyBytes);
        KeyFactory kf = KeyFactory.getInstance(algo);
        PublicKey publicKey = kf.generatePublic(spec);

        String signAlgo = "RSA".equals(algo) ? "SHA256withRSA" : "SHA1withDSA";

        Signature verifier = Signature.getInstance(signAlgo);
        verifier.initVerify(publicKey);

        verifier.update(inputText.getBytes(StandardCharsets.UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(base64Signature.trim());

        return verifier.verify(signatureBytes);
    }
}

