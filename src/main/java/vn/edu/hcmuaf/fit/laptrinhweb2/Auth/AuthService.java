package vn.edu.hcmuaf.fit.laptrinhweb2.Auth;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

public class AuthService {
    AuthDao authDao = new AuthDao();

    public Account login(String username, String pass) {
        // mã hóa mật khẩu người dùng nhập vào
        String hashedInput = PasswordUtils.md5(pass);

        // Lấy account theo username
        Account acc = authDao.getUserByUsername(username);

        // So sánh hash input với hash trong DB
        if (acc != null && acc.getPassword().equals(hashedInput)) {
            acc.setPassword(null); // không cho lộ password trong session
            return acc;
        } else  {
            return null;
        }
    }
}
