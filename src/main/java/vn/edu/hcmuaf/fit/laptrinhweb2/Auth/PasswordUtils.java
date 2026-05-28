package vn.edu.hcmuaf.fit.laptrinhweb2.Auth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    // mã hóa chuỗi thành MD5 (hex 32 ký tự)
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashText = number.toString(16);
            // thêm 0 phía trước cho đủ 32 ký tự
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Tăng bảo mật mật khẩu
    public static boolean isStrongPassword(String password) {
        // ít nhất 8 ký tự, 1 thường, 1 HOA, 1 số, 1 ký tự đặc biệt
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^()_+=\\-])[A-Za-z\\d@$!%*?&#^()_+=\\-]{8,}$";
        return password != null && password.matches(regex);
    }
}
