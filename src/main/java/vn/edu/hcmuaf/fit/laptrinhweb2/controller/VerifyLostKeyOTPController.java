package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.KeyDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.io.IOException;

@WebServlet("/VerifyLostKeyOTPController")
public class VerifyLostKeyOTPController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Ép kiểu phản hồi luôn là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            String inputOtp = request.getParameter("otp");
            String serverOtp = (String) session.getAttribute("LOST_KEY_OTP");

            Account authUser = (Account) session.getAttribute("auth");
            if (authUser == null) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Phiên đăng nhập hết hạn!\"}");
                return;
            }

            int userId = authUser.getId();

            if (serverOtp != null && serverOtp.equals(inputOtp)) {
                // Thực hiện gọi xuống database cập nhật trạng thái 'COMPROMISED'
                KeyDao keyDao = new KeyDao();
                keyDao.reportLostKey(userId);

                // Đồng bộ lại session với chuỗi rỗng "" để an toàn cho tầng JSP
                authUser.setPublicKey("");
                session.setAttribute("auth", authUser);

                // Dọn dẹp OTP
                session.removeAttribute("LOST_KEY_OTP");

                // Trả về JSON chuẩn tuyệt đối
                response.getWriter().write("{\"status\":\"verified\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Mã OTP xác nhận báo mất không chính xác!\"}");
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi cụ thể ra console của IntelliJ để kiểm tra
            // Tránh văng text thô, bọc message vào JSON
            String safeMessage = e.getMessage() != null ? e.getMessage().replace("\"", "\\\"") : "Lỗi kết nối DB";
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Lỗi hệ thống: " + safeMessage + "\"}");
        }
    }
}