package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.KeyDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.io.IOException;

@WebServlet("/VerifyKeyOTPController")
public class VerifyKeyOTPController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            String inputOtp = request.getParameter("otp");
            String serverOtp = (String) session.getAttribute("KEY_UPDATE_OTP");
            String newKey = (String) session.getAttribute("TEMP_KEY");

            Account authUser = (Account) session.getAttribute("auth");

            // Chặn đứng lỗi NullPointerException khi lấy UserId
            if (authUser == null) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Phiên đăng nhập hết hạn, vui lòng F5 làm mới và đăng nhập lại.\"}");
                return;
            }
            int userId = authUser.getId();

            if (serverOtp != null && serverOtp.equals(inputOtp)) {
                if (newKey == null || newKey.isEmpty()) {
                    response.getWriter().write("{\"status\":\"error\", \"message\":\"Không tìm thấy dữ liệu khóa tạm thời trong phiên làm việc!\"}");
                    return;
                }

                KeyDao keyDao = new KeyDao();
                keyDao.saveNewKey(userId, newKey);

                // Đồng bộ cập nhật thẳng vào session thực tế đang chạy
                authUser.setPublicKey(newKey);
                session.setAttribute("auth", authUser);

                // Dọn dẹp session rác
                session.removeAttribute("KEY_UPDATE_OTP");
                session.removeAttribute("TEMP_KEY");

                response.getWriter().write("{\"status\":\"verified\", \"maskedKey\": \"" + maskKey(newKey) + "\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Mã OTP nhập vào không chính xác!\"}");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi nhận log SQL cụ thể nếu phát sinh lỗi kết nối dữ liệu
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Lỗi xử lý lưu cơ sở dữ liệu: " + e.getMessage() + "\"}");
        }
    }

    private String maskKey(String key) {
        if (key == null || key.length() < 6) {
            return "***";
        }
        // Loại bỏ ký tự xuống dòng nếu có để tránh vỡ chuỗi JSON trả về
        String cleanKey = key.replace("\n", "").replace("\r", "").trim();
        return cleanKey.substring(0, 3) + "***" + cleanKey.substring(cleanKey.length() - 3);
    }
}