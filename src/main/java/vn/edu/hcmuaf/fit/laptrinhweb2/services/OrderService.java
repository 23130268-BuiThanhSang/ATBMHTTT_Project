package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.OrderDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;

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

    public void addOrder(Order order) {
        orderDao.addOrder(order);
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

